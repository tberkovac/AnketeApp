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

class FragmentPoruka(grupa2: Grupa) : Fragment() {
    private lateinit var textPoruka: TextView
    private var grupa: Grupa = grupa2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_poruka, container, false)

        textPoruka = view.findViewById(R.id.poruka)

        textPoruka.text = "Uspješno ste upisani u grupu ${grupa.naziv} istraživanja ${grupa.nazivIstrazivanja}!"

        return view
    }

}