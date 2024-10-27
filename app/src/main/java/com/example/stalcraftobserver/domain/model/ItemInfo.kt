package com.example.stalcraftobserver.domain.model

data class ItemInfo(
    val id: String,
    val category: String,
    val name: TranslationItem,
    val color: String,
    val status: Status,
    val infoBlocks: List<InfoBlock>
)

data class TranslationItem(
    val type: String,
    val key: String,
    val args: Map<String, String>,
    val lines: Lines
)

data class Lines(
    val ru: String,
    val en: String
)

data class Status(
    val state: String
)

data class InfoBlock(
    val type: String,
    val title: TextItem,
    val elements: List<Element>
)

data class TextItem(
    val type: String,
    val text: String
)

sealed class Element {
    data class KeyValueElement(
        val type: String,
        val key: TranslationItem,
        val value: TranslationItem
    ) : Element()

    data class NumericElement(
        val type: String,
        val name: TranslationItem,
        val value: Double,
        val formatted: Formatted
    ) : Element()

    data class TextElement(
        val type: String,
        val text: TranslationItem
    ) : Element()
}

data class Formatted(
    val value: FormattedValue,
    val nameColor: String,
    val valueColor: String
)

data class FormattedValue(
    val ru: String,
    val en: String
)
