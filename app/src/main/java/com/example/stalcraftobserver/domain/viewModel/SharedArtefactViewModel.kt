package com.example.stalcraftobserver.domain.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedArtefactViewModel : ViewModel() {

    // Внутренний MutableStateFlow (список из 6 "слотов" для артефактов)
    private val _artefactItemIds = MutableStateFlow(List<String?>(6) { null })
    // Публичный StateFlow, на который будет подписываться UI
    val artefactItemIds = _artefactItemIds.asStateFlow()

    // Устанавливаем артефакт (itemId) по индексу ячейки
    fun setArtefact(slot: Int, artefactId: String?) {
        val currentList = _artefactItemIds.value.toMutableList()
        if (slot in currentList.indices) {
            currentList[slot] = artefactId
            _artefactItemIds.value = currentList
        }
    }

    // Можно добавить метод для очистки слота
    fun clearArtefact(slot: Int) {
        val currentList = _artefactItemIds.value.toMutableList()
        if (slot in currentList.indices) {
            currentList[slot] = null
            _artefactItemIds.value = currentList
        }
    }
}