package com.example.stalcraftobserver.presentation.artefactBuildScreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.ArtefactBuildViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedItemIdViewModel
import com.example.stalcraftobserver.presentation.artefactBuildScreen.components.CustomArtefactGrid
import com.example.stalcraftobserver.presentation.common.TripleAttributeRow
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme
import com.example.stalcraftobserver.util.NavigationItem
import com.example.stalcraftobserver.util.itemSupportModel.Artefact

@Composable
fun ArtefactBuildScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    sharedItemIdViewModel: SharedItemIdViewModel,
    artefactBuildViewModel: ArtefactBuildViewModel
) {
    LaunchedEffect(Unit) {
        artefactBuildViewModel.clearArtefacts()
    }

    val artefacts by sharedItemIdViewModel.items.collectAsState()
    val stats by artefactBuildViewModel.statsList.collectAsState()
    val artefactsList by artefactBuildViewModel.artefactsList.collectAsState()
    val maxCell by artefactBuildViewModel.cellMaxGlobal.collectAsState()

    var isChanged by remember { mutableStateOf(false) }
    var selectedArtefactIndex by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(isChanged) {
        artefacts.forEach { (slot, id) ->
            val cellIndex = slot.removePrefix("artefact").toIntOrNull() ?: -1
            if (cellIndex in 0 until maxCell && !artefactBuildViewModel.checkArtefact(id, slot)) {
                artefactBuildViewModel.addArtefact(id, slot)
            }
        }
        isChanged = false
    }

    LazyColumn {
        item {
            CustomArtefactGrid(
                modifier = Modifier.padding(6.dp),
                maxCountCell = maxCell,
                artefacts = (0 until maxCell).map { index ->
                    artefactsList["artefact$index"]
                },
                onCellClick = { cellIndex ->
                    if (selectedArtefactIndex != null) {
                        Log.d("Copy", "Copy Artefact $selectedArtefactIndex to $cellIndex")
                        artefactBuildViewModel.copyArtefact(selectedArtefactIndex!!, cellIndex)
                        sharedItemIdViewModel.getItem("artefact$selectedArtefactIndex")
                            ?.let { sharedItemIdViewModel.setItem("artefact$cellIndex", it) }
                        selectedArtefactIndex = null
                    } else {
                        val itemSlot = "artefact$cellIndex"
                        navController.navigate(
                            NavigationItem.SelectItem.createRoute(itemSlot, listOf("artefact"))
                        )
                        isChanged = true
                    }
                },
                onDelete = { index ->
                    artefactBuildViewModel.removeArtefact("artefact$index")
                    sharedItemIdViewModel.clearItems("artefact$index")
                },
                onCopy = { index ->
                    selectedArtefactIndex = index
                }
            )
        }

        item {
            stats.let {
                it.forEach { (key, value, type) ->
                    TripleAttributeRow(label = key, value = value, type = type)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(device = "spec:width=411dp,height=891dp,dpi=420") // Pixel 4
@Preview(device = "spec:width=360dp,height=740dp,dpi=320") // Nexus 5
@Preview(device = "spec:width=320dp,height=480dp,dpi=160") // Small Phone
@Preview(device = "spec:width=600dp,height=1024dp,dpi=240") // 7-inch Tablet
@Preview(device = "spec:width=800dp,height=1280dp,dpi=240") // 10-inch Tablet
@Preview(device = "spec:width=1024dp,height=1366dp,dpi=264") // iPad Pro 10.5
@Preview(device = "spec:width=1440dp,height=2560dp,dpi=560") // Pixel XL
@Preview(device = "spec:width=1080dp,height=1920dp,dpi=480") // Full HD Phone
@Preview(device = "spec:width=1440dp,height=2960dp,dpi=560") // Galaxy S8
@Preview(device = "spec:width=768dp,height=1024dp,dpi=160") // Nexus 7
@Composable
fun GreetingPreview12() {
    StalcraftObserverTheme {

    }
}