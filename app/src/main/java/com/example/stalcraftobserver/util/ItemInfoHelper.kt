package com.example.stalcraftobserver.util

import android.util.Log
import com.example.stalcraftobserver.data.manager.Element
import com.example.stalcraftobserver.data.manager.InfoBlock
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.Lines
import com.example.stalcraftobserver.util.itemSupportModel.Armor

class ItemInfoHelper {


    companion object {

        private fun extractValues(input: String?): Pair<String, String> {
            if (input == null) {
                return Pair("", "")
            }
            val values = input
                .removeSurrounding("[", "]")
                .split(";")
                .map { it.trim().replace(",", ".") }

            return Pair(values[0], values[1])  // Возвращаем пару значений
        }

        private fun containsParts(substring: String, fullString: String): Boolean {
            val subParts = substring.split(".")
            val fullParts = fullString.split(".")

            var currentIndex = 0

            for (subPart in subParts) {
                // Найти первую позицию совпадения начиная с currentIndex
                while (currentIndex < fullParts.size && fullParts[currentIndex] != subPart) {
                    currentIndex++
                }
                if (currentIndex == fullParts.size) return false
                currentIndex++ // Сдвигаем указатель для следующей проверки
            }
            return true
        }


        fun getValuesByKeys(
            itemInfo: ItemInfo,
            keys: List<String>
        ): Map<String, Map<Lines?, Lines?>> =
            keys.associateWith { key -> getValuesForKey(itemInfo, key) }

        fun getValuesForKey(itemInfo: ItemInfo, key: String): Map<Lines?, Lines?> {
            fun checkKeyMatch(
                elementKey: String?,
                elementLinesMap: Map<Lines?, Lines?>?
            ): Pair<Lines?, Lines?>? {
                return if (elementKey?.let { containsParts(key, it) } == true) {

                    elementLinesMap?.entries?.firstOrNull()?.let {
                        Pair(it.key, it.value)
                    }
                } else null
            }

            val resultMap = mutableMapOf<Lines?, Lines?>()

            itemInfo.name?.key?.let {
                if (containsParts(key, it)) {
                    resultMap[Lines(ru = "Название", en = "Name")] = itemInfo.name.lines
                }
            }

            itemInfo.infoBlocks?.forEach { infoBlock ->
                when (infoBlock) {
                    is InfoBlock.List -> {
                        infoBlock.elements?.forEach { element ->
                            val result = when (element) {
                                is Element.KeyValueElement -> checkKeyMatch(
                                    element.key?.key,
                                    mapOf(element.key?.lines to element.value?.lines)
                                )

                                is Element.NumericElement -> checkKeyMatch(
                                    element.name?.key,
                                    mapOf(element.name?.lines to element.value.let {
                                        Lines(
                                            en = it.toString(),
                                            ru = it.toString()
                                        )
                                    })
                                )

                                is Element.TextElement -> checkKeyMatch(
                                    element.text?.key,
                                    mapOf(element.title?.lines to element.text?.lines)
                                )

                                is Element.ItemElement -> checkKeyMatch(
                                    element.name?.key,
                                    mapOf(element.name?.lines to null)
                                )

                                is Element.RangeElement -> checkKeyMatch(
                                    element.name?.key,
                                    mapOf(
                                        element.name?.lines to Lines(
                                            extractValues(element.formatted?.value?.ru).first,
                                            extractValues(element.formatted?.value?.ru).second
                                        )
                                    )
                                )

                                else -> null
                            }
                            result?.let { resultMap[it.first] = it.second }
                        }
                    }

                    is InfoBlock.Text -> {
                        val key: String? = infoBlock.title?.key ?: infoBlock.text?.key
                        checkKeyMatch(
                            key,
                            mapOf(infoBlock.title?.lines to infoBlock.text?.lines)
                        )?.let {
                            if (key != null) {
                                if (key.contains("description"))
                                    resultMap[Lines(ru = "Описание", en = "Description")] =
                                        it.second
                                else
                                    resultMap[it.first] = it.second
                            }

                        }
                    }

                    is InfoBlock.Damage -> {
                        //TODO
                    }

                    else -> null
                }
            }
            Log.d("DEBUG", "Key: $key, Result: $resultMap")
            return resultMap
        }


        fun getArmorClassFromItemInfo(item: ItemInfo): Armor? {
            if (item.category.contains("armor")) {
                return Armor(
                    name = getValuesForKey(item, ItemProperty.Armor.General.NAME),
                    rank = getValuesForKey(item, ItemProperty.Armor.General.RANK),
                    category = getValuesForKey(item, ItemProperty.Armor.General.CATEGORY),
                    weight = getValuesForKey(
                        item,
                        ItemProperty.Armor.General.WEIGHT
                    ).mapValues { entry ->
                        entry.value?.ru?.replace(" кг", "")?.toDouble() ?: 0.0
                    },
                    durability = getValuesForKey(
                        item,
                        ItemProperty.Armor.General.DURABILITY
                    ).mapValues { entry ->
                        entry.value?.ru?.replace("%", "")?.toDouble() ?: 0.0
                    },
                    maxDurability = getValuesForKey(
                        item,
                        ItemProperty.Armor.General.MAX_DURABILITY
                    ).mapValues { entry ->
                        entry.value?.ru?.replace("%", "")?.toDouble() ?: 0.0
                    },
                    bulletResistance = getValuesForKey(
                        item,
                        ItemProperty.Armor.ResistanceKeys.BULLET_RESISTANCE
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    lacerationProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ResistanceKeys.LACERATION_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    explosionProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ResistanceKeys.EXPLOSION_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    electricityResistance = getValuesForKey(
                        item,
                        ItemProperty.Armor.ResistanceKeys.ELECTRICITY_RESISTANCE
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    fireResistance = getValuesForKey(
                        item,
                        ItemProperty.Armor.ResistanceKeys.FIRE_RESISTANCE
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    chemicalProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ResistanceKeys.CHEMICAL_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    radiationProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ProtectionKeys.RADIATION_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    thermalProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ProtectionKeys.THERMAL_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    biologicalProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ProtectionKeys.BIOLOGICAL_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    psychoProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ProtectionKeys.PSYCHO_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    bleedingProtection = getValuesForKey(
                        item,
                        ItemProperty.Armor.ProtectionKeys.BLEEDING_PROTECTION
                    ).mapValues { entry ->
                        entry.value?.ru?.replace("%", "")?.toDouble() ?: 0.0
                    },
                    extraModifier = listOf(mapOf(Lines("kekw", "kekw") to 0.0))
                )
            }
            return null
        }
    }
}