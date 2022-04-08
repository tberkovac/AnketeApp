package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.dajGrupeStatic

object GrupaRepository {
    fun getGroupsByIstrazivanje(nazivIstrazivanja : String) : List<Grupa>{
        return dajGrupeStatic().filter { grupa -> grupa.nazivIstrazivanja.equals(nazivIstrazivanja) }
            .ifEmpty { emptyList() }
    }
}