package wtf.moonlight.flym.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import wtf.moonlight.flym.repositories.EntriesRepository
import javax.inject.Inject

@HiltViewModel
class EntriesViewModel @Inject constructor(
    private val entriesRepository: EntriesRepository
) : ViewModel() {
    val entries
        get() = entriesRepository.getAllEntries()

    fun setFavorite(feedId: Long, entryId: String, favorite: Boolean) {
        viewModelScope.launch {
            entriesRepository.setFavoriteStatus(feedId, entryId, favorite)
        }
    }
}