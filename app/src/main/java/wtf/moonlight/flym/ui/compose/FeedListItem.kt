package wtf.moonlight.flym.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wtf.moonlight.flym.data.model.Feed
import wtf.moonlight.flym.ui.theme.FlymTheme

@Composable
fun FeedListItem(feed: Feed) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (feed.imageLink != null) {
            // TODO show image
            Box(modifier = Modifier.size(24.dp)) {
                Text(text = feed.imageLink, fontSize = 12.sp)
            }
        } else {
            ItemAltImage(
                modifier = Modifier.size(24.dp),
                text = feed.initials,
                color = Color(feed.color),
                shape = CircleShape
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = feed.title ?: "Unnamed feed",
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun FeedListItemPreview() {
    FlymTheme {
        FeedListItem(
            feed = Feed(
                id = 1L,
                link = "https://example.com",
                title = "Example feed",
                imageLink = null
            )
        )
    }
}