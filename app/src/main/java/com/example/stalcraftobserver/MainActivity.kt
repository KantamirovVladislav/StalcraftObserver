// package com.example.stalcraftobserver

package com.example.stalcraftobserver

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.stalcraftobserver.data.manager.LocalUserManagerRel
import com.example.stalcraftobserver.domain.viewModel.ItemInfoViewModel
import com.example.stalcraftobserver.domain.viewModel.ItemViewModel
import com.example.stalcraftobserver.domain.viewModel.OnBoardingViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedCompareItemsViewModel
import com.example.stalcraftobserver.domain.viewModel.SharedItemViewModel
import com.example.stalcraftobserver.presentation.compareItems.CompareItemsScreen
import com.example.stalcraftobserver.presentation.itemInfoScreen.ItemInfoScreen
import com.example.stalcraftobserver.presentation.itemsListing.ItemsListScreen
import com.example.stalcraftobserver.presentation.loadoutScreen.LoadoutScreen
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
    private val sharedItemViewModel: SharedItemViewModel by viewModels()
    private val sharedCompareItemsViewModel: SharedCompareItemsViewModel by viewModels()

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
                                viewModel = itemViewModel,
                                mode = "view",
                                sharedCompareItemsViewModel = sharedCompareItemsViewModel,
                                sharedItemViewModel = sharedItemViewModel
                            )
                        }
                        composable(
                            route = NavigationItem.SelectItem.route,
                            arguments = listOf(
                                navArgument("itemSlot") { type = NavType.StringType },
                                navArgument("categories") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                }
                            )
                        ) { backStackEntry ->
                            val mode = "selection"
                            val categoriesString = backStackEntry.arguments?.getString("categories")
                            val categories = if (!categoriesString.isNullOrEmpty()) categoriesString.split(",") else emptyList()
                            val itemSlot = backStackEntry.arguments?.getString("itemSlot")
                            ItemsListScreen(
                                navController = navController,
                                viewModel = hiltViewModel(),
                                mode = mode,
                                itemSlot = itemSlot,
                                category = categories,
                                sharedCompareItemsViewModel = sharedCompareItemsViewModel,
                                sharedItemViewModel = sharedItemViewModel
                            )
                        }
                        composable(
                            route = NavigationItem.ItemInfo.route,
                            arguments = listOf(navArgument("idItem") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val idItem = backStackEntry.arguments?.getString("idItem")
                            idItem?.let {
                                val itemInfoViewModel: ItemInfoViewModel =
                                    hiltViewModel(backStackEntry)
                                Log.d("ScreenSwitch", "Go to ItemInfo $it")
                                ItemInfoScreen(
                                    id = it,
                                    viewModel = itemInfoViewModel,
                                    navController = navController
                                )
                            }
                        }
                        composable(
                            route = NavigationItem.CompareItems.route,
                            arguments = listOf(
                                navArgument("item1Id") { type = NavType.StringType; defaultValue = "" },
                                navArgument("item2Id") { type = NavType.StringType; defaultValue = "" }
                            )
                        ) { backStackEntry ->
                            val id1 = backStackEntry.arguments?.getString("item1Id")
                            val id2 = backStackEntry.arguments?.getString("item2Id")

                            if (id1 != null) {
                                sharedCompareItemsViewModel.setItem1Id(id1)
                            }
                            if (id2 != null){
                                sharedCompareItemsViewModel.setItem2Id(id2)
                            }
                            Log.d("CompareItems", "Navigate to CompareItemsScreen")
                            CompareItemsScreen(
                                navController = navController,
                                viewModel = hiltViewModel(),
                                sharedCompareItemsViewModel = sharedCompareItemsViewModel
                            )
                        }
                        composable(
                            route = NavigationItem.Loadout.route,
                            arguments = listOf(
                                navArgument("weapon") { type = NavType.StringType; defaultValue = "" },
                                navArgument("armor") { type = NavType.StringType; defaultValue = "" }
                            )
                        ) { backStackEntry ->
                            val weaponId = backStackEntry.arguments?.getString("weapon")
                            val armorId = backStackEntry.arguments?.getString("armor")
                            if (armorId?.isNotEmpty() == true){
                                sharedItemViewModel.setArmorId(armorId)
                            }
                            if (weaponId?.isNotEmpty() == true){
                                sharedItemViewModel.setWeaponId(weaponId)
                            }
                            Log.d("LoadoutId", "$weaponId - $armorId")
                            LoadoutScreen(
                                navController = navController,
                                viewModel = hiltViewModel(),
                                sharedItemViewModel = sharedItemViewModel
                            )
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
}