package com.example.stalcraftobserver.presentation.itemsListing

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.model.FilterItem
import com.example.stalcraftobserver.domain.viewModel.ItemViewModel
import com.example.stalcraftobserver.presentation.common.TopAppBarWithSearchAndFilter
import com.example.stalcraftobserver.presentation.itemsListing.components.ItemCell
import com.example.stalcraftobserver.util.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ItemViewModel,
    mode: String = "view", // "view" - обычный режим, "selection" - выбор предмета
    itemSlot: String? = null,
    category: String? = null
) {

    val itemsState = viewModel.itemsList
    val gridState = rememberLazyGridState()
    val selectedFilters = remember { mutableStateListOf<FilterItem>() }
    val searchQuery = remember { mutableStateOf("") }

    //var showCategoryDialog by remember { mutableStateOf(false) }
    //var showRarityDialog by remember { mutableStateOf(false) }
    val selectedCategories = remember { mutableStateListOf<String>() }
    val selectedRarities = remember { mutableStateListOf<String>() }
    val disabledFilters = remember { mutableStateListOf<FilterItem>() }

    val filters = listOf(
        FilterItem(name = "A-z", group = "alphabetSort"),
        FilterItem(name = "Z-a", group = "alphabetSort"),
        FilterItem(name = "Category", group = "categorySort"),
        FilterItem(name = "Rarity", group = "raritySort")
    )

    val currentHeightCell: Dp = ((LocalConfiguration.current.screenHeightDp / 5) + 10).dp
    val currentCellCount: Int = LocalConfiguration.current.screenWidthDp / 130

    if (category != null) {
        LaunchedEffect(category) {
            disabledFilters.addAll(listOf(filters[2]))
            viewModel.updateCategoryFilters(listOf(category))
        }
    }

    TopAppBarWithSearchAndFilter(
        query = searchQuery.value,
        onQueryChanged = { newQuery ->
            searchQuery.value = newQuery
            viewModel.searchItems(newQuery)
        },
        filters = filters,
        selectedFilters = selectedFilters,
        onFilterSelected = { updatedFilters ->
            updatedFilters.forEach { filter ->
                when (filter.name) {
                    //"Category" -> showCategoryDialog = true
                    //"Rarity" -> showRarityDialog = true
                    else -> {
                        // Применение сортировки
                        val sortCriteria = updatedFilters.mapNotNull { f ->
                            when (f.name) {
                                "A-z" -> "nameEng ASC"
                                "Z-a" -> "nameEng DESC"
                                else -> null
                            }
                        }
                        // Если фильтров сортировки нет, задаём сортировку по умолчанию
                        if (sortCriteria.isEmpty()) {
                            viewModel.updateSortFilters(listOf("nameEng ASC"))


                        } else {
                            viewModel.updateSortFilters(sortCriteria)
                        }
                    }
                }
            }
        },
        onMenuSelected = { value ->
            navController.navigate("compare_items?item1Id=${""}&item2Id=${""}")
        },
        isFilterDisabled = { filter -> filter in disabledFilters },
        onBack = {
            if (mode == "view") navController.popBackStack()
        }
    ) { modifierFromTopBar ->
        Column(
            modifier = modifierFromTopBar
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(currentCellCount),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                items(itemsState) { item ->
                    ItemCell(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(currentHeightCell)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (mode == "view") {
                                    navController.navigate(NavigationItem.ItemInfo(item.id).route)
                                } else if (mode == "selection") {
                                    navController.previousBackStackEntry?.savedStateHandle?.set(
                                        itemSlot ?: "Item not selected",
                                        item.id
                                    )
                                    navController.popBackStack()
                                }
                            },
                        item = item,
                        region = "ru"
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

//            // Диалог выбора категорий
//            if (showCategoryDialog) {
//                CategoryDialog(
//                    availableCategories = CategoryItemHelper.getAllCategories(),
//                    selectedCategories = selectedCategories,
//                    onConfirm = { selected ->
//                        selectedCategories.clear()
//                        selectedCategories.addAll(selected)
//                        showCategoryDialog = false
//                        viewModel.updateCategoryFilters(selected)
//                    },
//                    onDismiss = { showCategoryDialog = false }
//                )
//            }
//
//            // Диалог выбора редкостей
//            if (showRarityDialog) {
//                RarityDialog(
//                    availableRarities = RarityItemHelper.getAllRarities(),
//                    selectedRarities = selectedRarities,
//                    onConfirm = { selected ->
//                        selectedRarities.clear()
//                        selectedRarities.addAll(selected)
//                        showRarityDialog = false
//                        viewModel.updateRarityFilters(selected)
//                    },
//                    onDismiss = { showRarityDialog = false }
//                )
//            }
        }
    }
}