package com.example.stalcraftobserver.presentation.compareItems

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.CompareItemsViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedItemIdViewModel
import com.example.stalcraftobserver.presentation.common.WarningDialog
import com.example.stalcraftobserver.presentation.common.LoadingDialog
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.compareItems.components.CompareArmor
import com.example.stalcraftobserver.presentation.compareItems.components.CompareWeapon
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme
import com.example.stalcraftobserver.util.NavigationItem
import kotlin.text.substringBefore

const val item1 = "Item1Id"
const val item2 = "Item2Id"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompareItemsScreenV2(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    id1: String?,
    id2: String?
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Log.d("Launched", "Launched")
    LaunchedEffect(Unit) {
        viewModel.setItem1Id(id1)
        viewModel.setItem2Id(id2)
        Log.d("Launched", "LaunchedStart")
    }

    val item1Id by viewModel.item1.collectAsState()
    val item2Id by viewModel.item2.collectAsState()
    val errors by viewModel.errorQueue.collectAsState()

    viewModel.onCriticalError = {
        navController.navigate(NavigationItem.ListItems.route)
    }

    errors.let {
        errors.forEach { error -> error.ShowDialog() }
    }

    val listState = rememberLazyListState()


    //TODO Сделать анимацию свапа IconButton на кнопку, когда скролится экран в самый низ
    val isScrolledToBottom by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    val categories = listOfNotNull(
        item1Id?.category?.substringBefore("/"),
        item2Id?.category?.substringBefore("/")
    ).ifEmpty { listOf("weapon", "armor") }

    TopAppBarWithoutSearch(
        navController = navController,
        title = "Сравнение",
        scrollBehavior = scrollBehavior
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            when {
                item1Id?.category?.contains("armor") == true ||
                        item2Id?.category?.contains("armor") == true -> {
                    CompareArmor(
                        item1 = item1Id,
                        item2 = item2Id,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                item1Id?.category?.contains("weapon") == true ||
                        item2Id?.category?.contains("weapon") == true -> {
                    CompareWeapon(
                        item1 = item1Id,
                        item2 = item2Id,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }

            if (item1Id == null && item2Id == null) {
                NullTargetItems(
                    firstItemClick = {
                        if (viewModel.isClickable()){
                            navController.navigate(
                                NavigationItem.SelectItem.createRoute(item1, categories)
                            )
                        }

                    },
                    secondItemClick = {
                        if (viewModel.isClickable()){
                            navController.navigate(
                                NavigationItem.SelectItem.createRoute(item2, categories)
                            )
                        }
                    }
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        if (item1Id != null) {
                            CustomIconButton(
                                icon = Icons.Filled.SwapVert,
                                contentDescription = "Swap button",
                                onClick = {
                                    Log.d("Compare:SwapButton1", "Click!")
                                    if (viewModel.isClickable()){
                                        navController.navigate(
                                            NavigationItem.SelectItem.createRoute(item1, categories)
                                        )
                                    }
                                }
                            )
                        } else {
                            CustomIconButton(
                                icon = Icons.Filled.Add,
                                contentDescription = "Add button",
                                onClick = {
                                    if (viewModel.isClickable()){
                                        navController.navigate(
                                            NavigationItem.SelectItem.createRoute(item1, categories)
                                        )
                                    }
                                }
                            )
                        }
                        if (item2Id != null) {
                            CustomIconButton(
                                icon = Icons.Filled.SwapVert,
                                contentDescription = "Swap button",
                                onClick = {
                                    if (viewModel.isClickable()){
                                        navController.navigate(
                                            NavigationItem.SelectItem.createRoute(item2, categories)
                                        )
                                    }
                                }
                            )
                        } else {
                            CustomIconButton(
                                icon = Icons.Filled.Add,
                                contentDescription = "Remove button",
                                onClick = {
                                    if (viewModel.isClickable()){
                                        navController.navigate(
                                            NavigationItem.SelectItem.createRoute(item2, categories)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun NullTargetItems(
    modifier: Modifier = Modifier,
    firstItemClick: () -> Unit = {},
    secondItemClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIconButton(
                icon = Icons.Filled.Add,
                contentDescription = "Add button",
                onClick = firstItemClick
            )

            VerticalDivider(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxHeight(0.6f)
            )

            CustomIconButton(
                icon = Icons.Filled.Add,
                contentDescription = "Remove button",
                onClick = secondItemClick
            )
        }
    }
}

@Composable
fun CustomIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface
        )
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
fun kekewe() {
    StalcraftObserverTheme {

    }
}