package com.example.stalcraftobserver.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemDataService
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.data.manager.Lines
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.domain.model.StalcraftApplication
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun fetchItemWithId(id: String, onResult: (ItemInfo?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val itemResult = itemsRoomService.getItemWithId(id)) {
                is FunctionResult.Success -> {
                    val item = itemResult.data
                    if (item != null) {
                        when (val itemData = itemDataService.getItemData(item)) {
                            is FunctionResult.Success -> itemData.data
                            is FunctionResult.Error -> {
                                Log.e(Constants.ERROR_DATABASE_TAG, itemData.message)
                                null
                            }
                        }
                    } else {
                        Log.e(Constants.ERROR_DATABASE_TAG, "Item with id $id is null")
                        null
                    }
                }
                is FunctionResult.Error -> {
                    Log.e(Constants.ERROR_DATABASE_TAG, itemResult.message)
                    null
                }
            }
            onResult(result)
        }
    }


    private fun getItemData(item: Item?) = viewModelScope.launch {
        if (item == null) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Item is null in getItemData")
            return@launch
        }
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
}