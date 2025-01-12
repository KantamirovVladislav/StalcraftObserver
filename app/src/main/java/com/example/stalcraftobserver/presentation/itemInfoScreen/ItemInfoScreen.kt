// package com.example.stalcraftobserver.presentation.itemInfoScreen

package com.example.stalcraftobserver.presentation.itemInfoScreen

import android.annotation.SuppressLint
import android.net.Uri
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.ItemInfoViewModel
import com.example.stalcraftobserver.presentation.common.LotsPricesStates
import com.example.stalcraftobserver.presentation.common.TopAppBarWithoutSearch
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.ArmorInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.ArtefactInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.AttachmentInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.BulletInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.ContainerInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.DrinkInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.FoodInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.GrenadeInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.MedicineInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.MiscInfoScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.components.WeaponInfoScreen
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme
import com.example.stalcraftobserver.util.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemInfoScreen(
    id: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ItemInfoViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val info by viewModel.info.collectAsState()
    val priceHistory by viewModel.priceHistory.collectAsState()
    val activeLots by viewModel.activeLots.collectAsState()

    var isPriceHistory by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getItemWithId(id)
        viewModel.getPriceHistory(id)
        viewModel.getActiveLots(id)
    }
    Scaffold {
        TopAppBarWithoutSearch(
            navController = navController, onMenuSelected = { menu ->
                if (menu == "Loadout") {
                    val weaponId = if (info?.category?.contains("weapon") == true) id else ""
                    val armorId = if (info?.category?.contains("armor") == true) id else ""

                    navController.navigate(
                        NavigationItem.Loadout.createRoute(
                            weapon = weaponId,
                            armor = armorId
                        )
                    )
                } else if (menu == "Сравнить") {

                    navController.navigate(
                        NavigationItem.CompareItems.createRoute(
                            item1Id = id,
                            item2Id = ""
                        )
                    )
                }
            },
            scrollBehavior = scrollBehavior
        ) {
            info?.let {
                Log.d("Category", it.category)
                when {
                    it.category.contains("armor") -> ArmorInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("artefact") -> ArtefactInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("weapon") -> WeaponInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("attachment") -> AttachmentInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("bullet") -> BulletInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("container") -> ContainerInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("drink") -> DrinkInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("food") -> FoodInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("grenade") -> GrenadeInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("medicine") -> MedicineInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }

                    it.category.contains("misc") -> MiscInfoScreen(
                        imagePath = "https://github.com/EXBO-Studio/stalcraft-database/raw/main/ru/icons/${it.category}/${it.id}.png",
                        item = it
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = if (isPriceHistory) "История лотов" else "Актуальные лоты")
                            IconButton(
                                onClick = {
                                    isPriceHistory = !isPriceHistory
                                }, modifier = Modifier
                                    .scale(0.5f)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PriceChange,
                                    contentDescription = "Изменить тип гарфика",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        LotsPricesStates(
                            modifier = Modifier.fillMaxWidth(),
                            isPriceHistory = isPriceHistory,
                            priceHistoryResponse = priceHistory,
                            activeLotsResponse = activeLots
                        )
                    }
                }
            }
        }
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
fun GreetingPreviewItemInfoScreen() {
    StalcraftObserverTheme {
        // Предварительный просмотр можно настроить с фиктивными данными
    }
}
