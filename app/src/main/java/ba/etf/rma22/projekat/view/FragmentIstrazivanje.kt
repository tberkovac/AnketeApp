package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.util.Log
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
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeIGrupaViewModel
import kotlinx.coroutines.*

class FragmentIstrazivanje : Fragment() {
    private lateinit var spinerGodine : Spinner
    private lateinit var spinerIstrazivanja : Spinner
    private lateinit var spinerGrupe : Spinner
    private var istrazivanjeIGrupaViewModel = IstrazivanjeIGrupaViewModel()

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
        val adapterIstrazivanjaSpiner : ArrayAdapter<Istrazivanje> = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_item, ArrayList<Istrazivanje>()
        )
        val adapterGrupeSpiner : ArrayAdapter<Grupa> = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_item, ArrayList<Grupa>()
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
                GlobalScope.launch(Dispatchers.Main) {
                    Log.v("Istrazivanje korutina" , "zapoceta")
                    popuniIstrazivanja(adapterIstrazivanjaSpiner, spinerGodine.selectedItem.toString().toInt())
                    Log.v("Istrazivanje korutina", "zavrsena")
                }

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
                if(spinerIstrazivanja.selectedItem != "")
                GlobalScope.launch(Dispatchers.Main) {
                    popuniGrupamaGrupeSpinerAdapter(adapterGrupeSpiner, (spinerIstrazivanja.selectedItem as Istrazivanje).id)
                }
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
                val grupa = spinerGrupe.selectedItem as Grupa
                GlobalScope.launch (Dispatchers.Main){
                    istrazivanjeIGrupaViewModel.getUpisaneGrupe()


                    val dalje = istrazivanjeIGrupaViewModel.upisiUGrupu(grupa.id)
                    Log.v("VRIJEDNOST JE", dalje.toString())
                    if(dalje)
                        prikaziPoruku(grupa)

                }
            }
        }

        return view
    }

    private fun resetujIstrazivanjeSpinerAdapter(adapterIstrazivanjaSpiner: ArrayAdapter<Istrazivanje>) {
        adapterIstrazivanjaSpiner.clear()
        adapterIstrazivanjaSpiner.add(Istrazivanje(0,"",0))
    }

    private fun resetujGrupeSpinerAdapter(adapterGrupeSpiner: ArrayAdapter<Grupa>) {
        adapterGrupeSpiner.clear()
        adapterGrupeSpiner.add(Grupa(-1,""))
    }

    private suspend fun popuniIstrazivanja(adapterIstrazivanjaSpiner: ArrayAdapter<Istrazivanje>, godina: Int) {
        val istrazivanja = istrazivanjeIGrupaViewModel.getAllIstrazivanjaByGodina(godina)
        adapterIstrazivanjaSpiner.addAll(istrazivanja)
        Log.v("Istrzaivanja za godinu su" , istrazivanja.toString())
        adapterIstrazivanjaSpiner.notifyDataSetChanged()
    }

    private suspend fun popuniGrupamaGrupeSpinerAdapter(
        adapterGrupeSpiner: ArrayAdapter<Grupa>,
        idIstrazivanja: Int
    ) {
        val grupe = istrazivanjeIGrupaViewModel.getGrupeByIstrazivanje(idIstrazivanja)
        adapterGrupeSpiner.addAll(grupe)
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