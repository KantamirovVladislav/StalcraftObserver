package com.example.stalcraftobserver.util

import com.example.stalcraftobserver.data.manager.Element
import com.example.stalcraftobserver.data.manager.InfoBlock
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.Lines
import com.example.stalcraftobserver.util.itemSupportModel.Armor
import com.example.stalcraftobserver.util.itemSupportModel.Weapon

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
                    }

                    else -> null
                }
            }
            return resultMap
        }

        fun getStringDamageParam(item: ItemInfo): String {
            return if (item.category.contains("weapon")) {
                val damageBlock =
                    item.infoBlocks?.find { it is InfoBlock.Damage } as? InfoBlock.Damage
                if (damageBlock != null) {
                    "Start damage: ${damageBlock.startDamage}\n" +
                            "Start decrease damage: ${damageBlock.damageDecreaseStart}\n" +
                            "End damage: ${damageBlock.endDamage}\n" +
                            "End decrease damage: ${damageBlock.damageDecreaseEnd}\n" +
                            "Max distance: ${damageBlock.maxDistance}"
                } else {
                    "Not found damage"
                }
            } else {
                "Not weapon"
            }
        }

        fun getDamageParam(item: ItemInfo): InfoBlock.Damage? {
            return if (item.category.contains("weapon")) {
                val damageBlock =
                    item.infoBlocks?.find { it is InfoBlock.Damage } as? InfoBlock.Damage
                damageBlock
            } else null
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

        fun getWeaponClassFromItemInfo(item: ItemInfo): Weapon? {
            if (item.category.contains("weapon")) {
                return Weapon(
                    name = getValuesForKey(item, ItemProperty.Weapon.General.NAME),
                    rank = getValuesForKey(item, ItemProperty.Weapon.General.RANK),
                    category = getValuesForKey(item, ItemProperty.Weapon.General.CATEGORY),
                    weight = getValuesForKey(
                        item,
                        ItemProperty.Weapon.General.WEIGHT
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    durability = getValuesForKey(
                        item,
                        ItemProperty.Weapon.General.DURABILITY
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    ammoType = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.AMMO_TYPE
                    ),
                    damage = mapOf(
                        Lines(
                            ru = "Базовый урон",
                            "Start damage"
                        ) to getDamageParam(item)?.startDamage
                    ),
                    magazineCapacity = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.CLIP_SIZE
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    maxDistance = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.DISTANCE
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    rateOfFire = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.RATE_OF_FIRE
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    reload = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.MagazineInfo.RELOAD_TIME
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    tacticalReload = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.MagazineInfo.RELOAD_TIME_TACTICAL
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    ergonomics = getValuesForKey(
                        item,
                        ItemProperty.Weapon.StatFactor.RELOAD_MODIFIER
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    spread = getValuesForKey(
                        item,
                        ItemProperty.Weapon.StatFactor.SPREAD
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    hipFireSpread = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.HIP_SPREAD
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    verticalRecoil = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.RECOIL
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    horizontalRecoil = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.HORIZONTAL_RECOIL
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    drawTime = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.DRAW_TIME
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    aimingTime = getValuesForKey(
                        item,
                        ItemProperty.Weapon.ToolTip.WeaponInfo.AIM_SWITCH
                    ).mapValues { entry ->
                        entry.value?.ru?.toDouble() ?: 0.0
                    },
                    damageModifier = mapOf(null to null),
                    features = mapOf(null to null),
                    damageDecreaseStart = mapOf(
                        Lines(
                            ru = "Начало падения урона",
                            "Damage decrease start"
                        ) to getDamageParam(item)?.damageDecreaseStart
                    ),
                    endDamage = mapOf(
                        Lines(
                            ru = "Конечный урон",
                            "End damage"
                        ) to getDamageParam(item)?.endDamage
                    ),
                    damageDecreaseEnd = mapOf(
                        Lines(
                            ru = "Конец падения урона",
                            "Damage decrease end"
                        ) to getDamageParam(item)?.damageDecreaseEnd
                    )
                )
            }
            return null
        }

        fun getStringFromKeys(
            item: ItemInfo,
            property: List<String>,
            language: String = "ru"
        ): String {
            return getValuesByKeys(item, property).map { (_, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    val lineKeyText = when (language) {
                        "ru" -> lineKey?.ru
                        "en" -> lineKey?.en
                        else -> null
                    }?.trim()

                    val lineValueText = when (language) {
                        "ru" -> lineValue?.ru
                        "en" -> lineValue?.en
                        else -> null
                    }?.trim()

                    if (lineKeyText.isNullOrBlank()) {
                        lineValueText ?: ""
                    } else {
                        "$lineKeyText: ${lineValueText ?: ""}"
                    }
                }
                formattedValues.filter { it.isNotEmpty() }.joinToString(separator = ", ")
            }
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")
        }

        fun getStringFromKey(item: ItemInfo, propertyKey: String, language: String = "ru"): String {
            return getValuesForKey(item, propertyKey)
                .map { (lineKey, lineValue) ->
                    val lineKeyText = when (language) {
                        "ru" -> lineKey?.ru
                        "en" -> lineKey?.en
                        else -> null
                    }?.trim()

                    val lineValueText = when (language) {
                        "ru" -> lineValue?.ru
                        "en" -> lineValue?.en
                        else -> null
                    }?.trim()

                    if (lineKeyText.isNullOrBlank()) {
                        lineValueText ?: ""
                    } else {
                        "$lineKeyText: ${lineValueText ?: ""}"
                    }
                }
                .filter { it.isNotEmpty() }
                .joinToString(separator = ", ")
        }


    }
}