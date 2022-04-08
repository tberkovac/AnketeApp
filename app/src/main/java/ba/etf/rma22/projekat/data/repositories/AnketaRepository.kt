package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.dajAnketeStatic
import java.time.LocalDate
import java.util.*


object AnketaRepository {
    var k : Korisnik = Korisnik()
    fun getMyAnkete() : List<Anketa> {
        var naziviUpisanihIstrazivanja : MutableList<String> = mutableListOf()
        var naziviUpisanihGrupa: MutableList<String> = mutableListOf()

        for( i in k.companion.upisanaIstrazivanja) {
            naziviUpisanihIstrazivanja = naziviUpisanihIstrazivanja.plus(i.naziv) as MutableList<String>
        }
        for( i in k.companion.upisaneGrupe) {
            naziviUpisanihGrupa = naziviUpisanihGrupa.plus(i.naziv) as MutableList<String>
        }

        return dajAnketeStatic().filter { anketa -> naziviUpisanihIstrazivanja.contains(anketa.nazivIstrazivanja)}
            .filter { anketa -> k.companion.upisaneGrupe.contains(Grupa(anketa.nazivGrupe,anketa.nazivIstrazivanja)) }
            .ifEmpty { emptyList() }
    }

    fun getAll(): List<Anketa> {
        return dajAnketeStatic().ifEmpty { emptyList() }
    }

    fun getDone(): List<Anketa> {
        return getMyAnkete().filter{ anketa ->  anketa.datumRada!=null}
            .ifEmpty { emptyList() }
    }

    fun getFuture(): List<Anketa> {
        val cal: Calendar = Calendar.getInstance()
        cal.set(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth)
        val date: Date = cal.time

        return getMyAnkete().filter { anketa -> anketa.datumPocetak> date  && anketa.datumRada==null}
            .ifEmpty { emptyList() }
    }

    fun getNotTaken(): List<Anketa> {
        val cal: Calendar = Calendar.getInstance()
        cal.set(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth)
        val date: Date = cal.time

        return getMyAnkete().filter{ anketa -> anketa.datumRada == null && anketa.datumKraj<date }
            .ifEmpty { emptyList() }
    }
}
