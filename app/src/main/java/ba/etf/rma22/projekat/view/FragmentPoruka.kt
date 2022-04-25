package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik
import java.lang.IllegalStateException
import java.util.*

class FragmentPoruka: Fragment() {
    private lateinit var textPoruka: TextView
    private lateinit var grupa: Grupa
    private lateinit var odakleJePozvano: String
    private lateinit var anketu: Anketa
    private lateinit var istrazivanje: Istrazivanje

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_poruka, container, false)

        textPoruka = view.findViewById(R.id.poruka)

        odakleJePozvano = requireArguments().getString("odakleDolazi")!!

        if(odakleJePozvano.equals("fragmentPredaj")) {
            anketu = requireArguments().getParcelable("anketica")!!
            grupa = Grupa("null", "null")
            val istrazivanj = Korisnik.upisanaIstrazivanja.filter { istrazivanje -> istrazivanje.naziv == anketu.nazivIstrazivanja }
            val istrazivanje = istrazivanj[0]
            textPoruka.text = "Završili ste anketu ${anketu.naziv} u okviru istraživanja ${istrazivanje.naziv}"
        }else {
            anketu = Anketa("","", Date(), Date(),Date(),0,"",0f)
            istrazivanje = Istrazivanje("",1)
            grupa = arguments?.getParcelable("grupica")!!
            textPoruka.text = "Uspješno ste upisani u grupu ${grupa.naziv} istraživanja ${grupa.nazivIstrazivanja}!"
        }
        return view
    }

    companion object{
        fun newInstance(grupa: Grupa, info: String) : FragmentPoruka = FragmentPoruka().apply {
            arguments = Bundle().apply {
                putParcelable("grupica", grupa)
                putString("odakleDolazi", info)
            }
        }

        fun newInstance(anketa: Anketa, istrazivanje: Istrazivanje, info: String) : FragmentPoruka = FragmentPoruka().apply {
            arguments = Bundle().apply {
                putParcelable("anketica", anketa)
                putParcelable("istazivanjce", istrazivanje)
                putString("odakleDolazi", info)
            }
        }
    }

}