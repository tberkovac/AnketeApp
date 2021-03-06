package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.OnlineProvjera.Companion.isOnline
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
import kotlin.reflect.KFunction2

class AnketeListViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun getAll(offset: Int) : List<Anketa> {
        return AnketaRepository.getAll(offset)
    }


    fun writeDB(anketa:List<Anketa>, onSuccess: (ankete: String) -> Unit,
                onError: (s: String) -> Unit){
        scope.launch{
            val result = AnketaRepository.writeAnkete( anketa)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke("greska u zapisu anketa u bazu ")
            }
        }
    }

     fun getAll(onSuccess:  (List<Anketa>) -> Unit, onError: (s: String)-> Unit) {
        scope.launch {
            val result : List<Anketa>
            if(IstrazivanjeIGrupaRepository.isOnline()) {
                result = AnketaRepository.getAll()
            }else{
                result = AnketaRepository.getAllDB()
            }
                when (result ) {
                    is List<Anketa> -> onSuccess.invoke(result)
                    else -> onError?.invoke("greska u pozivanju getall")
                }

        }
    }

    fun getUpisaneAnkete(onSuccess : (ankete: List<Anketa>) -> Unit) {
        scope.launch {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            val sveAnkete = AnketaRepository.getAll()
            var grupeZaAnketu : List<Grupa>
            val listaUpisanihAnketa = mutableListOf<Anketa>()
            sveAnkete.forEach {
                grupeZaAnketu = AnketaRepository.getGroupsForAnketa(it.id)
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

    suspend fun getUpisaneAnkete() : List<Anketa> {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            val sveAnkete = AnketaRepository.getAll()
            var grupeZaAnketu : List<Grupa>
            val listaUpisanihAnketa = mutableListOf<Anketa>()
            sveAnkete.forEach {
                grupeZaAnketu = AnketaRepository.getGroupsForAnketa(it.id)
                for (grupa in grupeZaAnketu) {
                    if(upisaneGrupe.contains(grupa)){
                        listaUpisanihAnketa.add(it)
                        break
                    }
                }
            }
            return listaUpisanihAnketa
        }


    fun getUradjeneAnkete(onSuccess: (ankete: List<Anketa>) -> Unit){
        scope.launch {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            val sveAnkete = getUpisaneAnkete()
            var grupeZaAnketu : List<Grupa>
            val listaUpisanihAnketa = mutableListOf<Anketa>()
            sveAnkete.forEach {
                grupeZaAnketu = AnketaRepository.getGroupsForAnketa(it.id)
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



    fun getFutureAnkete(onSuccess: (ankete: List<Anketa>) -> Unit){
        scope.launch {
            var result = getUpisaneAnkete()
            val date = Calendar.getInstance().time
            result =  result.filter { it.datumPocetak > date}
            onSuccess.invoke(result)
        }
    }

    fun getExpiredAnkete(onSuccess: (ankete: List<Anketa>) -> Unit){
        scope.launch {
            val result = getUpisaneAnkete()
            val date = Calendar.getInstance().time
            val zaVratit = mutableListOf<Anketa>()
            for (anketa in result) {
                if(anketa.datumKraj != null && anketa.datumKraj < date){
                    var pokusajiSvi = TakeAnketaRepository.getPoceteAnkete()
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
            if(isOnline(context)) {
                val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(anketaId)
                val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
                var upisana = false

                for (grupa in grupeZaAnketu) {
                    if (upisaneGrupe.contains(grupa)) {
                        upisana = true
                        break
                    }
                }
                onSuccess.invoke(upisana)
            }else{
                val pokusaji = TakeAnketaRepository.getPoceteAnkete()
                if(pokusaji == null || pokusaji.isEmpty()){
                    onSuccess.invoke(false)
                }else{
                    val filtriranoPoAnketiPokusaj = pokusaji.filter { it.AnketumId == anketaId }
                    if(filtriranoPoAnketiPokusaj.isEmpty())
                        onSuccess.invoke(false)
                    else
                        onSuccess.invoke(true)
                }
            }
        }
    }

    fun jeLiZapocetaAnketa(anketaId: Int, onSuccess: (jeLiZapoceta: Boolean) -> Unit) {
        scope.launch {
            val pokusaji = TakeAnketaRepository.getPoceteAnkete()
            if(pokusaji == null || pokusaji.isEmpty()){
                onSuccess.invoke(false)
            }else{
                val filtriranoPoAnketiPokusaj = pokusaji.filter { it.AnketumId == anketaId }
                if(filtriranoPoAnketiPokusaj.isEmpty())
                    onSuccess.invoke(false)
                else
                    onSuccess.invoke(true)
            }
        }
    }


    suspend fun jeLiUpisanaAnketa(anketaId: Int) : Boolean{
        val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(anketaId)
        return grupeZaAnketu.isNotEmpty()
    }
}