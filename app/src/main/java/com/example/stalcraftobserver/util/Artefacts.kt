package com.example.stalcraftobserver.util

import com.example.stalcraftobserver.util.itemSupportModel.Artefact
import com.example.stalcraftobserver.util.itemSupportModel.ArtefactStat

object Artefacts {
    val ARTEFACT_1 = Artefact(
        id = "5r04",
        stats = listOf(
            ArtefactStat(
                id = "asd",
                startStat = 1.2,
                endStat = 4.3,
                isPositiveStat = true,
                typeStat = "number",
                keyStat = "artefact_properties.factor.biological_protection",
                isAdditionalStat = false
            )
        )
    )
    val ARTEFACT_2 = Artefact(
        id = "gy06",
        stats = listOf(
            ArtefactStat(
                id = "asd",
                startStat = 3.2,
                endStat = 5.3,
                isPositiveStat = true,
                typeStat = "number",
                keyStat = "artefact_properties.factor.biological_protection",
                isAdditionalStat = false
            )
        )
    )

    val ARTEFACT_3 = Artefact(
        id = "ljpq",
        stats = listOf(
            ArtefactStat(
                id = "asd",
                startStat = 3.2,
                endStat = 5.3,
                isPositiveStat = false,
                typeStat = "number",
                keyStat = "artefact_properties.factor.biological_protection",
                isAdditionalStat = false
            ),
            ArtefactStat(
                id = "sad",
                startStat = 2.2,
                endStat = 5.0,
                isPositiveStat = true,
                typeStat = "number",
                keyStat = "artefact_properties.factor.explosion_dmg_factor",
                isAdditionalStat = false
            )
        )
    )
}