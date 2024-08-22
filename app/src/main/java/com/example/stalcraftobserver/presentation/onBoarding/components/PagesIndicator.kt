package com.example.stalcraftobserver.presentation.onBoarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PagesIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int
) {
    val selectedColor = MaterialTheme.colorScheme.primary;
    val unselectedColor = MaterialTheme.colorScheme.secondary;

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) { page ->
            Box(modifier = Modifier.size(14.dp).clip(CircleShape)
                .background(color = if (page == selectedPage) selectedColor else unselectedColor)) {

            }
        }
    }
}