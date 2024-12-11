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
    isSelected: Boolean,
    onSelected: (FilterItem?) -> Unit,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    FilterChip(
        modifier = modifier,
        enabled = isEnabled,
        onClick = {
            if (isSelected) {
                onSelected(null) // Убираем фильтр
            } else {
                onSelected(filter) // Выбираем фильтр
            }
        },
        selected = isSelected,
        label = { Text(filter.name) },
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