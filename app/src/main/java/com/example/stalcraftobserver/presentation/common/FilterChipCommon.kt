package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.stalcraftobserver.domain.model.FilterItem

@Composable
fun FilterChipCommon(
    filter: FilterItem,
    isSelected: Boolean = false,
    onSelected: (FilterItem) -> Unit,
    onDisabled: (FilterItem) -> Unit,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    FilterChip(
        modifier = modifier,
        enabled = isEnabled,
        onClick = {
            if (isSelected) {
                onDisabled(filter)
            } else {
                onSelected(filter)
            }
        },
        selected = isSelected,
        label = { Text(filter.rusName) },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}