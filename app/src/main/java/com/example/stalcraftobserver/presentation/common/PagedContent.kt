package com.example.stalcraftobserver.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.launch

@Composable
fun PagedContent(
    pages: List<String>,
    modifier: Modifier = Modifier,
    content: @Composable (String, Modifier) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    val currentPage = pagerState.currentPage
    val totalPages = pages.size

    val showLeftArrow = currentPage > 0
    val showRightArrow = currentPage < totalPages - 1

    val onClickLeft: () -> Unit = {
        coroutineScope.launch {
            if (showLeftArrow) {
                pagerState.animateScrollToPage(currentPage - 1)
            }
        }
    }

    val onClickRight: () -> Unit = {
        coroutineScope.launch {
            if (showRightArrow) {
                pagerState.animateScrollToPage(currentPage + 1)
            }
        }
    }

    Column {
        Box(
            modifier = modifier.fillMaxWidth().fillMaxHeight(),
            contentAlignment = Alignment.TopStart
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) { page ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (showLeftArrow) {
                        IconButton(
                            onClick = onClickLeft,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ChevronLeft,
                                contentDescription = "Previous Page",
                                modifier = Modifier.scale(1.5f)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                    ) {
                        content(
                            pages[page],
                            Modifier.fillMaxWidth()
                        )
                    }

                    if (showRightArrow) {
                        IconButton(
                            onClick = onClickRight,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = "Next Page",
                                modifier = Modifier.scale(1.5f)
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PagesIndicatorRounder(
                modifier = Modifier.align(Alignment.CenterVertically),
                pageSize = totalPages,
                selectedPage = currentPage
            )
        }
    }
}