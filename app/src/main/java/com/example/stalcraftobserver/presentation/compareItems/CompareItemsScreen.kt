package com.example.stalcraftobserver.presentation.compareItems


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.domain.viewModel.CompareItemsViewModel
import com.example.stalcraftobserver.presentation.compareItems.components.CompareRow
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getWeaponClassFromItemInfo
import com.example.stalcraftobserver.util.NavigationItem

@Composable
fun CompareItemsScreen(
    navController: NavController,
    viewModel: CompareItemsViewModel,
    item1Id: String?,
    item2Id: String?
) {
    val item1Info by viewModel.item1.collectAsState()
    val item2Info by viewModel.item2.collectAsState()

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("item1")
            ?.observeForever { selectedId ->
                if (selectedId != null) {
                    viewModel.setItem1Id(selectedId)
                }
            }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("item2")
            ?.observeForever { selectedId ->
                if (selectedId != null) {
                    viewModel.setItem2Id(selectedId)
                }
            }

        if (!item1Id.isNullOrEmpty()) {
            viewModel.setItem1Id(item1Id)
        }

        if (!item2Id.isNullOrEmpty()) {
            viewModel.setItem2Id(item2Id)
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
    val armor1 = item1?.let { getWeaponClassFromItemInfo(it) }
    val armor2 = item2?.let { getWeaponClassFromItemInfo(it) }


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

//        item{
//            CompareRow(
//                "Пулестойкость",
//                armor1?.bulletResistance?.values?.firstOrNull(),
//                armor2?.bulletResistance?.values?.firstOrNull()
//            )
//        }

    }
}




