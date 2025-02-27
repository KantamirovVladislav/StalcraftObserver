package com.example.stalcraftobserver.domain.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.domain.model.FilterItem
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.domain.model.entities.Favorite
import com.example.stalcraftobserver.domain.model.entities.Item
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.logging.Filter
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ItemViewModel @Inject constructor(
    @Named("RoomDataService") private val itemsRoomService: ItemsRoomService
) : ViewModel() {

    private val _localFavoriteId =
        MutableStateFlow<List<Favorite>>(emptyList()) //For static favorite ids (Collect on init viewModel)
    private val _favoritesId = MutableStateFlow<List<Favorite>>(emptyList())
    private val _itemsList = MutableStateFlow<Set<Item>>(emptySet())
    private val _onErrorFavorite = MutableStateFlow(false)
    private val _selectedFilters = MutableStateFlow<Set<FilterItem>>(emptySet())
    private val _disabledFilters = MutableStateFlow<Set<FilterItem>>(emptySet())
    private val _globalExtendFilters = MutableStateFlow<Set<FilterItem>>(emptySet())
    private val _selectedCategoryFilters = MutableStateFlow<List<FilterItem>>(mutableListOf())
    private val _selectedRarityFilters = MutableStateFlow<List<FilterItem>>(mutableListOf())
    private val _searchQuery = MutableStateFlow<String>("")
    private val trigger = MutableStateFlow<FilterItem?>(null)
    val favoritesId: StateFlow<List<Favorite>> = _favoritesId
    val itemsList: StateFlow<Set<Item>> = _itemsList
    val onErrorFavorite: StateFlow<Boolean> = _onErrorFavorite
    val selectedFilters: StateFlow<Set<FilterItem>> = _selectedFilters
    val disabledFilters: StateFlow<Set<FilterItem>> = _disabledFilters
    val globalExtendFilters: StateFlow<Set<FilterItem>> = _globalExtendFilters
    val searchQuery = _searchQuery.asStateFlow()
    val selectedCategoryFilter = _selectedCategoryFilters

    private val currentSortFilters = mutableListOf("A-z")

    val selectedRarityFilters = _selectedRarityFilters


    private var currentPage = 0
    private val itemsPerPage = 20
    private var isLoading = false


    init {
        loadMoreItems()
        getFavoriteIds()
    }

    private fun getFavoriteIds() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = itemsRoomService.getFavorites()) {
                is FunctionResult.Error -> _onErrorFavorite.value = true
                is FunctionResult.Success -> {
                    _favoritesId.value = result.data
                    _localFavoriteId.value = result.data
                }
            }
        }
    }

    fun setFavorite(id: String) {
        val updatedFavorites = _favoritesId.value.toMutableList().apply { add(Favorite(id)) }
        _favoritesId.value = updatedFavorites
    }

    fun removeFavorite(id: String) {
        val updatedFavorites = _favoritesId.value.toMutableList().apply { remove(Favorite(id)) }
        _favoritesId.value = updatedFavorites
    }

    fun saveFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deletedFavorites = _localFavoriteId.value.filter { favorite ->
                    !_favoritesId.value.any { it.favoriteId == favorite.favoriteId }
                }

                if (deletedFavorites.isNotEmpty()) {
                    itemsRoomService.deleteFavorite(deletedFavorites)
                }

                itemsRoomService.setFavorites(_favoritesId.value)

                Log.d("ItemViewModel", "Favorites успешно обновлены при удалении ViewModel")
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Ошибка при обновлении favorites: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveFavorites()
    }

    fun shouldLoadMore(): Boolean {
        return !isLoading && _itemsList.value.size % itemsPerPage == 0
    }

    private fun reloadItems() {
        _itemsList.value = emptySet()
        currentPage = 0
        loadMoreItems()
    }

    fun loadMoreItems() {
        if (isLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            val offset = currentPage * itemsPerPage

            when (val result = itemsRoomService.getItemsWithDynamicSort(
                query = _searchQuery.value,
                sortColumns = currentSortFilters,
                limit = itemsPerPage,
                offset = offset,
                categoryFilters = _selectedCategoryFilters.value.map{ it.name },
                rarityFilters = _selectedRarityFilters.value.map { it.name }
            )) {
                is FunctionResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        Log.d("KEKEWLoadItems", result.data.toString())
                        val updatedList =
                            _itemsList.value.toMutableList().apply { addAll(result.data) }
                        _itemsList.value = updatedList.toSet()
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
        _searchQuery.value = newQuery
        reloadItems()
    }

    fun selectFilter(filter: FilterItem) {
        val deletedFilters =
            _selectedFilters.value.toMutableSet().apply { removeAll { it.group == filter.group } }
        _selectedFilters.value = deletedFilters
        _selectedFilters.value = _selectedFilters.value.toMutableSet().apply { add(filter) }

        Log.d("SelectedFilter", _selectedFilters.toString())

        if (calculateDepth(filter, 0) == 2) {
            _globalExtendFilters.value = filter.extendFilters.toMutableSet()
        }
    }

    fun disableFilter(filter: FilterItem?) {
        _selectedFilters.value = _selectedFilters.value.toMutableSet().apply { remove(filter) }
    }

    fun disableFilter(filter: List<FilterItem>) {
        _selectedFilters.value = _selectedFilters.value.toMutableSet().apply { removeAll(filter) }
    }

    fun updateSortFilters(filters: FilterItem) {
        currentSortFilters.clear()
        currentSortFilters.add(filters.name)
        Log.d("YOBANA", currentSortFilters.toString())
        reloadItems()
    }

    fun deleteSortFilters(filters: FilterItem) {
        currentSortFilters.remove(filters.name)
        reloadItems()
    }

    fun updateCategoryFilters(category: FilterItem) {
        _selectedCategoryFilters.value = _selectedCategoryFilters.value.toMutableList().apply{add(category)}
        reloadItems()
    }

    fun updateCategoryFilters(category: Set<FilterItem>) {
        val updatedCategoryFilters = _selectedCategoryFilters.value.toMutableList().apply { addAll(category) }
        _selectedCategoryFilters.value = updatedCategoryFilters
        reloadItems()
    }

    fun updateCategoryFilters(category: String) {
        _selectedCategoryFilters.value = _selectedCategoryFilters.value.toMutableList().apply{add(
            FilterItem(name = category, group = "kekw"))}
        reloadItems()
    }


    fun deleteCategoryFilters(category: FilterItem) {
        val updatedCategoryFilters = _selectedCategoryFilters.value.toMutableList().apply { remove(category) }
        _selectedCategoryFilters.value = updatedCategoryFilters
        reloadItems()
    }

    fun deleteCategoryFilters(category: Set<FilterItem>) {
        val updatedCategoryFilters = _selectedCategoryFilters.value.toMutableList().apply { removeAll(category) }
        _selectedCategoryFilters.value = updatedCategoryFilters
        reloadItems()
    }

    fun updateRarityFilters(rarity: FilterItem) {
        val updatedRarityFilters =
            _selectedRarityFilters.value.toMutableList().apply { add(rarity) }
        _selectedRarityFilters.value = updatedRarityFilters
        reloadItems()
    }

    fun updateRarityFilters(rarity: Set<FilterItem>) {
        val updatedRarityFilters =
            _selectedRarityFilters.value.toMutableList().apply { addAll(rarity) }
        _selectedRarityFilters.value = updatedRarityFilters
        reloadItems()
    }

    fun deleteRarityFilters(rarity: Set<FilterItem>) {
        val updatedRarityFilters = _selectedRarityFilters.value.toMutableList().apply { removeAll(rarity) }
        _selectedRarityFilters.value = updatedRarityFilters
        reloadItems()
    }

    fun collapseExtendFilters() {
        _globalExtendFilters.value = mutableSetOf()
    }

    fun calculateDepth(filter: FilterItem, currentLevel: Int = 0): Int {
        if (filter.extendFilters.isEmpty()) {
            return currentLevel
        }
        return filter.extendFilters.maxOf { calculateDepth(it, currentLevel + 1) }
    }
}