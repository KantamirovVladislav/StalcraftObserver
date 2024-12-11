package com.example.stalcraftobserver.presentation.compareItems


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.domain.model.viewModel.ItemInfoViewModel
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getArmorClassFromItemInfo
import com.example.stalcraftobserver.util.NavigationItem

@Composable
fun CompareItemsScreen(
    navController: NavController,
    viewModel: ItemInfoViewModel,
    item1Id: String?,
    item2Id: String?
) {
    var item1Info by remember { mutableStateOf<ItemInfo?>(null) }
    var item2Info by remember { mutableStateOf<ItemInfo?>(null) }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("item1")
            ?.observeForever { selectedId ->
                if (selectedId != null) {
                    viewModel.fetchItemWithId(selectedId) {
                        item1Info = it
                    }
                }
            }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("item2")
            ?.observeForever { selectedId ->
                if (selectedId != null) {
                    viewModel.fetchItemWithId(selectedId) {
                        item2Info = it
                    }
                }
            }

        if (item1Id != null) {
            viewModel.fetchItemWithId(item1Id) {
                item1Info = it
            }
        }

        if (item2Id != null) {
            viewModel.fetchItemWithId(item2Id) {
                item2Info = it
            }
        }
    }

    Column {
        Text("Сравнение предметов", style = MaterialTheme.typography.titleMedium)

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            item {
                if (item1Id.isNullOrEmpty()) {
                    Button(onClick = {
                        navController.navigate(
                            NavigationItem.SelectItem(itemSlot = "item1", category = "armor/combat").route
                        )
                    }) {
                        Text("Выбрать предмет 1")
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(item1Info?.name?.toString() ?: "Загрузка...")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.width(16.dp))
            }

            item {
                if (item2Id.isNullOrEmpty()) {
                    Button(onClick = {
                        navController.navigate(
                            NavigationItem.SelectItem(itemSlot = "item2", category = "armor/combat").route
                        )
                    }) {
                        Text("Выбрать предмет 2")
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(item2Info?.name?.toString() ?: "Загрузка...")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (item1Info != null || item2Info != null) {
            CompareAttributes(item1 = item1Info, item2 = item2Info)
        }
    }
}


@Composable
fun CompareAttributes(item1: ItemInfo?, item2: ItemInfo?) {
    val armor1 = item1?.let { getArmorClassFromItemInfo(it) }
    val armor2 = item2?.let { getArmorClassFromItemInfo(it) }

    LazyColumn() {
        item {
            CompareRow(
                "Название",
                armor1?.name?.values?.firstOrNull()?.ru,
                armor2?.name?.values?.firstOrNull()?.ru
            )
        }
        item{
            CompareRow(
                "Ранг",
                armor1?.rank?.values?.firstOrNull()?.ru,
                armor2?.rank?.values?.firstOrNull()?.ru
            )
        }

        item{
            CompareRow(
                "Категория",
                armor1?.category?.values?.firstOrNull()?.ru,
                armor2?.category?.values?.firstOrNull()?.ru
            )
        }

        item{
            CompareRow(
                "Вес",
                armor1?.weight?.values?.firstOrNull(),
                armor2?.weight?.values?.firstOrNull()
            )
        }

        item{
            CompareRow(
                "Прочность",
                armor1?.durability?.values?.firstOrNull(),
                armor2?.durability?.values?.firstOrNull()
            )
        }

        item{
            CompareRow(
                "Пулестойкость",
                armor1?.bulletResistance?.values?.firstOrNull(),
                armor2?.bulletResistance?.values?.firstOrNull()
            )
        }

    }
}

@Composable
fun <T> CompareRow(attribute: String, value1: T?, value2: T?) {
    val (color1, color2, sign) = if (value1 is Number && value2 is Number) {
        val v1 = value1.toDouble()
        val v2 = value2.toDouble()
        when {
            v1 < v2 -> Triple(Color.Red, Color.Green, "<")
            v1 > v2 -> Triple(Color.Green, Color.Red, ">")
            else -> Triple(Color.Black, Color.Black, "=")
        }
    } else {
        Triple(Color.Black, Color.Black, "")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Card(modifier = Modifier.padding(4.dp)) {
            Row(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$value1",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = color1
                )
                Text(
                    text = "$attribute\n$sign",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Text(
                    text = "$value2",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = color2
                )
            }
        }
    }
}


