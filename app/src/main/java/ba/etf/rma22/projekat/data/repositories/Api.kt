package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.GetPitanjaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/anketa/{idAnkete}/pitanja")
    suspend fun getPitanja(
        @Path("idAnkete") id : Int
    ): Response<GetPitanjaResponse>
}