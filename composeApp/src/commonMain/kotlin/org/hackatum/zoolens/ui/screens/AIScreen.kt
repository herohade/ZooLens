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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.network.apiClient
import org.jetbrains.compose.resources.painterResource
import zoolens.composeapp.generated.resources.Res
import zoolens.composeapp.generated.resources.compose_multiplatform
import zoolens.composeapp.generated.resources.send

@Composable
fun AIScreen() {
    val userInput = remember { mutableStateOf("") }
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
                )
            )

            IconButton(
                onClick = {
                    // TODO: Send message to LLM
                    // Define data classes for serialization
                    val message = userInput.value
                    if (message.isNotBlank()) {
                        // --- TODO Implemented ---
                        coroutineScope.launch {
                            try {
                                // NOTE: For Android Emulator, use 10.0.2.2 instead of localhost
                                val response: String =
                                    apiClient.post("http://10.0.2.2:8000/chat") {
                                        contentType(ContentType.Application.Json)
                                        setBody(ChatRequest(message = message))
                                    }.bodyAsText()

                                println("LLM Response: $response")
                                // TODO: Do something with the response, e.g., display it in the UI

                            } catch (e: Exception) {
                                println("Error: ${e.message}")
                                // TODO: Show an error message to the user
                            }
                        }
                    }
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