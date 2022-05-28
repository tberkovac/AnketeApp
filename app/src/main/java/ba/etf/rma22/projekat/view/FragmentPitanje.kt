package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.*
import java.util.*

class FragmentPitanje : Fragment() {

    private lateinit var textPitanja : TextView
    private lateinit var listaOdgovora : ListView
    private lateinit var zaustaviDugme : Button

    //Izbaceni iz konstruktora
    private lateinit var anketa: Anketa
    private lateinit var pitanje: Pitanje

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pitanje, container, false)

        textPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)
        zaustaviDugme = view.findViewById(R.id.dugmeZaustavi)

        pitanje = arguments?.getParcelable("pitanjce")!!
        anketa = arguments?.getParcelable("anketica")!!

        textPitanja.text = pitanje.tekstPitanja

        listaOdgovora.adapter = context?.let {
            OdgovoriListAdapter(anketa, pitanje, it)
        }

        zaustaviDugme.setOnClickListener {
            if(!anketaNijeDostupnaZaRad(anketa)) {
                MainActivity.adapter.removeAll()
             //   izmijeniAnketuUStaticData(anketa)
             //   FragmentAnkete.listaAnketaAdapter.updateAnkete(AnketaStaticData.sveAnkete)
                MainActivity.adapter.add(0, FragmentAnkete())
            }else {
                MainActivity.adapter.removeAll()
                MainActivity.adapter.add(0, FragmentAnkete())
            }
        }

        return view
    }
/*
    private fun obracunajProgres() : Float {
        val brojOdgovorenih = Korisnik.odgovorenaPitanjaAnketa
            .filter { pitanjeAnketa -> pitanjeAnketa.anketa == anketa.naziv && pitanjeAnketa.istrazivanje == anketa.nazivIstrazivanja }
            .size

        val brojPitanja = pitanjeAnketaViewModel.getPitanja(anketa.naziv, anketa.nazivIstrazivanja).size

        return brojOdgovorenih/brojPitanja.toFloat()
    }

 */
    private fun anketaNijeDostupnaZaRad(anketa: Anketa) : Boolean{
        return when {
          //  anketa.datumRada != null -> true
          //  anketa.datumKraj < Calendar.getInstance().time -> true
            anketa.datumPocetak > Calendar.getInstance().time -> true
            else -> false
        }
    }

    companion object {
        fun newInstance(anketa2: Anketa, pitanje2: Pitanje): FragmentPitanje = FragmentPitanje().apply {
            arguments = Bundle().apply {
                anketa = anketa2
                pitanje = pitanje2
                putParcelable("anketica", anketa2)
                putParcelable("pitanjce", pitanje2)
            }
        }
    }
}