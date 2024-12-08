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
    Legend
}

object RarityItemHelper {
    fun getAllRarities(): List<String> {
        return RarityItem.values().map { it.name }
    }
}

object CategoryItemHelper {
    fun getAllCategories(): List<String> {
        return listOf(
            CategoryItem.armorClothes,
            CategoryItem.armorCombat,
            CategoryItem.armorCombined,
            CategoryItem.armorDevice,
            CategoryItem.armorScientist,
            CategoryItem.artefactBiochemical,
            CategoryItem.artefactElectrophysical,
            CategoryItem.artefactGravity,
            CategoryItem.artefactOther_arts,
            CategoryItem.artefactThermal,
            CategoryItem.attachmentBarrel,
            CategoryItem.attachmentCollimator_sights,
            CategoryItem.attachmentForend,
            CategoryItem.attachmentMag,
            CategoryItem.attachmentOther,
            CategoryItem.attachmentPistol_handle,
            CategoryItem.backpack,
            CategoryItem.bullet,
            CategoryItem.containers,
            CategoryItem.drink,
            CategoryItem.food,
            CategoryItem.grenade,
            CategoryItem.medicine,
            CategoryItem.misc,
            CategoryItem.other,
            CategoryItem.weaponAssault_rifle,
            CategoryItem.weaponDevice,
            CategoryItem.weaponHeavy,
            CategoryItem.weaponMachine_gun,
            CategoryItem.weaponMelee,
            CategoryItem.weaponPistol,
            CategoryItem.weaponShotgun_rifle,
            CategoryItem.weaponSniper_rifle,
            CategoryItem.weaponSubmachine_gun
        )
    }
}

object CategoryItem{
    val armorClothes = "armor/clothes"
    val armorCombat = "armor/combat"
    val armorCombined = "armor/combined"
    val armorDevice = "armor/device"
    val armorScientist = "armor/scientist"
    val artefactBiochemical = "artefact/biochemical"
    val artefactElectrophysical = "artefact/electrophysical"
    val artefactGravity = "artefact/gravity"
    val artefactOther_arts = "artefact/other_arts"
    val artefactThermal = "artefact/thermal"
    val attachmentBarrel = "attachment/barrel"
    val attachmentCollimator_sights = "attachment/collimator_sights"
    val attachmentForend = "attachment/forend"
    val attachmentMag = "attachment/mag"
    val attachmentOther = "attachment/other"
    val attachmentPistol_handle = "attachment/pistol_handle"
    val backpack = "backpack"
    val bullet = "bullet"
    val containers = "containers"
    val drink = "drink"
    val food = "food"
    val grenade = "grenade"
    val medicine = "medicine"
    val misc = "misc"
    val other = "other"
    val weaponAssault_rifle = "weapon/assault_rifle"
    val weaponDevice = "weapon/device"
    val weaponHeavy = "weapon/heavy"
    val weaponMachine_gun = "weapon/machine_gun"
    val weaponMelee = "weapon/melee"
    val weaponPistol = "weapon/pistol"
    val weaponShotgun_rifle = "weapon/shotgun_rifle"
    val weaponSniper_rifle = "weapon/sniper_rifle"
    val weaponSubmachine_gun = "weapon/submachine_gun"
}

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