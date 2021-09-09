package wtf.moonlight.flym.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import net.frju.flym.data.entities.Entry

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries WHERE feed_id = :feedId AND entry_id = :entryId")
    fun observeEntry(feedId: Long, entryId: String): LiveData<Entry>

    @Query("SELECT * FROM entries WHERE feed_id = :feedId ORDER BY pub_date, entry_id DESC")
    fun observeEntriesOfFeed(feedId: Long): LiveData<List<Entry>>
}