package com.example.stalcraftobserver.presentation.itemInfoScreen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.presentation.common.CustomImage
import com.example.stalcraftobserver.presentation.common.CustomOutlinedCard

@Composable
fun ArmorInfoScreen(
    imagePath: String,
    item: ItemInfo,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row {
                CustomOutlinedCard(
                    data = "Name: CD-4 Armored Sui\n"+
                            "Rank: Master\n" +
                            "Class: Combat\n" +
                            "Weight: 16 kg\n" +
                            "Stamina: +20%\n"+
                            "Carry weight: +30\n" +
                            "Bullet resistance: 217\n"+
                            "Laceration protection: 232\n"+
                            "Explosion protection: 170\n",
                    modifier = Modifier.weight(1f)
                )
                CustomImage(
                    imagePath = imagePath,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
            }
        }
        item{
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                CustomOutlinedCard(
                    data = "Protection\n" +
                            "Radiation: 240\n" +
                            "Thermal: 120\n" +
                            "Bioinfection: 240\n" +
                            "Psy-Emission: 220"
                )
                CustomOutlinedCard(
                    data = "Resistance\n" +
                            "electricity: 150\n" +
                            "fire: 150\n" +
                            "chemicals: 150\n"
                )
            }
        }
        item{
            Row(){
                CustomOutlinedCard(
                    data = "Description:\n" +
                            "Combat armor worn by the Horizon clan. The developers based this suit on the SKAT series during production, and, by and large, they were successful. Despite a few minor differences, the cost of this armored suit is notably lower."
                )
            }
        }
    }
}