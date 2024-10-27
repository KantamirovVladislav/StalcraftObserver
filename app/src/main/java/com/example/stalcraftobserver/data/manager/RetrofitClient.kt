package com.example.stalcraftobserver.data.manager

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

object RetrofitClient {
    private const val BASE_URL = "https://eapi.stalcraft.net/"

    val instance: StalcraftApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(StalcraftApi::class.java)
    }
}

interface StalcraftApi {
    @GET("{region}/auction/{item}/history")
    fun getAuctionHistory(
        @Path("region") region: String,
        @Path("item") itemId: String,
        @Header("Client-ID") clientId: String,
        @Header("Client-Secret") clientSecret: String
    ): Call<PriceHistoryResponse>
}

data class PriceHistoryResponse(
    val total: Long,
    val prices: List<Price>
)

data class Price(
    val amount: Int,
    val price: Int,
    val time: String,
    val additional: Map<String, Any>
){
    override fun toString(): String {
        return "Кол-во $amount\nЦена $price\nВремя $time "
    }
}