package com.example.stalcraftobserver.presentation.itemsListing

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.model.FilterItem
import com.example.stalcraftobserver.domain.model.StalcraftApplication
import com.example.stalcraftobserver.domain.viewModel.ItemViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedItemIdViewModel
import com.example.stalcraftobserver.presentation.common.DropdownMenuMore
import com.example.stalcraftobserver.presentation.common.FilterChipsList
import com.example.stalcraftobserver.presentation.common.SearchView
import com.example.stalcraftobserver.presentation.common.SettingsPanel
import com.example.stalcraftobserver.presentation.itemsListing.components.FilterSelector
import com.example.stalcraftobserver.presentation.itemsListing.components.ItemCell
import com.example.stalcraftobserver.util.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ItemViewModel,
    mode: String = "view",
    itemSlot: String? = null,
    category: List<String>? = null,
    sharedItemIdViewModel: SharedItemIdViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val collapsedFraction = scrollBehavior.state.collapsedFraction

    var isSettingsVisible by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var isCategoryFilterVisible by remember { mutableStateOf(false) }
    var isRarityFilterVisible by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val categorySheetState = rememberModalBottomSheetState()
    val raritySheetState = rememberModalBottomSheetState()
    val application = StalcraftApplication()

    val itemsState by viewModel.itemsList.collectAsState()
    val favoritesState by viewModel.favoritesId.collectAsState()
    val errorFavorite by viewModel.onErrorFavorite.collectAsState()
    val selectedFilters by viewModel.selectedFilters.collectAsState()
    val disabledFilters by viewModel.disabledFilters.collectAsState()
    val globalExpandedFilter by viewModel.globalExtendFilters.collectAsState()
    val selectedCategoryFilter by viewModel.selectedCategoryFilter.collectAsState()
    val selectedRarityFilter by viewModel.selectedRarityFilters.collectAsState()

    val gridState = rememberLazyGridState()
    val searchQuery = remember { mutableStateOf("") }
    val triggeringGlobalFilter = remember { mutableStateOf<FilterItem?>(null) }
    val triggerRarityFilter = remember { mutableStateOf<FilterItem?>(null) }
    val filters = listOf(
        FilterItem(name = "A-z", group = "alphabetSort"),
        FilterItem(name = "Z-a", group = "alphabetSort"),
        FilterItem(
            name = "Category", group = "categorySort", extendFilters = listOf(
                FilterItem(
                    name = "armor", group = "armor", extendFilters = listOf(
                        FilterItem(name = "clothes", group = "armor"),
                        FilterItem(name = "combat", group = "armor"),
                        FilterItem(name = "combined", group = "armor"),
                        FilterItem(name = "device", group = "armor"),
                        FilterItem(name = "scientist", group = "armor"),
                    )
                ),
                FilterItem(
                    name = "artefact", group = "artefact", extendFilters = listOf(
                        FilterItem(name = "biochemical", group = "artefact"),
                        FilterItem(name = "electrophysical", group = "artefact"),
                        FilterItem(name = "gravity", group = "artefact"),
                        FilterItem(name = "other_arts", group = "artefact"),
                        FilterItem(name = "thermal", group = "artefact"),
                    )
                ),
                FilterItem(
                    name = "attachment", group = "attachment", extendFilters = listOf(
                        FilterItem(name = "barrel", group = "attachment"),
                        FilterItem(name = "collimator_sights", group = "attachment"),
                        FilterItem(name = "forend", group = "attachment"),
                        FilterItem(name = "mag", group = "attachment"),
                        FilterItem(name = "other", group = "attachment"),
                        FilterItem(name = "pistol_handle", group = "attachment"),
                    )
                ),
                FilterItem(
                    name = "weapon", group = "weapon", extendFilters = listOf(
                        FilterItem(name = "assault_rifle", group = "weapon"),
                        FilterItem(name = "device", group = "weapon"),
                        FilterItem(name = "heavy", group = "weapon"),
                        FilterItem(name = "machine_gun", group = "weapon"),
                        FilterItem(name = "melee", group = "weapon"),
                        FilterItem(name = "pistol", group = "weapon"),
                        FilterItem(name = "shotgun_rifle", group = "weapon"),
                        FilterItem(name = "sniper_rifle", group = "weapon"),
                        FilterItem(name = "submachine_gun", group = "weapon"),
                    )
                ),
                FilterItem(name = "backpack", group = "backpack"),
                FilterItem(name = "bullet", group = "bullet"),
                FilterItem(name = "containers", group = "containers"),
                FilterItem(name = "drink", group = "drink"),
                FilterItem(name = "food", group = "food"),
                FilterItem(name = "grenade", group = "grenade"),
                FilterItem(name = "medicine", group = "medicine"),
                FilterItem(name = "misc", group = "misc"),
                FilterItem(name = "other", group = "other")
            )
        ),
        FilterItem(
            name = "Rarity", group = "raritySort", extendFilters = listOf(
                FilterItem(name = "None", group = "raritySort"),
                FilterItem(name = "Common", group = "raritySort"),
                FilterItem(name = "Picklock", group = "raritySort"),
                FilterItem(name = "Newbie", group = "raritySort"),
                FilterItem(name = "Stalker", group = "raritySort"),
                FilterItem(name = "Veteran", group = "raritySort"),
                FilterItem(name = "Master", group = "raritySort"),
                FilterItem(name = "Legend", group = "raritySort"),
            )
        )
    )

    LaunchedEffect(category) {

    }

    LaunchedEffect(selectedCategoryFilter) {
        val shouldBeSelected = globalExpandedFilter.filter { filter ->
            selectedCategoryFilter.any { it.group.toString() == filter.group.toString() }
        }

        val shouldBeUnSelected = globalExpandedFilter.filter { filter ->
            selectedCategoryFilter.none { it.group.toString() == filter.group.toString() }
        }
        Log.d("Unselected", globalExpandedFilter.toString())
        Log.d("Unselected", selectedCategoryFilter.toString())
        shouldBeSelected.forEach(viewModel::selectFilter)
        shouldBeUnSelected.forEach(viewModel::disableFilter)

        if (selectedCategoryFilter.isNotEmpty()){
            viewModel.selectFilter(filters[2])
        } else {
            viewModel.disableFilter(filters[2])
        }
    }

    LaunchedEffect(selectedRarityFilter) {
        if (selectedRarityFilter.isNotEmpty()){
            viewModel.selectFilter(filters[3])
        }
        else {
            viewModel.disableFilter(filters[3])
        }
    }



    LaunchedEffect(Unit) {
        viewModel.selectFilter(filters[0])
    }

    val currentHeightCell: Dp = ((LocalConfiguration.current.screenHeightDp / 5) + 10).dp
    val currentCellCount: Int = LocalConfiguration.current.screenWidthDp / 130

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    SearchView(
                        query = searchQuery.value,
                        onQueryChanged = { viewModel.searchItems(searchQuery.value) }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More Options"
                        )
                        DropdownMenuMore(
                            isMenuExpanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false },
                            onMenuItemClick = { menu ->
                                when (menu) {
                                    "Loadout" -> navController.navigate(NavigationItem.Loadout.createRoute())
                                    "Сравнить" -> navController.navigate(NavigationItem.CompareItems.createRoute())
                                    "Artefact_build" -> navController.navigate(NavigationItem.ContainerSelectScreen.createRoute())
                                }
                            }
                        )
                    }
                    IconButton(onClick = { isSettingsVisible = !isSettingsVisible }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(
                visible = collapsedFraction == 0f,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                FilterChipsList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    filterList = filters,
                    selectedFilters = selectedFilters,
                    disabledFilters = disabledFilters,
                    onFilterSelected = {
                        if (it.group == "raritySort"){
                            viewModel.collapseExtendFilters()
                            triggerRarityFilter.value = it
                            isRarityFilterVisible = true
                        } else if (it.group == "categorySort"){
                            viewModel.selectFilter(it)
                        }
                        else if (it.group == "alphabetSort"){
                            viewModel.collapseExtendFilters()
                            viewModel.selectFilter(it)
                            viewModel.updateSortFilters(it)
                        }
                    },
                    onFilterDisabled = {
                        if (it.group == "raritySort"){
                            triggerRarityFilter.value = it
                            isRarityFilterVisible = true
                        } else if (it.group == "categorySort"){
                            viewModel.selectFilter(it)
                        }
                        else if (it.group == "alphabetSort"){
                            viewModel.disableFilter(it)
                        }
                    }
                )
            }

            AnimatedVisibility(
                visible = collapsedFraction == 0f,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                FilterChipsList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    filterList = globalExpandedFilter.toList(),
                    selectedFilters = selectedFilters,
                    disabledFilters = disabledFilters,
                    onFilterSelected = {
                        triggeringGlobalFilter.value = it
                        isCategoryFilterVisible = true
                    },
                    onFilterDisabled = {
                        triggeringGlobalFilter.value = it
                        isCategoryFilterVisible = true
                    }
                )
            }

            if (isCategoryFilterVisible) {
                FilterSelector(
                    sheetState = categorySheetState,
                    filterItem = triggeringGlobalFilter.value!!,
                    onCloseSheet = { isCategoryFilterVisible = false },
                    previouslySelectedFilters = selectedCategoryFilter.toSet(),
                    onFiltersSelected = viewModel::updateCategoryFilters,
                    onFilterDisabled = viewModel::deleteCategoryFilters
                )
            }

            if (isRarityFilterVisible){
                FilterSelector(
                    sheetState = raritySheetState,
                    filterItem = triggerRarityFilter.value!!,
                    onCloseSheet = { isRarityFilterVisible = false },
                    previouslySelectedFilters = selectedRarityFilter.toSet(),
                    onFiltersSelected = viewModel::updateRarityFilters,
                    onFilterDisabled = viewModel::deleteRarityFilters,
                    isGlobalVisible = false
                )
            }

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(currentCellCount),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                items(itemsState.toList()) { item ->
                    ItemCell(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(currentHeightCell)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                when (mode) {
                                    "view" -> navController.navigate(
                                        NavigationItem.ItemInfo.createRoute(
                                            item.id
                                        )
                                    )

                                    "selection" -> {
                                        itemSlot?.let { sharedItemIdViewModel.setItem(it, item.id) }
                                        navController.popBackStack()
                                    }
                                }
                            },
                        item = item,
                        region = "ru",
                        isHearted = favoritesState.any { it.favoriteId == item.id },
                        onHeartClick = { hearted ->
                            if (hearted) viewModel.setFavorite(item.id) else viewModel.removeFavorite(
                                item.id
                            )
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

            if (isSettingsVisible) {
                SettingsPanel(
                    sheetState = sheetState,
                    onCloseSheet = { isSettingsVisible = false },
                    application = application
                )
            }
        }

        if (errorFavorite) {
            Toast.makeText(
                LocalContext.current,
                "Ошибка при загрузке списка избранного.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}