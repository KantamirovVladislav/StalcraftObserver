package com.example.stalcraftobserver.presentation.itemsListing.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.stalcraftobserver.domain.model.Item
import com.example.stalcraftobserver.presentation.common.CustomImage

@Composable
fun ItemCell(modifier: Modifier = Modifier, item: Item, region: String) {
    var imagePath by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(item.id, region) {
        imagePath = item.createImagePath(region)
    }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier,

        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            )
        ) {
            imagePath?.let { CustomImage(imagePath = it,modifier = Modifier.weight(1.5f) ) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ){
                Text(
                    textAlign = TextAlign.Center,
                    text = item.titleRus,
                    modifier = Modifier
                        .align(Alignment.Center),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}