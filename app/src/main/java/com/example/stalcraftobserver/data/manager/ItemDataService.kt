package com.example.stalcraftobserver.data.manager

import android.app.Application
import android.util.Log
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.domain.model.entities.Item
import com.example.stalcraftobserver.domain.model.StalcraftApplication
import com.example.stalcraftobserver.ClientSecret
import com.example.stalcraftobserver.util.Constants
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Named


class ItemDataService @Inject constructor(
    application: Application,
    @Named("GitHubService") private val gitHubApi: GitHubApi,
    @Named("StalcraftApiService") private val stalcraftApi: StalcraftApi
) {

    private val app = application as StalcraftApplication

    suspend fun getItemData(item: Item): FunctionResult<ItemInfo> {
        return try {
            val response = gitHubApi.getItemData(
                app.globalRegion.value,
                category = item.category,
                id = item.id
            ).awaitResponse()
            if (response.isSuccessful) {
                val itemData = response.body()
                if (itemData != null) {
                    FunctionResult.Success(itemData)
                } else {
                    FunctionResult.Error("Data response is null")
                }
            } else {
                FunctionResult.Error("Response error: $response")
            }
        } catch (e: Exception) {
            Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, "Error message: ${e.message}")
            FunctionResult.Error("Error message: ${e.toString()}")
        }
    }

    suspend fun getItemDataWithLevel(item: Item, level: Int): FunctionResult<ItemInfo> {
        return try {
            val response = gitHubApi.getItemDataWithLevel(
                app.globalRegion.value,
                category = item.category,
                id = item.id,
                level = level
            ).awaitResponse()
            if (response.isSuccessful) {
                val itemData = response.body()
                if (itemData != null) {
                    FunctionResult.Success(itemData)
                } else {
                    FunctionResult.Error("Data response is null")
                }
            } else {
                FunctionResult.Error("Response error: $response")
            }
        } catch (e: Exception) {
            Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, "Error message: ${e.message}")
            FunctionResult.Error("Error message: ${e.toString()}")
        }
    }

    suspend fun getItemPriceHistory(item: String): FunctionResult<PriceHistoryResponse> {
        return try {
            val response = stalcraftApi.getItemPriceHistory(
                app.localRegion.value,
                item,
                ClientSecret.X_API_ID,
                ClientSecret.X_API_SECRET
            ).awaitResponse()
            Log.d("KAKAK", response.toString())
            if (response.isSuccessful) {
                val itemHistory = response.body()
                if (itemHistory != null) {
                    FunctionResult.Success(itemHistory)
                } else {
                    FunctionResult.Error("Data response is null")
                }
            } else {
                FunctionResult.Error("Data response is null")
            }
        } catch (e: Exception) {
            Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, "Error message: ${e.message}")
            FunctionResult.Error("Error message: ${e.message}")
        }
    }

    suspend fun getItemActiveLots(item: String): FunctionResult<AuctionResponse> {
        return try {
            val response = stalcraftApi.getItemActiveLots(
                app.localRegion.value,
                item,
                ClientSecret.X_API_ID,
                ClientSecret.X_API_SECRET
            ).awaitResponse()
            if (response.isSuccessful) {
                val itemActive = response.body()
                if (itemActive != null) {
                    FunctionResult.Success(itemActive)
                } else {
                    FunctionResult.Error("Data response is null")
                }
            } else {
                FunctionResult.Error("Data response is null")
            }
        } catch (e: Exception) {
            Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, "Error message: ${e.message}")
            FunctionResult.Error("Error message: ${e.message}")
        }
    }
}