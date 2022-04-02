package ba.etf.rma22.projekat.data

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