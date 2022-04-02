package ba.etf.rma22.projekat.data

class Korisnik(
    val ime: String = "Testni korisnik",
    var upisanaIstrazivanja: List<Istrazivanje> = listOf(Istrazivanje("lagano istrazivanje",1),
                                                        Istrazivanje("zadovoljavajuce istrazivanje", 2)
    ),
    var upisaneGrupe: List<Grupa> = listOf(Grupa("grupica 1","lagano istrazivanje"),
                                            Grupa("grupica 2","zadovoljavajuce istrazivanje"))
)