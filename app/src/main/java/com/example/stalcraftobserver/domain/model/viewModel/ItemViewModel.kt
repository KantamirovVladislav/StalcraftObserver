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
    private val currentSortFilters = mutableListOf("nameEng ASC")
    private val selectedCategoryFilters = mutableListOf<String>()
    private val selectedRarityFilters = mutableListOf<String>()

    val itemsList: List<Item> get() = _itemsList

    private var currentPage = 0
    private val itemsPerPage = 20
    private var isLoading = false

    private var searchQuery: String = ""

    init {
        loadMoreItems()
    }

    fun shouldLoadMore(): Boolean {
        return !isLoading && _itemsList.size % itemsPerPage == 0
    }

    fun updateSortFilters(filters: List<String>) {
        currentSortFilters.clear()
        currentSortFilters.addAll(filters)

        reloadItems()
    }

    fun updateCategoryFilters(categories: List<String>) {
        selectedCategoryFilters.clear()
        selectedCategoryFilters.addAll(categories)
        Log.d("Category", selectedCategoryFilters.toString())
        reloadItems()
    }

    fun updateRarityFilters(rarities: List<String>) {
        selectedRarityFilters.clear()
        selectedRarityFilters.addAll(rarities)

        reloadItems()
    }

    private fun reloadItems() {
        _itemsList.clear()
        currentPage = 0
        loadMoreItems()
    }

    fun loadMoreItems() {
        if (isLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            val offset = currentPage * itemsPerPage

            when (val result = itemsRoomService.getItemsWithDynamicSort(
                query = searchQuery,
                sortColumns = currentSortFilters,
                limit = itemsPerPage,
                offset = offset,
                categoryFilters = selectedCategoryFilters,
                rarityFilters = selectedRarityFilters
            )) {
                is FunctionResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        _itemsList.addAll(result.data)
                        currentPage++
                    }
                }
                is FunctionResult.Error -> {
                    Log.e(Constants.ERROR_DATABASE_TAG, result.message ?: "Unknown error")
                }
            }

            isLoading = false
        }
    }

    fun searchItems(newQuery: String) {
        searchQuery = newQuery
        reloadItems()
    }
}