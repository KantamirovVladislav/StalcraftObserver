package com.example.stalcraftobserver.domain.model

import com.example.stalcraftobserver.util.itemSupportModel.Artefact

class ArtefactAssembly(private val maxSlots: Int = 6) {

    private val artefacts = mutableListOf<Artefact>()

    fun addArtefact(artefact: Artefact): Boolean {
        return if (artefacts.size < maxSlots) {
            artefacts.add(artefact)
            true
        } else {
            false
        }
    }


    fun buildStatsString(): String {
        val allStats = artefacts.flatMap { it.stats }

        val groupedStats = allStats.groupBy { it.keyStat }

        val lines = groupedStats.map { (keyStat, statsList) ->
            val totalStart = statsList.sumOf { if (it.isPositiveStat) it.startStat else -it.startStat }
            val totalEnd   = statsList.sumOf { if (it.isPositiveStat) it.endStat   else -it.endStat   }

            val isAdditional    = statsList.any { it.isAdditionalStat }

            val typeStat = statsList.firstOrNull()?.typeStat ?: ""

            val formattedStart = formatWithSign(totalStart)
            val formattedEnd   = formatWithSign(totalEnd)

            val additionalMark = if (isAdditional) " (доп.)" else ""

            "$keyStat: $formattedStart..$formattedEnd $typeStat$additionalMark"
        }

        return lines.joinToString(separator = "\n")
    }

    private fun formatWithSign(value: Double, decimalPlaces: Int = 2): String {
        val sign = if (value >= 0) "+" else ""
        val formattedNumber = "%.${decimalPlaces}f".format(value)
        return "$sign$formattedNumber"
    }
}
