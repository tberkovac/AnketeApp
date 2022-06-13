package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IstrazivanjeIGrupaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun getAllIstrazivanja(context : Context) : List<Istrazivanje> {
        val result : List<Istrazivanje>
        if(isOnline(context)) {
            result = IstrazivanjeIGrupaRepository.getIstrazivanja()
            IstrazivanjeIGrupaRepository.writeIstrazivanja(context, result)
        }else{
            result = IstrazivanjeIGrupaRepository.getAllIstrazivanjaDB(context)
        }
        return result
    }

    suspend fun getAllGrupe(context : Context) : List<Grupa> {
        val result = IstrazivanjeIGrupaRepository.getGrupe(context)
        IstrazivanjeIGrupaRepository.writeGrupe(context, result)
        return result
    }

     fun getAllIstrazivanjaByGodina(context: Context, godina : Int, onSuccess: (context: Context, istrazivanja: List<Istrazivanje>) -> Unit,
                                           onError: () -> Unit) {
        scope.launch{
            val result = getAllIstrazivanja(context).filter { istrazivanje -> istrazivanje.godina == godina  }

            when (result) {
                is List<Istrazivanje> -> onSuccess?.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

     fun getGrupeByIstrazivanje(context: Context,idIstrazivanje: Int, onSuccess: (grupe: List<Grupa>) -> Unit,
                                            onError: () -> Unit) {
        scope.launch{
            val result : List<Grupa>
            if(isOnline(context)) {
                result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(context, idIstrazivanje)
            }else{
                result = IstrazivanjeIGrupaRepository.getAllGrupeDB(context).filter { grupa -> grupa.IstrazivanjeId == idIstrazivanje }
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

    suspend fun getIstrazivanjeByGroupId(idGrupa: Int) : Istrazivanje {
        return IstrazivanjeIGrupaRepository.getIstrazivanjeByGrupaId(idGrupa)
    }

     fun getGrupeZaAnketu(
        anketa: Anketa,
        onSuccess: (grupe: List<Grupa>) -> Unit
    ) {
        scope.launch {
            val result = AnketaRepository.getGroupsForAnketa(anketa.id)
            onSuccess.invoke(result)
        }
    }

    fun getIstrazivanjaZaAnketu(
        anketa: Anketa,
        onSuccess: (istrazivanja: List<Istrazivanje>) -> Unit
    ) {
        scope.launch {
            val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(anketa.id)
            var zaVratitIstrazivanaja = mutableListOf<Istrazivanje>()

            for (grupa in grupeZaAnketu) {
                zaVratitIstrazivanaja.add(getIstrazivanjeByGroupId(grupa.id))
            }
            onSuccess.invoke(zaVratitIstrazivanaja)
        }
    }


    fun writeDB(context: Context, istrazivanja: List<Istrazivanje>, onSuccess: (ankete: String) -> Unit,
                onError: (s: String) -> Unit){
        scope.launch {
            val result = IstrazivanjeIGrupaRepository.writeIstrazivanja(context, istrazivanja)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke("greska u zapisu istrazivanja")
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