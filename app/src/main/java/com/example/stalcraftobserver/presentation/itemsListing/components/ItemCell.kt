package com.example.stalcraftobserver.presentation.itemsListing.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.util.RarityItem
import com.example.stalcraftobserver.util.getRarityColorFromString

@Composable
fun ItemCell(modifier: Modifier = Modifier, item: Item, region: String) {
    var imagePath by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(item.id, region) {
        imagePath = item.createImagePath(region)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .shadow(1.dp, RoundedCornerShape(12.dp), spotColor = getRarityColorFromString(item.rarity), clip = true)
                .padding(10.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(12.dp))

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
                            Log.d("ImageError", err.throwable.message.toString())
                        },
                        onSuccess = { _, _ -> isLoading = false }
                    )
                    .build(),
                contentDescription = item.titleEng,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .weight(1.5f)
            )
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Text(
            textAlign = TextAlign.Center,
            text = item.titleRus,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .weight(1f)
        )
    }
}