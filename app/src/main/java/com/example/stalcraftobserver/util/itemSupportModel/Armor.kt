package com.example.stalcraftobserver.util.itemSupportModel

import com.example.stalcraftobserver.data.manager.Lines

data class Armor(
    val name: Map<Lines, Lines>,
    val rank: Map<Lines, Lines>,
    val category: Map<Lines, Lines>,
    val weight: Map<Lines, Double>,
    val durability: Map<Lines, Double>,
    val maxDurability: Map<Lines, Double>,
    val bulletResistance: Map<Lines, Double>,
    val explosionProtection: Map<Lines, Double>,
    val electricityResistance: Map<Lines, Double>,
    val fireResistance: Map<Lines, Double>,
    val chemicalProtection: Map<Lines, Double>,
    val radiationProtection: Map<Lines, Double>,
    val thermalProtection: Map<Lines, Double>,
    val biologicalProtection: Map<Lines, Double>,
    val psychoProtection: Map<Lines, Double>,
    val bleedingProtection: Map<Lines, Double>,
    val extraModifier: List<Map<Lines, Double>>
)
