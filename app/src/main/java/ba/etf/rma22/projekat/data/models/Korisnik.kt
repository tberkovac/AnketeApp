package ba.etf.rma22.projekat.data.models

class Korisnik {

    val ime: String = "Testni korisnik"
    companion object{
        var upisanaIstrazivanja: List<Istrazivanje> = listOf(
            Istrazivanje("lagano istrazivanje", 1),
            Istrazivanje("zadovoljavajuce istrazivanje", 2)
        )
        var upisaneGrupe: List<Grupa> = listOf(
            Grupa("grupica 1", "lagano istrazivanje"),
            Grupa("grupica 2", "zadovoljavajuce istrazivanje")
        )
    }
    var companion = Companion
}