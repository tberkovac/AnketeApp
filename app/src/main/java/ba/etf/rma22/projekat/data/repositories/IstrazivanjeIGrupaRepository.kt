package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeIGrupaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hamcrest.core.Is

object IstrazivanjeIGrupaRepository {

    val istrazivanjeIGrupaViewModel = IstrazivanjeIGrupaViewModel()
    suspend fun getIstrazivanja(offset:Int) : List<Istrazivanje> {
        return withContext(Dispatchers.IO){
            val response = ApiConfig.retrofit.getIstrazivanja(offset)
            return@withContext response
        }
    }

    suspend fun getIstrazivanja(context: Context) : List<Istrazivanje> {
        var svaIstrazivanja = listOf<Istrazivanje>()
        var i = 1
        while(true){
            val velicina : Int
            withContext(Dispatchers.IO){
                val response = ApiConfig.retrofit.getIstrazivanja(i)
                velicina = response.size
                svaIstrazivanja = svaIstrazivanja.plus(response)
            }
            if(velicina != 5)
                break
            i++
        }
        writeIstrazivanja(context, svaIstrazivanja)
        return svaIstrazivanja
    }

    suspend fun getGrupe(context: Context) : List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.retrofit.getGrupe()
            writeGrupe(context, response)
            return@withContext response
        }
    }

    suspend fun getAllGrupeDB(context: Context) : List<Grupa> {
        return withContext(Dispatchers.IO) {
            var db = RMA22DB.getInstance(context)
            val grupe = db.grupaDao().getAll()
            return@withContext grupe
        }
    }

    suspend fun getGrupeZaIstrazivanje(context: Context, idIstrazivanje: Int) : List<Grupa> {
        if(idIstrazivanje == 0){
            return emptyList()
        }
        val sveGrupe : List<Grupa> = if(isOnline(context)){
            getGrupe(context)
        } else {
            getAllGrupeDB(context)
        }
        var grupeZaVratiti = emptyList<Grupa>()

        val proslijedjenoIstrazivanje = if(isOnline(context)){
            ApiConfig.retrofit.getIstrazivanjaById(idIstrazivanje)
        }else {
            getAllIstrazivanjaDB(context).find { it.id == idIstrazivanje }
        }
        sveGrupe.forEach {
            val istrazivanjeZaGrupu : Istrazivanje
            if(isOnline(context)){
                istrazivanjeZaGrupu = ApiConfig.retrofit.getIstrazivanjaForGroupById(it.id)
            }else{
                istrazivanjeZaGrupu = getAllIstrazivanjaDB(context).find { istrazivanje -> it.IstrazivanjeId == istrazivanje.id }!!
            }

            if(istrazivanjeZaGrupu == proslijedjenoIstrazivanje)
                grupeZaVratiti = grupeZaVratiti.plus(it)
        }
        return grupeZaVratiti
    }

    suspend fun getAllIstrazivanjaDB(context: Context): List<Istrazivanje> {
        return withContext(Dispatchers.IO) {
            var db = RMA22DB.getInstance(context)
            val istrazivanja = db.istrazivanjeDao().getAll()
            return@withContext istrazivanja
        }
    }

    suspend fun upisiUGrupu(context: Context, idGrupa:Int):Boolean {
        if(getUpisaneGrupe().contains(ApiConfig.retrofit.getGrupaById(idGrupa)))
            return false
        if(getUpisanaIstrazivanja(context).contains(getIstrazivanjeByGrupaId(context, idGrupa)))
            return false
        val response = ApiConfig.retrofit.upisiGrupuSaId(idGrupa)
        if(response.message() == "OK")
            return true
        return false
    }

    private suspend fun getUpisanaIstrazivanja(context: Context): List<Istrazivanje> {
        var upisanaIstrazivanja = mutableListOf<Istrazivanje>()
        val upisaneGrupe = getUpisaneGrupe()
        upisaneGrupe.forEach {
            upisanaIstrazivanja.add(getIstrazivanjeByGrupaId(context, it.id))
        }
        return upisanaIstrazivanja
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return ApiConfig.retrofit.getUpisaneGrupe(AccountRepository.getHash())
    }


    suspend fun getGrupaById(context: Context, idGrupa: Int): Grupa {
        val result = if(isOnline(context)) {
            ApiConfig.retrofit.getGrupaById(idGrupa)
        } else{
            var db = RMA22DB.getInstance(context)
            db.grupaDao().getAll().filter { grupa -> grupa.id == idGrupa }.first()
        }
        return result
    }

    suspend fun getIstrazivanjeByGrupaId(context: Context, idGrupa: Int): Istrazivanje {

        val istrazivanje = if(isOnline(context)){
            getIstrazivanja(context)
            ApiConfig.retrofit.getIstrazivanjaForGroupById(idGrupa)
        }else{
            var grupe = getAllGrupeDB(context)
            var trazenaGrupa = grupe.find { grupa -> grupa.id == idGrupa }
            var result  = istrazivanjeIGrupaViewModel.getAllIstrazivanja(context)
            result.filter { istrazivanje -> istrazivanje.id == trazenaGrupa!!.IstrazivanjeId }.first()
        }
        return istrazivanje
    }

    suspend fun writeIstrazivanja(context: Context, istrazivanja: List<Istrazivanje>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                istrazivanja.forEach {
                    db.istrazivanjeDao().insertOne(it)
                }
                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun writeGrupe(context: Context, grupe: List<Grupa>) : String?{
        return withContext(Dispatchers.IO) {
            Log.v("PROSLIJEDJENEGRUPE", grupe.toString())
            try{
                var db = RMA22DB.getInstance(context)
                grupe.forEach {
                    Log.v("UPISANAGRUPA", it.toString())
                    db!!.grupaDao().insertOne(it)
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