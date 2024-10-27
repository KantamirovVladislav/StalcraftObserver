package com.example.stalcraftobserver.util

enum class Screen {
    OnBoarding,
    ListItems,
    ItemInfo
}

sealed class NavigationItem(val route: String){
    data object OnBoarding: NavigationItem(Screen.OnBoarding.name)
    data object ListItems: NavigationItem(Screen.ListItems.name)
    class ItemInfo(private val idItem: String): NavigationItem("${Screen.ItemInfo.name}/$idItem")
}