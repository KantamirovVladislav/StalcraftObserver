package com.example.stalcraftobserver.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemDataService
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.domain.model.DialogEntity
import com.example.stalcraftobserver.domain.model.ErrorsType
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _errorQueue = MutableStateFlow<List<DialogEntity>>(emptyList())
    val errorQueue = _errorQueue.asStateFlow()
    var onCriticalError: () -> Unit = {}

    fun handleCriticalError() {
        removeAllErrorFromQueue()
        onCriticalError()
    }

    fun addErrorToQueue(error: DialogEntity) {
        if (_errorQueue.value.none { it.label == error.label }) {
            _errorQueue.value = _errorQueue.value.toMutableList().apply { add(error) }
        }
        Log.d("ErrorQueue", _errorQueue.value.toString())
    }

    fun removeErrorFromQueue() {
        if (_errorQueue.value.isNotEmpty()) {
            _errorQueue.value = _errorQueue.value.toMutableList().apply { removeLastOrNull() }
        }
    }

    fun removeErrorFromQueue(error: DialogEntity) {
        if (_errorQueue.value.isNotEmpty()) {
            _errorQueue.value = _errorQueue.value.toMutableList().apply { remove(error) }
        }
    }

    fun removeAllErrorFromQueue() {
        if (_errorQueue.value.isNotEmpty()) {
            _errorQueue.value = emptyList()
        }
    }

    fun setItem1Id(id: String?) {
        if (id == null) {
            item1.value = null
        } else {
            fetchItemWithId(id) { item1.value = it }
        }
        Log.d("CompareItemsViewModel", "Item1 updated: $id")
    }

    fun setItem2Id(id: String?) {
        if (id == null) {
            item2.value = null
        } else {
            fetchItemWithId(id) { item2.value = it }
        }
        Log.d("CompareItemsViewModel", "Item2 updated: $id")
    }

    private fun fetchItemWithId(id: String, onResult: (ItemInfo?) -> Unit) {
        val loadingDialog = DialogEntity(
            errorType = ErrorsType.LOADING,
            label = "Идёт загрузка, подождите",
            onDismissRequest = { }
        )
        addErrorToQueue(
            loadingDialog
        )
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val itemResult = itemsRoomService.getItemWithId(id)) {
                is FunctionResult.Success -> {
                    val item = itemResult.data

                    when (val itemData = itemDataService.getItemData(item)) {
                        is FunctionResult.Success -> {
                            removeErrorFromQueue(loadingDialog)
                            itemData.data
                        }

                        is FunctionResult.Error -> {
                            Log.e(Constants.ERROR_DATABASE_TAG, itemData.message)
                            addErrorToQueue(
                                DialogEntity(
                                    errorType = ErrorsType.ERROR,
                                    label = "Не удолось получить данные (Проверьте подключение к интернету)",
                                    onDismissRequest = ::handleCriticalError
                                )
                            )
                            null
                        }
                    }
                }

                is FunctionResult.Error -> {
                    addErrorToQueue(
                        DialogEntity(
                            errorType = ErrorsType.ERROR,
                            label = "Не удолось получить данные из локального хранилища",
                            onDismissRequest = ::handleCriticalError
                        )
                    )
                    Log.e(Constants.ERROR_DATABASE_TAG, itemResult.message)
                    null
                }
            }
            onResult(result)
        }
    }
}