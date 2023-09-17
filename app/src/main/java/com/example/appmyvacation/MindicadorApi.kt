package com.example.appmyvacation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Modelo para representar detalles de una moneda, actualmente solo tiene un campo `valor` que es un Float.
data class CurrencyDetail(
    val valor: Float
)
// Modelo de respuesta para el tipo de cambio.
data class ExchangeRateResponse(
    val dolar: CurrencyDetail
)
// Interfaz que define cómo interactuar con la API Mindicador.
interface MindicadorApi {
    @GET("/api")
    suspend fun getExchangeRates(): ExchangeRateResponse
}
// Configura y crea una instancia de Retrofit para interactuar con la API.
val retrofit = Retrofit.Builder()
    .baseUrl("https://mindicador.cl")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
// Crea una instancia de la interfaz MindicadorApi usando Retrofit.
val api = retrofit.create(MindicadorApi::class.java)

