package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.dajIstrazivanjaStaticData

object IstrazivanjeRepository {
    fun getIstrazivanjeByGodina(godina:Int) : List<Istrazivanje>{
        return dajIstrazivanjaStaticData().filter { istrazivanje -> istrazivanje.godina == godina }
    }

    fun getAll() : List<Istrazivanje> {
        return dajIstrazivanjaStaticData()
    }

    fun getUpisani() : List<Istrazivanje>{
        return listOf()
    }
}