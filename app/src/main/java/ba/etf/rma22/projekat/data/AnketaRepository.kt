package ba.etf.rma22.projekat.data

import java.time.LocalDate
import java.util.*


object AnketaRepository {
    fun getMyAnkete() : List<Anketa> {
        return dajAnketeStatic()
    }

    fun getAll(): List<Anketa> {
        return dajAnketeStatic()
    }

    fun getDone(): List<Anketa> {
        return dajAnketeStatic().filter{anketa ->  anketa.datumRada!=null}.toList()
    }

    fun getFuture(): List<Anketa> {
        val cal: Calendar = Calendar.getInstance()
        cal.set(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth)
        val date: Date = cal.time
        return getAll().filter { anketa -> anketa.datumPocetak> date}
    }

    fun getNotTaken(): List<Anketa> {
        return getAll().filter{ anketa -> anketa.datumRada == null }
    }
}
