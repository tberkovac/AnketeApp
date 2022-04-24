package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.view.FragmentIstrazivanje

object PitanjeAnketaRepository {
    fun getPitanja(nazivAnkete: String, nazivIstrazivanja : String) :List<Pitanje>{
        return listOf(
            Pitanje("pitanje1","tralalala1", listOf("1.1","1.2","1.3")),
            Pitanje("pitanje2","tralalala2", listOf("2.1","2.2","2.3")),
            Pitanje("pitanje3","tralalala3", listOf("3.1","3.2","3.3"))
        )
    }
}