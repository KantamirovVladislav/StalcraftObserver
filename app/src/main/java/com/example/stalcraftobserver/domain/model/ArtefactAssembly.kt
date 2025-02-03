package com.example.stalcraftobserver.domain.model

import com.example.stalcraftobserver.util.itemSupportModel.Artefact

class ArtefactAssembly(private val maxSlots: Int = 6) {

    var artefacts = mutableMapOf<String, Artefact>()

    fun checkArtefact(id: String, slot: String): Boolean {
        return artefacts[slot]?.id == id
    }

    fun clearArtefacts(){
        artefacts = mutableMapOf<String, Artefact>()
    }

    fun addArtefact(slot: String, artefact: Artefact): Boolean {
        if (checkArtefact(artefact.id, slot)) {
            return false
        }

        if (artefacts.size < maxSlots || artefacts.containsKey(slot)) {
            artefacts[slot] = artefact
            return true
        }
        return false
    }

    fun removeArtefact(slot: String) {
        artefacts.remove(slot)
    }

    fun replaceArtefact(slot: String, newArtefact: Artefact) {
        artefacts[slot] = newArtefact
    }

    fun buildStatsString(): String {
        val allStats = artefacts.values.flatMap { it.stats }

        val groupedStats = allStats.groupBy { it.keyStat }

        val lines = groupedStats.map { (keyStat, statsList) ->
            val totalStart =
                statsList.sumOf { if (it.isPositiveStat) it.startStat else -it.startStat }
            val totalEnd = statsList.sumOf { if (it.isPositiveStat) it.endStat else -it.endStat }

            val isAdditional = statsList.any { it.isAdditionalStat }

            val typeStat = statsList.firstOrNull()?.typeStat ?: ""

            val formattedStart = formatWithSign(totalStart)
            val formattedEnd = formatWithSign(totalEnd)

            val additionalMark = if (isAdditional) " (доп.)" else ""

            "$keyStat: $formattedStart..$formattedEnd $typeStat$additionalMark"
        }

        return lines.joinToString(separator = "\n")
    }

    fun buildStatsList(): List<Triple<String, String, String>> {
        val allStats = artefacts.values.flatMap { it.stats }

        val groupedStats = allStats.groupBy { it.keyStat }

        return groupedStats.map { (keyStat, statsList) ->
            val totalStart =
                statsList.sumOf { if (it.isPositiveStat) it.startStat else -it.startStat }
            val totalEnd = statsList.sumOf { if (it.isPositiveStat) it.endStat else -it.endStat }

            val typeStat = statsList.firstOrNull()?.typeStat ?: ""

            val formattedStart = formatWithSign(totalStart)
            val formattedEnd = formatWithSign(totalEnd)

            Triple(keyStat, "$formattedStart..$formattedEnd", typeStat)
        }
    }


    private fun formatWithSign(value: Double, decimalPlaces: Int = 2): String {
        val sign = if (value >= 0) "+" else ""
        val formattedNumber = "%.${decimalPlaces}f".format(value)
        return "$sign$formattedNumber"
    }
}