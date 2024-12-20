package com.example.stalcraftobserver.presentation.compareItems

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.CompareItemsViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedCompareItemsViewModel
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.compareItems.components.CompareArmor
import com.example.stalcraftobserver.presentation.compareItems.components.CompareWeapon
import com.example.stalcraftobserver.util.NavigationItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompareItemsScreen(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    sharedCompareItemsViewModel: SharedCompareItemsViewModel
) {
    // Получаем item1Id и item2Id из общего ViewModel
    val item1Id by sharedCompareItemsViewModel.item1Id.collectAsState()
    val item2Id by sharedCompareItemsViewModel.item2Id.collectAsState()

    // Устанавливаем item1Id и item2Id в CompareItemsViewModel
    LaunchedEffect(item1Id) {
        item1Id?.let { viewModel.setItem1Id(it) }
    }
    LaunchedEffect(item2Id) {
        item2Id?.let { viewModel.setItem2Id(it) }
    }

    // Получаем информацию о предметах из CompareItemsViewModel
    val item1Info by viewModel.item1.collectAsState()
    val item2Info by viewModel.item2.collectAsState()

    // Определяем категории для фильтрации (на основе категорий выбранных предметов)
    val categories = listOfNotNull(
        item1Info?.category?.substringBefore("/"),
        item2Info?.category?.substringBefore("/")
    ).ifEmpty { listOf("weapon", "armor") }

    TopAppBarWithoutSearch(navController = navController, title = "Сравнение") {
        Scaffold {
            Column(modifier = Modifier.fillMaxSize()) {
                // Кнопки выбора предметов для сравнения
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        SelectItemButton(
                            navController = navController,
                            isItemEmpty = item1Id.isNullOrEmpty(),
                            itemSlot = "item1",
                            category = categories,
                            sharedCompareItemsViewModel = sharedCompareItemsViewModel
                        )
                    }
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                    item {
                        SelectItemButton(
                            navController = navController,
                            isItemEmpty = item2Id.isNullOrEmpty(),
                            itemSlot = "item2",
                            category = categories,
                            sharedCompareItemsViewModel = sharedCompareItemsViewModel
                        )
                    }
                }

                // Сравнение предметов
                when {
                    item1Info?.category?.contains("armor") == true || item2Info?.category?.contains(
                        "armor"
                    ) == true -> {
                        CompareArmor(
                            item1 = item1Info,
                            item2 = item2Info,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    item1Info?.category?.contains("weapon") == true || item2Info?.category?.contains(
                        "weapon"
                    ) == true -> {
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
    category: List<String?>?,
    sharedCompareItemsViewModel: SharedCompareItemsViewModel
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
