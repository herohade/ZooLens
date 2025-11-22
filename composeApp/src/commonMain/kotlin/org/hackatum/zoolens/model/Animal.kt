package org.hackatum.zoolens.model

import kotlinx.serialization.Serializable

@Serializable
data class AnimalWrapper(
    val id: String,
    val de: AnimalContent,
    val en: AnimalContent
)

@Serializable
data class AnimalContent(
    val id: String,
    val name: String,
    val shortDescription: String,
    val scientific_name: String? = null,
    val continent: String? = null,
    val conservationStatus: String? = null,
    val diet: String? = null,
    val habitat: List<String> = emptyList(),
    val funFacts: List<String> = emptyList()
)

fun AnimalWrapper.getContent(lang: String): AnimalContent {
    return if (lang == "de") this.de else this.en
}

