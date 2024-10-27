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