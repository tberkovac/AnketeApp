package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.OdgovorResponse

@Dao
interface OdgovorResponseDAO {
    @Transaction
    @Query("SELECT * FROM odgovorresponse")
    fun getAll(): List<OdgovorResponse>

    @Insert
    suspend fun insertOne(vararg odgovorResponse: OdgovorResponse)
}