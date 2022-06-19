package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2

@Dao
interface AnketaIGrupeDao2 {
    @Transaction
    @Query("SELECT * FROM anketaigrupe2")
    fun getAll(): List<AnketaiGrupe2>


    @Insert
    suspend fun insertOne(vararg anketaiGrupe2: AnketaiGrupe2)

    @Query("DELETE FROM anketaigrupe2")
    suspend fun deleteAll()
}