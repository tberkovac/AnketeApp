package ba.etf.rma22.projekat.data

import androidx.room.*
import ba.etf.rma22.projekat.data.models.AccountIGrupa
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2

@Dao
interface AccountIGrupaDAO {
    @Transaction
    @Query("SELECT * FROM accountigrupa")
    fun getAll(): List<AccountIGrupa>


    @Insert
    suspend fun insertOne(vararg accountIGrupa: AccountIGrupa)

    @Query("DELETE FROM accountigrupa")
    suspend fun deleteAll()
}