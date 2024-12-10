package com.example.stalcraftobserver.presentation.compareItems


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.domain.model.viewModel.ItemInfoViewModel
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getArmorClassFromItemInfo

@Composable
fun CompareItemsScreen(
    navController: NavController,
    viewModel: ItemInfoViewModel,
    item1Id: String?,
    item2Id: String?
) {
    var item1Info by remember { mutableStateOf<ItemInfo?>(null) }
    var item2Info by remember { mutableStateOf<ItemInfo?>(null) }

    LaunchedEffect(item1Id) {
        if (item1Id != null) {
            viewModel.fetchItemWithId(item1Id) {
                item1Info = it
            }
        }
    }

    LaunchedEffect(item2Id) {
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
                    Button(onClick = { navController.navigate("list_items") }) {
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
                    Button(onClick = { navController.navigate("list_items") }) {
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

    Column {
        Text("Сравнение характеристик", style = MaterialTheme.typography.titleMedium)

        CompareRow("Название", armor1?.name?.values?.joinToString(), armor2?.name?.values?.joinToString())
        CompareRow("Ранг", armor1?.rank?.values?.joinToString(), armor2?.rank?.values?.joinToString())
        CompareRow("Категория", armor1?.category?.values?.joinToString(), armor2?.category?.values?.joinToString())
        CompareRow("Вес", armor1?.weight?.values?.firstOrNull(), armor2?.weight?.values?.firstOrNull())
        CompareRow("Прочность", armor1?.durability?.values?.firstOrNull(), armor2?.durability?.values?.firstOrNull())
        CompareRow("Пулестойкость", armor1?.bulletResistance?.values?.firstOrNull(), armor2?.bulletResistance?.values?.firstOrNull())
    }
}

@Composable
fun <T> CompareRow(attribute: String, value1: T?, value2: T?) {
    Row {
        Text(attribute, modifier = Modifier.weight(1f))
        Text(value1?.toString() ?: "-", modifier = Modifier.weight(1f))
        Text(value2?.toString() ?: "-", modifier = Modifier.weight(1f))
    }
}
