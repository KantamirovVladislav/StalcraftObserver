package com.example.stalcraftobserver.presentation.loadoutScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.CompareItemsViewModel
import com.example.stalcraftobserver.presentation.common.PagedContent
import com.example.stalcraftobserver.presentation.common.PagesIndicatorLine
import com.example.stalcraftobserver.presentation.common.PagesIndicatorRounder
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.loadoutScreen.components.SingleArmor
import com.example.stalcraftobserver.presentation.loadoutScreen.components.SingleWeapon
import com.example.stalcraftobserver.util.NavigationItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadoutScreen(
    navController: NavController,
    viewModel: CompareItemsViewModel
) {
    val weaponInfo by viewModel.item1.collectAsState()
    val armorInfo by viewModel.item2.collectAsState()

    val pages = mutableListOf<String>()
    if (weaponInfo != null) pages.add("weapon")
    if (armorInfo != null) pages.add("armor")
    val pagerState = rememberPagerState(pageCount = {pages.size})
    val currentPage = pagerState.currentPage
    val totalPages = pages.size

    LaunchedEffect(Unit) {
        handleItemUpdates(navController, viewModel, "weapon")
        handleItemUpdates(navController, viewModel, "armor")
    }


    TopAppBarWithoutSearch(navController = navController, title = "Loadout") {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Кнопки выбора оружия и брони
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SelectLoadoutButton(
                        navController = navController,
                        isItemEmpty = weaponInfo == null,
                        itemSlot = "weapon",
                        label = "Выбрать оружие"
                    )
                    SelectLoadoutButton(
                        navController = navController,
                        isItemEmpty = armorInfo == null,
                        itemSlot = "armor",
                        label = "Выбрать броню"
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                PagesIndicatorLine(
                    pageSize = totalPages,
                    selectedPage = currentPage
                )
                if (pages.isNotEmpty()) {
                    // Создаем состояние Pager

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) { page ->
                        val currentPage = pages[page]
                        when (currentPage) {
                            "weapon" -> Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Характеристики оружия:",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                SingleWeapon(
                                    item = weaponInfo!!,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 0.dp)
                                )
                            }
                            "armor" -> Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Характеристики брони:",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                SingleArmor(
                                    item = armorInfo!!,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 0.dp)
                                )
                            }
                        }
                    }
                } else {
                    // Если нет доступных страниц
                    Text(
                        text = "Нет доступных данных для отображения.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}


@Composable
fun SelectLoadoutButton(
    navController: NavController,
    isItemEmpty: Boolean,
    itemSlot: String,
    label: String
) {
    OutlinedButton(
        onClick = {
            // Переход на экран выбора предметов с указанием категории
            val categories = if (itemSlot == "weapon") listOf("weapon") else listOf("armor")

            navController.navigate(
                NavigationItem.SelectItem(itemSlot = itemSlot, categories = categories).route
            )
        },
        modifier = Modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            if (isItemEmpty) label else "Изменить ${if (itemSlot == "weapon") "оружие" else "броню"}",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


// Функция для обработки навигации и получения выбранных предметов
suspend fun handleItemUpdates(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    itemSlot: String
) {
    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(itemSlot)
        ?.observeForever { selectedId ->
            if (!selectedId.isNullOrEmpty()) {
                if (itemSlot == "weapon") viewModel.setItem1Id(selectedId)
                if (itemSlot == "armor") viewModel.setItem2Id(selectedId)
            }
        }
}