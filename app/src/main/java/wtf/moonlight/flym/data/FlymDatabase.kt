package wtf.moonlight.flym.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import wtf.moonlight.flym.data.model.Entry
import wtf.moonlight.flym.data.model.Feed

@Database(
    entities = [
        Feed::class,
        Entry::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FlymDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "flym.db"

        fun build(context: Context): FlymDatabase {
            return Room
                .databaseBuilder(context, FlymDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

    abstract fun entryDao(): EntryDao
}