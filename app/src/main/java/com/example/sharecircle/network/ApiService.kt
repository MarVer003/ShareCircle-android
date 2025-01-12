package com.example.sharecircle.network


import com.example.sharecircle.data.ExchangeRates
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers


private const val MONEY_UNIT = "EUR"
private const val BASE_URL = "https://exchangerate-api.p.rapidapi.com/"
private const val KEY = "a5e962dab9msh0a2f57a5bcc880dp179d44jsncf9ccfe39093"
private const val HOST = "exchangerate-api.p.rapidapi.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @Headers("x-rapidapi-key: $KEY", "x-rapidapi-host: $HOST")
    @GET("rapid/latest/$MONEY_UNIT")
    suspend fun getExchangeRates(): ExchangeRates
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java)}
}