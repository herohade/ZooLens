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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.hackatum.zoolens.ChatRequest
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.model.AnimalWrapper
import org.hackatum.zoolens.model.getContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zoolens.composeapp.generated.resources.Res
import zoolens.composeapp.generated.resources.loewe
import zoolens.composeapp.generated.resources.giraffe
import zoolens.composeapp.generated.resources.asiatischer_elefant
import zoolens.composeapp.generated.resources.alpaka
import zoolens.composeapp.generated.resources.alpensteinbock
import zoolens.composeapp.generated.resources.alpensteinhuhn
import zoolens.composeapp.generated.resources.aldabra_riesenschildkroete
import zoolens.composeapp.generated.resources.compose_multiplatform

val animalApiClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

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
fun AssistantPanel(userInput: androidx.compose.runtime.MutableState<String>, llmOutput: androidx.compose.runtime.MutableState<String>, isLoading: androidx.compose.runtime.MutableState<Boolean>) {
    val coroutineScope = rememberCoroutineScope()

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
                ),
                enabled = !isLoading.value
            )

            IconButton(
                onClick = {
                    val message = userInput.value
                    if (message.isNotBlank()) {
                        coroutineScope.launch {
                            isLoading.value = true
                            try {
                                val response: String =
                                    animalApiClient.post("http://10.0.2.2:8000/chat") {
                                        contentType(ContentType.Application.Json)
                                        setBody(ChatRequest(message = message))
                                    }.bodyAsText()

                                println("LLM Response: $response")
                                llmOutput.value = Json.parseToJsonElement(response).jsonObject["response"]?.toString() ?: ""
                                userInput.value = ""

                            } catch (e: Exception) {
                                println("Error: ${e.message}")
                                llmOutput.value = "Error: ${e.message}"
                            } finally {
                                isLoading.value = false
                            }
                        }
                    }
                },
                modifier = Modifier.padding(end = 4.dp),
                enabled = !isLoading.value
            ) {
                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Send message"
                )
            }
        }

        // Display LLM output
        if (llmOutput.value.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = llmOutput.value.trim('"'),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
@Preview
fun AnimalScreen(id: String) {
    val userInput = remember { mutableStateOf("") }
    val llmOutput = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Get language dynamically from LocalStrings
    val strings = LocalStrings.current
    val language = if (strings.home == "Start") "de" else "en"

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
                shortDescription = "Das Hals- und Stummelpferd: Mit bis zu 6 Metern Höhe das höchste Landtier der Erde.",
                scientific_name = "Giraffa camelopardalis",
                continent = "Afrika",
                conservationStatus = "Vulnerable",
                diet = "Baumblätter (hauptsächlich Akazien)",
                habitat = listOf("Savanne"),
                funFacts = listOf("Giraffen haben lange Zungen bis zu 50 cm", "Mit bis zu 6 Metern Höhe sind sie die höchsten Landtiere")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "2",
                name = "Giraffe",
                shortDescription = "The tallest land animal on Earth with a height of up to 6 meters.",
                scientific_name = "Giraffa camelopardalis",
                continent = "Africa",
                conservationStatus = "Vulnerable",
                diet = "Tree leaves (mainly acacias)",
                habitat = listOf("Savannah"),
                funFacts = listOf("Giraffes have long tongues up to 50 cm", "At up to 6 meters tall, they are the tallest land animals")
            )
        ),
        AnimalWrapper(
            id = "3",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "3",
                name = "Asiatischer Elefant",
                shortDescription = "Mit bis zu drei Metern die zweitgrößten Landtiere der Erde. Ihr Rüssel besitzt rund 40.000 Muskeln und wird vielseitig als Greifwerkzeug, Schnorchel und zur Kommunikation eingesetzt.",
                scientific_name = "Elephas maximus",
                continent = "Asien",
                conservationStatus = "Stark gefährdet",
                diet = "Gras, Äste und Heu (eine Badewanne voll pro Tag), 100-200 Liter Wasser täglich",
                habitat = listOf("vorwiegend tropische Wälder"),
                funFacts = listOf("Der Rüssel besitzt rund 40.000 Muskeln und wird als Schnorchel beim Schwimmen verwendet", "Sie haben nur einen Greiffinger am Rüssel (Afrikanische Elefanten haben zwei)")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "3",
                name = "Asian Elephant",
                shortDescription = "At up to three meters tall, they are the second largest land animals on Earth. Their trunk has around 40,000 muscles and is used versatilely as a gripping tool, snorkel, and for communication.",
                scientific_name = "Elephas maximus",
                continent = "Asia",
                conservationStatus = "Endangered",
                diet = "Grass, branches, and hay (a bathtub full per day), 100-200 liters of water daily",
                habitat = listOf("Predominantly tropical forests"),
                funFacts = listOf("The trunk has around 40,000 muscles and is used as a snorkel when swimming", "They have only one finger-like projection at the trunk tip (African elephants have two)")
            )
        ),
        AnimalWrapper(
            id = "4",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "4",
                name = "Alpaka",
                shortDescription = "Seit vielen Tausend Jahren werden Alpakas in den Anden zur Wollgewinnung gezüchtet. Sie fressen sehr wenig und sind perfekt an die rauen Lebensbedingungen angepasst.",
                scientific_name = "Vicugna pacos",
                continent = "Amerika",
                conservationStatus = "Nicht beurteilt",
                diet = "Gras (ca. 1 kg pro Tag)",
                habitat = listOf("Kalte Höhenlagen", "Grasland und Steppen"),
                funFacts = listOf("Alpakas und Lamas spucken mit erstaunlicher Treffsicherheit", "Die Spucke besteht hauptsächlich aus Futterbrei und ätzender Magensäure")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "4",
                name = "Alpaca",
                shortDescription = "Alpacas have been bred in the Andes for thousands of years for their wool. They eat very little and are perfectly adapted to harsh living conditions.",
                scientific_name = "Vicugna pacos",
                continent = "America",
                conservationStatus = "Not evaluated",
                diet = "Grass (approx. 1 kg per day)",
                habitat = listOf("Cold high altitudes", "Grasslands and steppes"),
                funFacts = listOf("Alpacas and llamas spit with amazing accuracy", "The spit consists mainly of food pulp and corrosive stomach acid")
            )
        ),
        AnimalWrapper(
            id = "5",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "5",
                name = "Alpensteinbock",
                shortDescription = "Mitte des 19. Jahrhunderts nahezu ausgerottet, konnte sich der Alpensteinbock durch Schutzmaßnahmen und Auswilderungsprojekte erholen und gilt heute nicht mehr als gefährdet.",
                scientific_name = "Capra ibex",
                continent = "Europa",
                conservationStatus = "Nicht gefährdet",
                diet = "Gras und Kräuter",
                habitat = listOf("Gebirge"),
                funFacts = listOf("Die Hörner der Böcke erreichen über einen Meter Länge", "Beim Kampf stoßen sie ihre Hörner mit großer Wucht aufeinander")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "5",
                name = "Alpine Ibex",
                shortDescription = "Nearly extinct in the mid-19th century, the Alpine Ibex has recovered through conservation measures and reintroduction projects and is no longer considered endangered.",
                scientific_name = "Capra ibex",
                continent = "Europe",
                conservationStatus = "Least Concern",
                diet = "Grass and herbs",
                habitat = listOf("Mountains"),
                funFacts = listOf("The horns of the bucks reach over a meter in length", "During fights, they clash their horns together with great force")
            )
        ),
        AnimalWrapper(
            id = "6",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "6",
                name = "Alpensteinhuhn",
                shortDescription = "Alpensteinhühner sind in felsigen Steilhängen hervorragend getarnt und geschickte Kletterer. Im bayerischen Alpenraum sind sie einer der seltensten Brutvögel.",
                scientific_name = "Alectoris graeca saxatilis",
                continent = "Welt der Vögel",
                conservationStatus = "Gefährdet",
                diet = "Samen und Insekten",
                habitat = listOf("offene Bergregionen"),
                funFacts = listOf("Vor Gefahren fliehen sie lieber laufend als fliegend", "Sie verstecken sich in unzugänglichen Felsspalten")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "6",
                name = "Rock Partridge",
                shortDescription = "Rock Partridges are excellently camouflaged in rocky steep slopes and are skilled climbers. In the Bavarian Alps, they are one of the rarest breeding birds.",
                scientific_name = "Alectoris graeca saxatilis",
                continent = "World of Birds",
                conservationStatus = "Endangered",
                diet = "Seeds and insects",
                habitat = listOf("Open mountain regions"),
                funFacts = listOf("Despite their good flying skills, they prefer to flee by running", "They hide in inaccessible rock crevices")
            )
        ),
        AnimalWrapper(
            id = "7",
            de = org.hackatum.zoolens.model.AnimalContent(
                id = "7",
                name = "Aldabra-Riesenschildkröte",
                shortDescription = "Mit bis zu 1,2 m Panzerlänge werden sie nicht nur sehr groß, sondern auch sehr alt. 100 Jahre alte Tiere sind keine Seltenheit, manche können über 250 Jahre alt werden.",
                scientific_name = "Geochelone gigantea",
                continent = "Welt der Reptilien",
                conservationStatus = "Gefährdet",
                diet = "Gräser und Pflanzen",
                habitat = listOf("Küstengebiete sowie offene Gras- und Buschlandschaften"),
                funFacts = listOf("Sie können Flüssigkeit nicht nur durch den Mund, sondern auch durch die Nase aufnehmen", "Sie können über 250 Jahre alt werden")
            ),
            en = org.hackatum.zoolens.model.AnimalContent(
                id = "7",
                name = "Aldabra Giant Tortoise",
                shortDescription = "With a shell length of up to 1.2 m, they not only grow very large but also very old. Animals 100 years old are not uncommon; some can live to be over 250 years old.",
                scientific_name = "Geochelone gigantea",
                continent = "World of Reptiles",
                conservationStatus = "Vulnerable",
                diet = "Grasses and plants",
                habitat = listOf("Coastal areas as well as open grass and bush landscapes"),
                funFacts = listOf("They can intake liquid not only through their mouths but also through their noses", "They can live to be over 250 years old")
            )
        )
    )[(id.toIntOrNull() ?: 1) - 1]
    val content = contentWrapper.getContent(language)

    // Get the appropriate drawable resource based on animal ID
    val imageResource = when (id) {
        "1" -> Res.drawable.loewe
        "2" -> Res.drawable.giraffe
        "3" -> Res.drawable.asiatischer_elefant
        "4" -> Res.drawable.alpaka
        "5" -> Res.drawable.alpensteinbock
        "6" -> Res.drawable.alpensteinhuhn
        "7" -> Res.drawable.aldabra_riesenschildkroete
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
        AssistantPanel(userInput, llmOutput, isLoading)
    }
}