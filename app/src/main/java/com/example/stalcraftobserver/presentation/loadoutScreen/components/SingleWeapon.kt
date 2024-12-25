package com.example.stalcraftobserver.presentation.loadoutScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.SingleAttributeRow
import com.example.stalcraftobserver.presentation.common.UpgradeLevelControl
import com.example.stalcraftobserver.util.DamageCalculator
import com.example.stalcraftobserver.util.DamageCalculator.calculateDPS
import com.example.stalcraftobserver.util.DamageCalculator.calculateHeadDamage
import com.example.stalcraftobserver.util.DamageCalculator.calculateTTK
import com.example.stalcraftobserver.util.ItemInfoHelper.Companion.getWeaponClassFromItemInfo
import com.example.stalcraftobserver.util.itemSupportModel.Weapon

@Composable
fun SingleWeapon(
    modifier: Modifier = Modifier,
    item: ItemInfo?
) {
    val weapon: Weapon? = remember(item) {
        item?.let { getWeaponClassFromItemInfo(it) }
    }

    val initialMinDamage = remember(weapon) {
        weapon?.damage?.values?.firstOrNull()?.toDouble() ?: 0.0
    }
    val initialEndDamage = remember(weapon) {
        weapon?.endDamage?.values?.firstOrNull()?.toDouble() ?: 0.0
    }

    var upgradeLevel by rememberSaveable { mutableIntStateOf(0) }

    val (newMinDamage, newEndDamage) = remember(upgradeLevel, weapon) {
        try {
            DamageCalculator.increaseDamage(initialMinDamage, initialEndDamage, upgradeLevel)
        } catch (e: IllegalArgumentException) {
            initialMinDamage to initialEndDamage
        }
    }

    val rateOfFire = remember(weapon) {
        weapon?.rateOfFire?.values?.firstOrNull()?.toDouble() ?: 0.0
    }

    val dps = remember(newMinDamage, rateOfFire) {
        calculateDPS(newMinDamage, rateOfFire)
    }

    val ttk = remember(dps) {
        calculateTTK(dps, 600.0)
    }

    val headDamage = remember(newMinDamage) {
        calculateHeadDamage(newMinDamage)
    }

    val levelDecreased = remember(newMinDamage, newEndDamage, dps) {
        listOf(
            "Урон" to String.format("%.2f", newMinDamage),
            "Head damage" to String.format("%.2f", headDamage),
            "End damage" to String.format("%.2f", newEndDamage),
            "DPS" to String.format("%.2f", dps),
            "TTK (600hp)" to String.format("%.2f", ttk)
        )
    }

    val generalAttributes = remember(weapon) {
        listOf(
            "Название" to weapon?.name?.values?.firstOrNull()?.ru,
            "Ранг" to weapon?.rank?.values?.firstOrNull()?.ru,
            "Категория" to weapon?.category?.values?.firstOrNull()?.ru,
            "Вес" to weapon?.weight?.values?.firstOrNull(),
            "Прочность" to weapon?.durability?.values?.firstOrNull(),
            "Тип боеприпаса" to weapon?.ammoType?.values?.firstOrNull()?.ru,
            "Magazine capacity" to weapon?.magazineCapacity?.values?.firstOrNull()
        )
    }

    val damageAttributes = remember(weapon) {
        listOf(
            "Урон" to weapon?.damage?.values?.firstOrNull(),
            "Damage decrease start" to weapon?.damageDecreaseStart?.values?.firstOrNull(),
            "End damage" to weapon?.endDamage?.values?.firstOrNull(),
            "Damage decrease end" to weapon?.damageDecreaseEnd?.values?.firstOrNull(),
            "Max distance" to weapon?.maxDistance?.values?.firstOrNull()
        )
    }

    val otherAttributes = remember(weapon) {
        listOf(
            "Rate of fire" to weapon?.rateOfFire?.values?.firstOrNull(),
            "Reload" to weapon?.reload?.values?.firstOrNull(),
            "Tactical reload" to weapon?.tacticalReload?.values?.firstOrNull(),
            "Ergonomics" to weapon?.ergonomics?.values?.firstOrNull(),
            "Spread" to weapon?.spread?.values?.firstOrNull(),
            "Hip fire spread" to weapon?.hipFireSpread?.values?.firstOrNull(),
            "Vertical recoil" to weapon?.verticalRecoil?.values?.firstOrNull(),
            "Horizontal recoil" to weapon?.horizontalRecoil?.values?.firstOrNull(),
            "Draw time" to weapon?.drawTime?.values?.firstOrNull(),
            "Aiming time" to weapon?.aimingTime?.values?.firstOrNull()
        )
    }

    LazyColumn(modifier = modifier) {
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

        items(levelDecreased) { (label, value) ->
            SingleAttributeRow(label = label, value = value)
        }

        item {
            Text(
                text = "General",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        items(generalAttributes) { (label, value) ->
            SingleAttributeRow(label = label, value = value)
        }

        item {
            Text(
                text = "Damage",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        items(damageAttributes) { (label, value) ->
            SingleAttributeRow(label = label, value = value)
        }

        items(otherAttributes) { (label, value) ->
            SingleAttributeRow(label = label, value = value)
        }
    }
}