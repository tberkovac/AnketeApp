package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik

class KorisnikViewModel {
    fun jeLiUpisanaAnketa(anketa: Anketa): Boolean{
        return Korisnik.upisaneGrupe.contains(Grupa(anketa.nazivGrupe, anketa.nazivIstrazivanja))
    }
}