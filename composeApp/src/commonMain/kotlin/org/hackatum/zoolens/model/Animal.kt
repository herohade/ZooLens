package org.hackatum.zoolens.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

@Serializable
data class AnimalsJson(val animals: Map<String, AnimalJson>)

@Serializable
data class AnimalJson(
    val id: String,
    val scientificName: String? = null,
    val continent: Map<String, String>? = null,
    val conservationStatus: Map<String, String>? = null,
    val taxonomy: Taxonomy? = null,
    val weight: Weight? = null,
    val habitat: Map<String, List<String>>? = null,
    val diet: Map<String, String>? = null,
    val lifespan: Lifespan? = null,
    val translations: Map<String, AnimalTranslation>? = null
)

@Serializable
data class Taxonomy(
    val family: Map<String, String>? = null,
    val latinFamily: String? = null
)

@Serializable
data class Weight(
    val text: String? = null,
    val minKg: Int? = null,
    val maxKg: Int? = null
)

@Serializable
data class Lifespan(
    val wild: String? = null,
    val captivity: String? = null
)

@Serializable
data class AnimalTranslation(
    val name: String,
    val shortDescription: String,
    val funFacts: List<String> = emptyList(),
    val sections: List<Section> = emptyList()
)

@Serializable
data class Section(
    val title: String,
    val text: String
)

fun AnimalWrapper.getContent(lang: String): AnimalContent {
    return if (lang == "de") this.de else this.en
}

expect suspend fun loadAnimalsJsonString(context: Any? = null): String

@OptIn(ExperimentalResourceApi::class)
suspend fun loadAnimalsFromJson(context: Any? = null): List<AnimalWrapper> {
    val jsonString = loadAnimalsJsonString(context)
    val animalsJson = Json.decodeFromString<AnimalsJson>(jsonString)
    return animalsJson.animals.values.map { animalJson ->
        AnimalWrapper(
            id = animalJson.id,
            de = AnimalContent(
                id = animalJson.id,
                name = animalJson.translations?.get("de")?.name ?: "",
                shortDescription = animalJson.translations?.get("de")?.shortDescription ?: "",
                scientific_name = animalJson.scientificName,
                continent = animalJson.continent?.get("de"),
                conservationStatus = animalJson.conservationStatus?.get("de"),
                diet = animalJson.diet?.get("de"),
                habitat = animalJson.habitat?.get("de") ?: emptyList(),
                funFacts = animalJson.translations?.get("de")?.funFacts ?: emptyList()
            ),
            en = AnimalContent(
                id = animalJson.id,
                name = animalJson.translations?.get("en")?.name ?: "",
                shortDescription = animalJson.translations?.get("en")?.shortDescription ?: "",
                scientific_name = animalJson.scientificName,
                continent = animalJson.continent?.get("en"),
                conservationStatus = animalJson.conservationStatus?.get("en"),
                diet = animalJson.diet?.get("en"),
                habitat = animalJson.habitat?.get("en") ?: emptyList(),
                funFacts = animalJson.translations?.get("en")?.funFacts ?: emptyList()
            )
        )
    }
}
