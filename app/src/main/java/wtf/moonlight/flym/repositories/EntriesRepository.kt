package wtf.moonlight.flym.repositories

import androidx.lifecycle.LiveData
import wtf.moonlight.flym.data.model.EntryCardData

interface EntriesRepository {
    fun getAllEntries(): LiveData<List<EntryCardData>>
}