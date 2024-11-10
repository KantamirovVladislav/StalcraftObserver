package com.example.stalcraftobserver.presentation.itemsListing

import android.util.Log
import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.domain.model.ItemViewModel
import com.example.stalcraftobserver.presentation.itemsListing.components.ItemCell
import com.example.stalcraftobserver.util.Constants
import com.example.stalcraftobserver.util.NavigationItem
import com.example.stalcraftobserver.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ItemsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ItemViewModel
) {
    val itemsState by viewModel.itemsList.collectAsState()

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
                    .height(((LocalConfiguration.current.screenHeightDp / 4) + 10).dp)
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



