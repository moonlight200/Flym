package wtf.moonlight.flym.ui

import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import net.fred.feedex.R
import timber.log.Timber
import wtf.moonlight.flym.data.model.EntryCardData
import wtf.moonlight.flym.ui.compose.EntryCard
import wtf.moonlight.flym.ui.theme.FlymTheme
import java.util.*

@AndroidEntryPoint
class FlymActivity : ComponentActivity() {
    private val viewModel: EntriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlymTheme {
                val entries by viewModel.entries.observeAsState(emptyList())

                Scaffold { innerPadding ->
                    EntryList(
                        entries = entries,
                        contentPadding = innerPadding,
                        formatPublication = this::formatPublicationDate,
                        onEntryClicked = { feedId, entryId ->
                            // TODO navigate to feed
                            Timber.d("Navigating to %d, %s", feedId, entryId)
                        },
                        onFavoriteChanged = { feedId, entryId, favorite ->
                            viewModel.setFavorite(feedId, entryId, favorite)
                        }
                    )
                }
            }
        }
    }

    private fun formatPublicationDate(date: Date): String = buildString {
        if (!DateUtils.isToday(date.time)) {
            append(DateFormat.getMediumDateFormat(this@FlymActivity).format(date))
            append(' ')
        }
        append(DateFormat.getTimeFormat(this@FlymActivity).format(date))
    }
}

@Composable
fun EntryList(
    entries: List<EntryCardData>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    formatPublication: (Date) -> String = { date -> date.toString() },
    onEntryClicked: (feedId: Long, entryId: String) -> Unit = { _, _ -> },
    onFavoriteChanged: (feedId: Long, entryId: String, favorite: Boolean) -> Unit = { _, _, _ -> }
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = entries,
            key = { entry -> entry.entryId }
        ) { entry ->
            EntryCard(
                entry = entry,
                formatPublication = formatPublication,
                onCardClicked = onEntryClicked,
                onFavoriteChanged = onFavoriteChanged
            )
        }
    }
}

@Preview(
    name = "Entry List",
    showBackground = true
)
@Composable
fun EntryListPreview() {
    val entries = listOf(
        EntryCardData(0, "0-0", null, "SF", "First entry", "Sample feed", Date(), feedColor = 0x0L, false, false),
        EntryCardData(0, "0-1", null, "SF", "Second entry", "Sample feed", Date(), feedColor = 0x0L, true, false),
        EntryCardData(0, "0-2", null, "SF", "Third entry", "Sample feed", Date(), feedColor = 0x0L, false, true),
        EntryCardData(0, "0-3", null, "SF", "Fourth entry", "Sample feed", Date(), feedColor = 0x0L, true, true),
        EntryCardData(
            1,
            "1-0",
            null,
            "AF",
            "Entry of another feed with a title that is longer than that of the others",
            "Another feed",
            Date(),
            feedColor = 0xff0000L,
            false,
            false
        )
    )

    FlymTheme {
        EntryList(entries)
    }
}