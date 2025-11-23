package org.hackatum.zoolens.ui.screens

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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.model.AnimalWrapper
import org.hackatum.zoolens.model.getContent
import org.hackatum.zoolens.model.loadAnimalsFromJson
import kotlin.math.max

@Composable
fun SearchScreen(
    language: String = "de",
    onOpenAnimal: (id: String) -> Unit = {},
    context: Any? = null
) {
    val animalsState = remember { mutableStateOf<List<AnimalWrapper>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            animalsState.value = loadAnimalsFromJson(context)
        }
    }

    // Search query state
    var query by rememberSaveable { mutableStateOf("") }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top)
        ) {
            // Title with extra spacing and bold style
            Text(
                text = LocalStrings.current.search,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            // Search field with rounded corners and subtle shadow
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text(LocalStrings.current.search) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true
                )
            }

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
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                val spacing = 16.dp
                // adapt min cell size depending on available width (phone vs tablet vs desktop)
                val minCell = when {
                    maxWidth < 420.dp -> 150.dp
                    maxWidth < 800.dp -> 200.dp
                    else -> 240.dp
                }

                // Calculate columns based on available width. Ensure at least 2 columns on phones.
                val rawColumns = ((maxWidth + spacing) / (minCell + spacing)).toInt()
                val columns = max(2, rawColumns).coerceAtMost(6) // cap to avoid too many columns

                // compute exact item width so columns + spacing fill the available width exactly
                val totalSpacing = spacing * (columns - 1)
                val itemWidth: Dp = (maxWidth - totalSpacing) / columns

                if (filtered.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = LocalStrings.current.noResult,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(32.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(spacing),
                        verticalArrangement = Arrangement.spacedBy(spacing)
                    ) {
                        items(filtered) { wrapper ->
                            val content = wrapper.getContent(language)
                            AnimalGridItem(
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
private fun AnimalGridItem(name: String, itemWidth: Dp, onClick: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .width(itemWidth)
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
