package ba.etf.rma22.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    var retrofit: Api = Retrofit.Builder()
        .baseUrl("https://rma22ws.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun postaviBaseURL(baseUrl:String):Unit{
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}