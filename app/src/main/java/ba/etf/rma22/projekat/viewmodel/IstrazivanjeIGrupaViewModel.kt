package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.ApiConfig
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IstrazivanjeIGrupaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun getAllIstrazivanja() : List<Istrazivanje> {
        val result : List<Istrazivanje>
        if(IstrazivanjeIGrupaRepository.isOnline()) {
            result = IstrazivanjeIGrupaRepository.getIstrazivanja()
            IstrazivanjeIGrupaRepository.writeIstrazivanja(result)
        }else{
            result = IstrazivanjeIGrupaRepository.getAllIstrazivanjaDB()
        }
        return result
    }

    suspend fun getAllGrupe() : List<Grupa> {
        val result = IstrazivanjeIGrupaRepository.getGrupe()
        IstrazivanjeIGrupaRepository.writeGrupe(result)
        return result
    }

     fun getAllIstrazivanjaByGodina( godina : Int, onSuccess: (istrazivanja: List<Istrazivanje>) -> Unit,
                                           onError: () -> Unit) {
        scope.launch{
            val result = getAllIstrazivanja().filter { istrazivanje -> istrazivanje.godina == godina  }

            when (result) {
                is List<Istrazivanje> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

     fun getGrupeByIstrazivanje(idIstrazivanje: Int, onSuccess: (grupe: List<Grupa>) -> Unit,
                                            onError: () -> Unit) {
        scope.launch{
            val result : List<Grupa>
            if(IstrazivanjeIGrupaRepository.isOnline()) {
                result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(idIstrazivanje)
            }else{
                result = IstrazivanjeIGrupaRepository.getAllGrupeDB().filter { grupa -> grupa.IstrazivanjeId == idIstrazivanje }
            }
            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

     fun upisiUGrupu(idGrupa:Int, onSuccess: (uspjelo: Boolean) -> Unit, onError: () -> Unit) {
        scope.launch {
            val result = IstrazivanjeIGrupaRepository.upisiUGrupu(idGrupa)

            when (result) {
                is Boolean -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    suspend fun getUpisaneGrupe(onSuccess: (grupe: List<Grupa>) -> Unit, onError: () -> Unit){
        scope.launch {
            val result = IstrazivanjeIGrupaRepository.getUpisaneGrupe()

            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    suspend fun getIstrazivanjeByGroupId(grupaId: Int): Istrazivanje {
        return IstrazivanjeIGrupaRepository.getIstrazivanjeByGrupaId(grupaId)
    }

    fun getIstrazivanjaZaAnketu(
        anketa: Anketa,
        onSuccess: ( istrazivanja: List<Istrazivanje>) -> Unit
    ) {
        var zaVratitIstrazivanaja = mutableListOf<Istrazivanje>()
        scope.launch {
            var grupeZaAnketu : List<Grupa> = listOf()
            grupeZaAnketu = AnketaRepository.getGroupsForAnketa(anketa.id)
            for (grupa in grupeZaAnketu) {
                var istrazivanjeDodatno = IstrazivanjeIGrupaRepository.getIstrazivanjeByGrupaId(grupa.id)
                zaVratitIstrazivanaja.add(istrazivanjeDodatno)
            }
            onSuccess.invoke(zaVratitIstrazivanaja)
        }
    }


    fun writeDB(istrazivanja: List<Istrazivanje>, onSuccess: (ankete: String) -> Unit,
                onError: (s: String) -> Unit){
        scope.launch {
            val result = IstrazivanjeIGrupaRepository.writeIstrazivanja(istrazivanja)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke("greska u zapisu istrazivanja")
            }
        }
    }

    fun obrisiIstrazivanjaDB(){
        scope.launch {
            IstrazivanjeIGrupaRepository.obrisiIstrazivanjaDB()
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