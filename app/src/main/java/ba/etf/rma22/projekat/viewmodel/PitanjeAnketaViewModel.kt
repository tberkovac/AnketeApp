package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository

class PitanjeAnketaViewModel {
    suspend fun getPitanja(idAnkete: Int): List<Pitanje> {
        return PitanjeAnketaRepository.getPitanja(idAnkete)
    }
}