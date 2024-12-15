package com.example.stalcraftobserver.presentation.compareItems.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <T> CompareRow(attribute: String, value1: T?, value2: T?) {
    val (color1, color2, sign) = if (value1 is Number && value2 is Number) {
        val v1 = value1.toDouble()
        val v2 = value2.toDouble()
        when {
            v1 < v2 -> Triple(Color.Red, Color.Green, "<")
            v1 > v2 -> Triple(Color.Green, Color.Red, ">")
            else -> Triple(Color.Black, Color.Black, "=")
        }
    } else {
        Triple(Color.Black, Color.Black, "")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Row(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$value1",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = color1
                )
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = "$attribute\n$sign",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Text(
                    text = "$value2",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = color2
                )
            }
        }
    }
}