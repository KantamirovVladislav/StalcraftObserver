package com.example.stalcraftobserver.presentation.common

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.domain.model.FilterItem
import java.util.logging.Filter

@Composable
fun FilterChipsList(
    filterList: List<FilterItem>,
    selectedFilters: Set<FilterItem>,
    onFilterSelected: (FilterItem) -> Unit,
    onFilterDisabled: (FilterItem) -> Unit,
    disabledFilters: Set<FilterItem> = emptySet(),
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 6.dp)
    ) {
        items(filterList) { filter ->
            val isSelected = selectedFilters.any { it.group == filter.group && it == filter }
            val isEnabled = !disabledFilters.contains(filter)

            FilterChipCommon(
                filter = filter,
                isSelected = isSelected,
                onSelected = {
                    onFilterSelected(filter)
                },
                onDisabled = {
                    onFilterDisabled(filter)
                },
                isEnabled = isEnabled,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}