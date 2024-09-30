package com.example.stalcraftobserver.presentation.onBoarding.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun PagesIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int
) {
    val selectedColor = MaterialTheme.colorScheme.primary;
    val unselectedColor = MaterialTheme.colorScheme.secondary;

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.Bottom) {
        repeat(pageSize) { page ->
            Box(modifier = Modifier.height(height = animateDpAsState(30.dp))//if (page == selectedPage) 40.dp else 30.dp)
                .width(10.dp)
                .clip(shape = RoundedCornerShape(size = 5.dp))
                .background(color = if (page == selectedPage) selectedColor else unselectedColor)) {

            }
        }
    }
}