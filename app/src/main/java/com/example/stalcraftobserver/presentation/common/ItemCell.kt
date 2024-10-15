package com.example.stalcraftobserver.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.util.RarityItem

@Composable
fun ItemCell(modifier: Modifier = Modifier, item: Item, region: String) {
    var imagePath by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(true) }

    val minHeight = (LocalConfiguration.current.screenHeightDp / 4) + 10
    LaunchedEffect(item.id, region) {
        imagePath = item.createImagePath(region)
        Log.d("ItemCell", "Generated image path: $imagePath")
    }

    val backGroundRarity = when(item.rarity){
        RarityItem.None.toString() -> Color(0xFFCECBCB)
        RarityItem.Common.toString() -> Color(0xFF808080)
        RarityItem.Picklock.toString() -> Color(0xFF3EB23F)
        RarityItem.Stalker.toString() -> Color(0xFF3677A2)
        RarityItem.Veteran.toString() -> Color(0xFF682583)
        RarityItem.Master.toString() -> Color(0xFFC43232)
        RarityItem.Legend.toString() -> Color(0xFFFFEB3B)
        else -> Color(0xFF232323)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.then(
            Modifier
                .shadow(1.dp, RoundedCornerShape(12.dp), spotColor = backGroundRarity, clip = true)
                .fillMaxWidth()
                .padding(5.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                .height(minHeight.dp)

        )

    ) {
        imagePath?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .crossfade(true)
                    .listener(
                        onError = { _, err ->
                            isLoading = true
                            Log.d("ImageError", err.toString())
                        },
                        onSuccess = { _, _ -> isLoading = false }
                    )
                    .build(),
                contentDescription = item.titleEng,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }

        Text(
            textAlign = TextAlign.Center,
            text = item.titleEng,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .weight(1f)
        )
    }
}