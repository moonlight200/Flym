package wtf.moonlight.flym.ui.compose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.fred.feedex.R
import org.jetbrains.anko.wrapContent
import wtf.moonlight.flym.ui.theme.FlymTheme

@Composable
fun EntryCard(modifier: Modifier = Modifier) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            EntryTextImage()
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .height(84.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Some entry, that is part of the feed, has been published",
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Icon(Icons.Outlined.Star, contentDescription = "Favourite")
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "News Site",
                        maxLines = 1,
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.overline
                    )
                    Text(
                        text = "14:23",
                        style = MaterialTheme.typography.overline
                    )
                }
            }
        }
    }
}

@Composable
fun EntryTextImage(modifier: Modifier = Modifier, text: String? = null) {
    Surface(
        color = Color(0xff9575cd),
        modifier = modifier
            .size(84.dp)
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
        EntryCard()
    }
}

@Preview(name = "EntryCard Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EntryCardPreviewDark() {
    FlymTheme {
        EntryCard()
    }
}
