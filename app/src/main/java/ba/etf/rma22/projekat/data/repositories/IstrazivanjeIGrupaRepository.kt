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
        Log.v("Istrazivanja su", svaIstrazivanja.toString())
        return svaIstrazivanja
    }

    suspend fun getGrupe() : List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.retrofit.getGrupe()
            return@withContext response
        }
    }
//ova je problem
    suspend fun getGrupeByIstrazivanje(idIstrazivanje: Int) : List<Grupa> {
    if(idIstrazivanje == 0){
        return emptyList()
    }
        var grupeZaVratit = getGrupe()
    var grupeZaVratiti2 = emptyList<Grupa>()
    Log.v("ID ISTRAZIVANJA JE" , idIstrazivanje.toString())

        var trazenoIstrazivanje = ApiConfig.retrofit.getIstrazivanjaById(idIstrazivanje)
          Log.v("Trazeno istrazivanje je ", trazenoIstrazivanje?.toString())
        grupeZaVratit.forEach {
            val istrazivanjaZaGrupu = ApiConfig.retrofit.getIstrazivanjaForGroupById(it.id)
            Log.v("Istrazivanja su ", it.naziv + " " + istrazivanjaZaGrupu.toString())
            if(  ApiConfig.retrofit.getIstrazivanjaForGroupById(it.id) == (trazenoIstrazivanje))
                grupeZaVratiti2 = grupeZaVratiti2.plus(it)
        }

        Log.v("GRUPE ZA VRATIT SU ", grupeZaVratit.toString())

        return grupeZaVratiti2
    }

    suspend fun upisiUGrupu(idGrupa:Int):Boolean {
        if(getUpisaneGrupe().contains(ApiConfig.retrofit.getGrupaById(idGrupa)))
            return false
        val response = ApiConfig.retrofit.upisiGrupuSaId(idGrupa)
        Log.v("PORUKA UPISA JE", response.message())
        if(response.message() == "OK")
            return true
        return false
    }

    suspend fun getUpisaneGrupe():List<Grupa>{
        val response = ApiConfig.retrofit.getUpisaneGrupe("19b70587-76b0-4444-a964-fad0a04d426b")
        Log.v("UPISANE GRUPE SU", response.toString())
        return response
    }
}