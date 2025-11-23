package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.model.AnimalWrapper
import org.hackatum.zoolens.model.getContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zoolens.composeapp.generated.resources.Res
import zoolens.composeapp.generated.resources.loewe
import zoolens.composeapp.generated.resources.giraffe
import zoolens.composeapp.generated.resources.asiatischer_elefant

@Composable
fun AnimalName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun AnimalScientificName(scientificName: String?) {
    Text(
        text = scientificName ?: "",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun AnimalDescription(description: String, scrollState: androidx.compose.foundation.ScrollState, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun AssistantPanel(userInput: androidx.compose.runtime.MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Text(
            text = LocalStrings.current.askAssistant,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            BasicTextField(
                value = userInput.value,
                onValueChange = { userInput.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 48.dp)
                    .padding(8.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
@Preview
fun AnimalScreen(id: String, language: String = "en") {
    val userInput = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val contentWrapper = listOf(
        AnimalWrapper(
            id = "1",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "1",
                name = "Löwe",
                shortDescription = "Große Raubkatze, lebt in Rudeln.",
                scientific_name = "Panthera leo",
                continent = "Afrika",
                conservationStatus = "Vulnerable",
                diet = "Fleischfresser",
                habitat = listOf("Savanne"),
                funFacts = listOf("Löwen leben in Rudeln", "Männliche Löwen haben Mähnen")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "1",
                name = "Lion",
                shortDescription = "Large big cat living in prides.",
                scientific_name = "Panthera leo",
                continent = "Africa",
                conservationStatus = "Vulnerable",
                diet = "Carnivore",
                habitat = listOf("Savannah"),
                funFacts = listOf("Lions live in prides", "Male lions have manes")
            )
        ),
        AnimalWrapper(
            id = "2",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "2",
                name = "Giraffe",
                shortDescription = "Größtes Landtier mit sehr langem Hals.",
                scientific_name = "Giraffa camelopardalis",
                continent = "Afrika",
                conservationStatus = "Vulnerable",
                diet = "Pflanzenfresser",
                habitat = listOf("Savanne"),
                funFacts = listOf("Giraffen haben lange Hälse")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "2",
                name = "Giraffe",
                shortDescription = "Tallest land animal with a very long neck.",
                scientific_name = "Giraffa camelopardalis",
                continent = "Africa",
                conservationStatus = "Vulnerable",
                diet = "Herbivore",
                habitat = listOf("Savannah"),
                funFacts = listOf("Giraffes have long necks")
            )
        ),
        AnimalWrapper(
            id = "3",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "3",
                name = "Elefant",
                shortDescription = "Großes, intelligentes Säugetier mit Rüssel.",
                scientific_name = "Loxodonta africana",
                continent = "Afrika",
                conservationStatus = "Vulnerable",
                diet = "Pflanzenfresser",
                habitat = listOf("Savanne", "Wälder"),
                funFacts = listOf("Elefanten haben ein hervorragendes Gedächtnis")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "3",
                name = "Elephant",
                shortDescription = "Large intelligent mammal with a trunk.",
                scientific_name = "Loxodonta africana",
                continent = "Africa",
                conservationStatus = "Vulnerable",
                diet = "Herbivore",
                habitat = listOf("Savannah", "Forests"),
                funFacts = listOf("Elephants have excellent memory")
            )
        )
    )[(id.toIntOrNull() ?: 1) - 1]
    val content = contentWrapper.getContent(language)

    // Get the appropriate drawable resource based on animal ID
    val imageResource = when (id) {
        "1" -> Res.drawable.loewe
        "2" -> Res.drawable.giraffe
        "3" -> Res.drawable.asiatischer_elefant
        else -> Res.drawable.loewe
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AnimalName(content.name)
        AnimalScientificName(content.scientific_name)

        // Large animal image
        Image(
            painter = painterResource(imageResource),
            contentDescription = content.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentScale = ContentScale.Crop
        )

        AnimalDescription(
            description = content.shortDescription,
            scrollState = scrollState,
            modifier = Modifier.weight(1f)
        )
        AssistantPanel(userInput)
    }
}