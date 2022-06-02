package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.AnketaTaken
import retrofit2.Response

object TakeAnketaRepository {
    val idStudent = "19b70587-76b0-4444-a964-fad0a04d426b"

    suspend fun zapocniAnketu(idAnkete: Int): Response<AnketaTaken> {
        return ApiConfig.retrofit.takeAnketa(idStudent, idAnkete)
    }

    suspend fun getPoceteAnkete() : List<AnketaTaken> {
        return ApiConfig.retrofit.getTakenAnkete(idStudent)
    }
}