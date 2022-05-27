package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.GetAnketaResponse
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

    @GET("/anketa")
    suspend fun getAnkete(
        @Query("offset") offset: Int
    ) : Response<GetAnketaResponse>

    @GET("/anketa/{id}")
    suspend fun getAnketaById(
        @Path("id") id: Int
    ) : Response<Anketa>

    @GET("/istrazivanje")
    suspend fun getIstrazivanja(
        @Query("offset") offset: Int
    ) : Response<GetPitanjaResponse>

    @GET("/istrazivanje/{id}")
    suspend fun getIstrazivanjaById(
        @Path("id")  id : Int
    ) : Response<GetPitanjaResponse>

    @GET("/grupa/{gid}/istrazivanje")
    suspend fun getIstrazivanjaForGroupById(
        @Path("gid")  id : Int
    ) : Response<GetPitanjaResponse>
}