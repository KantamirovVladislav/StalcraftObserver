package com.example.stalcraftobserver.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@Composable
fun PagesIndicatorLine(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int
) {
    // Проверяем корректность pageSize
    if (pageSize <= 0) {
        // Можно отобразить пустой индикатор или бросить исключение
        // Здесь просто выходим из функции
        return
    }

    // Убедимся, что selectedPage находится в допустимом диапазоне
    val currentPage = selectedPage.coerceIn(0, pageSize - 1)

    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)

    // Вычисляем долю линии, которая будет подсвечена
    val pageFraction = 1f / pageSize

    // Вычисляем целевую стартовую позицию подсвеченной части в долях от общей ширины
    val targetStartFraction = when (pageSize) {
        1 -> 0.5f - (pageFraction / 2) // Центрируем, если только одна страница
        2 -> if (currentPage == 0) 0f else 1f - pageFraction // Для двух страниц: 0 или 0.5
        else -> {
            // Для большего количества страниц распределяем равномерно
            (currentPage.toFloat() / (pageSize - 1)) - (pageFraction / 2)
        }
    }.coerceIn(0f, 1f - pageFraction) // Ограничиваем значения от 0 до максимально возможного

    // Анимируем изменения позиции и размера подсвеченной части
    val animatedStartFraction by animateFloatAsState(targetValue = targetStartFraction)
    val animatedPageFraction by animateFloatAsState(targetValue = pageFraction)

    Canvas(modifier = modifier
        .height(8.dp)
        .fillMaxWidth()
    ) {
        // Рисуем фоновую линию
        drawRoundRect(
            color = unselectedColor,
            size = size,
            cornerRadius = CornerRadius(4.dp.toPx())
        )

        // Вычисляем ширину и стартовую позицию подсвеченной части
        val selectedWidth = size.width * animatedPageFraction
        val startX = size.width * animatedStartFraction

        // Рисуем подсвеченную часть линии
        drawRoundRect(
            color = selectedColor,
            topLeft = Offset(startX, 0f),
            size = Size(selectedWidth, size.height),
            cornerRadius = CornerRadius(4.dp.toPx())
        )
    }
}