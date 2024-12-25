package com.example.stalcraftobserver.domain.model

import com.example.stalcraftobserver.util.itemSupportModel.Artefact

class ArtefactAssembly(private val maxSlots: Int = 6) {

    private val artefacts = mutableListOf<Artefact>()

    /**
     * Добавляем артефакт в сборку, если есть свободные слоты
     */
    fun addArtefact(artefact: Artefact): Boolean {
        return if (artefacts.size < maxSlots) {
            artefacts.add(artefact)
            true
        } else {
            false
        }
    }

    /**
     * Формируем строку, в которой:
     * 1) Группируем все характеристики по ключу (keyStat).
     * 2) Суммируем/вычитаем значения startStat и endStat (учитывая isPositiveStat).
     * 3) Учитываем тип stat (например, "%", "number").
     * 4) Учитываем флаги isAdditionalStat (если нужно как-то дополнительно отметить).
     */
    fun buildStatsString(): String {
        // Собираем все характеристики из всех артефактов
        val allStats = artefacts.flatMap { it.stats }

        // Группируем по ключу keyStat
        val groupedStats = allStats.groupBy { it.keyStat }

        // Проходимся по каждой группе и суммируем/вычитаем startStat и endStat
        val lines = groupedStats.map { (keyStat, statsList) ->
            // Суммируем startStat и endStat с учётом знака
            val totalStart = statsList.sumOf { if (it.isPositiveStat) it.startStat else -it.startStat }
            val totalEnd   = statsList.sumOf { if (it.isPositiveStat) it.endStat   else -it.endStat   }

            // Проверяем, есть ли среди статов хотя бы один, у которого isAdditionalStat = true
            val isAdditional    = statsList.any { it.isAdditionalStat }

            // Берём первый (или любой) тип стата — предполагается, что у всех в группе он одинаковый
            val typeStat = statsList.firstOrNull()?.typeStat ?: ""

            // Форматируем итоговую строку
            // Например, хотим указать знак только для положительных значений (+1.5)
            val formattedStart = formatWithSign(totalStart)
            val formattedEnd   = formatWithSign(totalEnd)

            val additionalMark = if (isAdditional) " (доп.)" else ""

            "$keyStat: $formattedStart..$formattedEnd $typeStat$additionalMark"
        }

        // Склеиваем все строки через перенос строки
        return lines.joinToString(separator = "\n")
    }

    /**
     * Вспомогательная функция, чтобы красиво отформатировать число со знаком
     * Например, 1.2 -> +1.20, -2.3456 -> -2.35
     */
    private fun formatWithSign(value: Double, decimalPlaces: Int = 2): String {
        val sign = if (value >= 0) "+" else ""
        // Ограничиваем число до нужного количества знаков после запятой
        val formattedNumber = "%.${decimalPlaces}f".format(value)
        return "$sign$formattedNumber"
    }
}
