package com.example.stalcraftobserver

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stalcraftobserver.data.manager.LocalUserManagerRel
import com.example.stalcraftobserver.domain.model.viewModel.ItemInfoViewModel
import com.example.stalcraftobserver.domain.model.viewModel.ItemViewModel
import com.example.stalcraftobserver.domain.model.viewModel.OnBoardingViewModel
import com.example.stalcraftobserver.presentation.itemInfoScreen.ItemInfoScreen
import com.example.stalcraftobserver.presentation.itemsListing.ItemsListScreen
import com.example.stalcraftobserver.presentation.onBoarding.OnBoardingScreen
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme
import com.example.stalcraftobserver.util.NavigationItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val itemViewModel: ItemViewModel by viewModels()
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    @Inject
    @Named("LocalUserManager")
    lateinit var localUserManager: LocalUserManagerRel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(3000)
            keepSplashScreen = false
        }
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            var isAppInitialized by remember { mutableStateOf(false) }
            var appEntry by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                appEntry = localUserManager.readAppEntry().first()
                isAppInitialized = true
            }
            LaunchedEffect(Unit) {
                itemViewModel.loadMoreItems()
            }

            StalcraftObserverTheme {
                if (isAppInitialized) {
                    NavHost(
                        navController = navController,
                        startDestination = if (appEntry) NavigationItem.ListItems.route else NavigationItem.OnBoarding.route
                    ) {
                        composable(NavigationItem.OnBoarding.route) {
                            Log.d("ScreenSwitch", "Go to OnBoarding")
                            OnBoardingScreen(
                                navController = navController,
                                viewModel = onBoardingViewModel
                            )
                        }
                        composable(NavigationItem.ListItems.route) {
                            Log.d("ScreenSwitch", "Go to ListItems")
                            ItemsListScreen(
                                navController = navController,
                                viewModel = itemViewModel
                            )
                        }
                        composable(
                            route = NavigationItem.ItemInfo("{idItem}").route,
                            arguments = listOf(navArgument("idItem") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val idItem = backStackEntry.arguments?.getString("idItem")
                            idItem?.let {
                                val itemInfoViewModel: ItemInfoViewModel =
                                    hiltViewModel(backStackEntry)
                                Log.d("ScreenSwitch", "Go to ItemInfo $it")
                                ItemInfoScreen(id = it, viewModel = itemInfoViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(device = "spec:width=411dp,height=891dp,dpi=420") // Pixel 4
@Preview(device = "spec:width=360dp,height=740dp,dpi=320") // Nexus 5
@Preview(device = "spec:width=320dp,height=480dp,dpi=160") // Small Phone
@Preview(device = "spec:width=600dp,height=1024dp,dpi=240") // 7-inch Tablet
@Preview(device = "spec:width=800dp,height=1280dp,dpi=240") // 10-inch Tablet
@Preview(device = "spec:width=1024dp,height=1366dp,dpi=264") // iPad Pro 10.5
@Preview(device = "spec:width=1440dp,height=2560dp,dpi=560") // Pixel XL
@Preview(device = "spec:width=1080dp,height=1920dp,dpi=480") // Full HD Phone
@Preview(device = "spec:width=1440dp,height=2960dp,dpi=560") // Galaxy S8
@Preview(device = "spec:width=768dp,height=1024dp,dpi=160") // Nexus 7
@Composable
fun GreetingPreview() {
    StalcraftObserverTheme {

    }
}