package com.example.stalcraftobserver.util

import android.util.Log
import com.example.stalcraftobserver.data.manager.Element
import com.example.stalcraftobserver.data.manager.InfoBlock
import com.example.stalcraftobserver.data.manager.ItemInfo
import com.example.stalcraftobserver.data.manager.Lines
import com.example.stalcraftobserver.util.itemSupportModel.Armor

class ItemInfoHelper {


    companion object {

        private fun containsParts(substring: String, fullString: String): Boolean {
            val subParts = substring.split(".")
            val fullParts = fullString.split(".")

            var currentIndex = 0

            Log.d("KEKWPARTS", "$substring : $fullString")
            for (subPart in subParts) {
                // Найти первую позицию совпадения начиная с currentIndex
                while (currentIndex < fullParts.size && fullParts[currentIndex] != subPart) {
                    currentIndex++
                }
                if (currentIndex == fullParts.size) return false
                currentIndex++ // Сдвигаем указатель для следующей проверки
            }
            return true
        }


        fun getValuesByKeys(
            itemInfo: ItemInfo,
            keys: List<String>
        ): Map<String, Map<Lines?, Lines?>> =
            keys.associateWith { key -> getValuesForKey(itemInfo, key) }

        fun getValuesForKey(itemInfo: ItemInfo, key: String): Map<Lines?, Lines?> {
            fun checkKeyMatch(
                elementKey: String?,
                elementLinesMap: Map<Lines?, Lines?>?
            ): Pair<Lines?, Lines?>? {
                return if (elementKey?.let { containsParts(key, it) } == true) {

                    elementLinesMap?.entries?.firstOrNull()?.let {
                        Log.d("KeyCheck", "$elementKey - ${it.key} - ${it.value}")
                        Pair(it.key, it.value)
                    }
                } else null
            }

            val resultMap = mutableMapOf<Lines?, Lines?>()

            itemInfo.name.key?.let {
                if (containsParts(key, it)) {
                    resultMap[Lines(ru = "Название", en = "Name")] = itemInfo.name.lines
                }
            }

            itemInfo.infoBlocks?.forEach { infoBlock ->
                when (infoBlock) {
                    is InfoBlock.List -> {
                        infoBlock.elements?.forEach { element ->
                            val result = when (element) {
                                is Element.KeyValueElement -> checkKeyMatch(
                                    element.key?.key,
                                    mapOf(element.key?.lines to element.value?.lines)
                                )

                                is Element.NumericElement -> checkKeyMatch(
                                    element.name?.key,
                                    mapOf(element.name?.lines to element.formatted?.value.let {
                                        Lines(
                                            en = it?.en,
                                            ru = it?.ru
                                        )
                                    })
                                )

                                is Element.TextElement -> checkKeyMatch(
                                    element.text?.key,
                                    mapOf(element.title?.lines to element.text?.lines)
                                )

                                is Element.ItemElement -> checkKeyMatch(
                                    element.name?.key,
                                    mapOf(element.name?.lines to null)
                                )

                                is Element.RangeElement -> null
                                else -> null
                            }
                            result?.let { resultMap[it.first] = it.second }
                        }
                    }

                    is InfoBlock.Text -> {
                        val key: String? = infoBlock.title?.key ?: infoBlock.text?.key
                        Log.d("InfoBlock", "${infoBlock.title?.lines} - ${infoBlock.text?.lines}")
                        checkKeyMatch(
                            key,
                            mapOf(infoBlock.title?.lines to infoBlock.text?.lines)
                        )?.let {
                            if (key != null) {
                                if (key.contains("description"))
                                    resultMap[Lines(ru = "Описание", en = "Description")] = it.second
                                else
                                    resultMap[it.first] = it.second
                            }

                        }
                    }

                    is InfoBlock.Damage -> {
                        //TODO
                    }

                    else -> null
                }
            }
            Log.d("DEBUG", "Key: $key, Result: $resultMap")
            return resultMap
        }


        suspend fun getArmorClassFromItemInfo(item: ItemInfo): Armor? {
            if (!item.category.contains("armor")) return null
            return null

        }
    }
}