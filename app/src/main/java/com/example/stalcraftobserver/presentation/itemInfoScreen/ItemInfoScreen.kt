package com.example.stalcraftobserver.presentation.itemInfoScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.stalcraftobserver.domain.model.ItemInfoViewModel
import com.example.stalcraftobserver.util.Constants
import com.example.stalcraftobserver.util.NavigationItem

@Composable
fun ItemInfoScreen(
    id: String,
    modifier: Modifier = Modifier,
    viewModel: ItemInfoViewModel
){
    val info by viewModel.info.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getItemWithId(id)
    }

    LazyColumn() {
        item {
            Text(
                text = info
            )
        }

    }
}