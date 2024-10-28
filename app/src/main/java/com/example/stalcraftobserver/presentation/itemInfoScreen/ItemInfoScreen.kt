package com.example.stalcraftobserver.presentation.itemInfoScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.stalcraftobserver.domain.model.ItemInfoViewModel
import com.example.stalcraftobserver.util.Constants

@Composable
fun ItemInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: ItemInfoViewModel
){
    val info by viewModel.info.collectAsState()

    LaunchedEffect(Unit) {
        Log.d(Constants.SUCCES_DATABASE_TAG, "ReadData in LaunchedEffect ...")

    }

    Column {
        Text(
            text = info
        )
    }

}