package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.Anketa
import ba.etf.rma22.projekat.data.AnketaRepository

class AnketeListViewModel {
    fun getAnkete(): List<Anketa>{
        return AnketaRepository.getAnkete()
    }
}