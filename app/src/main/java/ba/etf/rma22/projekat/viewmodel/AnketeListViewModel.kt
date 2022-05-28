package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository

class AnketeListViewModel {
    suspend fun getAll(offset: Int) : List<Anketa> {
        return AnketaRepository.getAll(offset)
    }

    suspend fun getAll() : List<Anketa> {
        return AnketaRepository.getAll()
    }

    suspend fun jeLiUpisanaAnketa(anketaId: Int) : Boolean {
        val grupeZaAnketu = AnketaRepository.getGroupsForAnketa(anketaId)
        if(grupeZaAnketu.isNotEmpty())
            return true
        return false
    }
}