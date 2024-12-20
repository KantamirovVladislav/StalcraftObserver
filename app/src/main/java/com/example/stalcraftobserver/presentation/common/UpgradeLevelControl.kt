package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UpgradeLevelControl(
    currentLevel: Int,
    onLevelChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Кнопка уменьшения уровня
        IconButton(
            onClick = { onLevelChange(currentLevel - 1) },
            enabled = currentLevel > 0
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease Level"
            )
        }

        // Отображение текущего уровня
        Text(
            text = "Уровень улучшения: $currentLevel",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Кнопка увеличения уровня
        IconButton(
            onClick = { onLevelChange(currentLevel + 1) },
            enabled = currentLevel < 15
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase Level"
            )
        }
    }
}