package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.i18n.TicketCardData
import androidx.navigation.NavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun ZooLensCard(date: String, headline: String, description: String) {
    val maxHeadlineLines = 2
    val maxDescriptionLines = 3
    Card(
        modifier = Modifier
            .width(400.dp)
            .height(240.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(date, style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(8.dp))
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium,
                maxLines = maxHeadlineLines,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = maxDescriptionLines,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TicketCard(ticket: TicketCardData) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(ticket.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            Button(onClick = { uriHandler.openUri(ticket.link) }) {
                Text(ticket.buttonText)
            }
        }
    }
}

@Composable
@Preview
fun HomeScreen(navController: NavController) {
    val headline = LocalStrings.current.newsHeadline
    val newsCards = LocalStrings.current.newsCards
    val ticketHeadline = LocalStrings.current.ticketsHeadline
    val ticketCards = LocalStrings.current.ticketCards
    val uriHandler = LocalUriHandler.current
    val readMoreText = LocalStrings.current.newsCardReadMore

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding()
                .height(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tierpark Hellabrunn",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
        Spacer(Modifier.height(16.dp))

        Scaffold { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(headline, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    itemsIndexed(newsCards) { _, card ->
                        Box {
                            ZooLensCard(date = card.date, headline = card.headline, description = card.description)
                            Button(
                                onClick = { uriHandler.openUri(card.link) },
                                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                            ) {
                                Text(readMoreText)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(32.dp))
                Text(ticketHeadline, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ticketCards) { ticket ->
                        TicketCard(ticket = ticket)
                    }
                }
            }
        }
    }
}
