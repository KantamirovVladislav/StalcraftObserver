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
    val itemsList: StateFlow<List<Item>> = _itemsList

    private var currentPage = 0
    private val itemsPerPage = 20
    private var isLoading = false

    init {
        loadMoreItems()
    }

    fun shouldLoadMore(): Boolean {
        return !isLoading && _itemsList.value.size % itemsPerPage == 0
    }

    fun loadMoreItems() {
        if (isLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            val offset = currentPage * itemsPerPage


            when (val newItems = itemsService.getItemsPaged(itemsPerPage, offset)) {
            is FunctionResult.Success -> {
                if (newItems.data.isNotEmpty()) {
                    _itemsList.value += newItems.data
                    currentPage++
                }

            }
            is FunctionResult.Error -> {
                Log.e(Constants.ERROR_DATABASE_TAG, "itemsResult.message")
            }
        }

            isLoading = false
        }
    }
}
