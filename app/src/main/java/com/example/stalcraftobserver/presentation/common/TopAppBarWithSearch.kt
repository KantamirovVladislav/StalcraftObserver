package com.example.stalcraftobserver.presentation.common

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.domain.model.FilterItem
import com.example.stalcraftobserver.domain.model.StalcraftApplication
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithSearchAndFilter(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChanged: (String) -> Unit = {},
    filters: List<FilterItem> = emptyList(),
    selectedFilters: MutableList<FilterItem>,
    onFilterSelected: (List<FilterItem>) -> Unit = {},
    onCategorySelected: (List<String>) -> Unit = {}, // Новый параметр
    onRaritySelected: (List<String>) -> Unit = {}, // Новый параметр
    onMenuSelected: (String) -> Unit = {},
    content: @Composable (modifier: Modifier) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    // Получаем collapsedFraction для управления видимостью фильтров
    val collapsedFraction = scrollBehavior.state.collapsedFraction

    var isSettingsVisible by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()

    val application = StalcraftApplication()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Column(
                        modifier = Modifier
                    ) {
                        SearchView(
                            query = query,
                            onQueryChanged = onQueryChanged
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Navigation back logic */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isMenuExpanded = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More Options"
                        )

                        DropdownMenuMore(
                            isMenuExpanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false },
                            onMenuItemClick = onMenuSelected
                        )
                    }
                    IconButton(onClick = {
                        isSettingsVisible = !isSettingsVisible
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(
                visible = collapsedFraction == 0f,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                FilterChipsList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    filterList = filters,
                    selectedFilters = selectedFilters,
                    onFilterSelected =
                    onFilterSelected
                )
            }

            content(Modifier.fillMaxSize())

        }

        if (isSettingsVisible) {
            SettingsPanel(
                sheetState = sheetState,
                onCloseSheet = { isSettingsVisible = false },
                application = application
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(device = "spec:width=411dp,height=891dp,dpi=420") // Pixel 4
@Preview(device = "spec:width=360dp,height=740dp,dpi=320") // Nexus 5
@Preview(device = "spec:width=320dp,height=480dp,dpi=160") // Small Phone
@Preview(device = "spec:width=600dp,height=1024dp,dpi=240") // 7-inch Tablet
@Preview(device = "spec:width=800dp,height=1280dp,dpi=240") // 10-inch Tablet
@Preview(device = "spec:width=1024dp,height=1366dp,dpi=264") // iPad Pro 10.5
@Preview(device = "spec:width=1440dp,height=2560dp,dpi=560") // Pixel XL
@Preview(device = "spec:width=1080dp,height=1920dp,dpi=480") // Full HD Phone
@Preview(device = "spec:width=1440dp,height=2960dp,dpi=560") // Galaxy S8
@Preview(device = "spec:width=768dp,height=1024dp,dpi=160") // Nexus 7
@Composable
fun GreetingPreview() {
    StalcraftObserverTheme {

    }
}