package com.example.stalcraftobserver.domain.model.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ItemViewModel @Inject constructor(
    @Named("RoomDataService") private val itemsRoomService: ItemsRoomService
) : ViewModel() {
    private val _itemsList = mutableStateListOf<Item>()
    val itemsList: List<Item> get() = _itemsList

    private var currentPage = 0
    private val itemsPerPage = 20
    private var isLoading = false


    init {
        loadMoreItems()
    }

    fun shouldLoadMore(): Boolean {
        return !isLoading && _itemsList.size % itemsPerPage == 0
    }

    fun loadMoreItems() {
        if (isLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            val offset = currentPage * itemsPerPage

            val newItemsResult = async { itemsRoomService.getItemsPaged(itemsPerPage, offset) }
            when (val newItems = newItemsResult.await()) {
                is FunctionResult.Success -> {
                    if (newItems.data.isNotEmpty()) {
                        _itemsList.addAll(newItems.data)
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
