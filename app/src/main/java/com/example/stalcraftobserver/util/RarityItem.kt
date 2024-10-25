package com.example.stalcraftobserver.util

import com.example.stalcraftobserver.ui.theme.Black
import com.example.stalcraftobserver.ui.theme.RarityCommonColor
import com.example.stalcraftobserver.ui.theme.RarityLegendColor
import com.example.stalcraftobserver.ui.theme.RarityMasterColor
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

fun getRarityColorFromString(rarity: String) = when(rarity){
    RarityItem.None.toString() -> RarityNoneColor
    RarityItem.Common.toString() -> RarityCommonColor
    RarityItem.Picklock.toString() -> RarityPicklockColor
    RarityItem.Stalker.toString() -> RarityStalkerColor
    RarityItem.Veteran.toString() -> RarityVeteranColor
    RarityItem.Master.toString() -> RarityMasterColor
    RarityItem.Legend.toString() -> RarityLegendColor
    else -> Black
}