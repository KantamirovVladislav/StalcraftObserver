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
fun AttachmentInfoScreen(
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
        generalParam.value =
            ItemInfoHelper.getValuesByKeys(item, ItemProperty.Artefact.General.generalKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: ""}:  ${lineValue?.ru ?: ""}"
                }
                formattedValues.filter { it.isNotEmpty() }.joinToString(separator = ", ")
            }
                // Фильтруем пустые строки перед добавлением в финальную строку
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")

        qualityParam.value =
            ItemInfoHelper.getValuesByKeys(item, ItemProperty.Artefact.Quality.qualityKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: ""}:  ${lineValue?.ru ?: ""}"
                }
                formattedValues.filter { it.isNotEmpty() }.joinToString(separator = ", ")
            }
                // Фильтруем пустые строки перед добавлением в финальную строку
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")

        resistanceParam.value =
            ItemInfoHelper.getValuesByKeys(item, ItemProperty.Artefact.Resistance.resistanceKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: ""}:  ${lineValue?.ru ?: ""}"
                }
                formattedValues.filter { it.isNotEmpty() }.joinToString(separator = ", ")
            }
                // Фильтруем пустые строки перед добавлением в финальную строку
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")

        accumulationParam.value =
            ItemInfoHelper.getValuesByKeys(item, ItemProperty.Artefact.Accumulation.accumulationKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: ""}:  ${lineValue?.ru ?: ""}"
                }
                formattedValues.filter { it.isNotEmpty() }.joinToString(separator = ", ")
            }
                // Фильтруем пустые строки перед добавлением в финальную строку
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")

        statModifierParam.value =
            ItemInfoHelper.getValuesByKeys(item, ItemProperty.Artefact.StatModifiers.statModifiersKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: ""}:  ${lineValue?.ru ?: ""}"
                }
                formattedValues.filter { it.isNotEmpty() }.joinToString(separator = ", ")
            }
                // Фильтруем пустые строки перед добавлением в финальную строку
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")

        specialParam.value =
            ItemInfoHelper.getValuesByKeys(item, ItemProperty.Artefact.Special.specialKeys).map { (key, valueMap) ->
                val formattedValues = valueMap.map { (lineKey, lineValue) ->
                    "${lineKey?.ru ?: ""}:  ${lineValue?.ru ?: ""}"
                }
                formattedValues.filter { it.isNotEmpty() }.joinToString(separator = ", ")
            }
                // Фильтруем пустые строки перед добавлением в финальную строку
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")
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