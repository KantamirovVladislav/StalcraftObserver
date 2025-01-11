package com.example.stalcraftobserver.presentation.itemsListing.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.domain.model.FilterItem
import com.example.stalcraftobserver.domain.model.StalcraftApplication
import com.example.stalcraftobserver.presentation.common.RegionSelection
import com.example.stalcraftobserver.util.Regions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSelector(
    sheetState: SheetState,
    onCloseSheet: () -> Unit,
    label: String = "",
    filterItem: FilterItem,
    previouslySelectedFilters: Set<FilterItem>,
    onFiltersSelected: (Set<FilterItem>) -> Unit,
    onFilterDisabled: (Set<FilterItem>) -> Unit,
    isGlobalVisible: Boolean = true
) {
    val scope = rememberCoroutineScope()

    var selectedFilters by remember { mutableStateOf(previouslySelectedFilters) }

    ModalBottomSheet(
        onDismissRequest = {
            onCloseSheet()
            onFiltersSelected(selectedFilters.toSet())
            onFilterDisabled(previouslySelectedFilters.filter { !selectedFilters.contains(it) }.toSet())
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = label)

            Spacer(modifier = Modifier.height(16.dp))

            var checkedState by remember { mutableStateOf(selectedFilters.contains(filterItem)) }

            Column(modifier = Modifier.fillMaxWidth()) {
                if (isGlobalVisible){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = {
                                checkedState = it
                                selectedFilters = if (it) {
                                    selectedFilters + filterItem
                                } else {
                                    selectedFilters - filterItem
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = filterItem.name)
                    }
                }

                if (filterItem.extendFilters.isNotEmpty()) {
                    extendFilterList(
                        filterItem.extendFilters.toSet(),
                        selectedFilters = selectedFilters,
                        onSelectedFiltersChange = {
                            selectedFilters = it
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onCloseSheet()
                            onFiltersSelected(selectedFilters)
                            onFilterDisabled(previouslySelectedFilters.filter { !selectedFilters.contains(it) }.toSet())
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Закрыть фильтр")
            }
        }
    }
}

@Composable
fun extendFilterList(
    extendFilters: Set<FilterItem>,
    selectedFilters: Set<FilterItem>,
    onSelectedFiltersChange: (Set<FilterItem>) -> Unit
) {
    extendFilters.forEach { filter ->
        var checkedState by remember { mutableStateOf(selectedFilters.contains(filter)) }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it

                        onSelectedFiltersChange(
                            if (it) {
                                selectedFilters + filter
                            } else {
                                selectedFilters - filter
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = filter.name, textAlign = TextAlign.Left)
            }

            if (filter.extendFilters.isNotEmpty()) {
                extendFilterList(
                    filter.extendFilters.toSet(),
                    selectedFilters = selectedFilters,
                    onSelectedFiltersChange = onSelectedFiltersChange
                )
            }
        }
    }
}