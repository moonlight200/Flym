package wtf.moonlight.flym.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import wtf.moonlight.flym.data.model.Feed

@Dao
interface FeedDao {
    @Insert
    fun insertFeed(feed: Feed)

    @Query("SELECT * FROM feeds")
    fun getAllFeeds(): List<Feed>

    @Update
    fun updateFeed(feed: Feed)
}