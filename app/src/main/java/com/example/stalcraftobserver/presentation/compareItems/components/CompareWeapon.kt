package com.example.stalcraftobserver.presentation.compareItems.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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

@Composable
fun CompareWeapon(
    modifier: Modifier = Modifier,
    item1: ItemInfo?,
    item2: ItemInfo?
) {
    val weapon1 = item1?.let { getWeaponClassFromItemInfo(it) }
    val weapon2 = item2?.let { getWeaponClassFromItemInfo(it) }

    // Список атрибутов для отображения
    val generalAttributes = listOf(
        "Название" to Pair(
            weapon1?.name?.values?.firstOrNull()?.ru,
            weapon2?.name?.values?.firstOrNull()?.ru
        ),
        "Ранг" to Pair(
            weapon1?.rank?.values?.firstOrNull()?.ru,
            weapon2?.rank?.values?.firstOrNull()?.ru
        ),
        "Категория" to Pair(
            weapon1?.category?.values?.firstOrNull()?.ru,
            weapon2?.category?.values?.firstOrNull()?.ru
        ),
        "Вес" to Pair(
            weapon1?.weight?.values?.firstOrNull(),
            weapon2?.weight?.values?.firstOrNull()
        ),
        "Прочность" to Pair(
            weapon1?.durability?.values?.firstOrNull(),
            weapon2?.durability?.values?.firstOrNull()
        ),
        "Тип боеприпаса" to Pair(
            weapon1?.ammoType?.values?.firstOrNull()?.ru,
            weapon2?.ammoType?.values?.firstOrNull()?.ru
        ),
        "Magazine capacity" to Pair(
            weapon1?.magazineCapacity?.values?.firstOrNull(),
            weapon2?.magazineCapacity?.values?.firstOrNull()
        )
    )

    val damageAttributes = listOf(
        "Урон" to Pair(
            weapon1?.damage?.values?.firstOrNull(),
            weapon2?.damage?.values?.firstOrNull()
        ),
        "Damage decrease start" to Pair(
            weapon1?.damageDecreaseStart?.values?.firstOrNull(),
            weapon2?.damageDecreaseStart?.values?.firstOrNull()
        ),
        "End damage" to Pair(
            weapon1?.endDamage?.values?.firstOrNull(),
            weapon2?.endDamage?.values?.firstOrNull()
        ),
        "Damage decrease end" to Pair(
            weapon1?.damageDecreaseEnd?.values?.firstOrNull(),
            weapon2?.damageDecreaseEnd?.values?.firstOrNull()
        ),
        "Max distance" to Pair(
            weapon1?.maxDistance?.values?.firstOrNull(),
            weapon2?.maxDistance?.values?.firstOrNull()
        )
    )

    val otherAttributes = listOf(
        "Rate of fire" to Pair(
            weapon1?.rateOfFire?.values?.firstOrNull(),
            weapon2?.rateOfFire?.values?.firstOrNull()
        ),
        "Reload" to Pair(
            weapon1?.reload?.values?.firstOrNull(),
            weapon2?.reload?.values?.firstOrNull()
        ),
        "Tactical reload" to Pair(
            weapon1?.tacticalReload?.values?.firstOrNull(),
            weapon2?.tacticalReload?.values?.firstOrNull()
        ),
        "Ergonomics" to Pair(
            weapon1?.ergonomics?.values?.firstOrNull(),
            weapon2?.ergonomics?.values?.firstOrNull()
        ),
        "Spread" to Pair(
            weapon1?.spread?.values?.firstOrNull(),
            weapon2?.spread?.values?.firstOrNull()
        ),
        "Hip fire spread" to Pair(
            weapon1?.hipFireSpread?.values?.firstOrNull(),
            weapon2?.hipFireSpread?.values?.firstOrNull()
        ),
        "Vertical recoil" to Pair(
            weapon1?.verticalRecoil?.values?.firstOrNull(),
            weapon2?.verticalRecoil?.values?.firstOrNull()
        ),
        "Horizontal recoil" to Pair(
            weapon1?.horizontalRecoil?.values?.firstOrNull(),
            weapon2?.horizontalRecoil?.values?.firstOrNull()
        ),
        "Draw time" to Pair(
            weapon1?.drawTime?.values?.firstOrNull(),
            weapon2?.drawTime?.values?.firstOrNull()
        ),
        "Aiming time" to Pair(
            weapon1?.aimingTime?.values?.firstOrNull(),
            weapon2?.aimingTime?.values?.firstOrNull()
        )
    )

    LazyColumn(modifier = modifier) {
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

        item {
            Text(
                text = "Damage",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        damageAttributes.forEach { (label, values) ->
            item {
                CompareRow(label, values.first, values.second)
            }
        }

        otherAttributes.forEach { (label, values) ->
            item {
                CompareRow(label, values.first, values.second)
            }
        }

        item{
            Spacer(Modifier.height(60.dp))
        }
    }
}
