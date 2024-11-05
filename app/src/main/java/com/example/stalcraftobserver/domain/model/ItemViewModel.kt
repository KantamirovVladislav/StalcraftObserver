package com.example.stalcraftobserver.domain.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemViewModel(private val itemsService: ItemsService) : ViewModel() {

    private val _itemsList = MutableStateFlow(emptyList<Item>())

    val itemsList = _itemsList.asStateFlow()

    fun getItems() = viewModelScope.launch(Dispatchers.IO) {
        when (val itemsResult = itemsService.getAllItems()) {
            is FunctionResult.Success -> {
                Log.d("Items", "Fetched items count: ${itemsResult.data.size}")
                _itemsList.value = itemsResult.data
                Log.d("Items", "ItemsList updated with ${_itemsList.value.size} items")
            }
            is FunctionResult.Error -> {
                Log.e(Constants.ERROR_DATABASE_TAG, itemsResult.message)
            }
        }
    }
}
