package com.example.stalcraftobserver.domain.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedCompareItemsViewModel @Inject constructor() : ViewModel() {
    private val _item1Id = MutableStateFlow<String?>(null)
    val item1Id: StateFlow<String?> = _item1Id

    private val _item2Id = MutableStateFlow<String?>(null)
    val item2Id: StateFlow<String?> = _item2Id

    fun setItem1Id(id: String?) {
        if (id == null) {
            _item1Id.value = null
        } else {
            _item1Id.value = id
        }
    }

    fun setItem2Id(id: String?) {
        if (id == null) {
            _item2Id.value = null
        } else {
            _item2Id.value = id
        }
    }
}
