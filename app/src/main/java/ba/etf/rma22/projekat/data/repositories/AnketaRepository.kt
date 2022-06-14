package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository.isOnline
import kotlinx.coroutines.*


object AnketaRepository {

    suspend fun getAnkete(context: Context) : List<Anketa> {
        return withContext(Dispatchers.IO) {
            var db = RMA22DB.getInstance(context)
            var ankete = db!!.anketaDao().getAll()
            return@withContext ankete
        }
    }
    suspend fun writeAnketa(context: Context,ankete: Anketa) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                db!!.anketaDao().insertOne(ankete)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun getAll(offset:Int):List<Anketa> {
        return withContext(Dispatchers.IO){
            val response = ApiConfig.retrofit.getAnkete(offset)
            return@withContext response
        }
    }

    suspend fun getById(id:Int):Anketa? {
        return withContext(Dispatchers.IO){
            val response = ApiConfig.retrofit.getAnketaById(id)

            if(response == null)
                return@withContext  null

            return@withContext response
        }
    }

    suspend fun getAll() : List<Anketa> {
        var sveAnkete = listOf<Anketa>()
        var i = 1
        while(true){
            val velicina : Int
            withContext(Dispatchers.IO){
                val response = ApiConfig.retrofit.getAnkete(i)
                velicina = response.size
                sveAnkete = sveAnkete.plus(response)
            }
            if(velicina != 5)
                break
            i++
        }
        return sveAnkete
    }

    suspend fun getGroupsForAnketa(context: Context, anketaId: Int) : List<Grupa> {
        var grupe = listOf<Grupa>()
        if(isOnline(context)){
            var sveGrupe = IstrazivanjeIGrupaRepository.getGrupe(context)
            grupe = ApiConfig.retrofit.getGroupsForAnketa(anketaId)
            Log.v("GRUPE ZA ANKETU SU", grupe.toString())
            writeGrupe(context, grupe)
            writeGrupe(context, sveGrupe)
            var novo = grupe.map { grupa -> grupa.AnketaiGrupe2 }
            Log.v("AIG ZA ZAPISAT", novo.toString())
            writeAiG(context, novo as List<AnketaiGrupe2>)
        }else{
            grupe = IstrazivanjeIGrupaRepository.getAllGrupeDB(context) as MutableList<Grupa>
            var anketeIGrupe2 = getAIG(context)
            Log.v("AIG", anketeIGrupe2.toString())
            Log.v("ID ANKETE", anketaId.toString())
            anketeIGrupe2 = anketeIGrupe2.filter { anketaiGrupe2 -> anketaiGrupe2.AnketumId == anketaId }
            Log.v("AIGFILTRIRAN", anketeIGrupe2.toString())
            Log.v("GRUPEPRIJEFILTRIRANJA", grupe.toString())
            grupe = grupe.filter { grupa -> anketeIGrupe2.contains(AnketaiGrupe2(grupa.id,anketaId))}
        }

        return grupe
    }



    suspend fun getUpisane(context: Context): List<Anketa> {
        val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
        val sveAnkete = getAll()
        var grupeZaAnketu : List<Grupa>
        val listaUpisanihAnketa = mutableListOf<Anketa>()
        sveAnkete.forEach {
            grupeZaAnketu = AnketaRepository.getGroupsForAnketa(context, it.id)
            for (grupa in grupeZaAnketu) {
                if(upisaneGrupe.contains(grupa)){
                    listaUpisanihAnketa.add(it)
                    break
                }
            }
        }
        return listaUpisanihAnketa
    }

    suspend fun getAllDB(context: Context) : List<Anketa> {
        return withContext(Dispatchers.IO) {
                var db = RMA22DB.getInstance(context)
                val ankete = db.anketaDao().getAll()
                //   db!!.anketaDao().insertAll(ankete)
                return@withContext ankete
        }
    }
    suspend fun getAIG(context: Context): List<AnketaiGrupe2> {
        return withContext(Dispatchers.IO) {
            var db = RMA22DB.getInstance(context)
            var ag = db.anketaiGRUPEDAO2().getAll()
            Log.v("VRACENO AIG", ag.toString())
            return@withContext ag
        }
    }


    suspend fun writeAnkete(context: Context,ankete: List<Anketa>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                ankete.forEach {
                    Log.v("UPISANO", it.toString()+" ANKETA")
                    db!!.anketaDao().insertOne(it)
                }
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun writeAiG(context: Context, novo: List<AnketaiGrupe2>) : String?{
        return withContext(Dispatchers.IO) {
            try {
                var db = RMA22DB.getInstance(context)
                novo.forEach {
                    Log.v("UPISANO", it.toString())
                    db!!.anketaiGRUPEDAO2().insertOne(it)
                    Log.v("UPISANO FKT", getAIG(context).toString())
                }
                return@withContext "success"
            }catch (error:java.lang.Exception){
                Log.v("UPISANO", "AL VRAGA")
                error.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun writeGrupe(context: Context, grupe: List<Grupa>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                grupe.forEach {
                    Log.v("GRUPAMOZDA", it.toString())
                    db!!.grupaDao().insertOne(it)
                    Log.v("GRUPAMOZDASIGURNO","DA")
                }
                return@withContext "success"
            }
            catch(error:Exception){
                error.printStackTrace()
                return@withContext null
            }
        }
    }
}
