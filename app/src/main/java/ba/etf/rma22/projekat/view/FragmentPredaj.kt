package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import org.hamcrest.core.Is
import java.util.*

class FragmentPredaj : Fragment() {
    private lateinit var postotakUradjenosti : TextView

    private var pitanjeAnketaViewModel = PitanjeAnketaViewModel()
    private lateinit var anketa: Anketa
    private lateinit var istrazivanje: Istrazivanje

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var predajDugme : Button
        var view = inflater.inflate(R.layout.fragment_predaj, container, false)

        postotakUradjenosti = view.findViewById(R.id.progresTekst)
        predajDugme = view.findViewById(R.id.dugmePredaj)

        anketa = arguments?.getParcelable("anketica")!!

        var postotak : Float

        var brojOdgovorenih = Korisnik.odgovorenaPitanjaAnketa
                            .filter { pitanjeAnketa -> pitanjeAnketa.anketa == anketa.naziv }.size

        var brojPitanja = pitanjeAnketaViewModel.getPitanja(anketa.naziv, anketa.nazivIstrazivanja).size

        postotak = brojOdgovorenih/brojPitanja.toFloat()

        anketa.progres = postotak

         fun zaokruziProgres(progres: Float): Int {
            var rez : Int = (progres*10).toInt()
            if(rez % 2 != 0) rez += 1
            rez *= 10
            return rez
        }

        postotakUradjenosti.text = zaokruziProgres(postotak).toString() + "%"

        predajDugme.setOnClickListener {
            val date = Calendar.getInstance()
            anketa.datumRada = date.time
            MainActivity.adapter.removeAll()
            MainActivity.adapter.add(0, FragmentAnkete())
            val istrazivanje = Korisnik.upisanaIstrazivanja.filter { istrazivanje -> istrazivanje.naziv == anketa.nazivIstrazivanja }
            MainActivity.adapter.add(1, FragmentPoruka.newInstance(anketa, istrazivanje[0] ,"fragmentPredaj"))
            MainActivity.viewPager2.currentItem = 1
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        var postotak : Float

        var brojOdgovorenih = Korisnik.odgovorenaPitanjaAnketa
            .filter { pitanjeAnketa -> pitanjeAnketa.anketa == anketa.naziv }.size

        var brojPitanja = pitanjeAnketaViewModel.getPitanja(anketa.naziv, anketa.nazivIstrazivanja).size

        postotak = brojOdgovorenih/brojPitanja.toFloat()

        anketa.progres = postotak

        fun zaokruziProgres(progres: Float): Int {
            var rez : Int = (progres*10).toInt()
            if(rez % 2 != 0) rez += 1
            rez *= 10
            return rez
        }

        postotakUradjenosti.text = zaokruziProgres(postotak).toString() + "%"
    }

    companion object{
        fun newInstance(anketa: Anketa) : FragmentPredaj = FragmentPredaj().apply {
            arguments = Bundle().apply {
                putParcelable("anketica", anketa)
            }
        }
    }
}