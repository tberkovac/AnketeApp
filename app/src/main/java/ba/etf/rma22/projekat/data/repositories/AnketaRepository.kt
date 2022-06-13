package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.RMA22DB
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    suspend fun getGroupsForAnketa(anketaId: Int) : List<Grupa> {
        return ApiConfig.retrofit.getGroupsForAnketa(anketaId)
    }

    suspend fun getUpisane(): List<Anketa> {
        val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
        val sveAnkete = getAll()
        var grupeZaAnketu : List<Grupa>
        val listaUpisanihAnketa = mutableListOf<Anketa>()
        sveAnkete.forEach {
            grupeZaAnketu = AnketaRepository.getGroupsForAnketa(it.id)
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

    suspend fun writeAnkete(context: Context,ankete: List<Anketa>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = RMA22DB.getInstance(context)
                ankete.forEach {
                    db!!.anketaDao().insertOne(it)
                }
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }
}
