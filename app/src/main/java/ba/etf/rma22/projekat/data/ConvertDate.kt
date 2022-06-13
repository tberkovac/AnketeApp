package ba.etf.rma22.projekat.data

import androidx.room.TypeConverter
import java.util.*


object ConvertDate {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
