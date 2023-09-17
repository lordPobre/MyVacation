package com.example.appmyvacation


import retrofit2.Response
import retrofit2.http.GET

interface ExchangeRateApi {

    @GET("dolar")

    suspend fun getDollarRate(): Response<ExchangeRateResponse>
    // Clase que representa la respuesta esperada del servidor. Los campos se mapearán
    // desde el JSON de respuesta a las variables de esta clase.
    data class ExchangeRateResponse(
        val codigo: String,
        val nombre: String,
        val unidad_medida: String,
        val fecha: String,
        val valor: Double
    )
}

