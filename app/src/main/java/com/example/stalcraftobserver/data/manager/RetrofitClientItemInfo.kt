package com.example.stalcraftobserver.data.manager

import android.icu.text.IDNA.Info
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import java.lang.reflect.Type
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class RetrofitClientItemInfo {
    private val BASE_URL =
        "https://raw.githubusercontent.com/EXBO-Studio/stalcraft-database/main/"

    val gson = GsonBuilder()
        .registerTypeAdapter(InfoBlock::class.java, InfoBlockDeserializer())
        .registerTypeAdapter(Element::class.java, ListElementDeserializer())
        .create()

    val instance: GitHubApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(GitHubApi::class.java)
    }
}

interface GitHubApi {
    @GET("{region}/items/{category}/{id}.json")
    fun getItemData(
        @Path("region") region: String,
        @Path("category") category: String,
        @Path("id") id: String
    ): Call<ItemInfo>
}


data class ItemInfo(
    val id: String,
    val category: String,
    val name: TranslationItem?,
    val color: String?,
    val status: Status?,
    val infoBlocks: List<InfoBlock?>?
)


data class TranslationItem(
    val type: String?,
    val key: String?,
    val args: Map<String?, String?>?,
    val lines: Lines?
)


data class Lines(
    val ru: String?,
    val en: String?
)


data class Status(
    val state: String?
)

sealed class InfoBlock {
    data class Text(
        val type: String?,
        val title: TranslationItem?,
        val text: TranslationItem?
    ) : InfoBlock()

    data class List(
        val type: String?,
        val title: TextItem?,
        val elements: kotlin.collections.List<Element?>?
    ) : InfoBlock()

    data class Damage(
        val type: String?,
        val startDamage: Double?,
        val damageDecreaseStart: Double?,
        val endDamage: Double?,
        val damageDecreaseEnd: Double?,
        val maxDistance: Double?
    ) : InfoBlock()
}


data class TextItem(
    val type: String?,
    val text: String?
)

sealed class Element {
    data class KeyValueElement(
        val type: String?,
        val key: TranslationItem?,
        val value: TranslationItem?
    ) : Element()

    data class NumericElement(
        val type: String?,
        val name: TranslationItem?,
        val value: Double?,
        val formatted: Formatted?
    ) : Element()

    data class TextElement(
        val type: String?,
        val title: TranslationItem?,
        val text: TranslationItem?
    ) : Element()

    data class RangeElement(
        val type: String?,
        val name: TranslationItem?,
        val min: Double?,
        val max: Double?,
        val formatted: Formatted?
    ) : Element()

    data class ItemElement(
        val name: TranslationItem?
    ) : Element()

    data class UsageElement(
        val type: String?,
        val name: TranslationItem?
    ) : Element()
}


data class Formatted(
    val value: FormattedValue?,
    val nameColor: String?,
    val valueColor: String?
)

data class FormattedValue(
    val ru: String?,
    val en: String?
)


class InfoBlockDeserializer : JsonDeserializer<InfoBlock> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): InfoBlock {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")?.asString

        return when (type) {
            "text" -> {
                val title = jsonObject.get("title")?.let {
                    context.deserialize<TranslationItem>(it, TranslationItem::class.java)
                }
                val text = jsonObject.get("text")?.let {
                    context.deserialize<TranslationItem>(it, TranslationItem::class.java)
                }
                InfoBlock.Text(type, title, text)
            }

            "list" -> {
                val title = jsonObject.get("title")?.let {
                    context.deserialize<TextItem>(it, TextItem::class.java)
                }
                val elements = jsonObject.get("elements")?.let {
                    context.deserialize<List<Element>>(
                        it,
                        object : TypeToken<List<Element>>() {}.type
                    )
                }
                InfoBlock.List(type, title, elements)
            }

            "damage" -> context.deserialize(
                json,
                InfoBlock.Damage::class.java
            )

            else -> InfoBlock.List(type, TextItem(" ", " "), emptyList())
        }
    }
}

class ListElementDeserializer : JsonDeserializer<Element> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Element {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")?.asString

        return when (type) {
            "key-value" -> context.deserialize(
                json,
                Element.KeyValueElement::class.java
            )

            "numeric" -> context.deserialize(
                json,
                Element.NumericElement::class.java
            )

            "text" -> context.deserialize(json, Element.TextElement::class.java)
            "range" -> context.deserialize(json, Element.RangeElement::class.java)
            "item" -> context.deserialize(json, Element.ItemElement::class.java)
            "usage" -> context.deserialize(json, Element.UsageElement::class.java)
            else -> Element.TextElement(
                type, TranslationItem(null, null, null, null), TranslationItem(null, null, null, null)
            )
        }
    }
}