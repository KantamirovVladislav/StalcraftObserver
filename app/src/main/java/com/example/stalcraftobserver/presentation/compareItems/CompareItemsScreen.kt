package com.example.stalcraftobserver.presentation.compareItems

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.CompareItemsViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedItemIdViewModel
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.compareItems.components.CompareArmor
import com.example.stalcraftobserver.presentation.compareItems.components.CompareWeapon
import com.example.stalcraftobserver.util.NavigationItem
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompareItemsScreen(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    sharedItemIdViewModel: SharedItemIdViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val item1Id by remember { mutableStateOf(sharedItemIdViewModel.getItem(item1)) }
    val item2Id by remember { mutableStateOf(sharedItemIdViewModel.getItem(item2)) }

    LaunchedEffect(item1Id) {
        viewModel.setItem1Id(item1Id)
    }
    LaunchedEffect(item2Id) {
        viewModel.setItem2Id(item2Id)
    }

    val item1Info by viewModel.item1.collectAsState()
    val item2Info by viewModel.item2.collectAsState()

    val categories = listOfNotNull(
        item1Info?.category?.substringBefore("/"),
        item2Info?.category?.substringBefore("/")
    ).ifEmpty { listOf("weapon", "armor") }

    TopAppBarWithoutSearch(navController = navController, title = "Сравнение", scrollBehavior = scrollBehavior) {
        Scaffold {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        SelectItemButton(
                            navController = navController,
                            isItemEmpty = item1Id.isNullOrEmpty(),
                            itemSlot = item1,
                            category = categories
                        )
                    }

                    item { Spacer(modifier = Modifier.width(16.dp)) }

                    item {
                        SelectItemButton(
                            navController = navController,
                            isItemEmpty = item2Id.isNullOrEmpty(),
                            itemSlot = item2,
                            category = categories
                        )
                    }

                    item { Spacer(modifier = Modifier.width(16.dp)) }

                    item {

                    }
                }

                when {
                    item1Info?.category?.contains("armor") == true ||
                            item2Info?.category?.contains("armor") == true -> {
                        CompareArmor(
                            item1 = item1Info,
                            item2 = item2Info,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    item1Info?.category?.contains("weapon") == true ||
                            item2Info?.category?.contains("weapon") == true -> {
                        CompareWeapon(
                            item1 = item1Info,
                            item2 = item2Info,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectItemButton(
    navController: NavController,
    isItemEmpty: Boolean,
    itemSlot: String,
    category: List<String?>?
) {
    OutlinedButton(
        onClick = {
            // Навигация на экран выбора предметов с указанием слота и категорий
            navController.navigate(
                NavigationItem.SelectItem.createRoute(itemSlot, category?.filterNotNull())
            )
        },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            if (isItemEmpty) "Выбрать $itemSlot" else "Изменить $itemSlot",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

//TODO Доделать очистку
@Composable
fun ClearSelectedItemsButton(
) {
    IconButton(
        onClick = {

        },
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Очистить",
            tint = Color.Red
        )
    }
}