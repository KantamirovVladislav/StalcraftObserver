package com.example.stalcraftobserver.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.stalcraftobserver.domain.model.Item

@Composable
fun ItemCell(modifier: Modifier = Modifier, item: Item, region: String) {
    var imagePath by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(item.id, region) {
        imagePath = item.createImagePath(region)
        Log.d("ItemCell", "Generated image path: $imagePath")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
    ) {
        imagePath?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .crossfade(true)
                    .listener(
                        onError = { _, _ -> isLoading = true },
                        onSuccess = { _, _ -> isLoading = false }
                    )
                    .build(),
                contentDescription = item.titleEng
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
        )
    }
}

@Preview
@Composable
fun previewEpta() {
    ItemCell(item = Item("asdd", "asdsad", "фывфыв", "asddd", "asd"), region = "ru")
}