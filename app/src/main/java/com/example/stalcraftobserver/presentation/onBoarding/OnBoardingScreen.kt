package com.example.stalcraftobserver.presentation.onBoarding

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stalcraftobserver.domain.viewModel.OnBoardingViewModel
import com.example.stalcraftobserver.presentation.common.ContinueButton
import com.example.stalcraftobserver.presentation.common.ContinueTextButton
import com.example.stalcraftobserver.presentation.onBoarding.components.OnBoardingPage
import com.example.stalcraftobserver.presentation.onBoarding.components.PagesIndicator
import com.example.stalcraftobserver.util.NavigationItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnBoardingViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val pageState = rememberPagerState(initialPage = 0) {
        viewModel.pages.size
    }

    val currentPage by viewModel.currentPage.collectAsState()
    val buttonState by viewModel.buttonState.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(pageState.currentPage) {
        viewModel.onPageChanged(pageState.currentPage)
    }
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            HorizontalPager(state = pageState) { index ->
                OnBoardingPage(page = viewModel.pages[index])
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PagesIndicator(
                    modifier = Modifier.width(70.dp),
                    pageSize = viewModel.pages.size,
                    selectedPage = currentPage
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (buttonState[0].isNotEmpty()) {
                        ContinueTextButton(
                            text = buttonState[0],
                            onClick = {
                                if (currentPage > 0) {
                                    scope.launch {
                                        pageState.animateScrollToPage(page = currentPage - 1)
                                    }
                                }
                            }
                        )
                    }

                    ContinueButton(
                        text = buttonState[1],
                        onClick = {
                            if (currentPage == viewModel.pages.size - 1) {
                                navController.navigate(NavigationItem.ListItems.route)
                            } else {
                                scope.launch {
                                    pageState.animateScrollToPage(page = currentPage + 1)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}