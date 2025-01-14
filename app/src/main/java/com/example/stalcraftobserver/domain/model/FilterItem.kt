package com.example.stalcraftobserver.domain.model

data class FilterItem(
    val name: String,
    val rusName: String = "",
    val group: String,
    val extendFilters: List<FilterItem> = emptyList<FilterItem>()
)
