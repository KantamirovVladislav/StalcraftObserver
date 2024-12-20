package com.example.stalcraftobserver.util

import android.net.Uri

sealed class NavigationItem(val route: String) {
    object OnBoarding : NavigationItem("onboarding")
    object ListItems : NavigationItem("list_items")

    object ItemInfo : NavigationItem("item_info/{idItem}") {
        fun createRoute(idItem: String) = "item_info/$idItem"
    }

    object SelectItem : NavigationItem("select_item/{itemSlot}?categories={categories}") {
        fun createRoute(itemSlot: String, categories: List<String>? = null): String {
            val categoriesParam = categories?.joinToString(",") ?: ""
            return "select_item/$itemSlot?categories=$categoriesParam"
        }
    }

    object Loadout : NavigationItem("loadout?weapon={weapon}&armor={armor}") {
        fun createRoute(weapon: String? = null, armor: String? = null): String {
            val weaponParam = weapon?.let { Uri.encode(it) } ?: ""
            val armorParam = armor?.let { Uri.encode(it) } ?: ""
            return "loadout?weapon=$weaponParam&armor=$armorParam"
        }
    }

    object CompareItems : NavigationItem("compare_items?item1Id={item1Id}&item2Id={item2Id}") {
        fun createRoute(item1Id: String? = null, item2Id: String? = null): String {
            val item1Param = item1Id?.let { Uri.encode(it) } ?: ""
            val item2Param = item2Id?.let { Uri.encode(it) } ?: ""
            return "compare_items?item1Id=$item1Param&item2Id=$item2Param"
        }
    }
}
