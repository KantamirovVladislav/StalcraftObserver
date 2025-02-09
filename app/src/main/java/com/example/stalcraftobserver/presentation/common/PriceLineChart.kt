package com.example.stalcraftobserver.presentation.common

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stalcraftobserver.data.manager.AuctionResponse
import com.example.stalcraftobserver.data.manager.PriceHistoryResponse
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.auto
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisTickComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.BaseAxis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarkerValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.component.TextComponent
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PriceLineChart(
    priceHistory: PriceHistoryResponse?,
    modifier: Modifier = Modifier
) {
    if (priceHistory == null) {
        NullData(modifier = modifier)
    } else if (priceHistory.prices.isEmpty()) {
        NullData(modifier = modifier)
    } else if (priceHistory.total <= 0) {
        NullData(modifier = modifier)
    } else {

        val sortedPrices = remember(priceHistory) {
            priceHistory.prices.sortedBy { it.time }
        }

        val yValues = remember(sortedPrices) {
            sortedPrices.map { it.price.toDouble() }
        }


        val dateFormat = remember { SimpleDateFormat("dd MMM", Locale.getDefault()) }
        val dateFormatMarker = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
        val xLabels = remember(sortedPrices) {
            sortedPrices.map { price ->
                val date = try {
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }.parse(price.time)
                } catch (e: Exception) {
                    "N/A"
                }
                date?.let { dateFormat.format(it) } ?: "N/A"
            }
        }
        val xLabelsMarker = remember(sortedPrices) {
            sortedPrices.map { price ->
                val date = try {
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }.parse(price.time)
                } catch (e: Exception) {
                    "N/A"
                }
                date?.let { dateFormatMarker.format(it) } ?: "N/A"
            }
        }

        val modelProducer = remember { CartesianChartModelProducer() }

        LaunchedEffect(sortedPrices) {
            if (yValues.size == xLabels.size) {
                modelProducer.runTransaction {
                    lineSeries {
                        series(yValues)
                    }
                }
            } else {
                Log.e(
                    "PriceChart",
                    "Data size mismatch: yValues=${yValues.size}, xLabels=${xLabels.size}"
                )
            }
        }

        val cartesianMarker = rememberDefaultCartesianMarker(
            label = TextComponent(
                color = MaterialTheme.colorScheme.secondary.toArgb()
            ),
            valueFormatter = CartesianMarkerValueFormatter { context, targets ->
                if (targets.isNotEmpty()) {
                    val xIndex = targets[0].x.toInt()
                    if (xIndex in yValues.indices) {
                        val price = yValues[xIndex]
                        String.format("${formatDoubleWithDots(price.toDouble())} - ${xLabelsMarker[xIndex]}, Кол-во ${sortedPrices[xIndex].amount}")
                    } else {
                        "N/A"
                    }
                } else {
                    "N/A"
                }
            },
            labelPosition = DefaultCartesianMarker.LabelPosition.Top,
            indicatorSize = 16.dp
        )

        Box(
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize()
                .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    marker = cartesianMarker,
                    startAxis = VerticalAxis.rememberStart(
                        line = rememberAxisLineComponent(),
                        label = rememberAxisLabelComponent(),
                        labelRotationDegrees = 0f,
                        horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Outside,
                        valueFormatter = CartesianValueFormatter { _, value, _ ->
                            if ((value.toInt() / 1000000) > 0) "${value.toInt() / 1000000}M"
                            else if ((value.toInt() / 1000) > 0) "${value.toInt() / 1000}K"
                            else {
                                "${value.toInt()}"
                            }
                        },
                        tick = rememberAxisTickComponent(),
                        tickLength = 8.dp,
                        guideline = rememberAxisGuidelineComponent(),
                        itemPlacer = remember { VerticalAxis.ItemPlacer.step() },
                        size = BaseAxis.Size.auto(),
                        title = "Цена",
                        titleComponent = rememberAxisLabelComponent()
                    ),
                    bottomAxis = HorizontalAxis.rememberBottom(
                        line = rememberAxisLineComponent(),
                        label = rememberAxisLabelComponent(),
                        labelRotationDegrees = 45f,
                        valueFormatter = CartesianValueFormatter { _, value, _ ->
                            val index = value.toInt()
                            xLabels.getOrNull(index) ?: "N/A"
                        },
                        tick = rememberAxisTickComponent(),
                        tickLength = 8.dp,
                        guideline = rememberAxisGuidelineComponent(),
                        itemPlacer = remember { HorizontalAxis.ItemPlacer.aligned() },
                        size = BaseAxis.Size.auto(),
                        title = "Дата",
                        titleComponent = rememberAxisLabelComponent()
                    )
                ),
                modelProducer,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun formatDoubleWithDots(number: Double): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }

    val decimalFormat = DecimalFormat("#,###", symbols)
    return decimalFormat.format(number)
}

