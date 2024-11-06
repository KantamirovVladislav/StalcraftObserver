package com.example.stalcraftobserver.domain.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.GitHubApi
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.data.manager.RetrofitClientItemInfo
import com.example.stalcraftobserver.data.manager.StalcraftApi
import com.example.stalcraftobserver.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemInfoViewModel(
    private val itemsService: ItemsService,
    private val gitHubApi: RetrofitClientItemInfo
) : ViewModel() {

    private lateinit var _item: Item

    var info = MutableStateFlow<String>(" ")

    public fun getItemWithId(id: String) = viewModelScope.launch(Dispatchers.IO) {
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
            "Current item: Category - ${_item.category}  Id - ${_item.id} ${this@ItemInfoViewModel}"
        )
        val call = gitHubApi.instance.getItemData("ru", category = _item.category, id = _item.id)
        call.enqueue(object : Callback<ItemInfo> {
            override fun onResponse(call: Call<ItemInfo>, response: Response<ItemInfo>) {
                if (response.isSuccessful) {
                    val itemData = response.body()
                    if (itemData != null) {
                        info.value = itemData.toString()
                        Log.d(
                            Constants.RETROFIT_CLIENT_GIT_SUCCES,
                            "Response success with: $itemData"
                        )
                    } else Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, "Data response is null ${this@ItemInfoViewModel}")
                } else Log.e(
                    Constants.RETROFIT_CLIENT_GIT_ERROR,
                    "Response is error ${this@ItemInfoViewModel}"
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