package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.AccountIGrupa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2

@Dao
interface AnketaTakenDAO {
    @Transaction
    @Query("SELECT * FROM anketataken")
    fun getAll(): List<AnketaTaken>


    @Insert
    suspend fun insertOne(vararg anketaTaken: AnketaTaken)
}