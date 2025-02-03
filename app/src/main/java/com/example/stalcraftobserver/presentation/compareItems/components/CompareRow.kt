package com.example.stalcraftobserver.presentation.compareItems.components

import android.graphics.BlurMaskFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

object CompareConfig {
    val compareRules = mapOf(
        // Общие атрибуты (General)
        "Название" to true,        // Для строк формально не применится, пусть будет true по умолчанию
        "Ранг" to true,            // Аналогично
        "Категория" to true,       // Аналогично
        "Вес" to false,            // Меньше вес = лучше
        "Прочность" to true,       // Больше прочность = лучше
        "Тип боеприпаса" to true,  // Для строки
        "Magazine capacity" to true,  // Больше вместимость магазина = лучше

        // Урон (Damage)
        "Урон" to true,                // Больше урон = лучше
        "Damage decrease start" to true, // Чем дальше начинается падение урона — тем лучше
        "End damage" to true,          // Больше конечный урон = лучше
        "Damage decrease end" to true, // Чем дальше заканчивается падение урона — тем лучше
        "Max distance" to true,        // Больше максимальная дистанция = лучше

        // Другие характеристики (Other)
        "Rate of fire" to true,        // Выше темп стрельбы = лучше
        "Reload" to false,             // Меньше время перезарядки = лучше
        "Tactical reload" to false,    // Меньше время тактической перезарядки = лучше
        "Ergonomics" to true,          // Выше эргономика = лучше
        "Spread" to false,             // Меньше разброс = лучше
        "Hip fire spread" to false,    // Меньше разброс от бедра = лучше
        "Vertical recoil" to false,    // Меньше вертикальная отдача = лучше
        "Horizontal recoil" to false,  // Меньше горизонтальная отдача = лучше
        "Draw time" to false,          // Меньше время вскидывания = лучше
        "Aiming time" to false         // Меньше время прицеливания = лучше
    )
}

@Composable
fun <T> CompareRow(attribute: String, value1: T?, value2: T?) {
    val biggerIsBetter = CompareConfig.compareRules[attribute] ?: true

    val (color1, color2, sign) = if (value1 is Number && value2 is Number) {
        val v1 = value1.toDouble()
        val v2 = value2.toDouble()

        if (biggerIsBetter) {
            when {
                v1 < v2 -> Triple(Color.Red, Color.Green, "<")
                v1 > v2 -> Triple(Color.Green, Color.Red, ">")
                else -> Triple(Color.Unspecified, Color.Unspecified, "=")
            }
        } else {
            when {
                v1 < v2 -> Triple(Color.Green, Color.Red, "<")
                v1 > v2 -> Triple(Color.Red, Color.Green, ">")
                else -> Triple(Color.Unspecified, Color.Unspecified, "=")
            }
        }
    } else {
        Triple(Color.Unspecified, Color.Unspecified, "")
    }

    var cardHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box {
        Row(
            modifier = Modifier
                .zIndex(1f)
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .height(cardHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .drawBehind {
                        val shadowPadding = 0.dp.toPx()
                        val shadowColor = color1.copy(alpha = 0.3f)
                        val cornerRadius = 12.dp.toPx()

                        val paint = android.graphics
                            .Paint()
                            .apply {
                                color = shadowColor.toArgb()
                                maskFilter =
                                    BlurMaskFilter(
                                        12f,
                                        BlurMaskFilter.Blur.NORMAL
                                    )
                            }

                        drawContext.canvas.nativeCanvas.apply {
                            save()
                            translate(-shadowPadding / 2, -shadowPadding / 2)
                            drawRoundRect(
                                0f,
                                0f,
                                size.width + shadowPadding,
                                size.height + shadowPadding,
                                cornerRadius,
                                cornerRadius,
                                paint
                            )
                            restore()
                        }
                    }

            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .drawBehind {
                        val shadowPadding = 0.dp.toPx()
                        val shadowColor = color2.copy(alpha = 0.3f)
                        val cornerRadius = 12.dp.toPx()

                        val paint = android.graphics
                            .Paint()
                            .apply {
                                color = shadowColor.toArgb()
                                maskFilter =
                                    BlurMaskFilter(
                                        12f,
                                        BlurMaskFilter.Blur.NORMAL
                                    )
                            }

                        drawContext.canvas.nativeCanvas.apply {
                            save()
                            translate(-shadowPadding / 2, -shadowPadding / 2)
                            drawRoundRect(
                                0f,
                                0f,
                                size.width + shadowPadding,
                                size.height + shadowPadding,
                                cornerRadius,
                                cornerRadius,
                                paint
                            )
                            restore()
                        }
                    }
            )
        }

        Card(
            modifier = Modifier
                .zIndex(2f)
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    cardHeight = with(density) {
                        layoutCoordinates.boundsInParent().height.toDp()
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()

                    .padding(horizontal = 4.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${value1 ?: "-"}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = color1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = "$attribute\n$sign",
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )


                // Значение item2
                Text(
                    text = "${value2 ?: "-"}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = color2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}