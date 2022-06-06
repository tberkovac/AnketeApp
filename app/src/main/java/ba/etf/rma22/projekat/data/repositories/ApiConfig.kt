package ba.etf.rma22.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    var baseURL = "https://rma22ws.herokuapp.com"
    var retrofit: Api = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun postaviBaseURL(baseUrl:String) {
        baseURL = baseUrl
    }
}