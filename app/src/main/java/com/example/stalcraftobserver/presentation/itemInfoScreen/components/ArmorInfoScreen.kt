package com.example.stalcraftobserver.presentation.itemInfoScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.CustomOutlinedCard
import com.example.stalcraftobserver.util.ItemInfoHelper
import com.example.stalcraftobserver.util.ItemProperty
import com.example.stalcraftobserver.util.ItemProperty.Armor.General.generalKeys
import com.example.stalcraftobserver.util.ItemProperty.Armor.ProtectionKeys.protectionKeys
import com.example.stalcraftobserver.util.ItemProperty.Armor.ResistanceKeys.resistanceKeys
import com.example.stalcraftobserver.util.ItemProperty.Armor.StatModifiers.statModifierKeys

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArmorInfoScreen(
    imagePath: String,
    item: ItemInfo,
    modifier: Modifier = Modifier
) {
    val generalParam = remember { mutableStateOf<String>("") }
    val resistanceParam = remember { mutableStateOf<String>("") }
    val protectionParam = remember { mutableStateOf<String>("") }
    val descriptionParam = remember { mutableStateOf<String>("") }
    val statModifiersParam = remember { mutableStateOf<String>("") }
    val compatibilityParam = remember { mutableStateOf<String>("") }
    val armorParam = remember { mutableStateOf<String>("") }

    LaunchedEffect(Unit) {
        generalParam.value =
            ItemInfoHelper.getValuesByKeys(item, generalKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: ""}:  ${lineValue?.ru ?: ""}"
                }.joinToString(separator = ", ")
                formattedValues
            }.joinToString(separator = "\n")

        statModifiersParam.value =
            ItemInfoHelper.getValuesByKeys(item, statModifierKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: "null"}:  ${lineValue?.ru ?: "null"}"
                }.joinToString(separator = ", ")
                formattedValues
            }.joinToString(separator = "\n")

        resistanceParam.value =
            ItemInfoHelper.getValuesByKeys(item, resistanceKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: "null"}:  ${lineValue?.ru ?: "null"}"
                }.joinToString(separator = ", ")
                formattedValues
            }.joinToString(separator = "\n")

        protectionParam.value =
            ItemInfoHelper.getValuesByKeys(item, protectionKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: "null"}:  ${lineValue?.ru ?: "null"}"
                }.joinToString(separator = ", ")
                formattedValues
            }.joinToString(separator = "\n")

        descriptionParam.value =
            ItemInfoHelper.getValuesForKey(item, ItemProperty.Armor.General.DESCRIPTION)
                .map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: "null"}:  ${lineValue?.ru ?: "null"}"
                }.joinToString(separator = ", ")

        compatibilityParam.value =
            ItemInfoHelper.getValuesByKeys(
                item,
                ItemProperty.Armor.CompatibilityKeys.compatibilityKeys
            ).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: "null"}:  ${lineValue?.ru ?: "null"}"
                }.joinToString(separator = ", ")
                formattedValues
            }.joinToString(separator = "\n")

        armorParam.value = ItemInfoHelper.getArmorClassFromItemInfo(item).toString()
    }

    Scaffold {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row {
                    CustomOutlinedCard(
                        data = generalParam.value,
                        modifier = Modifier.weight(1f)
                    )
                    CustomImage(
                        imagePath = imagePath,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.Start
                ) {
                    item {
                        CustomOutlinedCard(
                            data = resistanceParam.value
                        )
                    }
                    item {
                        CustomOutlinedCard(
                            data = protectionParam.value
                        )
                    }
                    item {
                        CustomOutlinedCard(
                            data = compatibilityParam.value
                        )
                    }
                }
            }
            item {
                Row() {
                    CustomOutlinedCard(
                        data = descriptionParam.value
                    )
                }
            }
        }
    }

}