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
import ba.etf.rma22.projekat.viewmodel.AnketeListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentAnkete : Fragment() {

    private var anketeListViewModel = AnketeListViewModel()
    private lateinit var spinerFilter : Spinner
    private lateinit var listaAnketa : RecyclerView
    private lateinit var listaAnketaAdapter : AnketaListAdapter
    private lateinit var upisIstrazivanja : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ankete, container, false)

        spinerFilter = view.findViewById(R.id.filterAnketa)
        upisIstrazivanja = view.findViewById(R.id.upisDugme)
        listaAnketa = view.findViewById(R.id.listaAnketa)
        listaAnketa.layoutManager = GridLayoutManager(
            view.context, 2, GridLayoutManager.VERTICAL, false
        )
        listaAnketaAdapter = AnketaListAdapter(arrayListOf())
        listaAnketa.adapter = listaAnketaAdapter
        listaAnketaAdapter.updateAnkete(anketeListViewModel.getAnkete())

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
                    listaAnketaAdapter.updateAnkete(anketeListViewModel.getAnkete())
                else if (vrijednost == podaciZaSpinner[1])
                    listaAnketaAdapter.updateAnkete(anketeListViewModel.getAll())
                else if (vrijednost == podaciZaSpinner[2])
                    listaAnketaAdapter.updateAnkete(anketeListViewModel.getDone())
                else if (vrijednost == podaciZaSpinner[3])
                    listaAnketaAdapter.updateAnkete(anketeListViewModel.getFuture())
                else if (vrijednost == podaciZaSpinner[4])
                    listaAnketaAdapter.updateAnkete(anketeListViewModel.getNotTaken())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        Log.v("POCNE OPET", "DAAA")
        Handler(Looper.getMainLooper()).postDelayed({
            MainActivity.adapter.refreshFragment(1,FragmentIstrazivanje())
        }, 100)
    }
}