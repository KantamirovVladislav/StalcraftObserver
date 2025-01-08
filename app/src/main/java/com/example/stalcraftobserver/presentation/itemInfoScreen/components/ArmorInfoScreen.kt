package com.example.stalcraftobserver.presentation.itemInfoScreen.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.Price
import com.example.stalcraftobserver.data.manager.PriceHistoryResponse
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.CustomOutlinedCard
import com.example.stalcraftobserver.presentation.common.PagedContent
import com.example.stalcraftobserver.presentation.common.PriceLineChart
import com.example.stalcraftobserver.util.ItemInfoHelper
import com.example.stalcraftobserver.util.ItemProperty
import com.example.stalcraftobserver.util.ItemProperty.Armor.General.generalKeys
import com.example.stalcraftobserver.util.ItemProperty.Armor.ProtectionKeys.protectionKeys
import com.example.stalcraftobserver.util.ItemProperty.Armor.ResistanceKeys.resistanceKeys
import com.example.stalcraftobserver.util.ItemProperty.Armor.StatModifiers.statModifierKeys

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArmorInfoScreen(
    imagePath: String,
    item: ItemInfo,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }

    val generalParam = remember { mutableStateOf<String>("") }
    val resistanceParam = remember { mutableStateOf<String>("") }
    val protectionParam = remember { mutableStateOf<String>("") }
    val descriptionParam = remember { mutableStateOf<String>("") }
    val statModifiersParam = remember { mutableStateOf<String>("") }
    val compatibilityParam = remember { mutableStateOf<String>("") }
    val armorParam = remember { mutableStateOf<String>("") }

    LaunchedEffect(Unit) {

        generalParam.value = ItemInfoHelper.getStringFromKeys(item, generalKeys)
        statModifiersParam.value = ItemInfoHelper.getStringFromKeys(item, statModifierKeys)
        resistanceParam.value = ItemInfoHelper.getStringFromKeys(item, resistanceKeys)
        protectionParam.value = ItemInfoHelper.getStringFromKeys(item, protectionKeys)
        descriptionParam.value =
            ItemInfoHelper.getStringFromKey(item, ItemProperty.Armor.General.DESCRIPTION)
        compatibilityParam.value = ItemInfoHelper.getStringFromKeys(
            item,
            ItemProperty.Armor.CompatibilityKeys.compatibilityKeys
        )
        armorParam.value = ItemInfoHelper.getArmorClassFromItemInfo(item).toString()

        isLoading = false
    }

    val pages = listOf(resistanceParam.value, protectionParam.value, compatibilityParam.value)

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                if (isLoading) {
                    ShimmerBlockRow()
                } else {
                    Row {
                        CustomOutlinedCard(
                            data = generalParam.value,
                            modifier = Modifier.weight(1f)
                        )
                        CustomImage(
                            imagePath = imagePath,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }

            item {
                if (isLoading) {
                    ShimmerPagedContent()
                } else {
                    PagedContent(
                        pages = pages,
                        modifier = Modifier.fillParentMaxWidth(),
                    ) { pageData, contentModifier ->
                        CustomOutlinedCard(
                            data = pageData,
                            modifier = contentModifier
                        )
                    }
                }
            }

            item {

                PriceLineChart(
                    modifier = Modifier.fillMaxWidth(),
                    priceHistory = PriceHistoryResponse(
                        total = 5L,
                        prices = listOf(
                            Price(
                                amount = 1,
                                price = 1000000,
                                time = "2023-12-25T10:00:00Z",
                                additional = emptyMap()
                            ),
                            Price(
                                amount = 2,
                                price = 100000,
                                time = "2023-12-26T10:00:00Z",
                                additional = emptyMap()
                            ),
                            Price(
                                amount = 3,
                                price = 500000,
                                time = "2023-12-27T10:00:00Z",
                                additional = emptyMap()
                            ),
                            Price(
                                amount = 4,
                                price = 600000,
                                time = "2023-12-28T10:00:00Z",
                                additional = emptyMap()
                            ),
                            Price(
                                amount = 5,
                                price = 900000,
                                time = "2024-12-29T10:00:00Z",
                                additional = emptyMap()
                            ),
                            Price(
                                amount = 5,
                                price = 900000,
                                time = "2023-12-29T10:00:00Z",
                                additional = emptyMap()
                            ),
                            Price(
                                amount = 5,
                                price = 900000,
                                time = "2023-12-30T10:00:00Z",
                                additional = emptyMap()
                            ),
                            Price(
                                amount = 5,
                                price = 900000,
                                time = "2023-12-31T10:00:00Z",
                                additional = emptyMap()
                            )
                        )
                    )
                )
        }

        item {
            if (isLoading) {
                ShimmerDescriptionBlock()
            } else {
                Row {
                    CustomOutlinedCard(
                        data = descriptionParam.value,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
}

@Composable
fun ShimmerBlockRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ShimmerPlaceholder(
            modifier = Modifier
                .weight(1f)
                .height(100.dp)
                .padding(end = 4.dp)
        )
        ShimmerPlaceholder(
            modifier = Modifier
                .weight(1f)
                .height(100.dp)
                .padding(start = 4.dp)
        )
    }
}

@Composable
fun ShimmerPagedContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        repeat(3) {
            ShimmerPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp)
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ShimmerDescriptionBlock(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        repeat(4) {
            ShimmerPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 20.dp)
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    baseColor: Color = Color(0xFFE0E0E0), // Более мягкий серый цвет
    highlightColor: Color = Color(0xFFF5F5F5) // Более светлый серый для подсветки
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500, // Увеличена длительность для плавности
                easing = LinearEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = androidx.compose.ui.geometry.Offset(translateAnim - 1000f, 0f),
        end = androidx.compose.ui.geometry.Offset(translateAnim, 0f)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)) // Добавлены округлые углы
            .background(brush)
    )
}
