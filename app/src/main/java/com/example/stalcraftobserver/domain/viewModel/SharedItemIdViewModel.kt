package com.example.stalcraftobserver.domain.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedItemIdViewModel: ViewModel() {

    private val _items = MutableStateFlow<HashMap<String, String>>(hashMapOf())

    val items = _items.asStateFlow()

    fun setItem(slot: String, artefactId: String) {
        val currentMap = _items.value
        currentMap[slot] = artefactId
        _items.value = HashMap(currentMap)
    }

    fun clearItems(slot: String) {
        val currentMap = _items.value
        currentMap.remove(slot)
        _items.value = HashMap(currentMap)
    }

    fun getItem(slot: String): String? {
        return _items.value[slot]
    }

    fun clearAllItems() {
        _items.value = hashMapOf()
    }
}