package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object AnketaRepository {

    suspend fun getAll(offset:Int):List<Anketa> {
        return withContext(Dispatchers.IO){
            val response = ApiConfig.retrofit.getAnkete(offset)
            return@withContext response
        }
    }

    suspend fun getById(id:Int):Anketa {
        return withContext(Dispatchers.IO){
            val response = ApiConfig.retrofit.getAnketaById(id)
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

    suspend fun getPitanjaForAnketa(anketaId: Int) : List<Pitanje> {
        return ApiConfig.retrofit.getPitanjaForAnketa(anketaId)
    }
}
