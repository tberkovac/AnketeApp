package ba.etf.rma22.projekat.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import ba.etf.rma22.projekat.data.models.AccountIGrupa
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeIGrupaViewModel

class FragmentIstrazivanje : Fragment() {
    private lateinit var spinerGodine : Spinner
    private lateinit var spinerIstrazivanja : Spinner
    private lateinit var spinerGrupe : Spinner
    private lateinit var adapterGodineSpiner : ArrayAdapter<String>
    private lateinit var adapterIstrazivanjaSpiner : ArrayAdapter<Istrazivanje>
    private lateinit var adapterGrupeSpiner : ArrayAdapter<Grupa>
    private var istrazivanjeIGrupaViewModel = IstrazivanjeIGrupaViewModel()
    val godine = listOf( "","1","2","3","4","5")

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
        dodajIstrazivanjeDugme.isEnabled = false

        GodinaAdapterInicijalizacijaIDodjelaSpineru(view.context)
        IstrazivanjeAdapterInicijalizacijaIDodijelaSpineru(view.context)
        GrupaAdapterInicijalizacijaIDodijelaSpineru(view.context)

        spinerGodine.setSelection(adapterGodineSpiner.getPosition(zadnji))

        spinerGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                zadnji = spinerGodine.selectedItem.toString()
                if(spinerGodine.selectedItem.toString() == "") {
                    resetujIstrazivanjeSpinerAdapter()
                    resetujGrupeSpinerAdapter()
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                resetujIstrazivanjeSpinerAdapter()
                popuniIstrazivanja(context!!, spinerGodine.selectedItem.toString().toInt())
                zadnji = godine[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinerGodine.setSelection(zadnji.toInt())
            }
        }



        spinerIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(spinerIstrazivanja.selectedItem.toString() == "") {
                    resetujGrupeSpinerAdapter()
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                resetujGrupeSpinerAdapter()
                popuniGrupamaGrupeSpinerAdapter(context!!, (spinerIstrazivanja.selectedItem as Istrazivanje).id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                resetujGrupeSpinerAdapter()
            }
        }


        spinerGrupe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(spinerGrupe.selectedItem.toString() == "") {
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                if(isOnline(context!!))
                    dodajIstrazivanjeDugme.isEnabled = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                dodajIstrazivanjeDugme.isEnabled = false
            }
        }


        dodajIstrazivanjeDugme.setOnClickListener{
            if(jeLiSveOdabrano() && isOnline(requireContext())) {
                val grupa = spinerGrupe.selectedItem as Grupa
                context?.let { it1 -> istrazivanjeIGrupaViewModel.upisiUGrupu(it1, grupa.id, ::onSuccessUpisGrupe, ::onError) }
            }
        }

        return view
    }

    private fun resetujIstrazivanjeSpinerAdapter() {
        adapterIstrazivanjaSpiner.clear()
        adapterIstrazivanjaSpiner.add(Istrazivanje(0,"",0))
        dodajIstrazivanjeDugme.isEnabled = false
    }

    private fun resetujGrupeSpinerAdapter() {
        adapterGrupeSpiner.clear()
        adapterGrupeSpiner.add(Grupa(-1,"",-1, AnketaiGrupe2(0,0), AccountIGrupa(0,0)))
        dodajIstrazivanjeDugme.isEnabled = false
    }

    private fun popuniIstrazivanja(context: Context, godina: Int) {
        istrazivanjeIGrupaViewModel.getAllIstrazivanjaByGodina(context, godina, ::onSuccessIstrazivanje, ::onError)
    }

    private fun popuniGrupamaGrupeSpinerAdapter(context: Context, idIstrazivanja: Int) {
        istrazivanjeIGrupaViewModel.getGrupeByIstrazivanje(context,idIstrazivanja, ::onSuccessGrupa, ::onError)
    }

    private fun jeLiSveOdabrano() : Boolean{
        if(spinerGodine.selectedItem.toString() != "" && spinerIstrazivanja.selectedItem.toString() !=""
            && spinerGrupe.selectedItem.toString() != "") return true
        return false
    }

    private fun prikaziPoruku(grupa: Grupa) {
        MainActivity.adapter.refreshFragment(1,FragmentPoruka.newInstance(grupa,"izFragmentIstrazivanje"))
        MainActivity.adapter.notifyDataSetChanged()
    }

    private fun onSuccessIstrazivanje(context: Context, istrazivanja : List<Istrazivanje>) {
        adapterIstrazivanjaSpiner.addAll(istrazivanja)
        if(isOnline(context))
            istrazivanjeIGrupaViewModel.writeDB(context, istrazivanja, ::onSuccess2, ::onError2)
        adapterIstrazivanjaSpiner.notifyDataSetChanged()
    }

    private fun onSuccessGrupa(grupe: List<Grupa>) {
        adapterGrupeSpiner.addAll(grupe)
        adapterGrupeSpiner.notifyDataSetChanged()
    }

    private fun onSuccessUpisGrupe(uspjelo: Boolean) {
        if(uspjelo)
            prikaziPoruku(spinerGrupe.selectedItem as Grupa)
    }

    private fun onSuccess2(s: String?){

    }

    private fun onError(){
        Log.v("Greska", "greska")
    }

    private fun onError2(s: String?){
        Log.v("Greska", s!!)
    }

    private fun GodinaAdapterInicijalizacijaIDodjelaSpineru(context: Context) {
        adapterGodineSpiner = ArrayAdapter(
            context,android.R.layout.simple_spinner_item,godine
        )
        spinerGodine.adapter = adapterGodineSpiner
    }

    private fun IstrazivanjeAdapterInicijalizacijaIDodijelaSpineru(context: Context) {
        adapterIstrazivanjaSpiner = ArrayAdapter(
            context, android.R.layout.simple_spinner_item, ArrayList<Istrazivanje>()
        )
        spinerIstrazivanja.adapter = adapterIstrazivanjaSpiner
    }

    private fun GrupaAdapterInicijalizacijaIDodijelaSpineru(context: Context) {
        adapterGrupeSpiner = ArrayAdapter(
            context, android.R.layout.simple_spinner_item, ArrayList<Grupa>()
        )
        spinerGrupe.adapter = adapterGrupeSpiner
    }


    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}