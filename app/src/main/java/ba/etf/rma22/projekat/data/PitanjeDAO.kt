package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface PitanjeDAO {
    @Transaction
    @Query("SELECT * FROM pitanje")
    fun getAll(): List<Pitanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(vararg pitanje: Pitanje)

    @Query("DELETE FROM pitanje")
    suspend fun deleteAll()
}