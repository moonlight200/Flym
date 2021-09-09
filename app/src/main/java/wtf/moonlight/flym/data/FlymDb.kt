package wtf.moonlight.flym.data

import androidx.room.Database
import androidx.room.RoomDatabase
import wtf.moonlight.flym.data.model.Entry
import wtf.moonlight.flym.data.model.Feed

@Database(
    entities = [
        Feed::class,
        Entry::class
    ],
    version = 1
)
abstract class FlymDb : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "flym.db"
    }

    abstract fun entryDao(): EntryDao
}