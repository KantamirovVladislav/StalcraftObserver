package com.example.stalcraftobserver.presentation.itemsListing.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.domain.model.entities.Item
import com.example.stalcraftobserver.presentation.common.CustomImage

@Composable
fun ItemCell(
    modifier: Modifier = Modifier,
    item: Item,
    region: String,
    shadowColor: Color = Color.Gray,
    onHeartClick: (Boolean) -> Unit,
    isHearted: Boolean = false
) {
    var imagePath by remember { mutableStateOf<String?>(null) }

    var hearted by remember { mutableStateOf(false) }
    hearted = isHearted

    LaunchedEffect(item.id, region) {
        imagePath = item.createImagePath(region)
    }

    OutlinedCard(
        modifier = modifier.shadow(
            elevation = 4.dp,
            shape = RoundedCornerShape(12.dp),
            clip = false,
            ambientColor = shadowColor.copy(alpha = 1f),
            spotColor = shadowColor.copy(alpha = 1f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                imagePath?.let {
                    CustomImage(
                        imagePath = it,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        contentScale = ContentScale.Fit
                    )
                }

                Icon(
                    imageVector = if (hearted) Icons.Filled.Star else Icons.Filled.StarOutline,
                    contentDescription = "Favorite Icon",
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .scale(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            hearted = !hearted
                            onHeartClick(hearted)
                        }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = item.titleRus.trim(),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}