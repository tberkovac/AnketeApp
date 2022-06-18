package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.SendOdgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class OdgovorRepository {
    companion object {

        private lateinit var mcontext: Context

        public fun setContext(context: Context) {
            mcontext = context
        }

        suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {
            val pokusajRjesavanja =
                TakeAnketaRepository.getPoceteAnkete()?.find { it.AnketumId == idAnkete }
            var listaUnesenihOdgovora = listOf<Odgovor>()
            if (pokusajRjesavanja != null) {
                if (isOnline()) {
                    listaUnesenihOdgovora = ApiConfig.retrofit.getOdgovoriAnketa(
                        AccountRepository.getHash(),
                        pokusajRjesavanja.id
                    )
                    writeOdgovori(listaUnesenihOdgovora)
                } else {
                    listaUnesenihOdgovora = getAllOdgovoriDB()
                    listaUnesenihOdgovora = listaUnesenihOdgovora.toSet().toList()
                    listaUnesenihOdgovora =
                        listaUnesenihOdgovora.filter { it.anketaTakenId == pokusajRjesavanja.id }
                }
            }
            return listaUnesenihOdgovora
        }

        suspend fun getOdgovoriAnketaProgres(idAnkete: Int): List<Odgovor> {
            val pokusajRjesavanja =
                TakeAnketaRepository.getPoceteAnkete()?.find { it.AnketumId == idAnkete }
            var listaUnesenihOdgovora = listOf<Odgovor>()
            if (pokusajRjesavanja != null) {
                if (isOnline()) {
                    listaUnesenihOdgovora = ApiConfig.retrofit.getOdgovoriAnketa(
                        AccountRepository.getHash(),
                        pokusajRjesavanja.id
                    )
                } else {
                    listaUnesenihOdgovora = getAllOdgovoriDB()
                    listaUnesenihOdgovora = listaUnesenihOdgovora.toSet().toList()
                    listaUnesenihOdgovora =
                        listaUnesenihOdgovora.filter { it.anketaTakenId == pokusajRjesavanja.id }
                }
            }
            return listaUnesenihOdgovora
        }

        suspend fun postaviOdgovorAnketa(
            idAnketaTaken: Int,
            idPitanje: Int,
            indexOdgovora: Int
        ): Int {
            try {
                val pokusajRjesavanja = TakeAnketaRepository.getPokusaj(idAnketaTaken)
                val prethodniProgres = pokusajRjesavanja.progres
                val buduciProgres =
                    obracunajBuduciProgresZaAnketuZaokruzeni(pokusajRjesavanja.AnketumId)
                val response = ApiConfig.retrofit.postaviOdgovorAnketa(
                    AccountRepository.getHash(), pokusajRjesavanja.id,
                    SendOdgovor(indexOdgovora, idPitanje, buduciProgres)
                )
                if (response.code() == 200) {
                    writeOdgovor(Odgovor(indexOdgovora,idAnketaTaken, idPitanje))
                    return buduciProgres
                }
                return prethodniProgres
            } catch (e: Exception) {
                return -1
            }
        }

        suspend fun obracunajProgresZaAnketu(idAnekete: Int): Int {
            val odgovoriNaAnketiOdgovoreni = getOdgovoriAnketa(idAnekete).size
            val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size
            return ((odgovoriNaAnketiOdgovoreni.toDouble() / brojPitanjaAnkete) * 100).toInt()
        }

        suspend fun obracunajBuduciProgresZaAnketuZaokruzeni(

            idAnekete: Int
        ): Int {
            var brojOdgovorenihPitanja =
                getOdgovoriAnketaProgres(idAnekete).size
            val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size

            val jeLiSveOdgovoreno = brojOdgovorenihPitanja == brojPitanjaAnkete

            if (!jeLiSveOdgovoreno)
                brojOdgovorenihPitanja += 1
            return zaokruziProgres((brojOdgovorenihPitanja.toDouble() / brojPitanjaAnkete).toFloat())
        }

        suspend fun obracunajBuduciProgresZaAnketu(idAnekete: Int): Int {
            var brojOdgovorenihPitanja = getOdgovoriAnketaProgres(idAnekete).size
            val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size

            val jeLiSveOdgovoreno = brojOdgovorenihPitanja == brojPitanjaAnkete

            if (!jeLiSveOdgovoreno)
                brojOdgovorenihPitanja += 1
            return ((brojOdgovorenihPitanja.toDouble() / brojPitanjaAnkete) * 100).toInt()
        }

        suspend fun obracunajProgresZaAnketuZaokruzeni(idAnekete: Int): Int {
            val odgovoriNaAnketiOdgovoreni = getOdgovoriAnketaProgres(idAnekete).size
            val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size
            return zaokruziProgres((odgovoriNaAnketiOdgovoreni.toDouble() / brojPitanjaAnkete).toFloat())
        }

        private fun zaokruziProgres(progres: Float): Int {
            var rez: Int = (progres * 10).toInt()
            if (rez % 2 != 0) rez += 1
            rez *= 10
            return rez
        }

        suspend fun writeOdgovori(odgovori: List<Odgovor>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    odgovori.forEach {
                        db!!.odgovorDAO().insertOne(it)
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun writeOdgovor(odgovor: Odgovor): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)

                        db!!.odgovorDAO().insertOne(odgovor)

                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun getAllOdgovoriDB(): List<Odgovor> {
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                val odgovori = db.odgovorDAO().getAll()
                return@withContext odgovori
            }
        }

        suspend fun obrisiSve(){
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                val odgovori = db.odgovorDAO().deleteAll()
            }
        }

        fun isOnline(): Boolean {
            val connectivityManager =
                mcontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
}