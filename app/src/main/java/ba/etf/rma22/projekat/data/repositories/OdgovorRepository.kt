package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.OdgovorResponse
import ba.etf.rma22.projekat.data.models.SendOdgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

object OdgovorRepository {

    suspend fun getOdgovoriAnketa(context: Context, idAnkete:Int):List<OdgovorResponse> {
        val pokusajRjesavanja = TakeAnketaRepository.getPoceteAnkete(context)?.find { it.AnketumId == idAnkete }
        var listaUnesenihOdgovora = listOf<OdgovorResponse>()
        if(pokusajRjesavanja != null){
            if(isOnline(context)) {
                listaUnesenihOdgovora = ApiConfig.retrofit.getOdgovoriAnketa(
                    AccountRepository.getHash(),
                    pokusajRjesavanja.id
                )
                writeOdgovori(context, listaUnesenihOdgovora)
            }else{
                listaUnesenihOdgovora = getAllOdgovoriDB(context)
                listaUnesenihOdgovora = listaUnesenihOdgovora.toSet().toList()
                listaUnesenihOdgovora = listaUnesenihOdgovora.filter { it.anketaTakenId==pokusajRjesavanja.id }
            }
        }
        return listaUnesenihOdgovora
    }

    suspend fun postaviOdgovorAnketa(context: Context, idAnketaTaken: Int, idPitanje:Int, indexOdgovora:Int) : Int {
        try {
            val pokusajRjesavanja = TakeAnketaRepository.getPokusaj(idAnketaTaken)
            val prethodniProgres = pokusajRjesavanja.progres
            val buduciProgres =
                obracunajBuduciProgresZaAnketuZaokruzeni(context, pokusajRjesavanja.AnketumId)
            val response = ApiConfig.retrofit.postaviOdgovorAnketa(
                AccountRepository.getHash(), pokusajRjesavanja.id,
                SendOdgovor(indexOdgovora, idPitanje, buduciProgres)
            )
            if (response.code() == 200)
                return buduciProgres
            return prethodniProgres
        }catch (e : Exception){
            return -1
        }
    }

     suspend fun obracunajProgresZaAnketu(context: Context, idAnekete: Int) : Int{
        val odgovoriNaAnketiOdgovoreni = getOdgovoriAnketa(context, idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(context, idAnekete).size
        return ((odgovoriNaAnketiOdgovoreni.toDouble()/brojPitanjaAnkete)*100).toInt()
     }

    suspend fun obracunajBuduciProgresZaAnketuZaokruzeni(context: Context, idAnekete: Int) : Int{
        var brojOdgovorenihPitanja = getOdgovoriAnketa(context, idAnekete).size //OVE DVIJE PROVJERIT
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(context, idAnekete).size

        val jeLiSveOdgovoreno = brojOdgovorenihPitanja == brojPitanjaAnkete

        if(!jeLiSveOdgovoreno)
            brojOdgovorenihPitanja += 1
        return zaokruziProgres((brojOdgovorenihPitanja.toDouble()/brojPitanjaAnkete).toFloat())
    }

    suspend fun obracunajBuduciProgresZaAnketu(context: Context, idAnekete: Int) : Int{
        var brojOdgovorenihPitanja = getOdgovoriAnketa(context, idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(context, idAnekete).size

        val jeLiSveOdgovoreno = brojOdgovorenihPitanja == brojPitanjaAnkete

        if(!jeLiSveOdgovoreno)
            brojOdgovorenihPitanja += 1
        return ((brojOdgovorenihPitanja.toDouble()/brojPitanjaAnkete)*100).toInt()
    }

    suspend fun obracunajProgresZaAnketuZaokruzeni(context: Context, idAnekete: Int) : Int{
        val odgovoriNaAnketiOdgovoreni = getOdgovoriAnketa(context, idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(context, idAnekete).size
        return zaokruziProgres((odgovoriNaAnketiOdgovoreni.toDouble()/brojPitanjaAnkete).toFloat())
    }

    private fun zaokruziProgres(progres: Float): Int {
        var rez : Int = (progres*10).toInt()
        if(rez % 2 != 0) rez += 1
        rez *= 10
        return rez
    }

    suspend fun writeOdgovori(context: Context, odgovori: List<OdgovorResponse>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                odgovori.forEach {
                    db!!.odgovorDAO().insertOne(it)
                }
                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getAllOdgovoriDB(context: Context) : List<OdgovorResponse> {
        return withContext(Dispatchers.IO) {
            var db = RMA22DB.getInstance(context)
            val odgovori = db.odgovorDAO().getAll()
            return@withContext odgovori
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