package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PitanjeAnketaRepository {
    suspend fun getPitanja(idAnkete:Int):List<Pitanje> {
        return withContext(Dispatchers.IO) {
            return@withContext ApiConfig.retrofit.getPitanja(idAnkete)
        }
    }
}