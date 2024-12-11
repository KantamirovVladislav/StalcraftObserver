package com.example.stalcraftobserver.presentation.common

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
    selectedFilters: MutableList<FilterItem>,
    onFilterSelected: (List<FilterItem>) -> Unit,
    isFilterDisabled: (FilterItem) -> Boolean,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
    ) {
        items(filterList) { filter ->
            val isSelected = filter in selectedFilters
            val isEnabled = !isFilterDisabled(filter)

            FilterChipCommon(
                filter = filter,
                isSelected = isSelected,
                onSelected = { selectedFilter ->
                    if (isSelected) {
                        // Если фильтр выбран, убираем его
                        selectedFilters.remove(filter)
                    } else {
                        // Если фильтр не выбран, добавляем его
                        // Сначала убираем фильтры из той же группы
                        selectedFilters.removeAll { it.group == filter.group }
                        selectedFilters.add(filter)
                    }
                    // Вызываем обновление фильтров
                    onFilterSelected(selectedFilters.toList())
                },
                isEnabled = isEnabled,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}

