package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorResponseDAO {
    @Transaction
    @Query("SELECT * FROM odgovor")
    fun getAll(): List<Odgovor>

    @Insert
    suspend fun insertOne(vararg odgovor: Odgovor)

    @Query("DELETE FROM odgovor")
    suspend fun deleteAll()
}