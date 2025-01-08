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
import com.example.stalcraftobserver.domain.viewModel.SharedItemIdViewModel
import com.example.stalcraftobserver.presentation.common.PagesIndicatorLine
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.compareItems.item1
import com.example.stalcraftobserver.presentation.compareItems.item2
import com.example.stalcraftobserver.presentation.loadoutScreen.components.SingleArmor
import com.example.stalcraftobserver.presentation.loadoutScreen.components.SingleWeapon
import com.example.stalcraftobserver.util.NavigationItem

const val weapon = "weaponId"
const val armor = "armorId"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadoutScreen(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    sharedItemIdViewModel: SharedItemIdViewModel
) {
    val weaponInfo by viewModel.item1.collectAsStateWithLifecycle()
    val armorInfo by viewModel.item2.collectAsStateWithLifecycle()

    val item1Id by remember { mutableStateOf(sharedItemIdViewModel.getItem(weapon)) }
    val item2Id by remember { mutableStateOf(sharedItemIdViewModel.getItem(armor)) }

    LaunchedEffect(item1Id) {
        viewModel.setItem1Id(item1Id)
    }

    LaunchedEffect(item2Id) {
        viewModel.setItem2Id(item2Id)
    }


    val pages = remember(weaponInfo, armorInfo) {
        buildList<String> {
            if (weaponInfo != null) add("weapon")
            if (armorInfo != null) add("armor")
        }
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val currentPage = pagerState.currentPage
    val totalPages = pages.size

    Scaffold {
        TopAppBarWithoutSearch(navController = navController, title = "Loadout") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SelectLoadoutButton(
                        navController = navController,
                        isItemEmpty = weaponInfo == null,
                        itemSlot = weapon,
                        label = "Выбрать оружие"
                    )
                    SelectLoadoutButton(
                        navController = navController,
                        isItemEmpty = armorInfo == null,
                        itemSlot = armor,
                        label = "Выбрать броню"
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                PagesIndicatorLine(
                    pageSize = totalPages,
                    selectedPage = currentPage
                )
                if (pages.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) { page ->
                        when (pages[page]) {
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
            val categories = if (itemSlot == weapon) listOf("weapon") else listOf("armor")

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
