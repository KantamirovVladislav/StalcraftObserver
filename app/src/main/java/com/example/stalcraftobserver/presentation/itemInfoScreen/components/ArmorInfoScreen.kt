package com.example.stalcraftobserver.presentation.itemInfoScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.CustomOutlinedCard
import com.example.stalcraftobserver.presentation.common.PagedContent
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
        generalParam.value = ItemInfoHelper.getStringFromKeys(item, generalKeys)

        statModifiersParam.value = ItemInfoHelper.getStringFromKeys(item, statModifierKeys)

        resistanceParam.value = ItemInfoHelper.getStringFromKeys(item, resistanceKeys)

        protectionParam.value = ItemInfoHelper.getStringFromKeys(item, protectionKeys)

        descriptionParam.value =
            ItemInfoHelper.getStringFromKey(item, ItemProperty.Armor.General.DESCRIPTION)

        compatibilityParam.value = ItemInfoHelper.getStringFromKeys(
            item,
            ItemProperty.Armor.CompatibilityKeys.compatibilityKeys
        )

        armorParam.value = ItemInfoHelper.getArmorClassFromItemInfo(item).toString()
    }

    val pages = listOf(resistanceParam.value, protectionParam.value, compatibilityParam.value)

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
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            item {
                PagedContent(
                    pages = pages,
                    modifier = Modifier.fillParentMaxWidth(),
                ) { pageData, contentModifier ->
                    CustomOutlinedCard(
                        data = pageData,
                        modifier = contentModifier
                    )
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