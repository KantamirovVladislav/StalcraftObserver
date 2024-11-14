package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedCard(
    data: String,
    modifier: Modifier = Modifier
){
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier.then(Modifier.padding(10.dp)),

        ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = data,
                //style = MaterialTheme.typography.subtitle1
            )
        }
    }
}