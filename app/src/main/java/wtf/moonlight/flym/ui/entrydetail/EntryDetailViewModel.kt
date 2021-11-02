package wtf.moonlight.flym.ui.entrydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import wtf.moonlight.flym.data.model.EntryWithFeed
import wtf.moonlight.flym.repositories.EntriesRepository
import javax.inject.Inject

@HiltViewModel
class EntryDetailViewModel @Inject constructor(
    private val entriesRepository: EntriesRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    val entry: LiveData<EntryWithFeed?>
        get() = MutableLiveData(null)
}