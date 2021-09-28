package wtf.moonlight.flym.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import wtf.moonlight.flym.data.model.Entry

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries WHERE feed_id = :feedId AND entry_id = :entryId")
    fun observeEntry(feedId: Long, entryId: String): LiveData<Entry>

    @Query("SELECT * FROM entries WHERE feed_id = :feedId ORDER BY pub_date, entry_id DESC")
    fun observeEntriesOfFeed(feedId: Long): LiveData<List<Entry>>

    @Query("SELECT entry_id FROM entries WHERE feed_id = :feedId")
    fun getEntryIdsInFeed(feedId: Long): List<String>

    @Query("SELECT entry_id FROM entries WHERE title in (:titles)")
    fun findEntryIdsByTitle(titles: List<String>): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // We don't want to overwrite the favorite status
    fun insertAll(entries: List<Entry>)
}