@Composable
fun NullData(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .height(200.dp)
            .fillMaxSize()
            .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No data available",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
fun AuctionChart(
    auctionResponse: AuctionResponse?,
    modifier: Modifier = Modifier
) {
    if (auctionResponse == null || auctionResponse.lots.isEmpty()) {
        NullData(modifier = Modifier)
    } else {

        val sortedLots = auctionResponse.lots

        val yValues = remember(sortedLots) {
            sortedLots.map { it.buyoutPrice.toDouble() }
        }


        val dateFormat = remember { SimpleDateFormat("dd MMM", Locale.getDefault()) }
        val dateFormatMarker = remember { SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()) }
        val xLabels = remember(sortedLots) {
            sortedLots.map { lot ->
                val date = try {
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }.parse(lot.startTime)
                } catch (e: Exception) {
                    "N/A"
                }
                date?.let { dateFormat.format(it) } ?: "N/A"
            }
        }
        val xLabelsMarker = remember(sortedLots) {
            sortedLots.map { lot ->
                val date = try {
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }.parse(lot.startTime)
                } catch (e: Exception) {
                    "N/A"
                }
                date?.let { dateFormatMarker.format(it) } ?: "N/A"
            }
        }

        val modelProducer = remember { CartesianChartModelProducer() }

        LaunchedEffect(sortedLots) {
            if (yValues.size == xLabels.size) {
                modelProducer.runTransaction {
                    lineSeries {
                        series(yValues)
                    }
                }
            } else {
                Log.e(
                    "PriceChart",
                    "Data size mismatch: yValues=${yValues.size}, xLabels=${xLabels.size}"
                )
            }
        }

        val cartesianMarker = rememberDefaultCartesianMarker(
            label = TextComponent(
                color = MaterialTheme.colorScheme.secondary.toArgb()
            ),
            valueFormatter = CartesianMarkerValueFormatter { context, targets ->
                if (targets.isNotEmpty()) {
                    val xIndex = targets[0].x.toInt()
                    if (xIndex in yValues.indices) {
                        val price = yValues[xIndex]
                        "${formatDoubleWithDots(price.toDouble())} - ${xLabelsMarker[xIndex]}, Кол-во ${sortedLots[xIndex].amount}"
                    } else {
                        "N/A"
                    }
                } else {
                    "N/A"
                }
            },
            labelPosition = DefaultCartesianMarker.LabelPosition.Top,
            indicatorSize = 16.dp
        )

        Box(
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize()
                .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ){
            CartesianChartHost(
                modifier = Modifier.padding(8.dp),
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    marker = cartesianMarker,
                    startAxis = VerticalAxis.rememberStart(
                        line = rememberAxisLineComponent(),
                        label = rememberAxisLabelComponent(),
                        valueFormatter = CartesianValueFormatter { _, value, _ ->
                            if ((value.toInt() / 1000000) > 0) "${value.toInt() / 1000000}M"
                            else if ((value.toInt() / 1000) > 0) "${value.toInt() / 1000}K"
                            else {
                                "${value.toInt()}"
                            }
                        },
                        tick = rememberAxisTickComponent(),
                        guideline = rememberAxisGuidelineComponent(),
                        title = "Цена",
                        titleComponent = rememberAxisLabelComponent()
                    ),
                    bottomAxis = HorizontalAxis.rememberBottom(
                        line = rememberAxisLineComponent(),
                        label = rememberAxisLabelComponent(),
                        valueFormatter = CartesianValueFormatter { _, value, _ ->
                            val index = value.toInt()
                            xLabels.getOrNull(index) ?: "N/A"
                        },
                        tick = rememberAxisTickComponent(),
                        guideline = rememberAxisGuidelineComponent(),
                        title = "Дата",
                        titleComponent = rememberAxisLabelComponent()
                    )
                ),
                modelProducer = modelProducer
            )
        }
    }
}
