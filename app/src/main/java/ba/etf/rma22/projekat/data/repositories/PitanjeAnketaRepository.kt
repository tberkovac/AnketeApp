package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PitanjeAnketaRepository {
    suspend fun getPitanja(context: Context, idAnkete:Int): List<Pitanje> {
        var pitanja : List<Pitanje>
        if(isOnline(context)){
            pitanja = ApiConfig.retrofit.getPitanjaForAnketa(idAnkete)
            writePitanjaDB(context, pitanja)
        }else{
            pitanja = getPitanjaDB(context)
            pitanja = pitanja.filter { pitanje -> pitanje.PitanjeAnketa.AnketumId == idAnkete }
        }
        return pitanja
    }


    suspend fun getPitanjaDB(context: Context) : List<Pitanje> {
        return withContext(Dispatchers.IO) {
            var db = RMA22DB.getInstance(context)
            val pitanja = db.pitanjeDAO().getAll()
            return@withContext pitanja
        }
    }

    suspend fun writePitanjaDB(context: Context, pitanja: List<Pitanje>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                pitanja.forEach {
                    db!!.pitanjeDAO().insertOne(it)
                }
                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
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