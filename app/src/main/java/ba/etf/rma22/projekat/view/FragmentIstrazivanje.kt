package ba.etf.rma22.projekat.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.UpisIstrazivanje
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository

class FragmentIstrazivanje : Fragment() {
    private lateinit var spinerGodine : Spinner
    private lateinit var spinerIstrazivanja : Spinner
    private lateinit var spinerGrupe : Spinner
    private var korisnik : Korisnik = Korisnik()
    private lateinit var dodajIstrazivanjeDugme : Button
    var companion  = Companion
    companion object {
        var zadnji : String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_istrazivanje, container, false)

        spinerGodine = view.findViewById(R.id.odabirGodina)
        spinerIstrazivanja = view.findViewById(R.id.odabirIstrazivanja)
        spinerGrupe = view.findViewById(R.id.odabirGrupa)
        dodajIstrazivanjeDugme = view.findViewById(R.id.dodajIstrazivanjeDugme)

        val godine = listOf( "","1","2","3","4","5")

        val adapterGodineSpiner : ArrayAdapter<String> = ArrayAdapter(
            view.context,android.R.layout.simple_spinner_item,godine
        )
        val adapterIstrazivanjaSpiner : ArrayAdapter<String> = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_item, ArrayList<String>()
        )
        val adapterGrupeSpiner : ArrayAdapter<String> = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_item, ArrayList<String>()
        )

        spinerGodine.adapter = adapterGodineSpiner
        spinerIstrazivanja.adapter = adapterIstrazivanjaSpiner
        spinerGrupe.adapter = adapterGrupeSpiner

        spinerGodine.setSelection(adapterGodineSpiner.getPosition(UpisIstrazivanje.zadnji))

        spinerGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                UpisIstrazivanje.zadnji = spinerGodine.selectedItem.toString()
                if(spinerGodine.selectedItem == "") {
                    adapterIstrazivanjaSpiner.clear()
                    adapterIstrazivanjaSpiner.add("")
                    adapterGrupeSpiner.clear()
                    adapterGrupeSpiner.add("")
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                adapterIstrazivanjaSpiner.clear()
                adapterIstrazivanjaSpiner.add("")
                adapterIstrazivanjaSpiner.addAll(
                    IstrazivanjeRepository.getIstrazivanjeByGodina(spinerGodine.selectedItem.toString().toInt())
                        .filter { istrazivanje -> !korisnik.companion.upisanaIstrazivanja.contains(istrazivanje) }
                        . map { istrazivanje -> istrazivanje.naziv })
                adapterIstrazivanjaSpiner.notifyDataSetChanged()
                UpisIstrazivanje.zadnji = godine[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinerGodine.setSelection(UpisIstrazivanje.zadnji.toInt())
            }
        }

        spinerIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(spinerIstrazivanja.selectedItem == "") {
                    adapterGrupeSpiner.clear()
                    adapterGrupeSpiner.add("")
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                adapterGrupeSpiner.clear()
                adapterGrupeSpiner.add("")
                adapterGrupeSpiner
                    .addAll(
                        GrupaRepository.getGroupsByIstrazivanje(spinerIstrazivanja.selectedItem.toString())
                            .filter { grupa -> !korisnik.companion.upisaneGrupe.contains(grupa) }
                            .map { grupa -> grupa.naziv })
                adapterGrupeSpiner.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                dodajIstrazivanjeDugme.isEnabled = false
                adapterGrupeSpiner.clear()
            }
        }

        spinerGrupe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(spinerGrupe.selectedItem == "") {
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                dodajIstrazivanjeDugme.isEnabled = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                dodajIstrazivanjeDugme.isEnabled = false
            }

        }

        dodajIstrazivanjeDugme.setOnClickListener{
            if(spinerGodine.selectedItem != "" && spinerIstrazivanja.selectedItem !=""
                && spinerGrupe.selectedItem != ""){
                korisnik.companion.upisanaIstrazivanja = korisnik.companion.upisanaIstrazivanja.plus(
                    Istrazivanje(spinerIstrazivanja.selectedItem.toString(),spinerGodine.selectedItemPosition)
                )
                korisnik.companion.upisaneGrupe = korisnik.companion.upisaneGrupe.plus(Grupa(spinerGrupe.selectedItem.toString(),spinerIstrazivanja.selectedItem.toString()))

                adapterIstrazivanjaSpiner.remove(spinerIstrazivanja.selectedItem.toString())
                adapterGrupeSpiner.notifyDataSetChanged()
                adapterIstrazivanjaSpiner.notifyDataSetChanged()
                val intent = Intent(view.context, MainActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }
}