package com.example.stalcraftobserver.domain.viewModel

import android.util.DumpableContainer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.data.manager.ItemDataService
import com.example.stalcraftobserver.data.manager.ItemsRoomService
import com.example.stalcraftobserver.domain.model.ArtefactAssembly
import com.example.stalcraftobserver.domain.model.DialogEntity
import com.example.stalcraftobserver.domain.model.ErrorsType
import com.example.stalcraftobserver.domain.model.FunctionResult
import com.example.stalcraftobserver.util.Constants
import com.example.stalcraftobserver.util.ItemInfoHelper
import com.example.stalcraftobserver.util.ItemProperty
import com.example.stalcraftobserver.util.itemSupportModel.Artefact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ArtefactBuildViewModel @Inject constructor(
    @Named("RoomDataService") private val itemsRoomService: ItemsRoomService,
    @Named("ItemDataService") private val itemDataService: ItemDataService
) : ViewModel() {

    private var cellMax = MutableStateFlow(0)

    val cellMaxGlobal = cellMax.asStateFlow()

    private val _isContainerChoosed = MutableStateFlow(false)

    val isContainerChoosed = _isContainerChoosed.asStateFlow()

    private var _artefactController = ArtefactAssembly(cellMax.value)

    private val _statsAsString = MutableStateFlow("")

    val artefactsList = MutableStateFlow(emptyMap<String, Artefact>())

    val statsAsString = _statsAsString.asStateFlow()

    private val _statsList = MutableStateFlow<List<Triple<String, String, String>>>(emptyList())
    val statsList = _statsList.asStateFlow()

    fun checkArtefact(id: String, slot: String): Boolean {
        return _artefactController.checkArtefact(id, slot)
    }

    fun clearArtefacts(){
        _artefactController.clearArtefacts()
        updateArtefactList()
        buildStatsList()
    }

    fun updateArtefactList() {
        Log.d("UpdateList", "Updating")
        val newList = _artefactController.artefacts
        artefactsList.value = newList.toMap()
        Log.d("UpdateList", artefactsList.value.toString())
    }

    fun removeArtefact(slot: String) {
        _artefactController.removeArtefact(slot)
        buildStatsList()
    }

    fun copyArtefact(fromIndex: Int, toIndex: Int) {
        val artefactToCopy = _artefactController.artefacts["artefact$fromIndex"]
        Log.d("CopyArtefact", artefactToCopy.toString())
        if (artefactToCopy != null) {
            _artefactController.replaceArtefact("artefact$toIndex", artefactToCopy.copy())
        }
        buildStatsList()
    }

    fun buildStatsList() {
        updateArtefactList()
        val newList = _artefactController.buildStatsList()
        _statsList.value = newList
    }

    fun updateCellMax(container: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val itemResult = itemsRoomService.getItemWithId(container)) {
                is FunctionResult.Success -> {
                    val item = itemResult.data

                    when (val itemData = itemDataService.getItemData(item)) {
                        is FunctionResult.Success -> {
                            cellMax.value = ItemInfoHelper.getValuesForKey(
                                itemData.data,
                                ItemProperty.Container.Stats.SIZE
                            ).values.firstOrNull()?.ru?.toDouble()?.toInt() ?: 0
                            Log.d("Cells", cellMax.toString())
                            _artefactController = ArtefactAssembly(cellMax.value)
                            _isContainerChoosed.value = true
                        }

                        is FunctionResult.Error -> {
                            Log.e(Constants.ERROR_DATABASE_TAG, itemData.message)
                            null
                        }
                    }
                }

                is FunctionResult.Error -> {
                    Log.e(Constants.ERROR_DATABASE_TAG, itemResult.message)
                    null
                }
            }
        }
    }

    fun addArtefact(id: String, slot: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val itemResult = itemsRoomService.getItemWithId(id)) {
                is FunctionResult.Success -> {
                    val item = itemResult.data

                    when (val itemData = itemDataService.getItemData(item)) {
                        is FunctionResult.Success -> {
                            ItemInfoHelper.getArtefactClassFromItemInfo(itemData.data)
                                ?.let { _artefactController.addArtefact(slot, it) }
                            buildStatsList()
                        }

                        is FunctionResult.Error -> {
                            Log.e(Constants.ERROR_DATABASE_TAG, itemData.message)
                            null
                        }
                    }
                }

                is FunctionResult.Error -> {
                    Log.e(Constants.ERROR_DATABASE_TAG, itemResult.message)
                    null
                }
            }
        }
    }

    fun getStats() {
        _statsAsString.value = _artefactController.buildStatsString()
    }
}