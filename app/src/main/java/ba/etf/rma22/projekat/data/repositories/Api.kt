package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("/anketa")
    suspend fun getAnkete(
        @Query("offset") offset: Int
    ) : ArrayList<Anketa>

    @GET("/anketa/{id}")
    suspend fun getAnketaById(
        @Path("id") id: Int
    ) : Anketa?

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

    @GET("/anketa/{id}/grupa")
    suspend fun getGroupsForAnketa(
        @Path("id") id : Int
    ) : List<Grupa>

    @GET("/anketa/{id}/pitanja")
    suspend fun getPitanjaForAnketa(
        @Path("id") id : Int
    ) : List<Pitanje>

    @POST("/student/{id}/anketa/{kid}")
    suspend fun takeAnketa(
        @Path("id") id : String,
        @Path("kid") kid: Int,
    ) : Response<AnketaTaken>

    @GET("/student/{id}/anketataken")
    suspend fun getTakenAnkete(
        @Path("id") id : String
    ) : List<AnketaTaken>

    @GET("/student/{id}/anketataken/{ktid}/odgovori")
    suspend fun getOdgovoriAnketa(
        @Path("id") id: String,
        @Path("ktid") ktid: Int
    ) : List<Odgovor>

    @POST("/student/{id}/anketataken/{ktid}/odgovor")
    suspend fun postaviOdgovorAnketa(
        @Path("id") id: String,
        @Path("ktid") ktid: Int,
        @Body odgovor: SendOdgovor
    ) : Response<Odgovor2>

    @DELETE("/student/{id}/upisugrupeipokusaji")
    suspend fun obrisiPodatke(
        @Path("id") id : String
    )
}