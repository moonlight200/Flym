package wtf.moonlight.flym.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import wtf.moonlight.flym.repositories.EntriesRepository
import javax.inject.Inject

@HiltViewModel
class EntriesViewModel @Inject constructor(
    private val entriesRepository: EntriesRepository
) : ViewModel() {
    val entries
        get() = entriesRepository.getAllEntries()
}