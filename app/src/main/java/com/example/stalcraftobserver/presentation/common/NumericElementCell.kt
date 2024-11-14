package com.example.stalcraftobserver.presentation.common

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
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.Element

@Composable
fun NumericElementCell(
    //element: List<Element.NumericElement>,
    modifier: Modifier = Modifier
){
    var outputData by remember { mutableStateOf("") }

    outputData = "Protection\n" +
            "Radiation: 240\n" +
            "Thermal: 120\n" +
            "Bioinfection: 240\n" +
            "Psy-Emission: 220"

//    for (curElement in element){
//        outputData += "${curElement.name.lines.ru}: ${curElement.value}\n"
//    }

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