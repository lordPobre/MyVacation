package com.example.appmyvacation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    // Dirección base de la API.
    private const val BASE_URL = "https://mindicador.cl/api/"
    // Instancia de Retrofit.
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Instancia de la interfaz API.
    val api: ExchangeRateApi by lazy {
        retrofit.create(ExchangeRateApi::class.java)
    }
}
