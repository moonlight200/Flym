package wtf.moonlight.flym.data.model

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.*

@Entity(
    tableName = "entries",
    primaryKeys = ["entry_id", "feed_id"],
    foreignKeys = [
        ForeignKey(
            entity = Feed::class,
            parentColumns = ["feed_id"],
            childColumns = ["feed_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Entry(
    @ColumnInfo(name = "entry_id") val id: String = "",
    @ColumnInfo(name = "feed_id") val feedId: Long = 0L,
    @ColumnInfo(name = "link") val link: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "fetch_date") val fetchDate: Date = Date(),
    @ColumnInfo(name = "pub_date") val publicationDate: Date = Date(),
    @ColumnInfo(name = "mobile_content") val mobilizedContent: String? = null,
    @ColumnInfo(name = "image_link") val imageLink: String? = null,
    @ColumnInfo(name = "author") val author: String? = null,
    @ColumnInfo(name = "read") val read: Boolean = false,
    @ColumnInfo(name = "favorite") val favorite: Boolean = false
) {
    fun getReadablePublicationDate(context: Context): String = buildString {
        if (!DateUtils.isToday(publicationDate.time)) {
            append(DateFormat.getMediumDateFormat(context).format(publicationDate))
            append(' ')
        }
        append(DateFormat.getTimeFormat(context).format(publicationDate))
    }
}
