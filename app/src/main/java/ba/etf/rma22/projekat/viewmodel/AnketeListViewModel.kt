package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class AnketeListViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun getAll(offset: Int) : List<Anketa> {
        return AnketaRepository.getAll(offset)
    }

    fun fetchDB(context: Context, onSuccess: (ankete: List<Anketa>) -> Unit,
                onError: (s: String) -> Unit){
        scope.launch{
            val result = AnketaRepository.getAllDB(context)
            when (result) {
                is List<Anketa> -> onSuccess?.invoke(result)
                else-> onError?.invoke("greska u povlacenju sa baze")
            }
        }
    }


    fun writeDB(context: Context, anketa:List<Anketa>, onSuccess: (ankete: String) -> Unit,
                onError: (s: String) -> Unit){
        scope.launch{
            val result = AnketaRepository.writeAnkete(context, anketa)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke("greska u zapisu anketa u bazu ")
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

     fun getAll(context: Context, onSuccess : (context: Context, ankete: List<Anketa>)-> Unit, onError : (s: String)-> Unit) {
        scope.launch {
            val result : List<Anketa>
            if(isOnline(context)) {
                result = AnketaRepository.getAll()
            }else{
                result = AnketaRepository.getAllDB(context)
            }
                when (result ) {
                    is List<Anketa> -> onSuccess.invoke(context, result)
                    else -> onError?.invoke("greska u pozivanju getall")
                }

        }
    }

    fun getUpisaneAnkete(context: Context, onSuccess : (ankete: List<Anketa>) -> Unit) {
        scope.launch {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            val sveAnkete = AnketaRepository.getAll()
            var grupeZaAnketu : List<Grupa>
            val listaUpisanihAnketa = mutableListOf<Anketa>()
            sveAnkete.forEach {
                grupeZaAnketu = AnketaRepository.getGroupsForAnketa(context, it.id)
                for (grupa in grupeZaAnketu) {
                    if(upisaneGrupe.contains(grupa)){
                        listaUpisanihAnketa.add(it)
                        break
                    }
                }
            }

            onSuccess.invoke(listaUpisanihAnketa)
        }
    }

    suspend fun getUpisaneAnkete(context: Context) : List<Anketa> {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            val sveAnkete = AnketaRepository.getAll()
            var grupeZaAnketu : List<Grupa>
            val listaUpisanihAnketa = mutableListOf<Anketa>()
            sveAnkete.forEach {
                grupeZaAnketu = AnketaRepository.getGroupsForAnketa(context, it.id)
                for (grupa in grupeZaAnketu) {
                    if(upisaneGrupe.contains(grupa)){
                        listaUpisanihAnketa.add(it)
                        break
                    }
                }
            }
            return listaUpisanihAnketa
        }

/*
    fun getUradjeneAnkete(context: Context, onSuccess: (ankete: List<Anketa>) -> Unit){
        scope.launch {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            val sveAnkete = getUpisaneAnkete(context)
            var grupeZaAnketu : List<Grupa>
            val listaUpisanihAnketa = mutableListOf<Anketa>()
            sveAnkete.forEach {
                grupeZaAnketu = AnketaRepository.getGroupsForAnketa(context, it.id)
                for (grupa in grupeZaAnketu) {
                    if(upisaneGrupe.contains(grupa)){
                        if(OdgovorRepository.obracunajProgresZaAnketu(it.id) == 100)
                        listaUpisanihAnketa.add(it)
                        break
                    }
                }
            }
            onSuccess.invoke(listaUpisanihAnketa)
        }
    }

 */



    fun getFutureAnkete(context: Context, onSuccess: (ankete: List<Anketa>) -> Unit){
        scope.launch {
            var result = getUpisaneAnkete(context)
            val date = Calendar.getInstance().time
            result =  result.filter { it.datumPocetak > date}
            onSuccess.invoke(result)
        }
    }

    fun getExpiredAnkete(context: Context, onSuccess: (ankete: List<Anketa>) -> Unit){
        scope.launch {
            val result = getUpisaneAnkete(context)
            val date = Calendar.getInstance().time
            val zaVratit = mutableListOf<Anketa>()
            for (anketa in result) {
                if(anketa.datumKraj != null && anketa.datumKraj < date){
                    var pokusajiSvi = TakeAnketaRepository.getPoceteAnkete(context)
                    pokusajiSvi = pokusajiSvi?.filter { it.AnketumId == anketa.id }
                    if(pokusajiSvi?.isNotEmpty() == true)
                        zaVratit.add(anketa)
                }
            }
            onSuccess.invoke(zaVratit)
        }
    }



     fun jeLiUpisanaAnketa(context: Context, anketaId: Int, onSuccess: (jeLiUpisana: Boolean) -> Unit) {
        scope.launch {
            val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(context, anketaId)
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            var upisana = false

            for (grupa in grupeZaAnketu) {
                if(upisaneGrupe.contains(grupa)){
                    upisana = true
                    break
                }
            }
            onSuccess.invoke(upisana)
        }
    }


    suspend fun jeLiUpisanaAnketa(context: Context, anketaId: Int) : Boolean{
        val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(context, anketaId)
        return grupeZaAnketu.isNotEmpty()
    }
}