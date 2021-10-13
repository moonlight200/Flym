package wtf.moonlight.flym.ui.entries

import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
                var filter by remember { mutableStateOf(EntriesViewFilter.ALL) }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent()
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_menu_24dp),
                                            contentDescription = "Navigation"
                                        )
                                    }
                                },
                                title = {
                                    Text(text = getString(R.string.app_name))
                                },
                                elevation = 8.dp
                            )
                        },
                        bottomBar = {
                            BottomEntryFilters(
                                activeFilter = filter,
                                onFilterChange = { f -> filter = f }
                            )
                        }
                    ) { innerPadding ->
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
fun DrawerContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.header_background),
            contentDescription = null
        )
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

@Composable
fun BottomEntryFilters(
    activeFilter: EntriesViewFilter = EntriesViewFilter.ALL,
    onFilterChange: (filter: EntriesViewFilter) -> Unit = { _ -> }
) {
    BottomAppBar(
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (viewFilter in EntriesViewFilter.values()) {
                val title = stringResource(id = viewFilter.title)
                val alpha: Float by animateFloatAsState(targetValue = if (viewFilter == activeFilter) 1.0f else 0.54f)
                val scale: Float by animateFloatAsState(targetValue = if (viewFilter == activeFilter) 1.0f else 0.9f)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable(
                            role = Role.Button,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = 48.dp)
                        ) { onFilterChange(viewFilter) }
                        .alpha(alpha = alpha)
                        .scale(scale = scale)
                ) {
                    Icon(
                        painter = painterResource(id = viewFilter.icon),
                        contentDescription = title
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.caption,
                        maxLines = 1
                    )
                }
            }
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

@Preview(
    name = "Entry Filters",
    showBackground = true
)
@Composable
fun EntryFiltersPreview() {
    FlymTheme {
        BottomEntryFilters()
    }
}