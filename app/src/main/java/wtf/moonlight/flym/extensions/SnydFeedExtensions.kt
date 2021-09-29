package wtf.moonlight.flym.extensions

import androidx.core.text.HtmlCompat
import com.rometools.rome.feed.synd.SyndEntry
import okio.ByteString
import wtf.moonlight.flym.data.model.Entry
import wtf.moonlight.flym.data.model.Feed
import java.util.*

fun SyndEntry.toFlymEntry(feed: Feed, fetchDate: Date = Date()): Entry {
    // Find enclosure image, alternatively use an image from the content
    val imageLink: String? = enclosures?.find { it.type?.contains("image") == true }?.url
        ?: foreignMarkup?.find { it.namespace?.prefix == "media" && it.name == "content" }
            ?.attributes?.find { it.name == "url" }?.value

    return Entry(
        id = "${feed.id}_$id",
        feedId = feed.id,
        link = link,
        title = title?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() },
        description = contents?.let { contents ->
            if (contents.isNotEmpty())
                contents.joinToString(separator = "<hr/>") { content -> content.value }
            else
                description?.value
        },
        author = author,
        imageLink = imageLink,
        fetchDate = fetchDate,
        publicationDate = publishedDate ?: fetchDate
    )
}

val SyndEntry.id: String
    get() = ByteString.of(
        *(link ?: uri ?: title ?: UUID.randomUUID().toString()).toByteArray()
    ).sha1().hex()