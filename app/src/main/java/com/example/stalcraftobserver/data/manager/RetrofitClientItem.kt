package com.example.stalcraftobserver.data.manager

import  retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

class RetrofitClientItem {
    private val BASE_URL = "https://eapi.stalcraft.net/"

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
    fun getItemPriceHistory(
        @Path("region") region: String,
        @Path("item") itemId: String,
        @Header("Client-ID") clientId: String,
        @Header("Client-Secret") clientSecret: String
    ): Call<PriceHistoryResponse>

    @GET("{region}/auction/{item}/lots")
    fun getItemActiveLots(
        @Path("region") region: String,
        @Path("item") itemId: String,
        @Header("Client-ID") clientId: String,
        @Header("Client-Secret") clientSecret: String
    ): Call<AuctionResponse>
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
data class AuctionResponse(
    val total: Long,
    val lots: List<Lot>
)

data class Lot(
    val itemId: String,
    val amount: Int,
    val startPrice: Int,
    val currentPrice: Int,
    val buyoutPrice: Int,
    val startTime: String,
    val endTime: String,
    val additional: Map<String, Any>
)
