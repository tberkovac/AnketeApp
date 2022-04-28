package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.dajIstrazivanjaStaticData
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Korisnik

object IstrazivanjeRepository {
    private val korisnik = Korisnik()

    fun getIstrazivanjeByGodina(godina:Int) : List<Istrazivanje>{
        return dajIstrazivanjaStaticData().filter { istrazivanje -> istrazivanje.godina == godina }
            .ifEmpty { emptyList() }
    }

    fun getAll() : List<Istrazivanje> {
        return dajIstrazivanjaStaticData().ifEmpty { emptyList() }
    }

    fun getUpisani() : List<Istrazivanje>{
        return dajIstrazivanjaStaticData().filter { istrazivanje -> korisnik.companion.upisanaIstrazivanja.contains(istrazivanje) }
            .ifEmpty { emptyList() }
    }
}