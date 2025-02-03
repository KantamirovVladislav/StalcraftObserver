package com.example.stalcraftobserver.util

import com.example.stalcraftobserver.util.ItemProperty.Attachment.CompatibilityKeys.SUITABLE_TARGETS
import retrofit2.http.HEAD

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
    const val ITEM_DISPLAY_AMMO_TYPES = "item.wpn.display_ammo_types"
    const val WEAPON_STAT_FACTOR = "weapon.stat_factor"
    const val WEAPON_TOOLTIP_WEAPON_INFO = "weapon.tooltip.weapon.info"
    const val WEAPON_TOOLTIP_MAGAZINE_INFO = "weapon.tooltip.magazine.info"
}
data class ItemPropertyKey(val key: String, val type: String)

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
            const val BULLET_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bullet_dmg_factor"
            const val LACERATION_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.tear_dmg_factor"
            const val EXPLOSION_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.explosion_dmg_factor"
            const val ELECTRICITY_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.electra_dmg_factor"
            const val FIRE_RESISTANCE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.burn_dmg_factor"
            const val CHEMICAL_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.chemical_burn_dmg_factor"

            val resistanceKeys = listOf(
                Armor.ResistanceKeys.BULLET_RESISTANCE,
                Armor.ResistanceKeys.LACERATION_PROTECTION,
                Armor.ResistanceKeys.EXPLOSION_PROTECTION,
                Armor.ResistanceKeys.ELECTRICITY_RESISTANCE,
                Armor.ResistanceKeys.FIRE_RESISTANCE,
                Armor.ResistanceKeys.CHEMICAL_PROTECTION
            )
        }

        object ProtectionKeys {
            const val RADIATION_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.radiation_protection"
            const val THERMAL_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.thermal_protection"
            const val BIOLOGICAL_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.biological_protection"
            const val PSYCHO_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.psycho_protection"
            const val BLEEDING_PROTECTION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bleeding_protection"

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
            const val SPEED_MODIFIER =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.speed_modifier"
            const val MAX_WEIGHT_BONUS =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.max_weight_bonus"
            const val PERIODIC_HEALING =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.artefakt_heal"
            const val BLEEDING_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bleeding_accumulation"
            const val STAMINA_BONUS = "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_bonus"
            const val STAMINA_REGENERATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_regeneration_bonus"

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
            const val COMPATIBILITY_BACKPACKS =
                "${ItemsKey.STALKER_LORE_ARMOR_ARTEFACT_INFO}.compatible_backpacks"
            const val COMPATIBILITY_CONTAINERS =
                "${ItemsKey.STALKER_LORE_ARMOR_ARTEFACT_INFO}.compatible_containers"
            const val COMPATIBLE_BACKPACKS_LIGHT =
                "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.backpacks.light"
            const val COMPATIBLE_BACKPACKS_SUPERHEAVY =
                "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.backpacks.superheavy"
            const val COMPATIBLE_CONTAINERS_STANDARD =
                "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.containers.standard"
            const val COMPATIBLE_CONTAINERS_BULKY =
                "${ItemsKey.GENERAL_ARMOR_COMPATIBILITY}.containers.bulky"

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

            val generalKeys = listOf(
                NAME,
                CATEGORY,
                TYPE,
                WEIGHT,
                BASE_PRICE,
                DURABILITY,
                MAX_DURABILITY
            )
        }


        object Quality {
            // Качество артефактов
            const val COMMON = "${ItemsKey.ITEM_QUALITY}.common"
            const val NOT_PROBED = "stalker.tooltip.artefact.not_probed"
            const val FRESHNESS = "stalker.tooltip.artefact.info.freshness"

            val qualityKeys = listOf(
                COMMON,
                NOT_PROBED,
                FRESHNESS
            )
        }



        object Resistance {
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

            const val BIOLOGICAL_PROTECTION = "artefact_properties.factor.biological_protection"

            val resistanceKeys = listOf(
                BULLET_RESISTANCE,
                LACERATION_PROTECTION,
                EXPLOSION_PROTECTION,
                RADIATION_RESISTANCE,
                THERMAL_RESISTANCE,
                BIOLOGICAL_RESISTANCE,
                PSYCHO_RESISTANCE,
                BIOLOGICAL_PROTECTION
            )
        }



        object Accumulation {
            // Ключи для накоплений (негативные эффекты)
            const val RADIATION_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.radiation_accumulation"
            const val THERMAL_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.thermal_accumulation"
            const val BIOLOGICAL_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.biological_accumulation"
            const val PSYCHO_ACCUMULATION =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.psycho_accumulation"

            const val BLEEDING_ACCUMULATION = "stalker.artefact_properties.factor.bleeding_accumulation"

            val accumulationKeys = listOf(
                RADIATION_ACCUMULATION,
                THERMAL_ACCUMULATION,
                BIOLOGICAL_ACCUMULATION,
                PSYCHO_ACCUMULATION,
                BLEEDING_ACCUMULATION
            )
        }

        object AllKeys{
            val keys = listOf(
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.bullet_dmg_factor",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.tear_dmg_factor",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.explosion_dmg_factor",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.radiation_dmg_factor",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.thermal_dmg_factor",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.biological_dmg_factor",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.psycho_dmg_factor",
                "artefact_properties.factor.biological_protection",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.radiation_accumulation",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.thermal_accumulation",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.biological_accumulation",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.psycho_accumulation",
                "stalker.artefact_properties.factor.bleeding_accumulation",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.speed_modifier",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_bonus",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.stamina_regeneration_bonus",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.health_bonus",
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.heal_efficiency",
                "stalker.artefact_properties.factor.regeneration_bonus",
                "stalker.artefact_properties.factor.reaction_to_tear"
            )
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

            const val REGENERATION_BONUS = "stalker.artefact_properties.factor.regeneration_bonus"

            const val REACTION_ON_TEAR = "stalker.artefact_properties.factor.reaction_to_tear"

            val statModifiersKeys = listOf(
                SPEED_MODIFIER,
                STAMINA_BONUS,
                STAMINA_REGENERATION,
                HEALTH_BONUS,
                HEALING_EFFECTIVENESS,
                REGENERATION_BONUS,
                REACTION_ON_TEAR
            )
        }

        object Special {
            // Специальные ключи для редких свойств
            const val LIFESAVER_TRIGGER_DAMAGE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.lifesaver_trigger_damage"
            const val LIFESAVER_BLOCKING_DAMAGE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.lifesaver_blocking_damage"
            const val LIFESAVER_RECHARGE =
                "${ItemsKey.STALKER_ARTEFACT_PROPERTIES_FACTOR}.lifesaver_recharge"
            const val DESCRIPTION = "${ItemsKey.ITEM_DESCRIPTION}.special_rubik"

            val specialKeys = listOf(
                LIFESAVER_TRIGGER_DAMAGE,
                LIFESAVER_BLOCKING_DAMAGE,
                LIFESAVER_RECHARGE,
                DESCRIPTION
            )
        }
    }

    object Attachment {
        object General {
            const val ID = "attachment.general.id"
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            const val NAME = "item.att.name"
            const val COLOR = "${ItemsKey.CORE_TOOLTIP_INFO}.color"
            const val RANK = "${ItemsKey.CORE_TOOLTIP_INFO}.rank"
            const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
            const val CLASS = "${ItemsKey.CORE_TOOLTIP_INFO}.category"

            val generalKeys = listOf(
                ID,
                CATEGORY,
                NAME,
                COLOR,
                RANK,
                WEIGHT,
                CLASS
            )
        }

        object StatModifiers {
            const val SPREAD = "weapon.stat_factor.spread"
            const val HIP_SPREAD = "weapon.stat_factor.hip_spread"
            const val RECOIL = "weapon.stat_factor.recoil"
            const val HORIZONTAL_RECOIL = "weapon.stat_factor.horizontal_recoil"
            const val SHOOT_FACTOR_DECREMENT = "weapon.stat_factor.shoot_factor_decrement"
            const val AIM_SWITCH_TIME = "weapon.stat_factor.aim_switch_time"
            const val DRAW_TIME = "weapon.stat_factor.draw_time"
            const val RECOIL_GAIN = "weapon.stat_factor.recoil_gain"
            const val STABILIZATION = "weapon.stat_factor.shoot_factor_decrement"
            const val SWAY = "weapon.stat_factor.wiggle"

            val statModifiersKeys = listOf(
                SPREAD,
                HIP_SPREAD,
                RECOIL,
                HORIZONTAL_RECOIL,
                SHOOT_FACTOR_DECREMENT,
                AIM_SWITCH_TIME,
                DRAW_TIME,
                RECOIL_GAIN,
                STABILIZATION,
                SWAY
            )
        }

        object CompatibilityKeys {
            const val SUITABLE_TARGETS = "weapon.lore.attachment.all_suitable_targets"

            val compatibilityKeys = listOf(SUITABLE_TARGETS)
        }

        object Specific {
            const val MAGNIFICATION = "weapon.tooltip.sight.info.zoom"
            const val RETICLE_SHAPE = "weapon.tooltip.sight.info.reticle_shape"
            const val RETICLE_COLOR = "weapon.tooltip.sight.info.reticle_color"
            const val CLIP_SIZE = "weapon.tooltip.magazine.info.clip_size"
            const val RELOAD_TIME = "weapon.tooltip.magazine.info.reload_time"
            const val TACTICAL_RELOAD_TIME = "weapon.tooltip.magazine.info.reload_time_tactical"

            val specificKeys = listOf(MAGNIFICATION,RETICLE_SHAPE, RETICLE_COLOR, CLIP_SIZE, RELOAD_TIME, TACTICAL_RELOAD_TIME )
        }
    }

    object Weapon {
        object General {
            const val NAME = ItemsKey.ITEM_NAME
            const val DESCRIPTION = ItemsKey.ITEM_DESCRIPTION
            const val RANK = ItemsKey.CORE_RANK
            const val CATEGORY = "${ItemsKey.CORE_TOOLTIP_INFO}.category"
            const val WEIGHT = "${ItemsKey.CORE_TOOLTIP_INFO}.weight"
            const val DURABILITY = "${ItemsKey.CORE_TOOLTIP_INFO}.durability"
            const val FEATURES = "item.features"
            const val AMMO_545MM = "${ItemsKey.ITEM_DISPLAY_AMMO_TYPES}.545mm"
            const val AMMO_762MM = "${ItemsKey.ITEM_DISPLAY_AMMO_TYPES}.762mm"
            const val AMMO_556MM = "${ItemsKey.ITEM_DISPLAY_AMMO_TYPES}.556mm"
            const val AMMO_127MM = "${ItemsKey.ITEM_DISPLAY_AMMO_TYPES}.127mm"
            const val AMMO_939MM = "${ItemsKey.ITEM_DISPLAY_AMMO_TYPES}.939mm"
            const val HEAD_DAMAGE_MODIFIER = "weapon.tooltip.weapon.head_damage_modifier"
            const val LIMBS_DAMAGE_MODIFIER = "weapon.tooltip.weapon.limbs_damage_modifier"

            val generalKeys = listOf(
                NAME,
                RANK,
                CATEGORY,
                WEIGHT,
                DURABILITY,
                FEATURES,
                HEAD_DAMAGE_MODIFIER,
                LIMBS_DAMAGE_MODIFIER
            )
        }

        object StatFactor {
            const val DAMAGE_DISTANT = "${ItemsKey.WEAPON_STAT_FACTOR}.damage_distant"
            const val DAMAGE = "${ItemsKey.WEAPON_STAT_FACTOR}.damage"
            const val RECOIL = "${ItemsKey.WEAPON_STAT_FACTOR}.recoil"
            const val SPREAD = "weapon.tooltip.weapon.info.spread"
            const val DRAW_TIME = "${ItemsKey.WEAPON_STAT_FACTOR}.draw_time"
            const val DAMAGE_DECREASE_START = "${ItemsKey.WEAPON_STAT_FACTOR}.damage_decrease_start"
            const val WIGGLE = "${ItemsKey.WEAPON_STAT_FACTOR}.wiggle"
            const val RELOAD_MODIFIER = "${ItemsKey.WEAPON_STAT_FACTOR}.reload_modifier"
            const val HORIZONTAL_RECOIL = "${ItemsKey.WEAPON_STAT_FACTOR}.horizontal_recoil"
            const val AIM_SWITCH_TIME = "${ItemsKey.WEAPON_STAT_FACTOR}.aim_switch_time"

            val statFactorKeys = listOf(
                DAMAGE_DISTANT,
                DAMAGE,
                RECOIL,
                SPREAD,
                DRAW_TIME,
                DAMAGE_DECREASE_START,
                WIGGLE,
                RELOAD_MODIFIER,
                HORIZONTAL_RECOIL,
                AIM_SWITCH_TIME
            )
        }

        object ToolTip {
            object WeaponInfo {
                const val SPREAD = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.spread"
                const val CLIP_SIZE = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.clip_size"
                const val ATTACHMENT_STATS =
                    "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.attachment_stat"
                const val RATE_OF_FIRE = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.rate_of_fire"
                const val DAMAGE = "core.tooltip.stat_name.damage_type.direct"
                const val HORIZONTAL_RECOIL =
                    "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.horizontal_recoil"
                const val DISTANCE = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.distance"
                const val AIM_SWITCH = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.aim_switch"
                const val DAMAGE_MODIFIERS =
                    "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.damage_modifiers"
                const val UPGRADE_STATS = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.upgrade_stat"
                const val OVERHEAT_AFTER = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.overheat_after"
                const val DRAW_TIME = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.draw_time"
                const val FEATURE = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.feature"
                const val RECOIL = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.recoil"
                const val HIP_SPREAD = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.hip_spread"
                const val AMMO_TYPE = "${ItemsKey.WEAPON_TOOLTIP_WEAPON_INFO}.ammo_type"

                val weaponInfoKeys = listOf(
                    SPREAD,
                    CLIP_SIZE,
                    ATTACHMENT_STATS,
                    RATE_OF_FIRE,
                    HORIZONTAL_RECOIL,
                    DAMAGE,
                    DISTANCE,
                    AIM_SWITCH,
                    DAMAGE_MODIFIERS,
                    UPGRADE_STATS,
                    OVERHEAT_AFTER,
                    DRAW_TIME,
                    FEATURE,
                    RECOIL,
                    HIP_SPREAD,
                    AMMO_TYPE
                )
            }

            object MagazineInfo {
                const val RELOAD_TIME_TACTICAL =
                    "${ItemsKey.WEAPON_TOOLTIP_MAGAZINE_INFO}.reload_time_tactical"
                const val RELOAD_TIME = "${ItemsKey.WEAPON_TOOLTIP_MAGAZINE_INFO}.reload_time"

                val magazineInfoKeys = listOf(
                    RELOAD_TIME,
                    RELOAD_TIME_TACTICAL
                )
            }
        }
    }

    object Bullet{
        object General{
            const val NAME = "item.name"
            const val DESCRIPTION = "item.description"
            const val USED_IN_CRAFTS = "core.tooltip.used_in_crafts"
            const val CATEGORY_BULLET = "core.handbook.category.bullet"
            const val INFO_RANK = "core.tooltip.info.rank"
            const val INFO_CATEGORY = "core.tooltip.info.category"
            const val INFO_WEIGHT = "core.tooltip.info.weight"
            const val INFO_DURABILITY = "core.tooltip.info.durability"
            const val COMMAND_INFO_USAGES_LEFT = "customitem.lore.command.info.usages_left"

            val generalKeys = listOf(
                NAME,
                DESCRIPTION,
                USED_IN_CRAFTS,
                CATEGORY_BULLET,
                INFO_RANK,
                INFO_CATEGORY,
                INFO_WEIGHT,
                INFO_DURABILITY,
                COMMAND_INFO_USAGES_LEFT
            )
        }



        object Stats{
            const val LIMBS_DAMAGE_MODIFIER = "weapon.stat_factor.limbs_damage_modifier"
            const val RECOIL = "weapon.stat_factor.recoil"
            const val ARMOR_PLATE_DAMAGE = "weapon.stat_factor.armor_plate_damage"
            const val MOBS_DAMAGE_MULTIPLIER = "weapon.stat_factor.mobs_damage_multiplier"
            const val SPREAD = "weapon.tooltip.bullet.stat_name.spread"
            const val COMBUSTION = "weapon.tooltip.bullet.stat_name.combustion"
            const val STOPPING_POWER = "weapon.tooltip.bullet.stat_name.stopping_power"
            const val PIERCING = "weapon.tooltip.bullet.stat_name.piercing"
            const val DISTANCE = "weapon.tooltip.bullet.stat_name.distance"
            const val BLEEDING = "weapon.tooltip.bullet.stat_name.bleeding"
            const val DAMAGE = "weapon.tooltip.bullet.stat_name.damage"
            const val WEAPON_HEATING = "weapon.tooltip.bullet.stat_name.weapon_heating"
            const val DAMAGE_TYPE_EXPLOSION = "core.tooltip.stat_name.damage_type.explosion"
            const val DAMAGE_TYPE_DEFAULT = "core.tooltip.stat_name.damage_type.default"
            const val DAMAGE_TYPE_BURN = "core.tooltip.stat_name.damage_type.burn"
            const val DAMAGE_TYPE_ELECTROSHOCK = "core.tooltip.stat_name.damage_type.electroshock"
            const val DAMAGE_TYPE_TEAR = "core.tooltip.stat_name.damage_type.tear"
            const val DAMAGE_TYPE_CHEMICAL_BURN = "core.tooltip.stat_name.damage_type.chemical_burn"
            const val DAMAGE_TYPE_FROST = "core.tooltip.stat_name.damage_type.frost"

            val statsKeys = listOf(
                LIMBS_DAMAGE_MODIFIER,
                RECOIL,
                ARMOR_PLATE_DAMAGE,
                MOBS_DAMAGE_MULTIPLIER,
                SPREAD,
                COMBUSTION,
                STOPPING_POWER,
                PIERCING,
                DISTANCE,
                BLEEDING,
                DAMAGE,
                WEAPON_HEATING,
                DAMAGE_TYPE_EXPLOSION,
                DAMAGE_TYPE_DEFAULT,
                DAMAGE_TYPE_BURN,
                DAMAGE_TYPE_ELECTROSHOCK,
                DAMAGE_TYPE_TEAR,
                DAMAGE_TYPE_CHEMICAL_BURN,
                DAMAGE_TYPE_FROST
            )
        }

        object Types{
            const val NUM_BULLETS = "weapon.tooltip.bullet.info.num_bullets"
            const val BULLET_TYPE_INCENDIARY = "weapon.tooltip.bullet.bullet_type.incendiary"
            const val BULLET_TYPE_SPECIAL = "weapon.tooltip.bullet.bullet_type.special"
            const val BULLET_TYPE_PIERCING = "weapon.tooltip.bullet.bullet_type.piercing"
            const val BULLET_TYPE_DEFAULT = "weapon.tooltip.bullet.bullet_type.default"
            const val BULLET_TYPE_EXPANDING = "weapon.tooltip.bullet.bullet_type.expanding"
            const val HIGH_PENETRATION = "weapon.tooltip.bullet.high_penetration"
            const val LOW_PENETRATION = "weapon.tooltip.bullet.low_penetration"

            val typesKeys = listOf(
                NUM_BULLETS,
                BULLET_TYPE_INCENDIARY,
                BULLET_TYPE_SPECIAL,
                BULLET_TYPE_PIERCING,
                BULLET_TYPE_DEFAULT,
                BULLET_TYPE_EXPANDING,
                HIGH_PENETRATION,
                LOW_PENETRATION
            )

        }
    }

    object Container {
        object General {
            const val NAME = "item.cont.name"
            const val CATEGORY_CONTAINERS = "core.handbook.category.containers"
            const val INFO_RANK = "core.tooltip.info.rank"
            const val INFO_CATEGORY = "core.tooltip.info.category"
            const val INFO_WEIGHT = "core.tooltip.info.weight"
            const val CLASS_SPACIOUS = "general.container.class.spacious"
            const val CLASS_COMPACT = "general.container.class.compact"
            const val CLASS_BULKY = "general.container.class.bulky"
            const val CLASS_STANDARD = "general.container.class.standard"
            const val BACKPACK_CLASS_LIGHT = "general.backpack.class.light"
            const val BACKPACK_CLASS_MEDIUM = "general.backpack.class.medium"
            const val BACKPACK_CLASS_HEAVY = "general.backpack.class.heavy"
            const val DESCRIPTION = "item.description"

            val generalKeys = listOf(
                NAME,
                CATEGORY_CONTAINERS,
                INFO_RANK,
                INFO_CATEGORY,
                INFO_WEIGHT,
                CLASS_SPACIOUS,
                CLASS_COMPACT,
                CLASS_BULKY,
                CLASS_STANDARD,
                BACKPACK_CLASS_LIGHT,
                BACKPACK_CLASS_MEDIUM,
                BACKPACK_CLASS_HEAVY
            )
        }

        object Stats {
            const val INNER_PROTECTION = "stalker.tooltip.backpack.stat_name.inner_protection"
            const val EFFECTIVENESS = "stalker.tooltip.backpack.stat_name.effectiveness"
            const val SIZE = "stalker.tooltip.backpack.info.size"
            const val MAX_WEIGHT_BONUS = "stalker.artefact_properties.factor.max_weight_bonus"
            const val FROST_ACCUMULATION = "stalker.artefact_properties.factor.frost_accumulation"
            const val BIOLOGICAL_ACCUMULATION = "stalker.artefact_properties.factor.biological_accumulation"
            const val RADIATION_ACCUMULATION = "stalker.artefact_properties.factor.radiation_accumulation"
            const val PSYCHO_ACCUMULATION = "stalker.artefact_properties.factor.psycho_accumulation"
            const val THERMAL_ACCUMULATION = "stalker.artefact_properties.factor.thermal_accumulation"
            const val SPEED_MODIFIER = "stalker.artefact_properties.factor.speed_modifier"

            val statKeys = listOf(
                INNER_PROTECTION,
                EFFECTIVENESS,
                SIZE,
                MAX_WEIGHT_BONUS,
                FROST_ACCUMULATION,
                BIOLOGICAL_ACCUMULATION,
                RADIATION_ACCUMULATION,
                PSYCHO_ACCUMULATION,
                THERMAL_ACCUMULATION,
                SPEED_MODIFIER
            )
        }
    }

    object Drink {
        object General {
            const val NAME = "item.name"
            const val CATEGORY_DRINK = "core.handbook.category.drink"
            const val INFO_BASE_PRICE = "core.tooltip.info.base_price"
            const val INFO_USED_IN_CRAFTS = "core.tooltip.used_in_crafts"
            const val INFO_CATEGORY = "core.tooltip.info.category"
            const val INFO_WEIGHT = "core.tooltip.info.weight"
            const val INFO_RANK = "core.tooltip.info.rank"
            const val INFO_RUBLES = "core.tooltip.info.rubles"

            val generalKeys = listOf(
                NAME,
                CATEGORY_DRINK,
                INFO_BASE_PRICE,
                INFO_USED_IN_CRAFTS,
                INFO_CATEGORY,
                INFO_WEIGHT,
                INFO_RANK,
                INFO_RUBLES
            )
        }

        object Stats {
            const val RADIATION_ACCUMULATION = "stalker.artefact_properties.factor.radiation_accumulation"
            const val STAMINA_REGENERATION_BONUS = "stalker.artefact_properties.factor.stamina_regeneration_bonus"
            const val SPEED_MODIFIER = "stalker.artefact_properties.factor.speed_modifier"
            const val TOXIC_ACCUMULATION = "stalker.artefact_properties.factor.toxic_accumulation"
            const val FILLER_MODIFIER = "stalker.artefact_properties.factor.filler_modifier"
            const val REGENERATION_BONUS = "stalker.artefact_properties.factor.regeneration_bonus"
            const val HEALTH_BONUS = "stalker.artefact_properties.factor.health_bonus"
            const val STAMINA_BONUS = "stalker.artefact_properties.factor.stamina_bonus"
            const val HEAL_EFFICIENCY = "stalker.artefact_properties.factor.heal_efficiency"

            val statKeys = listOf(
                RADIATION_ACCUMULATION,
                STAMINA_REGENERATION_BONUS,
                SPEED_MODIFIER,
                TOXIC_ACCUMULATION,
                FILLER_MODIFIER,
                REGENERATION_BONUS,
                HEALTH_BONUS,
                STAMINA_BONUS,
                HEAL_EFFICIENCY
            )
        }

        object ToolTips {
            const val PERSISTENT = "stalker.tooltip.medicine.info.persistent"
            const val NON_PERSISTENT = "stalker.tooltip.medicine.info.non_persistent"
            const val EFFECT_TYPE = "stalker.tooltip.medicine.info.effect_type"
            const val TOXICITY = "stalker.tooltip.medicine.info.toxicity"
            const val DURATION = "stalker.tooltip.medicine.info.duration"

            val toolTipKeys = listOf(
                PERSISTENT,
                NON_PERSISTENT,
                EFFECT_TYPE,
                TOXICITY,
                DURATION
            )
        }

        object Effect {
            const val LOW_MEDICINE = "item.effects.effect_type.low_medicine"
            const val LOW_ANTIRAD = "item.effects.effect_type.low_antirad"
            const val POWERFUL_MEDICINE = "item.effects.effect_type.powerful_medicine"

            val effectKeys = listOf(
                LOW_MEDICINE,
                LOW_ANTIRAD,
                POWERFUL_MEDICINE
            )
        }
    }

    object Food {
        object General {
            const val NAME = "item.name"
            const val CATEGORY_FOOD = "core.handbook.category.food"
            const val INFO_BASE_PRICE = "core.tooltip.info.base_price"
            const val INFO_USED_IN_CRAFTS = "core.tooltip.used_in_crafts"
            const val INFO_CATEGORY = "core.tooltip.info.category"
            const val INFO_WEIGHT = "core.tooltip.info.weight"
            const val INFO_RANK = "core.tooltip.info.rank"
            const val INFO_RUBLES = "core.tooltip.info.rubles"

            val generalKeys = listOf(
                NAME,
                CATEGORY_FOOD,
                INFO_BASE_PRICE,
                INFO_USED_IN_CRAFTS,
                INFO_CATEGORY,
                INFO_WEIGHT,
                INFO_RANK,
                INFO_RUBLES
            )
        }

        object Stats {
            const val FROST_PROTECTION = "stalker.artefact_properties.factor.frost_protection"
            const val BLEEDING_ACCUMULATION = "stalker.artefact_properties.factor.bleeding_accumulation"
            const val REACTION_TO_TEAR = "stalker.artefact_properties.factor.reaction_to_tear"
            const val MAX_WEIGHT_BONUS = "stalker.artefact_properties.factor.max_weight_bonus"
            const val SPEED_MODIFIER = "stalker.artefact_properties.factor.speed_modifier"
            const val STAMINA_BONUS = "stalker.artefact_properties.factor.stamina_bonus"
            const val STAMINA_REGENERATION_BONUS = "stalker.artefact_properties.factor.stamina_regeneration_bonus"
            const val HEALTH_BONUS = "stalker.artefact_properties.factor.health_bonus"
            const val HEAL_EFFICIENCY = "stalker.artefact_properties.factor.heal_efficiency"
            const val REGENERATION_BONUS = "stalker.artefact_properties.factor.regeneration_bonus"

            val statKeys = listOf(
                FROST_PROTECTION,
                BLEEDING_ACCUMULATION,
                REACTION_TO_TEAR,
                MAX_WEIGHT_BONUS,
                SPEED_MODIFIER,
                STAMINA_BONUS,
                STAMINA_REGENERATION_BONUS,
                HEALTH_BONUS,
                HEAL_EFFICIENCY,
                REGENERATION_BONUS
            )
        }

        object Effect {
            const val LOW_MEDICINE = "item.effects.effect_type.low_medicine"
            const val STAMINA_EFFECT = "item.effects.effect_type.stamina"

            val effectKeys = listOf(
                LOW_MEDICINE,
                STAMINA_EFFECT
            )
        }

        object ToolTips {
            const val TOOLTIP_PERSISTENT = "stalker.tooltip.medicine.info.persistent"
            const val TOOLTIP_NON_PERSISTENT = "stalker.tooltip.medicine.info.non_persistent"
            const val TOOLTIP_DURATION = "stalker.tooltip.medicine.info.duration"
            const val TOOLTIP_EFFECT_TYPE = "stalker.tooltip.medicine.info.effect_type"

            val toolTipsKeys = listOf(
                TOOLTIP_PERSISTENT,
                TOOLTIP_NON_PERSISTENT,
                TOOLTIP_DURATION,
                TOOLTIP_EFFECT_TYPE
            )
        }
    }

    object Grenade {
        object General {
            const val NAME = "item.name"
            const val CATEGORY_GRENADE = "core.handbook.category.grenade"
            const val INFO_USED_IN_CRAFTS = "core.tooltip.used_in_crafts"
            const val INFO_CATEGORY = "core.tooltip.info.category"
            const val INFO_WEIGHT = "core.tooltip.info.weight"
            const val INFO_RANK = "core.tooltip.info.rank"

            val generalKeys = listOf(
                NAME,
                CATEGORY_GRENADE,
                INFO_USED_IN_CRAFTS,
                INFO_CATEGORY,
                INFO_WEIGHT,
                INFO_RANK
            )
        }

        object Stats {
            const val GRENADE_FLASH_LIFETIME = "weapon.grenade.flash.stats.info.lifetime"
            const val GRENADE_FLASH_EXPLOSION_SIZE = "weapon.grenade.flash.stats.info.explosion_size"
            const val GRENADE_FLASH_TIME = "weapon.grenade.flash.stats.info.flash_time"
            const val GRENADE_FRAG_EXPLOSION_STRENGTH_MIN = "weapon.grenade.frag.stats.info.explosion_strength_min"
            const val GRENADE_FRAG_EXPLOSION_STRENGTH = "weapon.grenade.frag.stats.info.explosion_strength"
            const val GRENADE_FRAG_STOPPING_POWER = "weapon.grenade.frag.stats.info.stopping_power"
            const val GRENADE_FRAG_EXPLOSION_SIZE = "weapon.grenade.frag.stats.info.explosion_size"
            const val GRENADE_FRAG_LIFETIME = "weapon.grenade.frag.stats.info.lifetime"
            const val GRENADE_FRAG_EXPLOSION_ACTIVATION_TIME = "weapon.grenade.frag.stats.info.explosion_activation_time"
            const val GRENADE_FRAG_EXPLOSION_ON_COLLIDE = "weapon.grenade.frag.stats.explosion_on_collide"

            val statKeys = listOf(
                GRENADE_FLASH_LIFETIME,
                GRENADE_FLASH_EXPLOSION_SIZE,
                GRENADE_FLASH_TIME,
                GRENADE_FRAG_EXPLOSION_STRENGTH_MIN,
                GRENADE_FRAG_EXPLOSION_STRENGTH,
                GRENADE_FRAG_STOPPING_POWER,
                GRENADE_FRAG_EXPLOSION_SIZE,
                GRENADE_FRAG_LIFETIME,
                GRENADE_FRAG_EXPLOSION_ACTIVATION_TIME,
                GRENADE_FRAG_EXPLOSION_ON_COLLIDE
            )
        }
    }

    object Medicine {
        object General {
            const val CATEGORY_MEDICINE = "core.handbook.category.medicine"
            const val INFO_USED_IN_CRAFTS = "core.tooltip.used_in_crafts"
            const val INFO_RANK = "core.tooltip.info.rank"
            const val INFO_CATEGORY = "core.tooltip.info.category"
            const val INFO_WEIGHT = "core.tooltip.info.weight"
            const val INFO_BASE_PRICE = "core.tooltip.info.base_price"
            const val INFO_DURABILITY = "core.tooltip.info.durability"
            const val INFO_RUBLES = "core.tooltip.info.rubles"

            val generalKeys = listOf(
                CATEGORY_MEDICINE,
                INFO_USED_IN_CRAFTS,
                INFO_RANK,
                INFO_CATEGORY,
                INFO_WEIGHT,
                INFO_BASE_PRICE,
                INFO_DURABILITY,
                INFO_RUBLES
            )
        }

        object Effect {
            const val EFFECT_SHORT_TIME_MEDICINE = "item.effects.effect_type.short_time_medicine"
            const val EFFECT_LOW_MEDICINE = "item.effects.effect_type.low_medicine"
            const val EFFECT_POWERFUL_MEDICINE = "item.effects.effect_type.powerful_medicine"
            const val EFFECT_BANDAGE = "item.effects.effect_type.bandage"
            const val EFFECT_HEALING = "item.effects.effect_type.healing"
            const val EFFECT_STRENGTH = "item.effects.effect_type.strength"
            const val EFFECT_STAMINA = "item.effects.effect_type.stamina"
            const val EFFECT_FIRE_PROTECTION = "item.effects.effect_type.fire_protection"
            const val EFFECT_TEMPERATURE_PROTECTION = "item.effects.effect_type.temperature_protection"
            const val EFFECT_BIO_PROTECTION = "item.effects.effect_type.bio_protection"
            const val EFFECT_PSI_PROTECTION = "item.effects.effect_type.psi_protection"
            const val EFFECT_BLEEDING_PROTECTION = "item.effects.effect_type.bleeding_protection"
            const val EFFECT_ANTIRAD = "item.effects.effect_type.antirad"
            const val EFFECT_PSI = "item.effects.effect_type.psi"

            val effectKeys = listOf(
                EFFECT_SHORT_TIME_MEDICINE,
                EFFECT_LOW_MEDICINE,
                EFFECT_POWERFUL_MEDICINE,
                EFFECT_BANDAGE,
                EFFECT_HEALING,
                EFFECT_STRENGTH,
                EFFECT_STAMINA,
                EFFECT_FIRE_PROTECTION,
                EFFECT_TEMPERATURE_PROTECTION,
                EFFECT_BIO_PROTECTION,
                EFFECT_PSI_PROTECTION,
                EFFECT_BLEEDING_PROTECTION,
                EFFECT_ANTIRAD,
                EFFECT_PSI
            )
        }

        object Stats {
            const val ARTEFACT_BIOLOGICAL_PROTECTION = "stalker.artefact_properties.factor.biological_protection"
            const val ARTEFACT_BIOLOGICAL_DMG_FACTOR = "stalker.artefact_properties.factor.biological_dmg_factor"
            const val ARTEFACT_BIOLOGICAL_ACCUMULATION = "stalker.artefact_properties.factor.biological_accumulation"
            const val ARTEFACT_THERMAL_PROTECTION = "stalker.artefact_properties.factor.thermal_protection"
            const val ARTEFACT_THERMAL_ACCUMULATION = "stalker.artefact_properties.factor.thermal_accumulation"
            const val ARTEFACT_THERMAL_DMG_FACTOR = "stalker.artefact_properties.factor.thermal_dmg_factor"
            const val ARTEFACT_TOXIC_ACCUMULATION = "stalker.artefact_properties.factor.toxic_accumulation"
            const val ARTEFACT_REGENERATION_BONUS = "stalker.artefact_properties.factor.regeneration_bonus"
            const val ARTEFACT_HEAL_EFFICIENCY = "stalker.artefact_properties.factor.heal_efficiency"
            const val ARTEFACT_PSYCHO_PROTECTION = "stalker.artefact_properties.factor.psycho_protection"
            const val ARTEFACT_PSYCHO_ACCUMULATION = "stalker.artefact_properties.factor.psycho_accumulation"
            const val ARTEFACT_PSYCHO_DMG_FACTOR = "stalker.artefact_properties.factor.psycho_dmg_factor"
            const val ARTEFACT_RADIATION_ACCUMULATION = "stalker.artefact_properties.factor.radiation_accumulation"
            const val ARTEFACT_RADIATION_DMG_FACTOR = "stalker.artefact_properties.factor.radiation_dmg_factor"
            const val ARTEFACT_BURN_DMG_FACTOR = "stalker.artefact_properties.factor.burn_dmg_factor"
            const val ARTEFACT_BULLET_DMG_FACTOR = "stalker.artefact_properties.factor.bullet_dmg_factor"
            const val ARTEFACT_FROST_ACCUMULATION = "stalker.artefact_properties.factor.frost_accumulation"
            const val ARTEFACT_FROST_DMG_FACTOR = "stalker.artefact_properties.factor.frost_dmg_factor"
            const val ARTEFACT_BLEEDING_PROTECTION = "stalker.artefact_properties.factor.bleeding_protection"
            const val ARTEFACT_BLEEDING_ACCUMULATION = "stalker.artefact_properties.factor.bleeding_accumulation"
            const val ARTEFACT_SPEED_MODIFIER = "stalker.artefact_properties.factor.speed_modifier"
            const val ARTEFACT_STAMINA_BONUS = "stalker.artefact_properties.factor.stamina_bonus"
            const val ARTEFACT_STAMINA_REGENERATION_BONUS = "stalker.artefact_properties.factor.stamina_regeneration_bonus"
            const val ARTEFACT_MAX_WEIGHT_BONUS = "stalker.artefact_properties.factor.max_weight_bonus"
            const val ARTEFACT_COMBUSTION_ACCUMULATION = "stalker.artefact_properties.factor.combustion_accumulation"
            const val ARTEFACT_STOPPING_PROTECTION = "stalker.artefact_properties.factor.stopping_protection"
            const val ARTEFACT_RECOIL_BONUS = "stalker.artefact_properties.factor.recoil_bonus"
            const val ARTEFACT_REACTION_TO_TEAR = "stalker.artefact_properties.factor.reaction_to_tear"

            val statsKeys = listOf(
                ARTEFACT_BIOLOGICAL_PROTECTION,
                ARTEFACT_BIOLOGICAL_DMG_FACTOR,
                ARTEFACT_BIOLOGICAL_ACCUMULATION,
                ARTEFACT_THERMAL_PROTECTION,
                ARTEFACT_THERMAL_ACCUMULATION,
                ARTEFACT_THERMAL_DMG_FACTOR,
                ARTEFACT_TOXIC_ACCUMULATION,
                ARTEFACT_REGENERATION_BONUS,
                ARTEFACT_HEAL_EFFICIENCY,
                ARTEFACT_PSYCHO_PROTECTION,
                ARTEFACT_PSYCHO_ACCUMULATION,
                ARTEFACT_PSYCHO_DMG_FACTOR,
                ARTEFACT_RADIATION_ACCUMULATION,
                ARTEFACT_RADIATION_DMG_FACTOR,
                ARTEFACT_BURN_DMG_FACTOR,
                ARTEFACT_BULLET_DMG_FACTOR,
                ARTEFACT_FROST_ACCUMULATION,
                ARTEFACT_FROST_DMG_FACTOR,
                ARTEFACT_BLEEDING_PROTECTION,
                ARTEFACT_BLEEDING_ACCUMULATION,
                ARTEFACT_SPEED_MODIFIER,
                ARTEFACT_STAMINA_BONUS,
                ARTEFACT_STAMINA_REGENERATION_BONUS,
                ARTEFACT_MAX_WEIGHT_BONUS,
                ARTEFACT_COMBUSTION_ACCUMULATION,
                ARTEFACT_STOPPING_PROTECTION,
                ARTEFACT_RECOIL_BONUS,
                ARTEFACT_REACTION_TO_TEAR
            )

        }
    }

    object Misc {
        object General {
            const val NAME = "item.name"
            const val RANK = "core.tooltip.info.rank"
            const val CATEGORY = "core.tooltip.info.category"
            const val WEIGHT = "core.tooltip.info.weight"
            const val DURABILITY = "core.tooltip.info.durability"
            const val DESCRIPTION = "item.description"
            const val BASE_PRICE = "core.tooltip.info.base_price"

            val generalKeys = listOf(
                NAME,
                RANK,
                CATEGORY,
                WEIGHT,
                DURABILITY,
                BASE_PRICE
            )
        }

        object ToolTip {
            const val ADDITIONAL_STATS = "item.misc.additional_stats_tip"
            const val USED_IN_CRAFTS = "core.tooltip.used_in_crafts"

            val toolTipStats = listOf(
                ADDITIONAL_STATS,
                USED_IN_CRAFTS
            )
        }
    }

    object Other {
        object General {
            const val NAME = "item.name"
            const val RANK = "core.tooltip.info.rank"
            const val CATEGORY = "core.tooltip.info.category"
            const val WEIGHT = "core.tooltip.info.weight"
            const val DURABILITY = "core.tooltip.info.durability"
            const val DESCRIPTION = "item.description"
            const val BASE_PRICE = "core.tooltip.info.base_price"

            val generalKeys = listOf(
                NAME,
                RANK,
                CATEGORY,
                WEIGHT,
                DURABILITY,
                BASE_PRICE
            )
        }

        object ToolTip {
            const val ADDITIONAL_STATS = "stalker.tooltip.armor_plate.stat_name.damage_absorption"
            const val ADDITIONAL_STATS_1 = "go.hideout_kitchen_kitchen_items.additional_stats_tip"
            const val ADDITIONAL_STATS_2 = "go.hideout_laboratory_chemical_reactor.additional_stats_tip"

            val toolTipStats = listOf(
                ADDITIONAL_STATS,
                ADDITIONAL_STATS_1,
                ADDITIONAL_STATS_2
            )
        }
    }
}