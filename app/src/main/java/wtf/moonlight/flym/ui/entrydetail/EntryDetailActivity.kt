package wtf.moonlight.flym.ui.entrydetail

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import net.fred.feedex.R
import wtf.moonlight.flym.data.model.Entry
import wtf.moonlight.flym.ui.theme.FlymTheme

@AndroidEntryPoint
class EntryDetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_FEED_ID = "feedId"
        const val EXTRA_ENTRY_ID = "entryId"
    }

    private val viewModel: EntryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlymTheme {
                val entryWithFeed by viewModel.entry.observeAsState()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        finish()
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_back_white_24dp),
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            title = {
                                Text(
                                    text = entryWithFeed?.entry?.title
                                        ?: getString(R.string.app_name)
                                )
                            },
                            elevation = 8.dp
                        )
                    }
                ) { innerPadding ->
                    AndroidView(factory = { context ->
                        WebView(context)
                    },
                        update = {
                            val entry = entryWithFeed?.entry
                            if (entry == null) {
                                it.loadData("", "text/html", "utf8")
                            } else {
                                it.loadDataWithBaseURL(
                                    "",
                                    formatHtml(it.context, entry, Color.White, Color.Black),
                                    "text/html",
                                    "utf8",
                                    null
                                )
                            }
                        })
                }
            }
        }
    }

    private fun formatHtml(
        context: Context,
        entry: Entry,
        textColor: Color,
        backgroundColor: Color
    ) =
        """
            <html>
            <head>
                <meta name="viewport" content="width=device-width"/>
                <style type="text/css">
                    body {
                        max-width: 100%;
                        margin: 0.3cm;
                        font-family: sans-serif-light;
                        color: #${"%08X".format(textColor.value)};
                        background-color: #${"%08X".format(backgroundColor.value)};
                        line-height: 150%;
                    }
                    
                    * {
                        max-width: 100%;
                        word-break: break-word;
                    }
                    
                    h1, h2 {
                        font-weight: normal;
                        line-height: 130%;
                    }
                    
                    h1 {
                        font-size: 170%;
                        margin-bottom: 0.1em;
                    }
                    
                    h2 {
                        font-size: 140%;
                    }
                    
                    a {
                        color: #0099CC;
                    }
                    
                    h1 a {
                        color: inherit;
                        text-decoration: none;
                    }
                    
                    img {
                        height: auto;
                    }
                    
                    pre {
                        white-space: pre-wrap;
                        direction: ltr;
                    }
                    
                    blockquote {
                        border-left: thick solid QUOTE_LEFT_COLOR;
                        background-color: QUOTE_BACKGROUND_COLOR;
                        margin: 0.5em 0 0.5em 0em;
                        padding: 0.5em
                    }
                    
                    p {
                        margin: 0.8em 0 0.8em 0;
                    }
                    
                    p.flym-subtitle {
                        color: SUBTITLE_COLOR;
                        border-top: 1px SUBTITLE_BORDER_COLOR;
                        border-bottom: 1px SUBTITLE_BORDER_COLOR;
                        padding-top: 2px;
                        padding-bottom: 2px;
                        font-weight: 800;
                    }
                    
                    ul, ol {
                        margin: 0 0 0.8em 0.6em;
                        padding: 0 0 0 1em;
                    }
                    
                    ul li, ol li {
                        margin: 0 0 0.8em 0;
                        padding: 0;
                    }
                </style>
            </head>
            <body dir="auto">
                <h1>
                    <a href="${entry.link}">${entry.title}</a>
                </h1>
                <p class="flym-subtitle">
                    ${entry.getReadablePublicationDate(context)}
                    ${entry.author?.let { " &mdash; $it" }}
                </p>
                ${entry.mobilizedContent}
            </body>
            </html>
        """.trimIndent()
}