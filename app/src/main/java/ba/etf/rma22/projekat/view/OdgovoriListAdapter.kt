package ba.etf.rma22.projekat.view

import android.content.Context
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.get
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa

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

        odgovor.isClickable = true
        odgovor.setOnClickListener {
            // Log.v("KLIKNUTO", "OZNACEN JE ODGOVOR " + anketa.naziv + " " + anketa.nazivIstrazivanja + ": " + odgovori[p0])
            var zaZapisat = PitanjeAnketa(pitanje.naziv,anketa.naziv)

            Korisnik.odgovorenaPitanjaAnketa = Korisnik.odgovorenaPitanjaAnketa.plus(zaZapisat)

            Log.v("ZAPISANO", zaZapisat.naziv + " u " + zaZapisat.anketa + " Odgovor je " + pitanje.opcije[p0])
            Log.v("VELICINA LISTE ODGVORENIH", Korisnik.odgovorenaPitanjaAnketa.size.toString())
            odgovor.setTextColor(Color.parseColor("#0000FF"))

            Korisnik.odgovorenaPitanjaSaOdgovorom = Korisnik.odgovorenaPitanjaSaOdgovorom.plus(pitanje to odgovor.text.toString())

        }
        return view
    }

    private fun odgovorOznacenZaPitanje(pitanje: Pitanje, anketa: Anketa): Boolean {
        if(Korisnik.odgovorenaPitanjaAnketa.contains(PitanjeAnketa(pitanje.naziv, anketa.naziv)))
            return true
        return false
    }
}

