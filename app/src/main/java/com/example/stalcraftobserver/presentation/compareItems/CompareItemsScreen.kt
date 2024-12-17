package com.example.stalcraftobserver.presentation.compareItems


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.domain.viewModel.CompareItemsViewModel
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.compareItems.components.CompareArmor
import com.example.stalcraftobserver.presentation.compareItems.components.CompareRow
import com.example.stalcraftobserver.presentation.compareItems.components.CompareWeapon
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getArmorClassFromItemInfo
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getWeaponClassFromItemInfo
import com.example.stalcraftobserver.util.NavigationItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompareItemsScreen(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    item1Id: String?,
    item2Id: String?
) {
    val item1Info by viewModel.item1.collectAsState()
    val item2Info by viewModel.item2.collectAsState()

    val category = listOfNotNull(
        item1Info?.category?.substringBefore("/"),
        item2Info?.category?.substringBefore("/")
    ).ifEmpty { listOf("weapon", "armor") }

    LaunchedEffect(Unit) {
        handleItemIdUpdates(navController, viewModel, item1Id, "item1")
        handleItemIdUpdates(navController, viewModel, item2Id, "item2")
    }
    TopAppBarWithoutSearch(navController = navController, title = "Сравнение") {
        Scaffold {
            Column{
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    item {
                        SelectItemButton(
                            navController = navController,
                            isItemEmpty = item1Id.isNullOrEmpty(),
                            itemSlot = "item1",
                            category = category
                        )
                    }
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                    item {
                        SelectItemButton(
                            navController = navController,
                            isItemEmpty = item2Id.isNullOrEmpty(),
                            itemSlot = "item2",
                            category = category
                        )
                    }
                }
                when {
                    item1Info?.category?.contains("armor") == true || item2Info?.category?.contains(
                        "armor"
                    ) == true -> {
                        CompareArmor(item1 = item1Info, item2 = item2Info, modifier = Modifier.padding(vertical = 10.dp))
                    }

                    item1Info?.category?.contains("weapon") == true || item2Info?.category?.contains(
                        "weapon"
                    ) == true -> {
                        CompareWeapon(item1 = item1Info, item2 = item2Info)
                    }
                }
            }
        }
    }
}

// Вынесенная функция для обработки навигации по id
suspend fun handleItemIdUpdates(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    itemId: String?,
    itemSlot: String
) {
    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(itemSlot)
        ?.observeForever { selectedId ->
            if (!selectedId.isNullOrEmpty()) {
                if (itemSlot == "item1") viewModel.setItem1Id(selectedId)
                if (itemSlot == "item2") viewModel.setItem2Id(selectedId)
            }
        }

    if (!itemId.isNullOrEmpty()) {
        if (itemSlot == "item1") viewModel.setItem1Id(itemId)
        if (itemSlot == "item2") viewModel.setItem2Id(itemId)
    }
}

// Вынесенная функция для кнопок выбора предметов
@Composable
fun ItemButton(
    navController: NavController,
    isItemEmpty: Boolean,
    itemSlot: String,
    category: List<String?>?
) {

    Button(onClick = {
        navController.navigate(
            NavigationItem.SelectItem(itemSlot = itemSlot, categories = category).route
        )
    }) {
        Text("Выбрать $itemSlot")
    }

}

@Composable
fun SelectItemButton(
    navController: NavController,
    isItemEmpty: Boolean,
    itemSlot: String,
    category: List<String?>?
) {
    Button(
        onClick = {
            navController.navigate(
                NavigationItem.SelectItem(itemSlot = itemSlot, categories = category).route
            )
        },
        modifier = Modifier
            .width(160.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            if (isItemEmpty) "Выбрать $itemSlot" else "Изменить $itemSlot",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

