package ba.etf.rma22.projekat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Account

@Dao
interface AccountDAO {
    @Transaction
    @Query("SELECT * FROM account")
    fun getAll(): Account


    @Insert
    suspend fun insertOne(vararg account: Account)

    @Query("DELETE FROM account")
    suspend fun deleteAll()
}