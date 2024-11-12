package com.example.stalcraftobserver.domain.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.GitHubApi
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.data.manager.RetrofitClientItemInfo
import com.example.stalcraftobserver.data.manager.StalcraftApi
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    application: Application,
    @Named("RoomDataService") private val itemsService: ItemsService,
    @Named("GitHubService") private val gitHubApi: RetrofitClientItemInfo
) : ViewModel() {

    private lateinit var _item: Item

    var info = MutableStateFlow(" ")

    private val app = application as StalcraftApplication

    fun getItemWithId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val itemsResult = itemsService.getItemWithId(id = id)) {
            is FunctionResult.Success -> {
                _item = itemsResult.data
                Log.i(Constants.SUCCES_DATABASE_TAG, "Data is successfully read: ${_item.id} ${this@ItemInfoViewModel}")
                getItemData()
            }

            is FunctionResult.Error -> {
                Log.e(Constants.ERROR_DATABASE_TAG, itemsResult.message)
            }
        }
    }

    private fun getItemData() = viewModelScope.launch {
        if (!::_item.isInitialized) {
            Log.d(
                Constants.ERROR_DATABASE_TAG,
                "Item is not initialize ${this@ItemInfoViewModel}"
            )
            return@launch
        }
        Log.d(
            Constants.RETROFIT_CLIENT_GIT_DEBUG,
            "Current item: Category - ${_item.category}  Id - ${_item.id} Region - ${app.localRegion.value} ${this@ItemInfoViewModel}"
        )
        val call = gitHubApi.instance.getItemData(app.globalRegion.value, category = _item.category, id = _item.id)
        call.enqueue(object : Callback<ItemInfo> {
            override fun onResponse(call: Call<ItemInfo>, response: Response<ItemInfo>) {
                if (response.isSuccessful) {
                    val itemData = response.body()
                    if (itemData != null) {
                        info.value = itemData.toString()
                        Log.d(
                            Constants.RETROFIT_CLIENT_GIT_SUCCES,
                            "Response success $response with: $itemData"
                        )
                    } else Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, "Data response is null ${this@ItemInfoViewModel}")
                } else Log.e(
                    Constants.RETROFIT_CLIENT_GIT_ERROR,
                    "Response is error ${this@ItemInfoViewModel} $response"
                )
            }

            override fun onFailure(call: Call<ItemInfo>, t: Throwable) {
                Log.e(
                    Constants.RETROFIT_CLIENT_GIT_ERROR,
                    "Error message ${this@ItemInfoViewModel}: " + t.message.toString()
                )
            }
        })
    }

}