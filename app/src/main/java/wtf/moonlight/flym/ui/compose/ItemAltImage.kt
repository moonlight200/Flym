package wtf.moonlight.flym.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fred.feedex.R
import wtf.moonlight.flym.ui.theme.FlymTheme

@Composable
fun ItemAltImage(
    modifier: Modifier = Modifier,
    text: String? = null,
    color: Color = Color.Gray,
    size: Dp = 96.dp,
    shape: Shape = RectangleShape
) {
    Surface(
        color = color,
        modifier = modifier
            .size(size = size)
            .aspectRatio(1f),
        shape = shape
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
                        .size(size * 2 / 3)
                        .alpha(0.6f)
                )
            } else {
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h3,
                    fontSize = (size.value / 2.0).sp
                )
            }
        }
    }
}

@Composable
@Preview(name = "Alt image no text")
fun ItemAltImagePreviewNoText() {
    FlymTheme {
        ItemAltImage()
    }
}

@Composable
@Preview(name = "Alt image text")
fun ItemAltImagePreviewText() {
    FlymTheme {
        ItemAltImage(
            text = "AI",
            color = Color.Red
        )
    }
}

@Composable
@Preview(name = "Alt image round no text")
fun ItemAltImagePreviewRoundNoText() {
    FlymTheme {
        ItemAltImage(
            shape = CircleShape,
            color = Color.Green,
            size = 24.dp
        )
    }
}

@Composable
@Preview(name = "Alt image round text")
fun ItemAltImagePreviewRoundText() {
    FlymTheme {
        ItemAltImage(
            shape = CircleShape,
            text = "MM",
            color = Color.Yellow,
            size = 48.dp
        )
    }
}