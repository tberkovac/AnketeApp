package ba.etf.rma22.projekat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Odgovor2

@Dao
interface OdgovorDAO2 {
    @Transaction
    @Query("SELECT * FROM odgovor2")
    fun getAll(): List<Odgovor2>

    @Insert
    suspend fun insertOne(vararg odgovor2: Odgovor2)
}