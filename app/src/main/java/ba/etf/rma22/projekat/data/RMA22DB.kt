package ba.etf.rma22.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma22.projekat.data.models.*


@Database(entities = arrayOf(Anketa::class, Istrazivanje::class, Grupa::class, AnketaiGrupe2::class,
    AccountIGrupa::class, AnketaTaken::class, OdgovorResponse::class, Pitanje::class, Account::class), version = 16)
@TypeConverters(ConvertDate::class, ConverterAnketaIGrupe::class, ConverterAccountIGrupa::class,
    ConvertPitanjeAnketa::class, ConverterOpcije::class)
abstract class RMA22DB : RoomDatabase() {
    abstract fun anketaDao(): AnketaDao
    abstract fun istrazivanjeDao(): IstrazivanjeDao
    abstract fun grupaDao(): GrupaDao
    abstract fun anketaiGRUPEDAO2() : AnketaIGrupeDao2
    abstract fun accountIGrupaDAO() : AccountIGrupaDAO
    abstract fun anketaTakenDAO() : AnketaTakenDAO
    abstract fun odgovorDAO(): OdgovorResponseDAO
    abstract fun pitanjeDAO(): PitanjeDAO
    abstract fun accountDAO(): AccountDAO

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
            ).fallbackToDestructiveMigration().build()
    }
}