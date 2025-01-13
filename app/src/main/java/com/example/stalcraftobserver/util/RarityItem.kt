package com.example.stalcraftobserver.util

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.example.stalcraftobserver.ui.theme.Black
import com.example.stalcraftobserver.ui.theme.RarityCommonColor
import com.example.stalcraftobserver.ui.theme.RarityLegendColor
import com.example.stalcraftobserver.ui.theme.RarityMasterColor
import com.example.stalcraftobserver.ui.theme.RarityNewbieColor
import com.example.stalcraftobserver.ui.theme.RarityNoneColor
import com.example.stalcraftobserver.ui.theme.RarityPicklockColor
import com.example.stalcraftobserver.ui.theme.RarityStalkerColor
import com.example.stalcraftobserver.ui.theme.RarityVeteranColor

enum class RarityItem {
    None,
    Common,
    Picklock,
    Newbie,
    Stalker,
    Veteran,
    Master,
    Legend;

    companion object {
        fun getRank(rarity: RarityItem): Int {
            return when (rarity) {
                None -> 0
                Common -> 1
                Picklock -> 2
                Newbie -> 3
                Stalker -> 4
                Veteran -> 5
                Master -> 6
                Legend -> 7
            }
        }
    }
}

object RarityItemHelper {
    fun getAllRarities(): List<String> {
        return RarityItem.values().map { it.name }
    }
}

//object CategoryItemHelper {
//    fun getAllCategories(): List<String> {
//        return listOf(
//            CategoryItem.armorClothes,
//            CategoryItem.armorCombat,
//            CategoryItem.armorCombined,
//            CategoryItem.armorDevice,
//            CategoryItem.armorScientist,
//            CategoryItem.artefactBiochemical,
//            CategoryItem.artefactElectrophysical,
//            CategoryItem.artefactGravity,
//            CategoryItem.artefactOther_arts,
//            CategoryItem.artefactThermal,
//            CategoryItem.attachmentBarrel,
//            CategoryItem.attachmentCollimator_sights,
//            CategoryItem.attachmentForend,
//            CategoryItem.attachmentMag,
//            CategoryItem.attachmentOther,
//            CategoryItem.attachmentPistol_handle,
//            CategoryItem.backpack,
//            CategoryItem.bullet,
//            CategoryItem.containers,
//            CategoryItem.drink,
//            CategoryItem.food,
//            CategoryItem.grenade,
//            CategoryItem.medicine,
//            CategoryItem.misc,
//            CategoryItem.other,
//            CategoryItem.weaponAssault_rifle,
//            CategoryItem.weaponDevice,
//            CategoryItem.weaponHeavy,
//            CategoryItem.weaponMachine_gun,
//            CategoryItem.weaponMelee,
//            CategoryItem.weaponPistol,
//            CategoryItem.weaponShotgun_rifle,
//            CategoryItem.weaponSniper_rifle,
//            CategoryItem.weaponSubmachine_gun
//        )
//    }
//}

val categoryMap = mapOf(
    "armor" to mapOf(
        "clothes" to "armor/clothes",
        "combat" to "armor/combat",
        "combined" to "armor/combined",
        "device" to "armor/device",
        "scientist" to "armor/scientist"
    ),
    "artefact" to mapOf(
        "biochemical" to "artefact/biochemical",
        "electrophysical" to "artefact/electrophysical",
        "gravity" to "artefact/gravity",
        "other_arts" to "artefact/other_arts",
        "thermal" to "artefact/thermal"
    ),
    "attachment" to mapOf(
        "barrel" to "attachment/barrel",
        "collimator_sights" to "attachment/collimator_sights",
        "forend" to "attachment/forend",
        "mag" to "attachment/mag",
        "other" to "attachment/other",
        "pistol_handle" to "attachment/pistol_handle"
    ),
    "weapon" to mapOf(
        "assault_rifle" to "weapon/assault_rifle",
        "device" to "weapon/device",
        "heavy" to "weapon/heavy",
        "machine_gun" to "weapon/machine_gun",
        "melee" to "weapon/melee",
        "pistol" to "weapon/pistol",
        "shotgun_rifle" to "weapon/shotgun_rifle",
        "sniper_rifle" to "weapon/sniper_rifle",
        "submachine_gun" to "weapon/submachine_gun"
    ),
    "backpack" to null,
    "bullet" to null,
    "containers" to null,
    "drink" to null,
    "food" to null,
    "grenade" to null,
    "medicine" to null,
    "misc" to null,
    "other" to null
)

fun getRarityColorFromString(rarity: String) = when(rarity.toLowerCase(Locale.current)){
    RarityItem.None.toString().toLowerCase(Locale.current) -> RarityNoneColor
    RarityItem.Common.toString().toLowerCase(Locale.current) -> RarityCommonColor
    RarityItem.Newbie.toString().toLowerCase(Locale.current) -> RarityNewbieColor
    RarityItem.Picklock.toString().toLowerCase(Locale.current) -> RarityPicklockColor
    RarityItem.Stalker.toString().toLowerCase(Locale.current) -> RarityStalkerColor
    RarityItem.Veteran.toString().toLowerCase(Locale.current) -> RarityVeteranColor
    RarityItem.Master.toString().toLowerCase(Locale.current) -> RarityMasterColor
    RarityItem.Legend.toString().toLowerCase(Locale.current) -> RarityLegendColor
    else -> Black
}