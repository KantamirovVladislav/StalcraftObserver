package com.example.stalcraftobserver.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemDataService
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class CompareItemsViewModel @Inject constructor(
    application: Application,
    @Named("RoomDataService") private val itemsRoomService: ItemsRoomService,
    @Named("ItemDataService") private val itemDataService: ItemDataService
) : ViewModel() {

    val item1 = MutableStateFlow<ItemInfo?>(null)
    val item2 = MutableStateFlow<ItemInfo?>(null)

    fun setItem1Id(id: String?) {
        if (id != null) fetchItemWithId(id) { item1.value = it }
        Log.d("CompareItemsViewModel", "Item1 updated: $id")
    }

    fun setItem2Id(id: String?) {
        if (id != null) fetchItemWithId(id) { item2.value = it }
        Log.d("CompareItemsViewModel", "Item2 updated: $id")
    }

    private fun fetchItemWithId(id: String, onResult: (ItemInfo?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val itemResult = itemsRoomService.getItemWithId(id)) {
                is FunctionResult.Success -> {
                    val item = itemResult.data
                    when (val itemData = itemDataService.getItemData(item)) {
                        is FunctionResult.Success -> itemData.data
                        is FunctionResult.Error -> {
                            Log.e(Constants.ERROR_DATABASE_TAG, itemData.message)
                            null
                        }
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
}