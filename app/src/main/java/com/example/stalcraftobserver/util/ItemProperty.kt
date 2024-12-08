package com.example.stalcraftobserver.util

data class ItemPropertyKey(val key: String, val type: String)

object ItemsKey {
    // Глобальные ключи
    const val CORE_TOOLTIP_INFO = "core.tooltip.info"
    const val STALKER_ARTEFACT_PROPERTIES_FACTOR = "stalker.artefact_properties.factor"
    const val GENERAL_ARMOR_COMPATIBILITY = "general.armor.compatibility"
    const val ITEM_NAME = "item.name"
    const val ITEM_DESCRIPTION = "item.description"
    const val ITEM_DEVICE_NVD = "item.device"
    const val ITEM_QUALITY = "core.quality"
    const val CORE_HANDBOOK_CATEGORY = "core.handbook.category"
    const val CORE_RANK = "core.rank"
    const val STALKER_LORE_ARMOR_ARTEFACT_INFO = "stalker.lore.armor_artefact.info"
}
object ItemProperty {
    object Armor {
        object General {
            // Ключи для описания брони
            // Ранг
            const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank"
            // Категория
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            // Вес
            const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
            //Прочность
            const val DURABILITY = "${ItemsKey.CORE_TOOLTIP_INFO}.durability"
            // Максимальная прочность
            const val MAX_DURABILITY = "${ItemsKey.CORE_TOOLTIP_INFO}.max_durability"
            // Название
            const val NAME = "${ItemsKey.ITEM_NAME}"
            //Описание
            const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}"

            val generalKeys = listOf(
                Armor.General.NAME,
                Armor.General.RANK,
                Armor.General.CATEGORY,
                Armor.General.WEIGHT,
                Armor.General.DURABILITY,
                Armor.General.MAX_DURABILITY
            )
        }

        object ResistanceKeys {
            // Ключи для сопротивлений
            const val BULLET_RESISTANCE = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bullet_dmg_factor"
            const val LACERATION_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.tear_dmg_factor"
            const val EXPLOSION_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.explosion_dmg_factor"
            const val ELECTRICITY_RESISTANCE = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.electra_dmg_factor"
            const val FIRE_RESISTANCE = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.burn_dmg_factor"
            const val CHEMICAL_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.chemical_burn_dmg_factor"

            val resistanceKeys = listOf(
                Armor.ResistanceKeys.BULLET_RESISTANCE,
                Armor.ResistanceKeys.LACERATION_PROTECTION,
                Armor.ResistanceKeys.EXPLOSION_PROTECTION,
                Armor.ResistanceKeys.ELECTRICITY_RESISTANCE,
                Armor.ResistanceKeys.FIRE_RESISTANCE,
                Armor.ResistanceKeys.CHEMICAL_PROTECTION
            )
        }

        object ProtectionKeys{
            const val RADIATION_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.radiation_protection"
            const val THERMAL_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.thermal_protection"
            const val BIOLOGICAL_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.biological_protection"
            const val PSYCHO_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.psycho_protection"
            const val BLEEDING_PROTECTION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bleeding_protection"

            val protectionKeys = listOf(
                Armor.ProtectionKeys.RADIATION_PROTECTION,
                Armor.ProtectionKeys.THERMAL_PROTECTION,
                Armor.ProtectionKeys.BIOLOGICAL_PROTECTION,
                Armor.ProtectionKeys.PSYCHO_PROTECTION,
                Armor.ProtectionKeys.BLEEDING_PROTECTION
            )
        }

        object DeviceKeys {
            // Ключи для устройств
            const val NAME = "${ItemsKey.ITEM_DEVICE_NVD}.name"
        }

        object StatModifiers {
            // Ключи для модификаторов характеристик
            const val SPEED_MODIFIER = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.speed_modifier"
            const val MAX_WEIGHT_BONUS = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.max_weight_bonus"
            const val PERIODIC_HEALING = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.artefakt_heal"
            const val BLEEDING_ACCUMULATION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bleeding_accumulation"
            const val STAMINA_BONUS = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_bonus"
            const val STAMINA_REGENERATION = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_regeneration_bonus"

            val statModifierKeys = listOf(
                Armor.StatModifiers.SPEED_MODIFIER,
                Armor.StatModifiers.MAX_WEIGHT_BONUS,
                Armor.StatModifiers.PERIODIC_HEALING,
                Armor.StatModifiers.BLEEDING_ACCUMULATION,
                Armor.StatModifiers.STAMINA_BONUS,
                Armor.StatModifiers.STAMINA_REGENERATION
            )
        }

