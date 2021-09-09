package wtf.moonlight.flym.data.entities

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.room.ColumnInfo
import java.util.*

data class Entry(
    @ColumnInfo(name = "entryId") val id: String = "",
    val feedId: Long = 0L,
    val link: String? = null,
    val title: String? = null,
    var description: String? = null,
    val fetchDate: Date = Date(),
    val publicationDate: Date = Date(),
    val mobilizedContent: String? = null,
    val imageLink: String? = null,
    val author: String? = null,
    val read: Boolean = false,
    val favorite: Boolean = false
) {
    fun getReadablePublicationDate(context: Context): String = buildString {
        if (!DateUtils.isToday(publicationDate.time)) {
            append(DateFormat.getMediumDateFormat(context).format(publicationDate))
            append(' ')
        }
        append(DateFormat.getTimeFormat(context).format(publicationDate))
    }
}
