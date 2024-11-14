package com.example.stalcraftobserver.presentation.itemsListing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.model.ItemViewModel
import com.example.stalcraftobserver.presentation.common.FilterChipsList
import com.example.stalcraftobserver.presentation.common.SearchView
import com.example.stalcraftobserver.presentation.itemsListing.components.ItemCell
import com.example.stalcraftobserver.util.NavigationItem

@Composable
fun ItemsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ItemViewModel
) {
    val itemsState by viewModel.itemsList.collectAsState()
    val selectedFilters = remember { mutableStateListOf<String>() }
    val searchQuery = remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        SearchView(
            query = searchQuery.value,
            onQueryChanged = { searchQuery.value = it },
            modifier = Modifier.padding(8.dp)
        )
        FilterChipsList(
            listOf("Price more", "Price less", "A-Z", "Z-A", "Rarity", "Categories"),
            { value -> selectedFilters.add(value) },
            Modifier.padding(horizontal = 4.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            items(itemsState) { item ->
                ItemCell(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(((LocalConfiguration.current.screenHeightDp / 5) + 10).dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigate(NavigationItem.ItemInfo(item.id).route)
                        },
                    item = item,
                    region = "ru"
                )
            }

            item {
                LaunchedEffect(Unit) {
                    if (viewModel.shouldLoadMore()) {
                        viewModel.loadMoreItems()
                    }
                }
            }
        }
    }
}






