package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.view.FragmentIstrazivanje

object PitanjeAnketaRepository {
    fun getPitanja(nazivAnkete: String, nazivIstrazivanja : String) :List<Pitanje>{
        return listOf(
            Pitanje("pitanje1","tralalala", listOf("1","2","3")),
            Pitanje("pitanje2","tralalala", listOf("1","2","3")),
            Pitanje("pitanje3","tralalala", listOf("1","2","3"))
        )
    }
}