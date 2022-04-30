package ba.etf.rma22.projekat.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import java.util.*

class OdgovoriListAdapter(
    private var anketa: Anketa,
    private var pitanje: Pitanje,
    private val mcontext: Context
) : BaseAdapter(){

    override fun getCount(): Int {
        return pitanje.opcije.count()
    }

    override fun getItem(p0: Int): Any {
        return pitanje.opcije[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var odgovor: TextView
        var view = LayoutInflater.from(mcontext).inflate(R.layout.odgovor_element, p2, false)
        val lv = view.findViewById<ListView>(R.id.odgovoriLista)
        odgovor = view.findViewById(R.id.odgovor)

        odgovor.text = pitanje.opcije[p0]

        if(odgovorOznacenZaPitanje(pitanje, anketa)) {
            var index : Int
            if(Korisnik.odgovorenaPitanjaSaOdgovorom.contains(pitanje to odgovor.text)) {
                index = Korisnik.odgovorenaPitanjaSaOdgovorom.indexOf(pitanje to odgovor.text)
                if (odgovor.text.equals(Korisnik.odgovorenaPitanjaSaOdgovorom.get(index).second)) {
                    odgovor.setTextColor(Color.parseColor("#0000FF"))
                }
            }
        }
        if(!anketaNijeDostupnaZaRad(anketa)) {
            odgovor.isClickable = true
            odgovor.setOnClickListener {
                var zaZapisatOdgovor = PitanjeAnketa(pitanje.naziv, anketa.naziv, anketa.nazivIstrazivanja)
                Korisnik.odgovorenaPitanjaAnketa.add(zaZapisatOdgovor)
                odgovor.setTextColor(Color.parseColor("#0000FF"))
                Korisnik.odgovorenaPitanjaSaOdgovorom.add(pitanje to odgovor.text.toString())
            }
        }else{
            odgovor.isClickable = false
        }
        return view
    }

    private fun odgovorOznacenZaPitanje(pitanje: Pitanje, anketa: Anketa): Boolean {
        if(Korisnik.odgovorenaPitanjaAnketa.contains(PitanjeAnketa(pitanje.naziv, anketa.naziv, anketa.nazivIstrazivanja)))
            return true
        return false
    }

    fun anketaNijeDostupnaZaRad(anketa: Anketa) : Boolean{
        return when {
            anketa.datumRada != null -> true
            anketa.datumKraj < Calendar.getInstance().time -> true
            anketa.datumPocetak > Calendar.getInstance().time -> true
            else -> false
        }
    }

}

