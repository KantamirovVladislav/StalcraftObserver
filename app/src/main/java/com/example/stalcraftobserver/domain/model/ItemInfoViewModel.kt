package com.example.stalcraftobserver.domain.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemDataService
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.data.manager.RetrofitClientItemInfo
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    application: Application,
    @Named("RoomDataService") private val itemsRoomService: ItemsRoomService,
    @Named("ItemDataService") private val itemDataService: ItemDataService
) : ViewModel() {

    private var _info = MutableStateFlow<ItemInfo?>(null)

    var info = _info.asStateFlow()

    private val app = application as StalcraftApplication

    fun getItemWithId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val itemsResult = itemsRoomService.getItemWithId(id = id)) {
            is FunctionResult.Success -> {
                getItemData(itemsResult.data)
            }

            is FunctionResult.Error -> {
                Log.e(Constants.ERROR_DATABASE_TAG, itemsResult.message)
            }
        }
    }

    private fun getItemData(item: Item) = viewModelScope.launch {
        Log.d(
            Constants.RETROFIT_CLIENT_GIT_DEBUG,
            "Current item: Category - ${item.category}  Id - ${item.id} Region - ${app.localRegion.value} ${this@ItemInfoViewModel}"
        )
        when (val itemData = itemDataService.getItemData(item)) {
            is FunctionResult.Success -> {
                _info.value = itemData.data
                Log.i(Constants.RETROFIT_CLIENT_GIT_SUCCES, itemData.data.toString())
            }

            is FunctionResult.Error -> {
                Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, itemData.message)
            }
        }
    }

    private fun getAllElementFromItem(item: ItemInfo){

    }
}