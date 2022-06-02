package ba.etf.rma22.projekat.viewmodel

import android.util.Log
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
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
            val result = IstrazivanjeIGrupaRepository.getGrupeByIstrazivanje(idIstrazivanje)

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
}