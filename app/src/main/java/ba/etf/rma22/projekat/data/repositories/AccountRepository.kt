package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountRepository {
    var acHash = "19b70587-76b0-4444-a964-fad0a04d426b"

    suspend fun postaviHash(context: Context, acHash:String):Boolean{
        deleteAllAccountDB(context)
        var uspjesnoUpisano = writeAccountDB(context, acHash)
        if(uspjesnoUpisano == null)
            return false
        this.acHash = acHash
        return true
    }

    private suspend fun deleteAllAccountDB(context: Context): String? {
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                db.accountDAO().deleteAll()
                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
    }

    private suspend fun writeAccountDB(context: Context, acHash: String) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                db.accountDAO().insertOne(Account(acHash))
                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
    }

    fun getHash():String{
        return acHash
    }
}