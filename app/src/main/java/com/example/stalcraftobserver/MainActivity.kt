package com.example.stalcraftobserver

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stalcraftobserver.data.manager.ItemsService
import com.example.stalcraftobserver.domain.model.ItemViewModel
import com.example.stalcraftobserver.presentation.itemsListing.ItemsListScreen
import com.example.stalcraftobserver.presentation.onBoarding.OnBoardingScreen
import com.example.stalcraftobserver.ui.theme.StalcraftObserverTheme
import com.example.stalcraftobserver.util.Constants
import com.example.stalcraftobserver.util.NavigationItem
import com.example.stalcraftobserver.util.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel = ItemViewModel(ItemsService(LocalContext.current))
            StalcraftObserverTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavigationItem.OnBoarding.route
                ) {
                    composable(NavigationItem.OnBoarding.route) {
                        Log.i(Constants.SWITCH_SCREEN, "Go to ${NavigationItem.OnBoarding.route}")
                        OnBoardingScreen(navController = navController)
                    }
                    composable(NavigationItem.ListItems.route){
                        Log.i(Constants.SWITCH_SCREEN, "Go to ${NavigationItem.ListItems.route}")
                        ItemsListScreen(navController = navController,viewModel = viewModel)
                    }
                    composable(
                        route = NavigationItem.ItemInfo("{idItem}").route,
                        arguments = listOf(navArgument("idItem") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val idItem = backStackEntry.arguments?.getString("idItem")
                        Log.i(Constants.SWITCH_SCREEN, "Go to ${NavigationItem.ItemInfo("$idItem").route}")
                        idItem?.let {
                            //ItemInfoScreen(itemId = it)
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