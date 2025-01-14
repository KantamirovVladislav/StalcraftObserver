package com.example.stalcraftobserver.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stalcraftobserver.R
import com.example.stalcraftobserver.data.manager.LocalUserManagerRel
import com.example.stalcraftobserver.presentation.onBoarding.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    @Named("LocalUserManager") private val userManager: LocalUserManagerRel
) : ViewModel() {

    private var _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage

    private var _buttonState = MutableStateFlow(listOf("", "Next"))
    val buttonState: StateFlow<List<String>> = _buttonState

    val pages = listOf(
        Page(
            title = "История аукционных лотов",
            description = "Просматривайте историю аукционных лотов",
            image = R.drawable.auctionimage
        ),
        Page(
            title = "Активные аукционные лоты",
            description = "Просматривайте активные аукционные лоты",
            image = R.drawable.auctionimage
        )
//        ),
//        Page(
//            title = "Neque",
//            description = "Neque porro quisquam est qui dolorem ipsum.",
//            image = R.drawable.ic_launcher_background
//        ),
//        Page(
//            title = "Ut Enim",
//            description = "Ut enim ad minima veniam, quis nostrum exercitationem.",
//            image = R.drawable.ic_launcher_background
//        )
    )

    fun onPageChanged(page: Int) {
        _currentPage.value = page
        _buttonState.value =
            when (page) {
                0 -> listOf("", "Next")
                1 -> listOf("Back", "Get Started")
                else -> listOf("", "")
            }

        if (_currentPage.value == 1){
            viewModelScope.launch {
                userManager.saveAppEntry()
            }
        }
    }
}