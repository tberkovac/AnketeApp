package ba.etf.rma22.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class FragmentPredaj : Fragment() {
    private lateinit var postotakUradjenosti : TextView
    private lateinit var anketa: Anketa
    private var progres = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val predajDugme : Button
        val view = inflater.inflate(R.layout.fragment_predaj, container, false)

        postotakUradjenosti = view.findViewById(R.id.progresTekst)
        predajDugme = view.findViewById(R.id.dugmePredaj)

        anketa = arguments?.getParcelable("anketica")!!


        odrediProgres(postotakUradjenosti, anketa.id)
/*
        if(anketaNijeDostupnaZaRad(anketa)) {
            predajDugme.isEnabled = false
        } else {
            predajDugme.setOnClickListener {
            }
        }
 */
        predajDugme.isEnabled = false
        return view
    }

    companion object{
        fun newInstance(anketa: Anketa) : FragmentPredaj = FragmentPredaj().apply {
            arguments = Bundle().apply {
                putParcelable("anketica", anketa)
            }
        }
    }

    fun anketaNijeDostupnaZaRad(anketa: Anketa) : Boolean{
        return when {
           // anketa.datumRada != null -> true
          //  anketa.datumKraj < Calendar.getInstance().time -> true
            anketa.datumPocetak > Calendar.getInstance().time -> true
            else -> false
        }
    }

    fun zaokruziProgres(progres: Float): Int {
        var rez : Int = (progres*10).toInt()
        if(rez % 2 != 0) rez += 1
        rez *= 10
        return rez
    }

    fun odrediProgres(postotakUradjenosti: TextView, anketaId: Int) {
        GlobalScope.launch {
            progres = OdgovorRepository.obracunajProgresZaAnketuZaokruzeni(anketaId).toString()
            postotakUradjenosti.text = "$progres%"
        }
    }

}