package ba.etf.rma22.projekat.view

import android.content.Intent
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
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.viewmodel.AnketeListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentAnkete : Fragment() {

    private var anketeListViewModel = AnketeListViewModel()
    private lateinit var spinerFilter : Spinner
    private lateinit var listaAnketa : RecyclerView

    companion object{
        lateinit var listaAnketaAdapter : AnketaListAdapter
    }

    private suspend fun prikaziAnketu(anketa: Anketa) {
        MainActivity.adapter.removeAll()
        var pitanja : List<Pitanje> = PitanjeAnketaRepository.getPitanja(anketa.id)
        pitanja.forEach { pitanje -> MainActivity.adapter.addOnLastPosition(FragmentPitanje.newInstance(anketa, pitanje)) }
        MainActivity.adapter.addOnLastPosition(FragmentPredaj.newInstance(anketa))
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
        activity?.runOnUiThread {
            GlobalScope.launch(Dispatchers.Main) {
                Log.v("Korutina", " zapoceta")
                listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
            }
        }

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
                    activity?.runOnUiThread {
                        GlobalScope.launch(Dispatchers.Main) {
                            Log.v("Korutina", " zapoceta")
                            listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
                        }
                    }
                else if (vrijednost == podaciZaSpinner[1])
                    activity?.runOnUiThread {
                        GlobalScope.launch(Dispatchers.Main) {
                            Log.v("Korutina", " zapoceta")
                            listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
                        }
                    }
                else if (vrijednost == podaciZaSpinner[2])
                    activity?.runOnUiThread {
                        GlobalScope.launch(Dispatchers.Main) {
                            Log.v("Korutina", " zapoceta")
                            listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
                        }
                    }
                else if (vrijednost == podaciZaSpinner[3])
                    activity?.runOnUiThread {
                        GlobalScope.launch(Dispatchers.Main) {
                            Log.v("Korutina", " zapoceta")
                            listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
                        }
                    }
                else if (vrijednost == podaciZaSpinner[4])
                    activity?.runOnUiThread {
                        GlobalScope.launch(Dispatchers.Main) {
                            Log.v("Korutina", " zapoceta")
                            listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
                        }
                    }
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
        activity?.runOnUiThread {
            GlobalScope.launch(Dispatchers.Main){
                Log.v("Korutina", " zapoceta")
                listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
            }
        }

    }
}