package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeIGrupaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.hamcrest.core.Is
import java.lang.Thread.sleep

class IstrazivanjeIGrupaRepository {
    companion object {

        private lateinit var mcontext: Context

        public fun setContext(context: Context) {
            mcontext = context
        }

        val istrazivanjeIGrupaViewModel = IstrazivanjeIGrupaViewModel()
        suspend fun getIstrazivanja(offset: Int): List<Istrazivanje> {

            return withContext(Dispatchers.IO) {

                sleep(200)
                val response = ApiConfig.retrofit.getIstrazivanja(offset)
                async {obrisiIstrazivanjaDB() }.join()

                writeIstrazivanja(response)

                return@withContext response
            }
        }

        suspend fun getIstrazivanja(): List<Istrazivanje> {
            var svaIstrazivanja = listOf<Istrazivanje>()
            var i = 1
            while (true) {
                val velicina: Int
                withContext(Dispatchers.IO) {
                    val response = ApiConfig.retrofit.getIstrazivanja(i)
                    velicina = response.size
                    svaIstrazivanja = svaIstrazivanja.plus(response)
                }
                if (velicina != 5)
                    break
                i++
            }
            writeIstrazivanja(svaIstrazivanja)
            return svaIstrazivanja
        }

        suspend fun getGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                val response = ApiConfig.retrofit.getGrupe()
                writeGrupe(response)
                return@withContext response
            }
        }

        suspend fun getAllGrupeDB(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                val grupe = db.grupaDao().getAll()
                return@withContext grupe
            }
        }

        suspend fun getGrupeZaIstrazivanje(idIstrazivanje: Int): List<Grupa> {
            if (idIstrazivanje == 0) {
                return emptyList()
            }
            val sveGrupe: List<Grupa> = if (isOnline()) {
                getGrupe()
            } else {
                getAllGrupeDB()
            }
            var grupeZaVratiti = emptyList<Grupa>()

            val proslijedjenoIstrazivanje = if (isOnline()) {
                ApiConfig.retrofit.getIstrazivanjaById(idIstrazivanje)
            } else {
                getAllIstrazivanjaDB().find { it.id == idIstrazivanje }
            }
            sveGrupe.forEach {
                val istrazivanjeZaGrupu: Istrazivanje
                if (isOnline()) {
                    istrazivanjeZaGrupu = ApiConfig.retrofit.getIstrazivanjaForGroupById(it.id)
                } else {
                    istrazivanjeZaGrupu =
                        getAllIstrazivanjaDB().find { istrazivanje -> it.IstrazivanjeId == istrazivanje.id }!!
                }

                if (istrazivanjeZaGrupu == proslijedjenoIstrazivanje)
                    grupeZaVratiti = grupeZaVratiti.plus(it)
            }
            return grupeZaVratiti
        }

        suspend fun getAllIstrazivanjaDB(): List<Istrazivanje> {
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                val istrazivanja = db.istrazivanjeDao().getAll()
                return@withContext istrazivanja
            }
        }

        suspend fun upisiUGrupu(idGrupa: Int): Boolean {
            if (getUpisaneGrupe().contains(ApiConfig.retrofit.getGrupaById(idGrupa)))
                return false
            if (getUpisanaIstrazivanja().contains(
                    getIstrazivanjeByGrupaId(
                        idGrupa
                    )
                )
            )
                return false
            val response = ApiConfig.retrofit.upisiGrupuSaId(idGrupa)
            if (response.message() == "OK")
                return true
            return false
        }

        private suspend fun getUpisanaIstrazivanja(): List<Istrazivanje> {
            var upisanaIstrazivanja = mutableListOf<Istrazivanje>()
            val upisaneGrupe = getUpisaneGrupe()
            upisaneGrupe.forEach {
                upisanaIstrazivanja.add(getIstrazivanjeByGrupaId(it.id))
            }
            return upisanaIstrazivanja
        }

        suspend fun getUpisaneGrupe(): List<Grupa> {
            var upisaneGrupe: List<Grupa>
            if (isOnline()) {
                upisaneGrupe = ApiConfig.retrofit.getUpisaneGrupe(AccountRepository.getHash())
                writeGrupe(upisaneGrupe)
            } else {
                upisaneGrupe = getAllGrupeDB()
                upisaneGrupe = upisaneGrupe.filter { it.UpisaneGrupe != null }
                Log.v("UPISANEGRUPESU", upisaneGrupe.toString())
            }
            return upisaneGrupe
        }


        suspend fun getGrupaById(context: Context, idGrupa: Int): Grupa {
            val result = if (isOnline()) {
                ApiConfig.retrofit.getGrupaById(idGrupa)
            } else {
                var db = RMA22DB.getInstance(context)
                db.grupaDao().getAll().filter { grupa -> grupa.id == idGrupa }.first()
            }
            return result
        }

        suspend fun getIstrazivanjeByGrupaId(idGrupa: Int): Istrazivanje {

            val istrazivanje = if (isOnline()) {
                getIstrazivanja()
                ApiConfig.retrofit.getIstrazivanjaForGroupById(idGrupa)
            } else {
                var grupe = getAllGrupeDB()
                var trazenaGrupa = grupe.find { grupa -> grupa.id == idGrupa }
                var result = istrazivanjeIGrupaViewModel.getAllIstrazivanja()
                result.filter { istrazivanje -> istrazivanje.id == trazenaGrupa!!.IstrazivanjeId }
                    .first()
            }
            return istrazivanje
        }

        suspend fun writeIstrazivanja(istrazivanja: List<Istrazivanje>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    istrazivanja.forEach {
                        db.istrazivanjeDao().insertOne(it)
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun obrisiIstrazivanjaDB(){
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                db.istrazivanjeDao().deleteAll()
                var istr = db.istrazivanjeDao().getAll()
                Log.v("SVAISTRBRISANA", istr.toString())
            }
        }

        suspend fun writeGrupe(grupe: List<Grupa>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    grupe.forEach {
                        db!!.grupaDao().insertOne(it)
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
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
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            }
            return false
        }
    }
}