package ba.etf.rma22.projekat.data

object GrupaRepository {
    fun getGroupsByIstrazivanje(nazivIstrazivanja:String) : List<Grupa>{
        return dajGrupeStatic()
    }
}