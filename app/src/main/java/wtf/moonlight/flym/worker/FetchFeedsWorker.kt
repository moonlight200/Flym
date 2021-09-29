package wtf.moonlight.flym.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import wtf.moonlight.flym.data.EntryDao
import wtf.moonlight.flym.data.FeedDao
import wtf.moonlight.flym.data.model.Entry
import wtf.moonlight.flym.data.model.Feed
import wtf.moonlight.flym.extensions.HtmlUtils
import wtf.moonlight.flym.extensions.toBaseUrl
import wtf.moonlight.flym.extensions.toFlymEntry
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@HiltWorker
class FetchFeedsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val feedDao: FeedDao,
    private val entryDao: EntryDao,
    private val httpClient: OkHttpClient
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        for (feed in feedDao.getAllFeeds()) {
            if (isStopped) {
                return Result.retry()
            }

            fetchFeed(feed)
        }

        return Result.success()
    }

    private fun fetchFeed(feed: Feed) {
        loadAndParseFeed(feed)
            .onFailure {
                // TODO process loading/parsing failure
                Timber.e(it, "Failed to refresh feed (id: ${feed.id}, url: ${feed.link})")
            }
            .onSuccess { (updatedFeed, entries) ->
                filterEntries(updatedFeed, entries)

                val improvedEntries = entries.map { improveEntryContent(it) }
                entryDao.insertAll(improvedEntries)
                feedDao.updateFeed(updatedFeed)
                // TODO start downloading images (feed + new entries)
            }
    }

    private fun loadAndParseFeed(feed: Feed): kotlin.Result<Pair<Feed, MutableList<Entry>>> {
        try {
            val response = httpClient.newCall(
                Request.Builder()
                    .url(feed.link)
                    .build()
            ).execute()
            val syndFeed = response.use {
                val input = SyndFeedInput()
                input.build(XmlReader(it.body?.byteStream()))
            }

            val updatedFeed = feed.copy(
                title = syndFeed.title ?: feed.title,
                imageLink = syndFeed.image?.link ?: feed.imageLink
            )

            val fetchDate = Date()
            val entries = syndFeed.entries?.asSequence()
                ?.map { it.toFlymEntry(feed, fetchDate) }
                ?.toMutableList()
                ?: ArrayList()

            return kotlin.Result.success(updatedFeed to entries)
        } catch (exception: Exception) {
            return kotlin.Result.failure(exception)
        }
    }

    private fun filterEntries(feed: Feed, entries: MutableList<Entry>) {
        val entryIdsToFilter: MutableSet<String> = HashSet()

        // Filter entries that are already in the database
        entryIdsToFilter.addAll(entryDao.getEntryIdsInFeed(feed.id))
        // Remove duplicate entries by title
        entryIdsToFilter.addAll(entryDao.findEntryIdsByTitle(entries.mapNotNull { it.title }))

        entries.removeAll { it.id in entryIdsToFilter }
    }

    private fun improveEntryContent(entry: Entry): Entry {
        val improvedContent = entry.description?.let {
            val baseUrl = entry.link?.toBaseUrl() ?: ""
            HtmlUtils.improveHtmlContent(it, baseUrl)
        }
        return entry.copy(
            title = entry.title?.replace("\n", " ")?.trim(),
            description = improvedContent,
            imageLink = entry.imageLink ?: improvedContent?.let {
                HtmlUtils.getMainImageUrl(it)
            }
        )
    }
}