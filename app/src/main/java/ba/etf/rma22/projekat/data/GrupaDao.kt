package ba.etf.rma22.projekat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa

@Dao
interface GrupaDao {
    @Transaction
    @Query("SELECT * FROM grupa")
    fun getAll(): List<Grupa>

    @Insert
    suspend fun insertOne(vararg grupa: Grupa)
}
