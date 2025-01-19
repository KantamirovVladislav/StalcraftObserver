package com.example.stalcraftobserver.domain.model

data class FilterItem(
    val name: String,
    val rusName: String = "",
    val group: String,
    val type: FilterType = FilterType.None,
    val extendFilters: List<FilterItem> = emptyList<FilterItem>()
)


enum class FilterType {
    None,
    Category,
    Rarity
}