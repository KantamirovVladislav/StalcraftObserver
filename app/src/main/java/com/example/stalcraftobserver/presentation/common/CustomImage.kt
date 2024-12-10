package com.example.stalcraftobserver.presentation.common

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun CustomImage(
    imagePath: String,
    imageDescription: String = "description not initialize",
    contentScale: ContentScale = ContentScale.None,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }

    imagePath.let {
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
            contentDescription = imageDescription,
            contentScale = contentScale,
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
            )
        }
    }
}