package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.AnketaRepository

class AnketeListViewModel {
    fun getAnkete(): List<Anketa>{
        return AnketaRepository.getMyAnkete()
    }

    fun getAll(): List<Anketa> {
        return AnketaRepository.getAll()
    }

    fun getDone(): List<Anketa> {
        return AnketaRepository.getDone()
    }

    fun getFuture(): List<Anketa> {
        return AnketaRepository.getFuture()
    }

    fun getNotTaken(): List<Anketa> {
        return AnketaRepository.getNotTaken()
    }
}