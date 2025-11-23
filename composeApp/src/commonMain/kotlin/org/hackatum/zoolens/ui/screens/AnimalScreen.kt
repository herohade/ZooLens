package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.model.AnimalsJson
import org.hackatum.zoolens.model.AnimalJson
import org.hackatum.zoolens.model.Taxonomy
import org.hackatum.zoolens.model.Weight
import org.hackatum.zoolens.model.Lifespan
import org.hackatum.zoolens.model.Section
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun AnimalName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 4.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun AnimalScientificName(scientificName: String?) {
    if (!scientificName.isNullOrBlank()) {
        Text(
            text = scientificName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AnimalDescription(description: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AssistantPanel(userInput: androidx.compose.runtime.MutableState<String>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            Text(
                text = LocalStrings.current.askAssistant,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                BasicTextField(
                    value = userInput.value,
                    onValueChange = { userInput.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

@Composable
fun AnimalTaxonomy(taxonomy: Taxonomy?, language: String) {
    if (taxonomy == null) return
    val family = taxonomy.family?.get(language)
    val latinFamily = taxonomy.latinFamily
    val familyLabel = if (language == "de") "Familie" else "Family"
    val latinFamilyLabel = if (language == "de") "Lateinische Familie" else "Latin Family"
    if (family != null || latinFamily != null) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                if (family != null) Text("$familyLabel: $family", style = MaterialTheme.typography.bodyMedium)
                if (latinFamily != null) Text("$latinFamilyLabel: $latinFamily", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AnimalWeight(weight: Weight?, language: String) {
    if (weight == null) return
    val text = weight.text
    val label = if (language == "de") "Gewicht" else "Weight"
    if (text != null) {
        Text("$label: $text", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
    }
}

@Composable
fun AnimalContinent(continent: String?, language: String) {
    val label = if (language == "de") "Kontinent" else "Continent"
    if (continent != null) {
        Text("$label: $continent", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
    }
}

@Composable
fun AnimalConservationStatus(status: String?, language: String) {
    val label = if (language == "de") "Schutzstatus" else "Conservation Status"
    if (status != null) {
        Text("$label: $status", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
    }
}

@Composable
fun AnimalDiet(diet: String?, language: String) {
    val label = if (language == "de") "Ernährung" else "Diet"
    if (diet != null) {
        Text("$label: $diet", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
    }
}

@Composable
fun AnimalHabitat(habitat: List<String>, language: String) {
    val label = if (language == "de") "Lebensraum" else "Habitat"
    if (habitat.isNotEmpty()) {
        Text("$label: ${habitat.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
    }
}

@Composable
fun AnimalLifespan(lifespan: Lifespan?, language: String) {
    if (lifespan == null) return
    val wild = lifespan.wild
    val captivity = lifespan.captivity
    val wildLabel = if (language == "de") "Lebenserwartung (wild)" else "Lifespan (wild)"
    val captivityLabel = if (language == "de") "Lebenserwartung (Gefangenschaft)" else "Lifespan (captivity)"
    if (wild != null || captivity != null) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                if (wild != null) Text("$wildLabel: $wild", style = MaterialTheme.typography.bodyMedium)
                if (captivity != null) Text("$captivityLabel: $captivity", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun AnimalFunFacts(funFacts: List<String>, language: String) {
    val label = if (language == "de") "Wissenswertes:" else "Fun Facts:"
    if (funFacts.isNotEmpty()) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                Text(label, style = MaterialTheme.typography.titleMedium)
                funFacts.forEach { fact ->
                    Text("• $fact", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun AnimalSections(sections: List<Section>, language: String) {
    val label = if (language == "de") "Abschnitt" else "Section"
    if (sections.isNotEmpty()) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            sections.forEach { section ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(section.title, style = MaterialTheme.typography.titleMedium)
                        Text(section.text, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalScreen(id: String, language: String = "en", context: Any? = null) {
    val userInput = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val animalState = remember { mutableStateOf<AnimalJson?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(id) {
        coroutineScope.launch {
            val jsonString = org.hackatum.zoolens.model.loadAnimalsJsonString(context)
            val animalsJson = kotlinx.serialization.json.Json.decodeFromString<AnimalsJson>(jsonString)
            animalState.value = animalsJson.animals[id]
        }
    }
    val animal = animalState.value
    val translation = animal?.translations?.get(language)
    if (animal != null && translation != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AnimalName(translation.name)
            AnimalScientificName(animal.scientificName)
            AnimalDescription(description = translation.shortDescription)
            AnimalTaxonomy(animal.taxonomy, language)
            AnimalWeight(animal.weight, language)
            AnimalContinent(animal.continent?.get(language), language)
            AnimalConservationStatus(animal.conservationStatus?.get(language), language)
            AnimalDiet(animal.diet?.get(language), language)
            AnimalHabitat(animal.habitat?.get(language) ?: emptyList(), language)
            AnimalLifespan(animal.lifespan, language)
            AnimalFunFacts(translation.funFacts, language)
            AnimalSections(translation.sections, language)
            AssistantPanel(userInput)
        }
    }
}