package com.example.stalcraftobserver.util

enum class UpgradeLevel(val level: Int, val percentageIncrease: Double) {
    LEVEL_0(0,0.0),
    LEVEL_1(1, 0.76),
    LEVEL_2(2, 1.52),
    LEVEL_3(3, 2.29),
    LEVEL_4(4, 3.05),
    LEVEL_5(5, 5.34),
    LEVEL_6(6, 6.48),
    LEVEL_7(7, 7.62),
    LEVEL_8(8, 8.77),
    LEVEL_9(9, 9.91),
    LEVEL_10(10, 13.26),
    LEVEL_11(11, 14.94),
    LEVEL_12(12, 16.62),
    LEVEL_13(13, 18.29),
    LEVEL_14(14, 19.97),
    LEVEL_15(15, 25.0);

    companion object {
        fun fromLevel(level: Int): UpgradeLevel? {
            return values().find { it.level == level }
        }
    }
}

object DamageCalculator {
    fun increaseDamage(minDamage: Double, endDamage: Double, level: Int): Pair<Double, Double> {
        val upgradeLevel = UpgradeLevel.fromLevel(level)
            ?: throw IllegalArgumentException("Уровень улучшения должен быть от 1 до 15. Получен: $level")

        // Вычисляем множитель
        val multiplier = 1 + (upgradeLevel.percentageIncrease / 100)

        // Увеличиваем значения
        val newMinDamage = minDamage * multiplier
        val newEndDamage = endDamage * multiplier

        return Pair(newMinDamage, newEndDamage)
    }
}