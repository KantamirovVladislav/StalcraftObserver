package com.example.stalcraftobserver.presentation.itemsListing

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.presentation.common.ItemCell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ItemsListScreen() {
    val context = LocalContext.current
    val db = remember { ItemsService(context).db }
    val itemsState = remember { mutableStateOf<List<Item>>(emptyList()) }

    LaunchedEffect(Unit) {
        val items = withContext(Dispatchers.IO) {
            db.ItemDao().getAll()
        }
        Log.d("ItemsListScreen", "Loaded items: ${items.size}")
        itemsState.value = items
    }

    Text(text = "Loaded items count: ${itemsState.value.size}", modifier = Modifier.fillMaxSize())

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(itemsState.value) { item ->
            ItemCell(Modifier.padding(20.dp),item, "ru")
        }
    }
}

