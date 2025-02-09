package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TripleAttributeRow(label: String, value: String, type: String) {
    androidx.compose.material3.Card(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = type,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
