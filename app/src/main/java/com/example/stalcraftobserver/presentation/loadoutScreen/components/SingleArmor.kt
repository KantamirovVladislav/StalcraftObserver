package com.example.stalcraftobserver.presentation.loadoutScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.SingleAttributeRow
import com.example.stalcraftobserver.presentation.common.UpgradeLevelControl
import com.example.stalcraftobserver.util.DamageCalculator
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getArmorClassFromItemInfo
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getWeaponClassFromItemInfo
import com.example.stalcraftobserver.util.itemSupportModel.Armor
import com.example.stalcraftobserver.util.itemSupportModel.Weapon

@Composable
fun SingleArmor(
    modifier: Modifier = Modifier,
    upgradeLevel: Int = 0,
    onLevelChanged: (newLevel: Int) -> Unit,
    item: ItemInfo?
) {
    var armor: Armor? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        armor = item?.let { getArmorClassFromItemInfo(it) }
    }

    LaunchedEffect(item) {
        armor = item?.let { getArmorClassFromItemInfo(it) }
    }


    // Список атрибутов для отображения
    val generalAttributes = listOf(
        "Название" to (armor?.name?.values?.firstOrNull()?.ru),
        "Ранг" to (armor?.rank?.values?.firstOrNull()?.ru),
        "Категория" to (armor?.category?.values?.firstOrNull()?.ru),
        "Вес" to (armor?.weight?.values?.firstOrNull()),
        "Прочность" to (armor?.durability?.values?.firstOrNull()),
    )

    val protectionAttributes = listOf(
        "Сопротивление пулям" to (armor?.bulletResistance?.values?.firstOrNull()),
        "Защита от порезов" to (armor?.lacerationProtection?.values?.firstOrNull()),
        "Защита от взрывов" to (armor?.explosionProtection?.values?.firstOrNull()),
        "Сопротивление электричеству" to (armor?.electricityResistance?.values?.firstOrNull()),
        "Сопротивление огню" to (armor?.fireResistance?.values?.firstOrNull()),
        "Защита от химического воздействия" to (armor?.chemicalProtection?.values?.firstOrNull()),
        "Защита от радиации" to (armor?.radiationProtection?.values?.firstOrNull()),
        "Тепловая защита" to (armor?.thermalProtection?.values?.firstOrNull()),
        "Биологическая защита" to (armor?.biologicalProtection?.values?.firstOrNull()),
        "Психологическая защита" to (armor?.psychoProtection?.values?.firstOrNull()),
        "Защита от кровотечений" to (armor?.bleedingProtection?.values?.firstOrNull())
    )

    LazyColumn(modifier = modifier.padding(top = 4.dp)) {
        item {
            UpgradeLevelControl(
                currentLevel = upgradeLevel,
                onLevelChange = { newLevel ->
                    if (newLevel in 0..15) {
                        onLevelChanged(newLevel)
                    }
                }
            )
        }

        // Общая информация
        item {
            Text(
                text = "General",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        generalAttributes.forEach { (label, value) ->
            item {
                SingleAttributeRow(label, value)
            }
        }

        // Информация о защите
        item {
            Text(
                text = "Protection",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        protectionAttributes.forEach { (label, value) ->
            item {
                SingleAttributeRow(label, value)
            }
        }

        // Дополнительные модификаторы
        item {
            Text(
                text = "Extra Modifiers",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }

        armor?.extraModifier?.forEachIndexed { index, extraMod ->
            item {
                SingleAttributeRow("Модификатор ${index + 1}", extraMod.values.firstOrNull())
            }
        }
    }
}