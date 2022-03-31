package ba.etf.rma22.projekat.data


object AnketaRepository {
    fun getAnkete() : List<Anketa> {
        return dajAnketeStatic()
    }
}
