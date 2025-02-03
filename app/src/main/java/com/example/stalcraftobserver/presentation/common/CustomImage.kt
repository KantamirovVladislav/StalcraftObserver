package com.example.stalcraftobserver.presentation.common

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomImage(
    imagePath: String,
    imageDescription: String = "description not initialize",
    contentScale: ContentScale = ContentScale.None,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isErrorLoading by remember { mutableStateOf(false) }

    imagePath.let {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(it)
                .crossfade(true)
                .listener(
                    onError = { _, err ->
                        isLoading = false
                        isErrorLoading = true
                        Log.d("ImageError", err.toString())
                    },
                    onSuccess = { _, _ -> isLoading = false }
                )
                .build(),
            contentDescription = imageDescription,
            contentScale = contentScale,
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)


        )
        if (isLoading) {
            TripleOrbitLoadingAnimation(
            )
        } else if (isErrorLoading) {

            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info Icon",
                tint = Color.Red,
                modifier = modifier
                    .scale(0.8f)
                    .padding(8.dp)
                    .clickable {

                    }
            )

        }
    }
}