        object CompatibilityKeys {
            // Ключи для совместимости
            const val COMPATIBILITY_BACKPACKS = "${ItemsKey.STALKER_LORE_ARMOR_ARTEFACT_INFO}.compatible_backpacks"
            const val COMPATIBILITY_CONTAINERS = "${ItemsKey.STALKER_LORE_ARMOR_ARTEFACT_INFO}.compatible_containers"
            const val COMPATIBLE_BACKPACKS_LIGHT = "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.backpacks.light"
            const val COMPATIBLE_BACKPACKS_SUPERHEAVY = "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.backpacks.superheavy"
            const val COMPATIBLE_CONTAINERS_STANDARD = "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.containers.standard"
            const val COMPATIBLE_CONTAINERS_BULKY = "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.containers.bulky"

            val compatibilityKeys = listOf(
                Armor.CompatibilityKeys.COMPATIBILITY_BACKPACKS,
                Armor.CompatibilityKeys.COMPATIBILITY_CONTAINERS,
            )
        }
    }

    object Artefact {
        object General {
            // Общие ключи для артефактов
            const val NAME = "${ItemsKey.ITEM_NAME}"
            const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}"
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            const val TYPE = "${ItemsKey.CORE_TOOLTIP_INFO}.type"
            const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
            const val BASE_PRICE = "${ItemsKey.CORE_TOOLTIP_INFO}.base_price"
            const val DURABILITY = "${ItemsKey.CORE_TOOLTIP_INFO}.durability"
            const val MAX_DURABILITY = "${ItemsKey.CORE_TOOLTIP_INFO}.max_durability"
        }

        object Quality {
            // Качество артефактов
            const val COMMON = "${ItemsKey.ITEM_QUALITY}.common"
            const val NOT_PROBED = "stalker.tooltip.artefact.not_probed"
            const val FRESHNESS = "stalker.tooltip.artefact.info.freshness"
        }

        object ResistanceKeys {
            // Ключи для сопротивлений
            const val BULLET_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bullet_dmg_factor"
            const val LACERATION_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.tear_dmg_factor"
            const val EXPLOSION_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.explosion_dmg_factor"
            const val RADIATION_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.radiation_dmg_factor"
            const val THERMAL_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.thermal_dmg_factor"
            const val BIOLOGICAL_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.biological_dmg_factor"
            const val PSYCHO_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.psycho_dmg_factor"
        }

        object AccumulationKeys {
            // Ключи для накоплений (негативные эффекты)
            const val RADIATION_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.radiation_accumulation"
            const val THERMAL_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.thermal_accumulation"
            const val BIOLOGICAL_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.biological_accumulation"
            const val PSYCHO_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.psycho_accumulation"
        }

        object StatModifiers {
            // Ключи для модификаторов характеристик
            const val SPEED_MODIFIER =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.speed_modifier"
            const val STAMINA_BONUS =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_bonus"
            const val STAMINA_REGENERATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_regeneration_bonus"
            const val HEALTH_BONUS =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.health_bonus"
            const val HEALING_EFFECTIVENESS =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.heal_efficiency"
        }

        object SpecialKeys {
            // Специальные ключи для редких свойств
            const val LIFESAVER_TRIGGER_DAMAGE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.lifesaver_trigger_damage"
            const val LIFESAVER_BLOCKING_DAMAGE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.lifesaver_blocking_damage"
            const val LIFESAVER_RECHARGE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.lifesaver_recharge"
            const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}.special_rubik"
        }
    }

    object Attachment {
        object Barrel {
            // Ключи для насадок на ствол
            object General {
                const val ID = "attachment.barrel.id"
                const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
                const val NAME = "${ItemsKey.ITEM_NAME}"
                const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}"
                const val COLOR = "${ItemsKey.CORE_TOOLTIP_INFO}.color"
            }

            object StatModifiers {
                // Модификаторы характеристик для ствола
                const val SPREAD = "weapon.stat_factor.spread"
                const val HIP_SPREAD = "weapon.stat_factor.hip_spread"
                const val RECOIL = "weapon.stat_factor.recoil"
                const val HORIZONTAL_RECOIL = "weapon.stat_factor.horizontal_recoil"
                const val SHOOT_FACTOR_DECREMENT = "weapon.stat_factor.shoot_factor_decrement"
            }

            object InfoKeys {
                // Информация для ствола
                const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank"
                const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
                const val CLASS = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            }

            object CompatibilityKeys {
                // Совместимость для ствола
                const val SUITABLE_TARGETS = "weapon.lore.attachment.all_suitable_targets"
            }
        }

        object CollimatorSights {
            // Подкатегории для коллиматоров
            object GeneralCollimator {
                const val ID = "attachment.collimator_sights.id"
                const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
                const val NAME = "${ItemsKey.ITEM_NAME}"
                const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}"
                const val COLOR = "${ItemsKey.CORE_TOOLTIP_INFO}.color"
                const val MAGNIFICATION = "weapon.tooltip.sight.info.zoom"
                const val RETICLE_SHAPE = "weapon.tooltip.sight.info.reticle_shape"
                const val RETICLE_COLOR = "weapon.tooltip.sight.info.reticle_color"
            }

            object StatModifiers {
                // Общие модификаторы характеристик для коллиматоров
                const val AIM_SWITCH_TIME = "weapon.stat_factor.aim_switch_time"
                const val DRAW_TIME = "weapon.stat_factor.draw_time"
            }

            object InfoKeys {
                // Общая информация для всех категорий
                const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank"
                const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
                const val CLASS = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            }

            object CompatibilityKeys {
                // Совместимость для всех категорий
                const val SUITABLE_TARGETS = "weapon.lore.attachment.all_suitable_targets"
            }
        }

        object Forend {
            const val ID = "attachment.forend.id"
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            const val NAME = "${ItemsKey.ITEM_NAME}"
            const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}"
            const val COLOR = "${ItemsKey.CORE_TOOLTIP_INFO}.color"

            object InfoKeys {
                const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank"
                const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
                const val CLASS = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            }

            object CompatibilityKeys {
                const val SUITABLE_TARGETS = "weapon.lore.attachment.all_suitable_targets"
            }
        }

        object Mag {
            const val ID = "attachment.mag.id"
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            const val NAME = "${ItemsKey.ITEM_NAME}"
            const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}" // Неявно используется
            const val COLOR = "${ItemsKey.CORE_TOOLTIP_INFO}.color"

            object InfoKeys {
                const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank"
                const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
                const val CLASS = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            }

            object MagazineInfo {
                const val CLIP_SIZE = "weapon.tooltip.magazine.info.clip_size"
                const val RELOAD_TIME = "weapon.tooltip.magazine.info.reload_time"
                const val TACTICAL_RELOAD_TIME = "weapon.tooltip.magazine.info.reload_time_tactical"
            }

            object StatModifiers {
                const val HIP_SPREAD = "weapon.stat_factor.hip_spread"
                const val DRAW_TIME = "weapon.stat_factor.draw_time"
            }

            object CompatibilityKeys {
                const val SUITABLE_TARGETS = "weapon.lore.attachment.all_suitable_targets"
            }
        }

        object Other {
            const val ID = "attachment.other.id"
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            const val NAME = "${ItemsKey.ITEM_NAME}"
            const val COLOR = "${ItemsKey.CORE_TOOLTIP_INFO}.color"

            object InfoKeys {
                const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank" // Ранг
                const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight" // Вес
                const val CLASS = "${ItemsKey.CORE_TOOLTIP_INFO}.category" // Категория
            }

            object StatModifiers {
                const val RECOIL = "weapon.stat_factor.recoil" // Уменьшение отдачи
                const val HORIZONTAL_RECOIL = "weapon.stat_factor.horizontal_recoil" // Горизонтальная отдача
                const val RECOIL_GAIN = "weapon.stat_factor.recoil_gain" // Нарастание отдачи
                const val AIM_SWITCH_TIME = "weapon.stat_factor.aim_switch_time" // Скорость прицеливания
                const val DRAW_TIME = "weapon.stat_factor.draw_time" // Время доставания
                const val STABILIZATION = "weapon.stat_factor.shoot_factor_decrement" // Стабилизация
            }

            object CompatibilityKeys {
                const val SUITABLE_TARGETS = "weapon.lore.attachment.all_suitable_targets" // Совместимое оружие
            }
        }

        object PistolHandle {
            const val ID = "attachment.pistol_handle.id"
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            const val NAME = "${ItemsKey.ITEM_NAME}"
            const val COLOR = "${ItemsKey.CORE_TOOLTIP_INFO}.color"

            object InfoKeys {
                const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank" // Ранг
                const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight" // Вес
                const val CLASS = "${ItemsKey.CORE_TOOLTIP_INFO}.category" // Категория
            }

            object StatModifiers {
                const val RECOIL_GAIN = "weapon.stat_factor.recoil_gain" // Уменьшение нарастания отдачи
                const val SWAY = "weapon.stat_factor.wiggle" // Уменьшение покачивания
            }

            object CompatibilityKeys {
                const val SUITABLE_TARGETS = "weapon.lore.attachment.all_suitable_targets" // Совместимое оружие
            }
        }
    }
}