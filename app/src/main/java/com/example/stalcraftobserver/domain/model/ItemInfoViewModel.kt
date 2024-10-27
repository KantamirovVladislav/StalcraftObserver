package com.example.stalcraftobserver.domain.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.util.Constants
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemInfoViewModel(id: String, private val itemsService: ItemsService): ViewModel() {

    private lateinit var _item: Item

    init {
        getItemWithId(id)
    }

    public val item = _item

    private fun getItemWithId(id: String) = viewModelScope.launch {
        when (val itemsResult = itemsService.getItemWithId(id)) {
            is FunctionResult.Success -> {
                Log.d("Items", "Fetched items count: ${itemsResult.data}")
                _item = itemsResult.data
                Log.d("Items", "ItemsList updated with $_item items")
            }
            is FunctionResult.Error -> {
                Log.e(Constants.ERROR_DATABASE_TAG, itemsResult.message)
            }
        }
    }

    fun getItemData() = viewModelScope.launch {
        val infoPath = _item.createDataPath("ru")


    }

}