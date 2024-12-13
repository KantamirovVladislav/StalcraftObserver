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
import androidx.compose.ui.layout.ContentScale
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.CustomOutlinedCard
import com.example.stalcraftobserver.util.ItemInfoHelper
import com.example.stalcraftobserver.util.ItemProperty


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtefactInfoScreen(
    imagePath: String,
    item: ItemInfo,
    modifier: Modifier = Modifier
){
    if (!item.category.contains("artefact")){
        return
    }
    val generalParam = remember { mutableStateOf<String>("") }
    val qualityParam = remember { mutableStateOf<String>("") }
    val resistanceParam = remember { mutableStateOf<String>("") }
    val accumulationParam = remember { mutableStateOf<String>("") }
    val statModifierParam = remember { mutableStateOf<String>("") }
    val specialParam = remember { mutableStateOf<String>("") }

    LaunchedEffect(Unit) {
        generalParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Artefact.General.generalKeys)

        qualityParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Artefact.Quality.qualityKeys)

        resistanceParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Artefact.Resistance.resistanceKeys)

        accumulationParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Artefact.Accumulation.accumulationKeys)

        statModifierParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Artefact.StatModifiers.statModifiersKeys)

        specialParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Artefact.Special.specialKeys)
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
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.Start
                ) {
                    item {
                        CustomOutlinedCard(
                            data = "${resistanceParam.value} \n${accumulationParam.value}\n${statModifierParam.value}\n${specialParam.value}"
                        )
                    }

                }
            }
        }
    }
}