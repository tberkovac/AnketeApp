package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa

@Dao
interface GrupaDao {
    @Transaction
    @Query("SELECT * FROM grupa")
    fun getAll(): List<Grupa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(vararg grupa: Grupa)


}
