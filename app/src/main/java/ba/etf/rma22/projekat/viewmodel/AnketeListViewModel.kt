package ba.etf.rma22.projekat.viewmodel

import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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