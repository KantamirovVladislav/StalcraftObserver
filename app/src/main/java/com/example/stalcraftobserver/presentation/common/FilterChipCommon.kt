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

@Composable
fun FilterChipCommon(
    filter: String,
    callback: (choosedFilter: String) -> Unit,
    modifier: Modifier = Modifier
){
    var selected by remember { mutableStateOf(false) }
    FilterChip(
        modifier = modifier,
        onClick = {
            selected = !selected
            if (selected){
                callback(filter)
            }
        },
        selected = selected,
        label = { Text(filter) },
        leadingIcon = if (selected) {
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