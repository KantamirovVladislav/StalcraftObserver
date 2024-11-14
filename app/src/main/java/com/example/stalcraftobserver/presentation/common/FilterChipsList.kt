package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterChipsList(
    filterList: List<String>,
    callback: (choosedFilter: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
    ) {
        items(filterList) { filter ->
            FilterChipCommon(
                filter = filter,
                callback = callback,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}
