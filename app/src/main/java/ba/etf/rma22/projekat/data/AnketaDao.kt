package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa
import retrofit2.http.GET

@Dao
interface AnketaDao {
    @Transaction
    @Query("SELECT * FROM anketa")
    fun getAll() : List<Anketa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(vararg ankete: Anketa)

}