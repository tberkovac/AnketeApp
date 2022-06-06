package ba.etf.rma22.projekat.viewmodel

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

    fun getAllIstrazivanja(onSuccess: (istrazivanja: List<Istrazivanje>) -> Unit,
                                   onError: () -> Unit) {
        scope.launch {
            val result = IstrazivanjeIGrupaRepository.getIstrazivanja()

            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }

    }

     fun getAllIstrazivanjaByGodina(godina : Int, onSuccess: (istrazivanja: List<Istrazivanje>) -> Unit,
                                           onError: () -> Unit) {
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getIstrazivanja()
                            .filter { istrazivanje -> istrazivanje.godina == godina  }

            when (result) {
                is List<Istrazivanje> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

     fun getGrupeByIstrazivanje(idIstrazivanje: Int, onSuccess: (grupe: List<Grupa>) -> Unit,
                                            onError: () -> Unit) {
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(idIstrazivanje)

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
}