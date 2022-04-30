package ba.etf.rma22.projekat.view

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
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel

class FragmentIstrazivanje : Fragment() {
    private lateinit var spinerGodine : Spinner
    private lateinit var spinerIstrazivanja : Spinner
    private lateinit var spinerGrupe : Spinner
    private var korisnik : Korisnik = Korisnik()
    private var istrazivanjeViewModel = IstrazivanjeViewModel()
    private var grupaViewModel = GrupaViewModel()
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

        spinerGodine.setSelection(adapterGodineSpiner.getPosition(zadnji))

        spinerGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                zadnji = spinerGodine.selectedItem.toString()
                if(spinerGodine.selectedItem == "") {
                    resetujIstrazivanjeSpinerAdapter(adapterIstrazivanjaSpiner)
                    resetujGrupeSpinerAdapter(adapterGrupeSpiner)
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                resetujIstrazivanjeSpinerAdapter(adapterIstrazivanjaSpiner)
                popuniIstrazivanja(adapterIstrazivanjaSpiner, spinerGodine.selectedItem.toString().toInt())
                zadnji = godine[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinerGodine.setSelection(zadnji.toInt())
            }
        }

        spinerIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(spinerIstrazivanja.selectedItem == "") {
                    resetujGrupeSpinerAdapter(adapterGrupeSpiner)
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                resetujGrupeSpinerAdapter(adapterGrupeSpiner)
                popuniGrupamaGrupeSpinerAdapter(adapterGrupeSpiner, spinerIstrazivanja.selectedItem.toString())
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
            if(jeLiSveOdabrano()) {
                val istrazivanje = Istrazivanje(spinerIstrazivanja.selectedItem.toString(),spinerGodine.selectedItemPosition)
                korisnik.companion.upisanaIstrazivanja.add(istrazivanje)

                val grupa = Grupa(spinerGrupe.selectedItem.toString(),spinerIstrazivanja.selectedItem.toString())
                korisnik.companion.upisaneGrupe.add(grupa)

                izbaciIstrazivanjeIGrupu(adapterIstrazivanjaSpiner, adapterGrupeSpiner)
                prikaziPoruku(grupa)
            }
        }

        return view
    }

    private fun izbaciIstrazivanjeIGrupu(
        adapterIstrazivanjaSpiner: ArrayAdapter<String>,
        adapterGrupeSpiner: ArrayAdapter<String>
    ) {
        adapterGrupeSpiner.remove(spinerGrupe.selectedItem.toString())
        adapterIstrazivanjaSpiner.remove(spinerIstrazivanja.selectedItem.toString())
        adapterGrupeSpiner.notifyDataSetChanged()
        adapterIstrazivanjaSpiner.notifyDataSetChanged()
    }

    private fun resetujIstrazivanjeSpinerAdapter(adapterIstrazivanjaSpiner: ArrayAdapter<String>) {
        adapterIstrazivanjaSpiner.clear()
        adapterIstrazivanjaSpiner.add("")
    }

    private fun resetujGrupeSpinerAdapter(adapterGrupeSpiner: ArrayAdapter<String>) {
        adapterGrupeSpiner.clear()
        adapterGrupeSpiner.add("")
    }

    private fun popuniIstrazivanja(adapterIstrazivanjaSpiner: ArrayAdapter<String>, godina: Int) {
        val upisanaIstrazivanja = istrazivanjeViewModel.getUpisani()
        adapterIstrazivanjaSpiner.addAll(
            istrazivanjeViewModel.getIstrazivanjeByGodina(godina)
                .filter { istrazivanje -> !upisanaIstrazivanja.contains(istrazivanje) }
                    . map { istrazivanje -> istrazivanje.naziv })
        adapterIstrazivanjaSpiner.notifyDataSetChanged()
    }

    private fun popuniGrupamaGrupeSpinerAdapter(
        adapterGrupeSpiner: ArrayAdapter<String>,
        nazivIstrazivanja: String
    ) {
        val upisaneGrupe = grupaViewModel.getUpisaneGrupe()
        adapterGrupeSpiner
            .addAll(
                grupaViewModel.getGroupsByIstrazivanje(nazivIstrazivanja)
                    .filter { grupa -> !upisaneGrupe.contains(grupa) }
                        .map { grupa -> grupa.naziv })
        adapterGrupeSpiner.notifyDataSetChanged()
    }

    private fun jeLiSveOdabrano() : Boolean{
        if(spinerGodine.selectedItem != "" && spinerIstrazivanja.selectedItem !=""
            && spinerGrupe.selectedItem != "") return true
        return false
    }

    private fun prikaziPoruku(grupa: Grupa) {
        MainActivity.adapter.refreshFragment(1,FragmentPoruka.newInstance(grupa,"izFragmentIstrazivanje"))
        MainActivity.adapter.notifyDataSetChanged()
    }


}