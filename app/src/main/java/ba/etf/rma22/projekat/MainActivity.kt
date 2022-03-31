package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.view.AnketaListAdapter
import ba.etf.rma22.projekat.viewmodel.AnketeListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var spinerFilter: Spinner
    private lateinit var listaAnketa: RecyclerView
    private lateinit var listaAnketaAdapter: AnketaListAdapter
    private var anketeListViewModel = AnketeListViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinerFilter = findViewById(R.id.filterAnketa)
        listaAnketa = findViewById(R.id.listaAnketa)

        listaAnketa.layoutManager = GridLayoutManager(
            this, 2
        )
        listaAnketaAdapter = AnketaListAdapter(arrayListOf())

        listaAnketa.adapter = listaAnketaAdapter
        listaAnketaAdapter.updateAnkete(anketeListViewModel.getAnkete())
    }
}