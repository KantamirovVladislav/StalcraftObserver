// package com.example.stalcraftobserver.presentation.loadoutScreen

package com.example.stalcraftobserver.presentation.loadoutScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.CompareItemsViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedItemViewModel
import com.example.stalcraftobserver.presentation.common.PagesIndicatorLine
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.loadoutScreen.components.SingleArmor
import com.example.stalcraftobserver.presentation.loadoutScreen.components.SingleWeapon
import com.example.stalcraftobserver.util.NavigationItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadoutScreen(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    sharedItemViewModel: SharedItemViewModel,
) {
    // Получаем информацию о выбранных предметах из ViewModel
    val weaponInfo by viewModel.item1.collectAsState()
    val armorInfo by viewModel.item2.collectAsState()

    // Обновляем ViewModel на основе общего ViewModel
    LaunchedEffect(sharedItemViewModel.weaponId.collectAsState().value) {
        sharedItemViewModel.weaponId.value?.let { viewModel.setItem1Id(it) }
    }
    LaunchedEffect(sharedItemViewModel.armorId.collectAsState().value) {
        sharedItemViewModel.armorId.value?.let { viewModel.setItem2Id(it) }
    }

    // Наблюдаем за изменениями в общем ViewModel
    LaunchedEffect(Unit) {
        sharedItemViewModel.weaponId.collect { newWeaponId ->
            if (!newWeaponId.isNullOrEmpty()) {
                viewModel.setItem1Id(newWeaponId)
            }
        }
        sharedItemViewModel.armorId.collect { newArmorId ->
            if (!newArmorId.isNullOrEmpty()) {
                viewModel.setItem2Id(newArmorId)
            }
        }
    }

    val pages = mutableListOf<String>()
    if (weaponInfo != null) pages.add("weapon")
    if (armorInfo != null) pages.add("armor")
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val currentPage = pagerState.currentPage
    val totalPages = pages.size

    Scaffold {
        TopAppBarWithoutSearch(navController = navController, title = "Loadout") {
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
                    // Создаём состояние Pager
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
                NavigationItem.SelectItem.createRoute(itemSlot, categories)
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
