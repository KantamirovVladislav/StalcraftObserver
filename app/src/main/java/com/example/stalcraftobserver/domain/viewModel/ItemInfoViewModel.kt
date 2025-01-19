package com.example.stalcraftobserver.domain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.AuctionResponse
import com.example.stalcraftobserver.data.manager.ItemDataService
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.data.manager.PriceHistoryResponse
import com.example.stalcraftobserver.domain.model.ErrorEntity
import com.example.stalcraftobserver.domain.model.ErrorsType
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.domain.model.entities.Item
import com.example.stalcraftobserver.domain.model.StalcraftApplication
import com.example.stalcraftobserver.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    application: Application,
    @Named("RoomDataService") private val itemsRoomService: ItemsRoomService,
    @Named("ItemDataService") private val itemDataService: ItemDataService
) : ViewModel() {

    private var _info = MutableStateFlow<ItemInfo?>(null)
    private var _priceHistory = MutableStateFlow<PriceHistoryResponse?>(null)
    private var _activeLots = MutableStateFlow<AuctionResponse?>(null)
    private val _errorQueue = MutableStateFlow<List<ErrorEntity>>(emptyList())

    val errorQueue = _errorQueue.asStateFlow()

    var info = _info.asStateFlow()
    var priceHistory = _priceHistory.asStateFlow()
    var activeLots = _activeLots.asStateFlow()
    var onCriticalError: () -> Unit = {}

    private val app = application as StalcraftApplication

    fun handleCriticalError() {
        removeAllErrorFromQueue()
        onCriticalError()
    }

    fun addErrorToQueue(error: ErrorEntity) {
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
    fun removeAllErrorFromQueue() {
        if (_errorQueue.value.isNotEmpty()) {
            _errorQueue.value = emptyList()
        }
    }

    fun getItemWithId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val itemsResult = itemsRoomService.getItemWithId(id = id)) {
            is FunctionResult.Success -> {
                getItemData(itemsResult.data)
            }

            is FunctionResult.Error -> {
                addErrorToQueue(
                    ErrorEntity(
                        errorType = ErrorsType.ERROR,
                        label = "Не удалось получить данные из локального хранилища",
                        onDismissRequest = ::handleCriticalError
                    )
                )
                Log.e(Constants.ERROR_DATABASE_TAG, itemsResult.message)
            }
        }
    }

    private fun getItemData(item: Item?) = viewModelScope.launch {
        if (item == null) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Item is null in getItemData")
            addErrorToQueue(
                ErrorEntity(
                    errorType = ErrorsType.ERROR,
                    label = "Произошла непредвиденная ошибка",
                    onDismissRequest = ::handleCriticalError
                )
            )
            return@launch
        }
        when (val itemData = itemDataService.getItemData(item)) {
            is FunctionResult.Success -> {
                _info.value = itemData.data
                Log.i(Constants.RETROFIT_CLIENT_GIT_SUCCES, itemData.data.toString())
            }

            is FunctionResult.Error -> {
                addErrorToQueue(
                    ErrorEntity(
                        errorType = ErrorsType.ERROR,
                        label = "Не получилось получить данные об предмете, проверьте подключение к интернету",
                        onDismissRequest = ::handleCriticalError
                    )
                )
                Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, itemData.message)
            }
        }
    }

    fun getPriceHistory(item: String?) = viewModelScope.launch {
        if (item == null) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Item is null in getItemData")
            addErrorToQueue(
                ErrorEntity(
                    errorType = ErrorsType.ERROR,
                    label = "Произошла непредвиденная ошибка",
                    onDismissRequest = ::handleCriticalError
                )
            )
            return@launch
        }
        when (val itemData = itemDataService.getItemPriceHistory(item)) {
            is FunctionResult.Success -> {
                _priceHistory.value = itemData.data
                Log.i(Constants.RETROFIT_CLIENT_GIT_SUCCES, itemData.data.toString())
            }

            is FunctionResult.Error -> {
                addErrorToQueue(
                    ErrorEntity(
                        errorType = ErrorsType.WARNING,
                        label = "Не удалось получить данные Stalcraft",
                        onDismissRequest = ::removeErrorFromQueue
                    )
                )
                Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, itemData.message)
            }
        }
    }

    fun getActiveLots(item: String?) = viewModelScope.launch {
        if (item == null) {
            Log.e(Constants.ERROR_DATABASE_TAG, "Item is null in getItemData")
            addErrorToQueue(
                ErrorEntity(
                    errorType = ErrorsType.ERROR,
                    label = "Произошла непредвиденная ошибка",
                    onDismissRequest = ::handleCriticalError
                )
            )
            return@launch
        }
        when (val itemData = itemDataService.getItemActiveLots(item)) {
            is FunctionResult.Success -> {
                _activeLots.value = itemData.data
                Log.i(Constants.RETROFIT_CLIENT_GIT_SUCCES, itemData.data.toString())
            }

            is FunctionResult.Error -> {
                addErrorToQueue(
                    ErrorEntity(
                        errorType = ErrorsType.WARNING,
                        label = "Не удалось получить данные Stalcraft",
                        onDismissRequest = ::removeErrorFromQueue
                    )
                )
                Log.e(Constants.RETROFIT_CLIENT_GIT_ERROR, itemData.message)
            }
        }
    }
}