/*
package ba.etf.rma22.projekat.viewmodel


import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.GrupaRepository

class GrupaViewModel {
    val korisnik = Korisnik()
    fun getGroupsByIstrazivanje(nazivIstrazivanja : String) : List<Grupa>{
        return GrupaRepository.getGroupsByIstrazivanje(nazivIstrazivanja).ifEmpty { emptyList() }
    }

    fun getUpisaneGrupe() : List<Grupa> {
        return GrupaRepository.getAll()
            .filter { grupa -> korisnik.companion.upisaneGrupe.contains(grupa) }
            .ifEmpty { emptyList() }
    }
}

 */