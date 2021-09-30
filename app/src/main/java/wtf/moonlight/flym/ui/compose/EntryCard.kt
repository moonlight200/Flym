package wtf.moonlight.flym.ui.compose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.fred.feedex.R
import wtf.moonlight.flym.data.model.EntryCardData
import wtf.moonlight.flym.ui.theme.FlymTheme
import java.util.*

@Composable
fun EntryCard(
    entry: EntryCardData,
    modifier: Modifier = Modifier,
    formatPublication: (Date) -> String = { date -> date.toString() },
    onCardClicked: (feedId: Long, entryId: String) -> Unit = { _, _ -> },
    onFavoriteChanged: (feedId: Long, entryId: String, favorite: Boolean) -> Unit = { _, _, _ -> }
) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { onCardClicked(entry.feedId, entry.entryId) },
        ) {
            if (entry.imageLink != null && false) {
                // TODO show image
                Box(modifier = Modifier.size(96.dp)) {
                    Text(text = "img")
                }
            } else {
                EntryAltImage(
                    text = entry.feedLetters,
                    color = Color(entry.feedColor)
                )
            }
            Column(
                modifier = Modifier
                    .height(96.dp)
                    .alpha(if (entry.read) 0.6f else 1.0f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val titleStyle = if (entry.title != null)
                        MaterialTheme.typography.subtitle1
                    else
                        MaterialTheme.typography.subtitle1.copy(fontStyle = FontStyle.Italic)
                    val favoriteIconRes =
                        if (entry.favorite) R.drawable.ic_star_24dp else R.drawable.ic_star_border_24dp

                    Text(
                        text = entry.title ?: "No title",
                        style = titleStyle,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, top = 8.dp)
                    )
                    IconButton(
                        onClick = { onFavoriteChanged(entry.feedId, entry.entryId, !entry.favorite) },
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Icon(
                            painter = painterResource(id = favoriteIconRes),
                            contentDescription = "Favorite"
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = entry.feedName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatPublication(entry.publicationDate),
                        maxLines = 1,
                        style = MaterialTheme.typography.overline
                    )
                }
            }
        }
    }
}

@Composable
fun EntryAltImage(text: String?, color: Color, modifier: Modifier = Modifier) {
    Surface(
        color = color,
        modifier = modifier
            .size(96.dp)
            .aspectRatio(1f)
    ) {
        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            if (text == null) {
                Image(
                    painter = painterResource(id = R.drawable.flym),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .alpha(0.6f)
                )
            } else {
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h3
                )
            }
        }
    }
}

@Preview(name = "EntryCard Light", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun EntryCardPreview() {
    FlymTheme {
        EntryCard(
            EntryCardData(
                1L,
                "abc",
                imageLink = null,
                feedLetters = null,
                title = "Some entry, that is part of the feed, has been published",
                feedName = "News Site with a long title",
                publicationDate = Date(1631017380L),
                feedColor = 0xff9575cd,
                read = false,
                favorite = false
            )
        )
    }
}

@Preview(name = "EntryCard Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EntryCardPreviewDark() {
    FlymTheme {
        EntryCard(
            EntryCardData(
                1L,
                "abc",
                imageLink = null,
                feedLetters = null,
                title = "Some entry, that is part of the feed, has been published",
                feedName = "News Site with a long title",
                publicationDate = Date(1631017380L),
                feedColor = 0xff9575cd,
                read = false,
                favorite = false
            )
        )
    }
}
