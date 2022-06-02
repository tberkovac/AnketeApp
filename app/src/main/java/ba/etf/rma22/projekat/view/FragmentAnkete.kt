package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.AnketeListViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import ba.etf.rma22.projekat.viewmodel.TakeAnketaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentAnkete : Fragment() {

    private var anketeListViewModel = AnketeListViewModel()
    private lateinit var spinerFilter : Spinner
    private lateinit var listaAnketa : RecyclerView
    private val takeAnketaViewModel = TakeAnketaViewModel()
    private val pitanjeAnketaViewModel = PitanjeAnketaViewModel()
    private var pitanja : List<Pitanje> = listOf()

    companion object{
        lateinit var listaAnketaAdapter : AnketaListAdapter
    }

    private fun onSuccessGetPitanaja(anketa: Anketa, vracenaPitanja : List<Pitanje>){
        var pokusaj : AnketaTaken
        GlobalScope.launch (Dispatchers.Main) {
            pokusaj =  takeAnketaViewModel.zapocniAnketu(anketa.id)
            pitanja = vracenaPitanja
            pitanja.forEach { pitanje -> MainActivity.adapter.addOnLastPosition(FragmentPitanje.newInstance(anketa, pitanje, pokusaj)) }
            MainActivity.adapter.addOnLastPosition(FragmentPredaj.newInstance(anketa))
        }

    }



    private fun prikaziAnketu(anketa: Anketa) {
        Log.v("POZOVE SE", "OTVORIT ANKETU ${anketa.id}")
        MainActivity.adapter.removeAll()
        pitanjeAnketaViewModel.getPitanja(anketa, ::onSuccessGetPitanaja)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ankete, container, false)

        spinerFilter = view.findViewById(R.id.filterAnketa)
        listaAnketa = view.findViewById(R.id.listaAnketa)
        listaAnketa.layoutManager = GridLayoutManager(
            view.context, 2, GridLayoutManager.VERTICAL, false
        )
        listaAnketaAdapter = AnketaListAdapter(arrayListOf()) {
            prikaziAnketu(it)
        }

        listaAnketa.adapter = listaAnketaAdapter
        anketeListViewModel.getAll(::onSuccessGetAllAnkete, ::onError)

        val podaciZaSpinner = arrayOf(
            "Sve moje ankete",
            "Sve ankete",
            "Urađene ankete",
            "Buduće ankete",
            "Prošle ankete"
        )

        val spinerAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, podaciZaSpinner)

        spinerFilter.adapter = spinerAdapter
        spinerFilter.setSelection(0)

        spinerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val vrijednost: String = podaciZaSpinner[p2]
                if (vrijednost == podaciZaSpinner[0])
                    anketeListViewModel.getAll(::onSuccessGetAllAnkete, ::onError)
                else if (vrijednost == podaciZaSpinner[1])
                    anketeListViewModel.getAll(::onSuccessGetAllAnkete, ::onError)
                else if (vrijednost == podaciZaSpinner[2])
                    anketeListViewModel.getAll(::onSuccessGetAllAnkete, ::onError)
                else if (vrijednost == podaciZaSpinner[3])
                    anketeListViewModel.getAll(::onSuccessGetAllAnkete, ::onError)
                else if (vrijednost == podaciZaSpinner[4])
                    anketeListViewModel.getAll(::onSuccessGetAllAnkete, ::onError)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).post {
            MainActivity.adapter.notifyDataSetChanged()
            MainActivity.adapter.refreshFragment(1, FragmentIstrazivanje())
        }
        spinerFilter.setSelection(0)
        anketeListViewModel.getAll(::onSuccessGetAllAnkete, ::onError)
    }

    private fun onSuccessGetAllAnkete(ankete: List<Anketa>) {
        listaAnketaAdapter.updateAnkete(ankete)
        listaAnketaAdapter.notifyDataSetChanged()
    }

    private fun onError(){
        Log.v("Greska", "greska")
    }
}