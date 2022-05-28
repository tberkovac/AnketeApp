package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/anketa/{idAnkete}/pitanja")
    suspend fun getPitanja(
        @Path("idAnkete") id : Int
    ): ArrayList<Pitanje>

    @GET("/anketa")
    suspend fun getAnkete(
        @Query("offset") offset: Int
    ) : ArrayList<Anketa>

    @GET("/anketa/{id}")
    suspend fun getAnketaById(
        @Path("id") id: Int
    ) : Anketa

    @GET("/istrazivanje")
    suspend fun getIstrazivanja(
        @Query("offset") offset: Int
    ) : ArrayList<Istrazivanje>

    @GET("/istrazivanje/{id}")
    suspend fun getIstrazivanjaById(
        @Path("id")  id : Int
    ) : Istrazivanje

    @GET("/grupa/{gid}/istrazivanje")
    suspend fun getIstrazivanjaForGroupById(
        @Path("gid")  id : Int
    ) : Istrazivanje

    @GET("/grupa")
    suspend fun getGrupe() : ArrayList<Grupa>


    @POST("/grupa/{gid}/student/19b70587-76b0-4444-a964-fad0a04d426b")
    suspend fun upisiGrupuSaId(
        @Path("gid") gid : Int,
    ) : Response<UpisGrupeResponse>

    @GET("/student/{id}/grupa")
    suspend fun getUpisaneGrupe(
        @Path("id") id : String
    ) : ArrayList<Grupa>

    @GET("/grupa/{id}")
    suspend fun getGrupaById(
        @Path("id") id : Int
    ) : Grupa
}