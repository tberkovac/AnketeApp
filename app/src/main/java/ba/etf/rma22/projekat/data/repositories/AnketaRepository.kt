package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository.Companion.isOnline
import kotlinx.coroutines.*


class AnketaRepository {
    companion object {

        private lateinit var mcontext: Context

        public fun setContext(context: Context) {
            mcontext = context
        }

        suspend fun getAnkete(): List<Anketa> {
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                var ankete = db!!.anketaDao().getAll()
                return@withContext ankete
            }
        }

        suspend fun writeAnketa(ankete: Anketa): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    db!!.anketaDao().insertOne(ankete)
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }

        suspend fun getAll(offset: Int): List<Anketa> {
            return withContext(Dispatchers.IO) {
                val response = ApiConfig.retrofit.getAnkete(offset)
                return@withContext response
            }
        }

        suspend fun getById(id: Int): Anketa? {
            return withContext(Dispatchers.IO) {
                val response = ApiConfig.retrofit.getAnketaById(id)

                if (response == null)
                    return@withContext null

                return@withContext response
            }
        }

        suspend fun getAll(): List<Anketa> {
            var sveAnkete = listOf<Anketa>()

            if(isOnline()) {
                var i = 1
                while (true) {
                    val velicina: Int
                    withContext(Dispatchers.IO) {
                        val response = ApiConfig.retrofit.getAnkete(i)
                        velicina = response.size
                        sveAnkete = sveAnkete.plus(response)
                    }
                    if (velicina != 5)
                        break
                    i++
                }
                writeAnkete(sveAnkete)
                return sveAnkete
            }else{
                sveAnkete = getAllDB()
                return sveAnkete
            }
        }

        suspend fun getGroupsForAnketa( anketaId: Int): List<Grupa> {
            var grupe = listOf<Grupa>()
            if (isOnline()) {
                var sveGrupe = IstrazivanjeIGrupaRepository.getGrupe()
                grupe = ApiConfig.retrofit.getGroupsForAnketa(anketaId)
                writeGrupe( grupe)
                writeGrupe( sveGrupe)
                var novo = grupe.map { grupa -> grupa.AnketaiGrupe2 }
                writeAiG(novo as List<AnketaiGrupe2>)
            } else {
                grupe = IstrazivanjeIGrupaRepository.getAllGrupeDB() as MutableList<Grupa>
                var anketeIGrupe2 = getAIG()
                anketeIGrupe2 =
                    anketeIGrupe2.filter { anketaiGrupe2 -> anketaiGrupe2.AnketumId == anketaId }
                grupe = grupe.filter { grupa ->
                    anketeIGrupe2.contains(
                        AnketaiGrupe2(
                            grupa.id,
                            anketaId
                        )
                    )
                }
            }

            return grupe
        }


        suspend fun getUpisane(): List<Anketa> {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            val sveAnkete = getAll()
            var grupeZaAnketu: List<Grupa>
            val listaUpisanihAnketa = mutableListOf<Anketa>()
            sveAnkete.forEach {
                grupeZaAnketu = AnketaRepository.getGroupsForAnketa(it.id)
                for (grupa in grupeZaAnketu) {
                    if (upisaneGrupe.contains(grupa)) {
                        listaUpisanihAnketa.add(it)
                        break
                    }
                }
            }
            return listaUpisanihAnketa
        }

        suspend fun getAllDB(): List<Anketa> {
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                val ankete = db.anketaDao().getAll()
                return@withContext ankete
            }
        }

        suspend fun getAIG(): List<AnketaiGrupe2> {
            return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(mcontext)
                var ag = db.anketaiGRUPEDAO2().getAll()
                return@withContext ag
            }
        }


        suspend fun writeAnkete(ankete: List<Anketa>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance( mcontext)
                    ankete.forEach {
                        db!!.anketaDao().insertOne(it)
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }

        suspend fun writeAiG(novo: List<AnketaiGrupe2>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = RMA22DB.getInstance(mcontext)
                    novo.forEach {
                        db!!.anketaiGRUPEDAO2().insertOne(it)
                    }
                    return@withContext "success"
                } catch (error: java.lang.Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
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
    }
}
