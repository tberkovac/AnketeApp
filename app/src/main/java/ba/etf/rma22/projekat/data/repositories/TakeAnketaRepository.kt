package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Grupa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TakeAnketaRepository {
    companion object {

        private lateinit var mcontext: Context

        public fun setContext(context: Context) {
            mcontext = context
        }
        suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
            var result: Response<AnketaTaken>
            result = ApiConfig.retrofit.takeAnketa(AccountRepository.getHash(), idAnkete)

            if (result.body() == null)
                return null
            //zapisi u bazu novi pokusaj
            writePokusaj(result.body()!!)
            return result.body()
        }

        suspend fun getPoceteAnkete(): List<AnketaTaken>? {
            val result: List<AnketaTaken>
            if (isOnline()) {
                result = ApiConfig.retrofit.getTakenAnkete(AccountRepository.getHash())
                writePokusaji(result)
            } else {
                result = getPokusajiDB()
            }
            if (result == null || result.isEmpty())
                return null
            return result
        }

        suspend fun getPokusajiDB(): List<AnketaTaken> {
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                val pokusaji = db.anketaTakenDAO().getAll()
                return@withContext pokusaji
            }
        }

        suspend fun writePokusaj( pokusaj: AnketaTaken): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    db.anketaTakenDAO().insertOne(pokusaj)

                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun writePokusaji(pokusaji: List<AnketaTaken>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    pokusaji.forEach {
                        db.anketaTakenDAO().insertOne(it)
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun getPokusaj(idAnketaTaken: Int): AnketaTaken {
            var sviPokusaji = ApiConfig.retrofit.getTakenAnkete(AccountRepository.getHash())
            sviPokusaji = sviPokusaji.filter { it.id == idAnketaTaken }
            return sviPokusaji[0]
        }

        fun isOnline(): Boolean {
            val connectivityManager =
                mcontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            }
            return false
        }
    }
}