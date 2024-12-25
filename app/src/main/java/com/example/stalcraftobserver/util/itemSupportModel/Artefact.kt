package com.example.stalcraftobserver.util.itemSupportModel

data class ArtefactStat(
    val id: String,
    val startStat: Double,
    val endStat: Double,
        val isPositiveStat: Boolean,
    val typeStat: String = "%",
    val keyStat: String,
    val isAdditionalStat: Boolean
)

data class Artefact(
    val id: String,
    val stats: List<ArtefactStat>
)