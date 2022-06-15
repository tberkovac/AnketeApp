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

object TakeAnketaRepository {

    suspend fun zapocniAnketu(context: Context, idAnkete: Int): AnketaTaken? {
        var result : Response<AnketaTaken>
        result = ApiConfig.retrofit.takeAnketa(AccountRepository.getHash(), idAnkete)

        if(result.body()== null)
            return null
        //zapisi u bazu novi pokusaj
        writePokusaj(context, result.body()!!)
        return result.body()
    }

    suspend fun getPoceteAnkete(context: Context) : List<AnketaTaken>? {
        val result : List<AnketaTaken>
        if(isOnline(context)){
            result = ApiConfig.retrofit.getTakenAnkete(AccountRepository.getHash())
            writePokusaji(context, result)
        }else{
            result = getPokusajiDB(context)
        }
        if(result == null || result.isEmpty())
            return null
        return result
    }

    suspend fun getPokusajiDB(context: Context) : List<AnketaTaken> {
        return withContext(Dispatchers.IO) {
            var db = RMA22DB.getInstance(context)
            val pokusaji = db.anketaTakenDAO().getAll()
            return@withContext pokusaji
        }
    }

    suspend fun writePokusaj(context: Context, pokusaj: AnketaTaken) : String? {
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                db.anketaTakenDAO().insertOne(pokusaj)

                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun writePokusaji(context: Context, pokusaji: List<AnketaTaken>) : String? {
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                pokusaji.forEach {
                    db.anketaTakenDAO().insertOne(it)
                }
                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getPokusaj(idAnketaTaken: Int) : AnketaTaken {
        var sviPokusaji = ApiConfig.retrofit.getTakenAnkete(AccountRepository.getHash())
        sviPokusaji = sviPokusaji.filter { it.id == idAnketaTaken }
        return sviPokusaji[0]
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}