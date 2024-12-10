package com.example.stalcraftobserver.presentation.common

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.model.FilterItem
import com.example.stalcraftobserver.domain.model.StalcraftApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithoutSearch(
    modifier: Modifier = Modifier,
    navController: NavController,
    onMenuSelected: (String) -> Unit = {},
    content: @Composable (modifier: Modifier) -> Unit = {}
) {
    var isSettingsVisible by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()

    val application = StalcraftApplication()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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