package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stalcraftobserver.data.manager.AuctionResponse
import com.example.stalcraftobserver.data.manager.PriceHistoryResponse

@Composable
fun LotsPricesStates(
    modifier: Modifier = Modifier,
    isPriceHistory: Boolean = true,
    priceHistoryResponse: PriceHistoryResponse?,
    activeLotsResponse: AuctionResponse?){

    if (isPriceHistory){
        PriceLineChart(
            modifier = modifier.fillMaxWidth(),
            priceHistory = priceHistoryResponse
        )
    }
    else {
        AuctionChart(
            modifier = modifier.fillMaxWidth(),
            auctionResponse = activeLotsResponse
        )
    }

}