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
}