// package com.example.stalcraftobserver.presentation.itemsListing

package com.example.stalcraftobserver.presentation.itemsListing

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.model.FilterItem
import com.example.stalcraftobserver.domain.viewModel.ItemViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedCompareItemsViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedItemViewModel
import com.example.stalcraftobserver.presentation.common.TopAppBarWithSearchAndFilter
import com.example.stalcraftobserver.presentation.itemsListing.components.ItemCell
import com.example.stalcraftobserver.util.NavigationItem
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ItemViewModel,
    mode: String = "view",
    itemSlot: String? = null,
    category: List<String>? = null,
    sharedItemViewModel: SharedItemViewModel,
    sharedCompareItemsViewModel: SharedCompareItemsViewModel
) {
    val itemsState = viewModel.itemsList
    val gridState = rememberLazyGridState()
    val selectedFilters = remember { mutableStateListOf<FilterItem>() }
    val searchQuery = remember { mutableStateOf("") }
    val disabledFilters = remember { mutableStateListOf<FilterItem>() }
    val filters = listOf(
        FilterItem(name = "A-z", group = "alphabetSort"),
        FilterItem(name = "Z-a", group = "alphabetSort"),
        FilterItem(name = "Category", group = "categorySort"),
        FilterItem(name = "Rarity", group = "raritySort")
    )

    val currentHeightCell: Dp = ((LocalConfiguration.current.screenHeightDp / 5) + 10).dp
    val currentCellCount: Int = LocalConfiguration.current.screenWidthDp / 130

    if (!category.isNullOrEmpty()) {
        LaunchedEffect(category) {
            disabledFilters.add(filters[2])
            viewModel.updateCategoryFilters(category)
        }
    }

    TopAppBarWithSearchAndFilter(
        query = searchQuery.value,
        onQueryChanged = {
            searchQuery.value = it
            viewModel.searchItems(it)
        },
        filters = filters,
        selectedFilters = selectedFilters,
        onFilterSelected = { updatedFilters ->
            applyFilters(updatedFilters, viewModel)
        },
        onMenuSelected = { menu ->
            when (menu) {
                "Loadout" -> {
                    navController.navigate(NavigationItem.Loadout.createRoute())
                }
                "Сравнить" -> {
                    navController.navigate(NavigationItem.CompareItems.createRoute())
                }
                "Artefact_build" ->{
                    navController.navigate(NavigationItem.ContainerSelectScreen.createRoute())
                }
            }
        },
        isFilterDisabled = { it in disabledFilters },
        onBack = { navController.popBackStack() }
    ) { modifierFromTopBar ->
        Column(modifier = modifierFromTopBar.fillMaxSize()) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(currentCellCount),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                items(itemsState) { item ->
                    var isHearted by remember { mutableStateOf(false) }
                    ItemCell(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(currentHeightCell)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                                when (mode) {
                                    "view" -> navController.navigate(NavigationItem.ItemInfo.createRoute(item.id))
                                    "selection" -> {
                                        // Передаём выбранный id обратно через SharedLoadoutViewModel
                                        itemSlot?.let { slot ->
                                            when (slot) {
                                                "weapon" -> sharedItemViewModel.setWeaponId(item.id)
                                                "armor" -> sharedItemViewModel.setArmorId(item.id)
                                                "item1" -> sharedCompareItemsViewModel.setItem1Id(item.id)
                                                "item2" -> sharedCompareItemsViewModel.setItem2Id(item.id)
                                            }
                                        }
                                        // Возвращаемся на предыдущий экран
                                        navController.popBackStack()
                                    }
                                }
                            },
                        item = item,
                        region = "ru",
                        onHeartClick = { hearted ->
                            isHearted = hearted
                            Log.d("LazyVerticalGrid", "Item ID: ${item.id}, Hearted: $hearted")
                        }
                    )
                }
            }

            LaunchedEffect(gridState) {
                snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { lastVisibleIndex ->
                        if (lastVisibleIndex == itemsState.size - 1 && viewModel.shouldLoadMore()) {
                            viewModel.loadMoreItems()
                        }
                    }
            }
        }
    }
}

fun applyFilters(filters: List<FilterItem>, viewModel: ItemViewModel) {
    val sortCriteria = filters.mapNotNull {
        when (it.name) {
            "A-z" -> "nameEng ASC"
            "Z-a" -> "nameEng DESC"
            else -> null
        }
    }
    viewModel.updateSortFilters(sortCriteria.ifEmpty { listOf("nameEng ASC") })
}
