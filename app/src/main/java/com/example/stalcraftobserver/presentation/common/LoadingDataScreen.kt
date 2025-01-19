package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme

//TODO Добавить перевод строки по дефолту
@Composable
fun LoadingDataScreen(
    modifier: Modifier = Modifier,
    label: String = "Данные загружаются, подождите"
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PulseAnimation(modifier = Modifier.size(100.dp))
        Text(text = label, modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview
@Composable
fun previewKEKEWE(){
    StalcraftObserverTheme {
        LoadingDataScreen()
    }
}