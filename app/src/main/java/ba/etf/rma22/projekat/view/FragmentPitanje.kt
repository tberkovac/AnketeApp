package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Pitanje

class FragmentPitanje(private var pitanje: Pitanje) : Fragment() {
    private lateinit var textPitanja : TextView
    private lateinit var listaOdgovora : ListView
    private lateinit var zaustaviDugme : Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pitanje, container, false)

        textPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)
        zaustaviDugme = view.findViewById(R.id.dugmeZaustavi)

        textPitanja.text = pitanje.tekst

        val adapter = this.context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, pitanje.opcije) }
        listaOdgovora.adapter = adapter

        zaustaviDugme.setOnClickListener {
            MainActivity.adapter.removeAll()
            MainActivity.adapter.add(0, FragmentAnkete())
        }

        return view
    }
}