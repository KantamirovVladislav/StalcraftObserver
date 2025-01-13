package com.example.stalcraftobserver.presentation.itemInfoScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.CustomOutlinedCard
import com.example.stalcraftobserver.util.ItemInfoHelper
import com.example.stalcraftobserver.util.ItemProperty

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OtherInfoScreen(
    imagePath: String,
    item: ItemInfo,
    modifier: Modifier = Modifier,
    priceChart: @Composable () -> Unit
){
    if (!item.category.contains("other")){
        return
    }
    val generalParam = remember { mutableStateOf<String>("") }
    val toolTipParam = remember { mutableStateOf<String>("") }

    LaunchedEffect(Unit) {
        generalParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Other.General.generalKeys)

        toolTipParam.value = ItemInfoHelper.getStringFromKeys(item, ItemProperty.Other.ToolTip.toolTipStats)
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
                            .scale(3f),
                        contentScale = ContentScale.None
                    )
                }
            }
            if (toolTipParam.value.isNotBlank()){
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            CustomOutlinedCard(
                                modifier = Modifier.fillMaxWidth(),
                                data = "${toolTipParam.value} "
                            )
                        }

                    }
                }
            }

            item{
                priceChart()
            }
        }
    }
}