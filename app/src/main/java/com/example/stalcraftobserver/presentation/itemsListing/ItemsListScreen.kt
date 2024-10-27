package com.example.stalcraftobserver.presentation.itemsListing

import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ItemsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ItemViewModel
) {
    val itemsState by viewModel.itemsList.collectAsState()

    LaunchedEffect(Unit) {
        Log.d(Constants.SUCCES_DATABASE_TAG, "ReadData in LaunchedEffect ...")
        viewModel.getItems()
    }
    Text(text = "Loaded items count: ${itemsState.size}", modifier = Modifier.padding(8.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(itemsState) { item ->
            Log.d(Constants.SUCCES_DATABASE_TAG,"Loaded item ${itemsState.size}")
            ItemCell(
                modifier = Modifier
                    .padding(4.dp)
                    .height(((LocalConfiguration.current.screenHeightDp / 3) + 10).dp),
                item = item,
                region = "ru"
            )
        }
    }
    Log.d(Constants.SUCCES_DATABASE_TAG,"Loaded item ${itemsState.size}")
}



