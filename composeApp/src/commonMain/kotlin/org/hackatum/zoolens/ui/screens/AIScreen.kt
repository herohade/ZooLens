package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
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
import org.jetbrains.compose.resources.painterResource
import zoolens.composeapp.generated.resources.Res
import zoolens.composeapp.generated.resources.compose_multiplatform

val apiClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

@Composable
fun AIScreen() {
    val userInput = remember { mutableStateOf("") }
    val llmOutput = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(LocalStrings.current.ai, style = MaterialTheme.typography.headlineMedium)
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
                    // TODO: Send message to LLM
                    // Define data classes for serialization
                    val message = userInput.value
                    if (message.isNotBlank()) {
                        // --- TODO Implemented ---
                        coroutineScope.launch {
                            isLoading.value = true
                            try {
                                // NOTE: For Android Emulator, use 10.0.2.2 instead of localhost
                                val response: String =
                                    apiClient.post("http://10.0.2.2:8000/chat") {
                                        contentType(ContentType.Application.Json)
                                        setBody(ChatRequest(message = message))
                                    }.bodyAsText()

                                println("LLM Response: $response")
                                llmOutput.value = Json.parseToJsonElement(response).jsonObject["response"]?.toString() ?: ""
                                userInput.value = ""

                                // TODO: Do something with the response, e.g., display it in the UI

                            } catch (e: Exception) {
                                println("Error: ${e.message}")
                                // TODO: Show an error message to the user
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
//                        imageVector = Icons.AutoMirrored.Filled.Send,
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
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = llmOutput.value.trim('"'),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}