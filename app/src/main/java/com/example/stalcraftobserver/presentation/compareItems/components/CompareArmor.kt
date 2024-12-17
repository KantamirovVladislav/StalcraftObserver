package com.example.stalcraftobserver.presentation.compareItems.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getArmorClassFromItemInfo
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getWeaponClassFromItemInfo
import com.example.stalcraftobserver.util.itemSupportModel.Armor

@Composable
fun CompareArmor(
    modifier: Modifier = Modifier,
    item1: ItemInfo?,
    item2: ItemInfo?
) {
    val armor1 = item1?.let { getArmorClassFromItemInfo(it) }
    val armor2 = item2?.let { getArmorClassFromItemInfo(it) }

    // Список атрибутов для сравнения
    val generalAttributes = listOf(
        "Название" to Pair(
            armor1?.name?.values?.firstOrNull()?.ru,
            armor2?.name?.values?.firstOrNull()?.ru
        ),
        "Ранг" to Pair(
            armor1?.rank?.values?.firstOrNull()?.ru,
            armor2?.rank?.values?.firstOrNull()?.ru
        ),
        "Категория" to Pair(
            armor1?.category?.values?.firstOrNull()?.ru,
            armor2?.category?.values?.firstOrNull()?.ru
        ),
        "Вес" to Pair(
            armor1?.weight?.values?.firstOrNull(),
            armor2?.weight?.values?.firstOrNull()
        ),
        "Прочность" to Pair(
            armor1?.durability?.values?.firstOrNull(),
            armor2?.durability?.values?.firstOrNull()
        ),
        "Максимальная прочность" to Pair(
            armor1?.maxDurability?.values?.firstOrNull(),
            armor2?.maxDurability?.values?.firstOrNull()
        )
    )

    val protectionAttributes = listOf(
        "Сопротивление пулям" to Pair(
            armor1?.bulletResistance?.values?.firstOrNull(),
            armor2?.bulletResistance?.values?.firstOrNull()
        ),
        "Защита от порезов" to Pair(
            armor1?.lacerationProtection?.values?.firstOrNull(),
            armor2?.lacerationProtection?.values?.firstOrNull()
        ),
        "Защита от взрывов" to Pair(
            armor1?.explosionProtection?.values?.firstOrNull(),
            armor2?.explosionProtection?.values?.firstOrNull()
        ),
        "Сопротивление электричеству" to Pair(
            armor1?.electricityResistance?.values?.firstOrNull(),
            armor2?.electricityResistance?.values?.firstOrNull()
        ),
        "Сопротивление огню" to Pair(
            armor1?.fireResistance?.values?.firstOrNull(),
            armor2?.fireResistance?.values?.firstOrNull()
        ),
        "Защита от химического воздействия" to Pair(
            armor1?.chemicalProtection?.values?.firstOrNull(),
            armor2?.chemicalProtection?.values?.firstOrNull()
        ),
        "Защита от радиации" to Pair(
            armor1?.radiationProtection?.values?.firstOrNull(),
            armor2?.radiationProtection?.values?.firstOrNull()
        ),
        "Тепловая защита" to Pair(
            armor1?.thermalProtection?.values?.firstOrNull(),
            armor2?.thermalProtection?.values?.firstOrNull()
        ),
        "Биологическая защита" to Pair(
            armor1?.biologicalProtection?.values?.firstOrNull(),
            armor2?.biologicalProtection?.values?.firstOrNull()
        ),
        "Психологическая защита" to Pair(
            armor1?.psychoProtection?.values?.firstOrNull(),
            armor2?.psychoProtection?.values?.firstOrNull()
        ),
        "Защита от кровотечений" to Pair(
            armor1?.bleedingProtection?.values?.firstOrNull(),
            armor2?.bleedingProtection?.values?.firstOrNull()
        )
    )

    LazyColumn(
        modifier = modifier.padding(top = 4.dp)
    ) {
        // Общая информация
        item {
            Text(
                text = "General",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        generalAttributes.forEach { (label, values) ->
            item {
                CompareRow(label, values.first, values.second)
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
        protectionAttributes.forEach { (label, values) ->
            item {
                CompareRow(label, values.first, values.second)
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
        armor1?.extraModifier?.forEachIndexed { index, modifier1 ->
            val modifier2 = armor2?.extraModifier?.getOrNull(index)
            item {
                CompareRow(
                    attribute = "Модификатор ${index + 1}",
                    value1 = modifier1.values.firstOrNull(),
                    value2 = modifier2?.values?.firstOrNull()
                )
            }
        }
    }
}
