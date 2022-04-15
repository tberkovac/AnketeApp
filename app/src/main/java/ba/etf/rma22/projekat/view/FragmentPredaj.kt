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
import ba.etf.rma22.projekat.data.models.Anketa

class FragmentPredaj(private var anketa: Anketa) : Fragment() {
    private lateinit var postotakUradjenosti : TextView
    private lateinit var predajDugme : Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_predaj, container, false)

        postotakUradjenosti = view.findViewById(R.id.progresTekst)
        predajDugme = view.findViewById(R.id.dugmePredaj)

        postotakUradjenosti.text = "Treba napraviti"

        predajDugme.setOnClickListener {
            //anketa.progres = odrediti u ovisnosti od odgovorenih
            //implementirati i cuvanje odgovora
            MainActivity.adapter.removeAll()
            MainActivity.adapter.add(0, FragmentAnkete())
        }



        return view
    }
}