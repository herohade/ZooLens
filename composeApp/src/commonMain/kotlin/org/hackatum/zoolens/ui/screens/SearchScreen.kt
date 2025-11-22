package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.model.AnimalWrapper
import org.hackatum.zoolens.model.getContent
import kotlin.math.max

@Composable
fun SearchScreen(
    language: String = "de",
    onOpenAnimal: () -> Unit = {}
) {
    val animalsState = remember { mutableStateOf<List<AnimalWrapper>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Use a hardcoded sample list for frontend testing (3 animals)
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

            // Button centered
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = onOpenAnimal) {
                    Text("Open Animal")
                }
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
                        text = "Keine Treffer",
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
                                name = content.name,
                                description = content.shortDescription,
                                itemWidth = itemWidth,
                                onClick = { /* TODO: navigate to details */ }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimalGridItem(name: String, description: String, itemWidth: Dp, onClick: () -> Unit = {}) {
    // Make each item a square by forcing aspect ratio 1:1
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(itemWidth)
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
