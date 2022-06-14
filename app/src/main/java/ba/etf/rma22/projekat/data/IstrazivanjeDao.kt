package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDao {
    @Transaction
    @Query("SELECT * FROM Istrazivanje")
    fun getAll(): List<Istrazivanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(vararg istrazivanje: Istrazivanje)
}