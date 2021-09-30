package wtf.moonlight.flym.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class EntryWithFeed(
    @Embedded
    val entry: Entry,
    @Relation(
        parentColumn = "feed_id",
        entityColumn = "feed_id"
    )
    val feed: Feed
) {
    fun toCardData(): EntryCardData =
        EntryCardData(
            feedId = feed.id,
            entryId = entry.id,
            imageLink = entry.imageLink,
            feedLetters = feed.initials,
            title = entry.title,
            feedName = feed.title ?: "",
            publicationDate = entry.publicationDate,
            feedColor = feed.color,
            read = entry.read,
            favorite = entry.favorite
        )
}
