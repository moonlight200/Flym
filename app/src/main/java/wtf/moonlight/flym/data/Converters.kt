package wtf.moonlight.flym.data

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun toDate(value: Long?): Date? =
        value?.let { Date(it) }

    @TypeConverter
    fun fromDate(value: Date?): Long? =
        value?.time
}