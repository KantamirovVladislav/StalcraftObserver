package com.example.stalcraftobserver.data.manager

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import java.lang.reflect.Type
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class RetrofitClientItemInfo {
    private val BASE_URL =
        "https://raw.githubusercontent.com/EXBO-Studio/stalcraft-database/main/"

    val gson = GsonBuilder()
        .registerTypeAdapter(Element::class.java, ElementDeserializer())
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

class ElementDeserializer : JsonDeserializer<Element> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Element {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type").asString

        return when (type) {
            "key-value" -> context.deserialize(json, Element.KeyValueElement::class.java)
            "numeric" -> context.deserialize(json, Element.NumericElement::class.java)
            "text" -> context.deserialize(json, Element.TextElement::class.java)
            else -> throw JsonParseException("Unknown type: $type")
        }
    }
}