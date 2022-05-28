package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IstrazivanjeIGrupaRepository {

    suspend fun getIstrazivanja(offset:Int) : List<Istrazivanje> {
        return withContext(Dispatchers.IO){
            val response = ApiConfig.retrofit.getIstrazivanja(offset)
            return@withContext response
        }
    }

    suspend fun getIstrazivanja() : List<Istrazivanje> {
        var svaIstrazivanja = listOf<Istrazivanje>()
        var i = 1
        while(true){
            val velicina : Int
            withContext(Dispatchers.IO){
                val response = ApiConfig.retrofit.getIstrazivanja(i)
                velicina = response.size
                svaIstrazivanja = svaIstrazivanja.plus(response)
            }
            if(velicina != 5)
                break
            i++
        }

        return svaIstrazivanja
    }

    suspend fun getGrupe() : List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.retrofit.getGrupe()
            return@withContext response
        }
    }

    suspend fun getGrupeByIstrazivanje(idIstrazivanje: Int) : List<Grupa> {
        if(idIstrazivanje == 0){
            return emptyList()
        }
        val sveGrupe = getGrupe()
        var grupeZaVratiti = emptyList<Grupa>()

        val proslijedjenoIstrazivanje = ApiConfig.retrofit.getIstrazivanjaById(idIstrazivanje)
        sveGrupe.forEach {
            val istrazivanjeZaGrupu = ApiConfig.retrofit.getIstrazivanjaForGroupById(it.id)
            if(istrazivanjeZaGrupu == proslijedjenoIstrazivanje)
                grupeZaVratiti = grupeZaVratiti.plus(it)
        }
        return grupeZaVratiti
    }

    suspend fun upisiUGrupu(idGrupa:Int):Boolean {
        if(getUpisaneGrupe().contains(ApiConfig.retrofit.getGrupaById(idGrupa)))
            return false
        val response = ApiConfig.retrofit.upisiGrupuSaId(idGrupa)
        if(response.message() == "OK")
            return true
        return false
    }

    suspend fun getUpisaneGrupe():List<Grupa>{
        val response = ApiConfig.retrofit.getUpisaneGrupe("19b70587-76b0-4444-a964-fad0a04d426b")
        return response
    }

    suspend fun getGrupaById(idGrupa: Int) : Grupa {
        val response = ApiConfig.retrofit.getGrupaById(idGrupa)
        return response
    }

    suspend fun getIstrazivanjeByGrupaId(idGrupa: Int) : Istrazivanje {
        val response = ApiConfig.retrofit.getIstrazivanjaForGroupById(idGrupa)
        return response
    }
}