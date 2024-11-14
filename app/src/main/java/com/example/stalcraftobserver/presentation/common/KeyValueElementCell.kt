package com.example.stalcraftobserver.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.Element
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme

@Composable
fun KeyValueElementCell(
    //element: List<Element.KeyValueElement>,
    modifier: Modifier = Modifier
){
    var outputData by remember { mutableStateOf("") }

//    for (curElement in element){
//        outputData += "${curElement.key.lines.ru}: ${curElement.value.lines.ru}\n"
//    }
    outputData = "Name: CD-4 Armored Suit\n" +
            "Rank: Master\n" +
            "Class: Combat\n" +
            "Weight: 16 kg\n" +
            "Stamina: +20%\n" +
            "Carry weight: +30\n" +
            "Bullet resistance: 217\n" +
            "Laceration protection: 232\n" +
            "Explosion protection: 170\n"

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier.then(Modifier.padding(10.dp)),

        ) {
        Column(
            modifier = modifier.then(Modifier.padding(10.dp))
        ) {
            Text(
                text = outputData,
                //style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(device = "spec:width=411dp,height=891dp,dpi=420") // Pixel 4
@Preview(device = "spec:width=360dp,height=740dp,dpi=320") // Nexus 5
@Preview(device = "spec:width=320dp,height=480dp,dpi=160") // Small Phone
@Preview(device = "spec:width=600dp,height=1024dp,dpi=240") // 7-inch Tablet
@Preview(device = "spec:width=800dp,height=1280dp,dpi=240") // 10-inch Tablet
@Preview(device = "spec:width=1024dp,height=1366dp,dpi=264") // iPad Pro 10.5
@Preview(device = "spec:width=1440dp,height=2560dp,dpi=560") // Pixel XL
@Preview(device = "spec:width=1080dp,height=1920dp,dpi=480") // Full HD Phone
@Preview(device = "spec:width=1440dp,height=2960dp,dpi=560") // Galaxy S8
@Preview(device = "spec:width=768dp,height=1024dp,dpi=160") // Nexus 7
@Composable
fun GreetingPreviewKeyValueElement() {
    StalcraftObserverTheme {
        KeyValueElementCell()
    }
}