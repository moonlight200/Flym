package wtf.moonlight.flym.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.abs

@Entity(
    tableName = "feeds",
    ignoredColumns = [
        "initials\$delegate" // Generated property
    ]
)
data class Feed(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "feed_id") val id: Long = 0L,
    @ColumnInfo(name = "link") val link: String = "",
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "image_link") val imageLink: String? = null,
    @Embedded val settings: FeedSettings = FeedSettings()
) {
    companion object {
        private val FEED_COLORS = listOf(
            0xffe57373,
            0xfff06292,
            0xffba68c8,
            0xff9575cd,
            0xff7986cb,
            0xff64b5f6,
            0xff4fc3f7,
            0xff4dd0e1,
            0xff4db6ac,
            0xff81c784,
            0xffaed581,
            0xffff8a65,
            0xffd4e157,
            0xffffd54f,
            0xffffb74d,
            0xffa1887f,
            0xff90a4ae
        )
        private val DELIMITERS = arrayOf(" ", "-", "&", ":", "|", "\"")
    }

    val color: Long
        get() = FEED_COLORS[(abs(id) % FEED_COLORS.size).toInt()]

    val initials by lazy {
        title?.let { title ->
            title
                .split(*DELIMITERS)
                .filter { it.isNotBlank() }
                .take(2)
                .map { it[0].uppercase() }
                .reduceOrNull { acc, letter -> acc + letter }
        }
    }
}

data class FeedSettings(
    @ColumnInfo(name = "retrieve_full_text") val retrieveFullText: Boolean = false
)
