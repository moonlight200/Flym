package wtf.moonlight.flym.data.model

import java.util.*

data class EntryCardData(
    val feedId: Long,
    val entryId: String,
    val imageLink: String? = null,
    val feedLetters: String? = null,
    val title: String? = null,
    val feedName: String,
    val publicationDate: Date,
    val feedColor: Long,
    val read: Boolean,
    val favorite: Boolean
)
