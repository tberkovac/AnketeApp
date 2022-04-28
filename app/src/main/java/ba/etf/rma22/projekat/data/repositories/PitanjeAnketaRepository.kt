package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.staticdata.dajPitanjaStaticData
import ba.etf.rma22.projekat.data.staticdata.dajPitanjeAnkete
import ba.etf.rma22.projekat.view.FragmentIstrazivanje

object PitanjeAnketaRepository {
    fun getPitanja(nazivAnkete: String, nazivIstrazivanja : String) :List<Pitanje>{
        val pitanjeAnkete = dajPitanjeAnkete().filter { pitanjeAnketa -> pitanjeAnketa.anketa == nazivAnkete && pitanjeAnketa.istrazivanje == nazivIstrazivanja }
                                .ifEmpty { listOf() }
        return dajPitanjaStaticData()
            .filter { pitanje -> pitanjeAnkete.contains(PitanjeAnketa( pitanje.naziv,nazivAnkete, nazivIstrazivanja)) }
            .ifEmpty { listOf() }
    }
}