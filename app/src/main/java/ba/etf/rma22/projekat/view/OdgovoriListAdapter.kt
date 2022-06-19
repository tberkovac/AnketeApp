package ba.etf.rma22.projekat.view

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.viewmodel.OdgovorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class OdgovoriListAdapter(
    private var anketa: Anketa,
    private var pitanje: Pitanje,
    private val mcontext: Context,
    private var pokusajRjesavanja : AnketaTaken
) : BaseAdapter(){

    override fun getCount(): Int {
        return pitanje.opcije.count()
    }

    override fun getItem(p0: Int): Any {
        return pitanje.opcije[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val odgovor: TextView
        val view = LayoutInflater.from(mcontext).inflate(R.layout.odgovor_element, p2, false)
        val odgovorViewModel = OdgovorViewModel()
        odgovor = view.findViewById(R.id.odgovor)

        odgovor.text = pitanje.opcije[p0]
        oznaciOdogovreni(pokusajRjesavanja, p0, odgovor)

         fun onSuccessPostavljenOdgovor (promijenjenProgres : Boolean, updateovaniPokusaj : AnketaTaken) {
            if(promijenjenProgres){
                odgovor.setTextColor(Color.parseColor("#0000FF"))
                pokusajRjesavanja = updateovaniPokusaj
            }
         }

        if(!anketaNijeDostupnaZaRad(anketa) && isOnline(mcontext)) {
            odgovor.isClickable = true
            odgovor.setOnClickListener {
                odgovorViewModel.postaviOdgovorAnketa(pokusajRjesavanja, pitanje.id, p0, ::onSuccessPostavljenOdgovor)
            }
        }else{
            odgovor.isClickable = false
        }
        return view
    }

    fun anketaNijeDostupnaZaRad(anketa: Anketa) : Boolean{
        return when {
       //     anketa.datumRada != null -> true
        //    anketa.datumKraj < Calendar.getInstance().time -> true
            anketa.datumPocetak > Calendar.getInstance().time -> true
            else -> false
        }
    }

    fun oznaciOdogovreni(pokusaj: AnketaTaken, p0: Int, odgovor: TextView){
        GlobalScope.launch (Dispatchers.Main){
            val odgovorViewModel = OdgovorViewModel()
            val odgovori = odgovorViewModel.getOdgovorResponseAnkete(pokusaj.AnketumId)
            Log.v("ODGOVORI", odgovori.toString()+ pokusaj.AnketumId)
            Log.v("ODGOVORISVI", OdgovorRepository.getAllOdgovoriDB().toString())
            for(x in odgovori){
                if(x.pitanjeId == pitanje.id && x.odgovoreno == p0){
                    odgovor.setTextColor(Color.parseColor("#0000FF"))
                }
            }
        }
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

