package ba.etf.rma22.projekat.viewmodel

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

     fun getAll(onSuccess : (ankete: List<Anketa>)-> Unit, onError : ()-> Unit) {
        scope.launch {
            val result = AnketaRepository.getAll()
            when (result ) {
                is List<Anketa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
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



     fun jeLiUpisanaAnketa(anketaId: Int, onSuccess: (jeLiUpisana: Boolean) -> Unit) {
        scope.launch {
            val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(anketaId)
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


    suspend fun jeLiUpisanaAnketa(anketaId: Int) : Boolean{
        val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(anketaId)
        return grupeZaAnketu.isNotEmpty()
    }
}