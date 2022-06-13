package ba.etf.rma22.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma22.projekat.data.models.*


@Database(entities = arrayOf(Anketa::class
    // , AnketaTaken::class, Grupa::class, Istrazivanje::class, Odgovor::class, OdgovorResponse::class
//,Pitanje::class, UpisGrupeResponse::class)
), version = 1)
@TypeConverters(ConvertDate::class)
abstract class RMA22DB : RoomDatabase() {
    abstract fun anketaDao(): AnketaDao
    companion object {
        private var INSTANCE: RMA22DB? = null
        fun getInstance(context: Context): RMA22DB {
            if (INSTANCE == null) {
                synchronized(RMA22DB::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RMA22DB::class.java,
                "RMA22DB"
            ).build()
    }
}