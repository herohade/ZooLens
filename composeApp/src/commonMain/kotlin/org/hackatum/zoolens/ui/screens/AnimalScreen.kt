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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import zoolens.composeapp.generated.resources.Res
import zoolens.composeapp.generated.resources.compose_multiplatform

@Composable
fun AnimalScreen() {
    val userInput = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Section 1: Animal name (hardcoded)
        Text(
            text = "Lion",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        // Section 2: Latin name (hardcoded)
        Text(
            text = "Panthera leo",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Section 3: Image (optional - not rendered here as per requirements)
        // Image would go here if available

        // Section 4: Description (scrollable, takes bulk of screen)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "The lion is a large felid of the genus Panthera native to the African savanna. " +
                    "It is characterized by its golden-tan coat and, in males, a distinctive mane around the head and neck. " +
                    "Lions are social animals and live in groups called prides. They are apex predators and play a crucial role " +
                    "in their ecosystem. Known as the 'King of Beasts,' lions have captivated human imagination for millennia. " +
                    "They are incredibly powerful hunters, with males weighing up to 250 kg. Despite their fearsome reputation, " +
                    "lions spend much of their time resting and sleeping.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Section 5: Assistant panel at the bottom with text input and send button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Text(
                text = "Ask Assistant",
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

                IconButton(
                    onClick = {
                        // TODO: Send message to LLM
                        userInput.value = ""
                    },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.Send,
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = "Send message"
                    )
                }
            }
        }
    }
}