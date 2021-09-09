package wtf.moonlight.flym.data.entities

import android.content.Context
import androidx.room.Embedded
import androidx.room.Relation

data class EntryWithFeed(
    @Embedded
    val entry: Entry,
    @Relation(
        parentColumn = "feedId",
        entityColumn = "feedId"
    )
    val feed: Feed
) {
    fun toCardData(context: Context): EntryCardData =
        EntryCardData(
            feedId = feed.id,
            entryId = entry.id,
            imageLink = entry.imageLink,
            feedLetters = feed.initials,
            title = entry.title,
            feedName = feed.title ?: "",
            publishTime = entry.getReadablePublicationDate(context),
            feedColor = feed.color,
            read = entry.read,
            favorite = entry.favorite
        )
}
