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
        var odgovorenaPitanjaAnketa: MutableList<PitanjeAnketa> = mutableListOf(
            PitanjeAnketa("pitanje1","Anketa 3","lagano istrazivanje"),
            PitanjeAnketa("pitanje3","Anketa 3","lagano istrazivanje"),
        )
        var odgovorenaPitanjaSaOdgovorom: MutableList<Pair<PitanjeAnketa,String>> = mutableListOf(
            PitanjeAnketa("pitanje1", "Anketa 3", "lagano istrazivanje") to "da",
            PitanjeAnketa("pitanje3", "Anketa 3", "lagano istrazivanje") to "zelena"
        )
    }
    var companion = Companion
}