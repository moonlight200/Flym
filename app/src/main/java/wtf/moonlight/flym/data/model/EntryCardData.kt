package wtf.moonlight.flym.data.model

data class EntryCardData(
    val feedId: Long,
    val entryId: String,
    val imageLink: String? = null,
    val feedLetters: String? = null,
    val title: String? = null,
    val feedName: String,
    val publishTime: String,
    val feedColor: Long,
    val read: Boolean,
    val favorite: Boolean
)
