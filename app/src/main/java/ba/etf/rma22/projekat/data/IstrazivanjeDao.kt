package ba.etf.rma22.projekat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDao {
    @Transaction
    @Query("SELECT * FROM Istrazivanje")
    fun getAll(): List<Istrazivanje>

    @Insert
    suspend fun insertOne(vararg istrazivanje: Istrazivanje)
}