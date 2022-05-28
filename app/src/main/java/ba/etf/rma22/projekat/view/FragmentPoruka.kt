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
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeIGrupaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentPoruka: Fragment() {
    private lateinit var textPoruka: TextView
    private lateinit var grupa: Grupa
    private lateinit var odakleJePozvano: String
    private lateinit var anketu: Anketa
    private lateinit var istrazivanje: Istrazivanje
    private var istrazivanjeIGrupaViewModel = IstrazivanjeIGrupaViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poruka, container, false)

        textPoruka = view.findViewById(R.id.poruka)

        odakleJePozvano = requireArguments().getString("odakleDolazi")!!

        if(odakleJePozvano == "fragmentPredaj") {
         //   anketu = requireArguments().getParcelable("anketica")!!
         //   grupa = Grupa("null", "null")
         //   val istrazivanj = Korisnik.upisanaIstrazivanja.filter { istrazivanje -> istrazivanje.naziv == anketu.nazivIstrazivanja }
        //    val istrazivanje = istrazivanj[0]
            textPoruka.text = "Završili ste anketu ${anketu.naziv} u okviru istraživanja ${istrazivanje.naziv}"
        }else {
            grupa = arguments?.getParcelable("grupica")!!
            GlobalScope.launch(Dispatchers.Main) {
                istrazivanje = istrazivanjeIGrupaViewModel.getIstrazivanjeByGroupId(grupa.id)
                textPoruka.text = "Uspješno ste upisani u grupu ${grupa.naziv} istraživanja ${istrazivanje.naziv}!"
            }
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