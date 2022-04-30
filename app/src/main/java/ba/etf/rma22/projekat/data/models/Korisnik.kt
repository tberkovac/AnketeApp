package ba.etf.rma22.projekat.data.models

class Korisnik {
    val ime: String = "Testni korisnik"
    companion object{
        var upisanaIstrazivanja: MutableList<Istrazivanje> = mutableListOf(
            Istrazivanje("lagano istrazivanje", 1),
            Istrazivanje("zadovoljavajuce istrazivanje", 2)
        )
        var upisaneGrupe: MutableList<Grupa> = mutableListOf(
            Grupa("grupica 1", "lagano istrazivanje"),
            Grupa("grupica 2", "zadovoljavajuce istrazivanje")
        )
        var odgovorenaPitanjaAnketa: MutableList<PitanjeAnketa> = mutableListOf()
        var odgovorenaPitanjaSaOdgovorom: MutableList<Pair<Pitanje,String>> = mutableListOf()
    }
    var companion = Companion
}