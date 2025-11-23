package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.model.AnimalWrapper
import org.hackatum.zoolens.model.getContent
import org.jetbrains.compose.resources.painterResource
import zoolens.composeapp.generated.resources.Res
import zoolens.composeapp.generated.resources.loewe
import zoolens.composeapp.generated.resources.giraffe
import zoolens.composeapp.generated.resources.asiatischer_elefant
import zoolens.composeapp.generated.resources.alpaka
import zoolens.composeapp.generated.resources.alpensteinbock
import zoolens.composeapp.generated.resources.alpensteinhuhn
import zoolens.composeapp.generated.resources.aldabra_riesenschildkroete
import kotlin.math.max

@Composable
fun SearchScreen(
    onOpenAnimal: (id: String) -> Unit = {}
) {
    val animalsState = remember { mutableStateOf<List<AnimalWrapper>>(emptyList()) }

    // Get language dynamically from LocalStrings
    val strings = LocalStrings.current
    val language = if (strings.home == "Start") "de" else "en"

    LaunchedEffect(Unit) {
        // Use a hardcoded sample list for frontend testing (7 animals)
        animalsState.value = listOf(
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
                    diet = "Pflanzenfresser",
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
                    funFacts = listOf("Vor Gefahren fliehen sie lieber laufend als fliegend", "Sie verstecken sich in unzugaenglichen Felsspalten")
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
        )
    }

    // Search query state
    var query by rememberSaveable { mutableStateOf("") }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        // Use a single horizontal padding for column so search field and grid align exactly
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {
            // Title centered
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(LocalStrings.current.search, style = MaterialTheme.typography.headlineMedium)
            }

            // Search field uses same horizontal bounds (no extra padding)
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text(LocalStrings.current.search) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            // Filtered list computed from the full animals list
            val filtered = remember(query, animalsState.value, language) {
                val q = query.trim().lowercase()
                if (q.isEmpty()) {
                    animalsState.value
                } else {
                    animalsState.value.filter { wrapper ->
                        val content = wrapper.getContent(language)
                        val name = content.name.lowercase()
                        val desc = content.shortDescription.lowercase()
                        val sci = content.scientific_name?.lowercase() ?: ""
                        name.contains(q) || desc.contains(q) || sci.contains(q)
                    }
                }
            }

            // Responsive grid: compute number of columns based on available width
            BoxWithConstraints(modifier = Modifier
                .fillMaxWidth()) {
                val spacing = 12.dp
                // adapt min cell size depending on available width (phone vs tablet vs desktop)
                val minCell = when {
                    maxWidth < 420.dp -> 140.dp
                    maxWidth < 800.dp -> 180.dp
                    else -> 220.dp
                }

                // Calculate columns based on available width. Ensure at least 2 columns on phones.
                val rawColumns = ((maxWidth + spacing) / (minCell + spacing)).toInt()
                val columns = max(2, rawColumns).coerceAtMost(8) // cap to avoid too many columns

                // compute exact item width so columns + spacing fill the available width exactly
                val totalSpacing = spacing * (columns - 1)
                val itemWidth: Dp = (maxWidth - totalSpacing) / columns

                if (filtered.isEmpty()) {
                    Text(
                        text = LocalStrings.current.noResult,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(spacing),
                        verticalArrangement = Arrangement.spacedBy(spacing)
                    ) {
                        items(filtered) { wrapper ->
                            val content = wrapper.getContent(language)
                            AnimalGridItem(
                                id = wrapper.id,
                                name = content.name,
                                itemWidth = itemWidth,
                                onClick = { onOpenAnimal(wrapper.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimalGridItem(id: String, name: String, itemWidth: Dp, onClick: () -> Unit = {}) {
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

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(itemWidth)
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Image fills the entire card
            Image(
                painter = painterResource(imageResource),
                contentDescription = name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Name overlay at the bottom with semi-transparent background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}
