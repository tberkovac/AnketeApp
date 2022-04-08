package ba.etf.rma22.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.view.AnketaListAdapter
import ba.etf.rma22.projekat.viewmodel.AnketeListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var spinerFilter : Spinner
    private lateinit var listaAnketa : RecyclerView
    private lateinit var listaAnketaAdapter : AnketaListAdapter
    private lateinit var upisIstrazivanja : FloatingActionButton
    private var anketeListViewModel = AnketeListViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinerFilter = findViewById(R.id.filterAnketa)
        listaAnketa = findViewById(R.id.listaAnketa)
        upisIstrazivanja = findViewById(R.id.upisDugme)

        listaAnketa.layoutManager = GridLayoutManager(
            this, 2, GridLayoutManager.VERTICAL, false
        )
        listaAnketaAdapter = AnketaListAdapter(arrayListOf())

        listaAnketa.adapter = listaAnketaAdapter
        listaAnketaAdapter.updateAnkete(anketeListViewModel.getAnkete())

        upisIstrazivanja.setOnClickListener {
            val intent = Intent(this, UpisIstrazivanje::class.java)
            startActivity(intent)
        }

        val podaciZaSpinner = arrayOf(
            "Sve moje ankete",
            "Sve ankete",
            "Urađene ankete",
            "Buduće ankete",
            "Prošle ankete"
        )

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, podaciZaSpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinerFilter.adapter = adapter
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
    }
}