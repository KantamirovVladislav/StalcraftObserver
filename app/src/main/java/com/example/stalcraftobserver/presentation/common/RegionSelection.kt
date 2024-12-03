package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.util.Regions

@Composable
fun RegionSelection(
    selectedRegion: Regions,
    onRegionSelected: (Regions) -> Unit
) {
    val regions = Regions.entries.toTypedArray()

    Column {
        regions.forEach { region ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = selectedRegion == region,
                    onClick = { onRegionSelected(region) }
                )
                Text(
                    text = region.name,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}