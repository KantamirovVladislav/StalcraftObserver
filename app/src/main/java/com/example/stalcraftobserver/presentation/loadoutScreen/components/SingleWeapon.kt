package com.example.stalcraftobserver.presentation.loadoutScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.util.DamageCalculator
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getWeaponClassFromItemInfo

@Composable
fun SingleWeapon(
    modifier: Modifier = Modifier,
    item: ItemInfo?
) {
    val weapon = item?.let { getWeaponClassFromItemInfo(it) }

    val initialMinDamage = weapon?.damage?.values?.firstOrNull()?.toDouble() ?: 0.0
    val initialEndDamage = weapon?.endDamage?.values?.firstOrNull()?.toDouble() ?: 0.0

    var upgradeLevel by rememberSaveable { mutableIntStateOf(0) }

    val (newMinDamage, newEndDamage) = remember(upgradeLevel) {
        try {
            DamageCalculator.increaseDamage(initialMinDamage, initialEndDamage, upgradeLevel)
        } catch (e: IllegalArgumentException) {
            initialMinDamage to initialEndDamage
        }
    }

    val levelDecreased = listOf(
        "Урон" to String.format("%.2f", newMinDamage),
        "End damage" to String.format("%.2f", newEndDamage)
    )

    val generalAttributes = listOf(
        "Название" to (weapon?.name?.values?.firstOrNull()?.ru),
        "Ранг" to (weapon?.rank?.values?.firstOrNull()?.ru),
        "Категория" to (weapon?.category?.values?.firstOrNull()?.ru),
        "Вес" to (weapon?.weight?.values?.firstOrNull()),
        "Прочность" to (weapon?.durability?.values?.firstOrNull()),
        "Тип боеприпаса" to (weapon?.ammoType?.values?.firstOrNull()?.ru),
        "Magazine capacity" to (weapon?.magazineCapacity?.values?.firstOrNull())
    )

    val damageAttributes = listOf(
        "Урон" to (weapon?.damage?.values?.firstOrNull()),
        "Damage decrease start" to (weapon?.damageDecreaseStart?.values?.firstOrNull()),
        "End damage" to (weapon?.endDamage?.values?.firstOrNull()),
        "Damage decrease end" to (weapon?.damageDecreaseEnd?.values?.firstOrNull()),
        "Max distance" to (weapon?.maxDistance?.values?.firstOrNull())
    )

    val otherAttributes = listOf(
        "Rate of fire" to (weapon?.rateOfFire?.values?.firstOrNull()),
        "Reload" to (weapon?.reload?.values?.firstOrNull()),
        "Tactical reload" to (weapon?.tacticalReload?.values?.firstOrNull()),
        "Ergonomics" to (weapon?.ergonomics?.values?.firstOrNull()),
        "Spread" to (weapon?.spread?.values?.firstOrNull()),
        "Hip fire spread" to (weapon?.hipFireSpread?.values?.firstOrNull()),
        "Vertical recoil" to (weapon?.verticalRecoil?.values?.firstOrNull()),
        "Horizontal recoil" to (weapon?.horizontalRecoil?.values?.firstOrNull()),
        "Draw time" to (weapon?.drawTime?.values?.firstOrNull()),
        "Aiming time" to (weapon?.aimingTime?.values?.firstOrNull())
    )

    LazyColumn(modifier = modifier) {
        // Добавляем строку для управления уровнем улучшения
        item {
            UpgradeLevelControl(
                currentLevel = upgradeLevel,
                onLevelChange = { newLevel ->
                    if (newLevel in 0..15) {
                        upgradeLevel = newLevel
                    }
                }
            )
        }
        levelDecreased.forEach{ (label, value) ->
            item {
                SingleAttributeRow(label = label, value = value)
            }
        }
        // Раздел General
        item {
            Text(
                text = "General",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        generalAttributes.forEach { (label, value) ->
            item {
                SingleAttributeRow(label = label, value = value)
            }
        }

        // Раздел Damage
        item {
            Text(
                text = "Damage",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        damageAttributes.forEach { (label, value) ->
            item {
                SingleAttributeRow(label = label, value = value)
            }
        }

        // Другие атрибуты
        otherAttributes.forEach { (label, value) ->
            item {
                SingleAttributeRow(label = label, value = value)
            }
        }
    }
}

@Composable
fun <T> SingleAttributeRow(label: String, value: T?) {
    androidx.compose.material3.Card(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = value?.toString() ?: "-",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun UpgradeLevelControl(
    currentLevel: Int,
    onLevelChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Кнопка уменьшения уровня
        IconButton(
            onClick = { onLevelChange(currentLevel - 1) },
            enabled = currentLevel > 1
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease Level"
            )
        }

        // Отображение текущего уровня
        Text(
            text = "Уровень улучшения: $currentLevel",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Кнопка увеличения уровня
        IconButton(
            onClick = { onLevelChange(currentLevel + 1) },
            enabled = currentLevel < 15
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase Level"
            )
        }
    }
}