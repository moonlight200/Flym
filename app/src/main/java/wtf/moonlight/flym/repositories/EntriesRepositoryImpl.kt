package wtf.moonlight.flym.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import wtf.moonlight.flym.data.EntryDao
import wtf.moonlight.flym.data.model.EntryCardData
import javax.inject.Inject

class EntriesRepositoryImpl @Inject constructor(
    private val entriesDao: EntryDao
) : EntriesRepository {
    override fun getAllEntries(): LiveData<List<EntryCardData>> =
        Transformations.map(
            entriesDao.observeAllEntries()
        ) { entryList ->
            entryList.map { entryWithFeed -> entryWithFeed.toCardData() }
        }
}