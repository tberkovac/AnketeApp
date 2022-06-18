package ba.etf.rma22.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository {
    companion object {
        var acHash = "19b70587-76b0-4444-a964-fad0a04d426b"
        private lateinit var mcontext: Context

        fun setContext(context: Context) {
            mcontext = context
        }

        suspend fun postaviHash(acHash: String): Boolean {
            deleteAllAccountDB()
            var uspjesnoUpisano = writeAccountDB(mcontext, acHash)
            if (uspjesnoUpisano == null)
                return false
            this.acHash = acHash
            return true
        }

        private suspend fun deleteAllAccountDB(): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    db.accountDAO().deleteAll()
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        private suspend fun writeAccountDB(context: Context, acHash: String): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(context)
                    db.accountDAO().insertOne(Account(acHash))
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        fun getHash(): String {
            return acHash
        }
    }
}