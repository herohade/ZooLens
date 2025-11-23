package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import org.hackatum.zoolens.i18n.LocalStrings
import org.jetbrains.compose.resources.painterResource
import zoolens.composeapp.generated.resources.tierparkplan
import zoolens.composeapp.generated.resources.Res

// Data class for animal pins
data class AnimalPin(
    val id: String,
    val name: String,
    val nameEn: String,
    val x: Float,
    val y: Float
)

@Composable
fun MapScreen() {
    val strings = LocalStrings.current
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var selectedPin by remember { mutableStateOf<AnimalPin?>(null) }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 4f)
        val limit = 800f * scale
        offset = Offset(
            x = (offset.x + panChange.x).coerceIn(-limit, limit),
            y = (offset.y + panChange.y).coerceIn(-limit, limit)
        )
    }

    // Define animal pins with their positions on the map
    val baseAnimalPins = listOf(
        AnimalPin("1", "Löwe", "Lion", 112f, 389f),                                    // ✓ Positioned
        AnimalPin("2", "Giraffe", "Giraffe", 58f, 344f),                             // ✓ Positioned
        AnimalPin("3", "Asiatischer Elefant", "Asian Elephant", 147f, 299f),        // ✓ Positioned
        AnimalPin("4", "Alpaka", "Alpaca", 194f, 316f),                             // ✓ Positioned
        AnimalPin("5", "Alpensteinbock", "Alpine Ibex", 76f, 384f),                 // ✓ Positioned
        AnimalPin("6", "Alpensteinhuhn", "Rock Partridge", 268f, 402f),             // ✓ Positioned
        AnimalPin("7", "Aldabra-Riesenschildkröte", "Aldabra Giant Tortoise", 170f, 306f) // ✓ Positioned
    )

    // Apply adjusted positions
    val animalPins = baseAnimalPins

    val isGerman = strings.home == "Start"

    Column(modifier = Modifier.fillMaxSize()) {
        // Title bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = strings.map,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        // Outer overlay so we can keep controls fixed while the map is transformable
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
             // Transformable map (image + pins + popup)
             Box(
                 modifier = Modifier
                     .fillMaxSize()
                     .transformable(state = transformableState)
                     .clipToBounds(),
                 contentAlignment = Alignment.Center
             ) {
                 Image(
                     painter = painterResource(Res.drawable.tierparkplan),
                     contentDescription = strings.map,
                     modifier = Modifier
                         .fillMaxSize()
                         .graphicsLayer(
                             scaleX = scale,
                             scaleY = scale,
                             translationX = offset.x,
                             translationY = offset.y,
                             clip = true
                         ),
                     contentScale = ContentScale.Fit
                 )

                 // Draw animal pins on the map
                 animalPins.forEach { pin ->
                     AnimalPinMarker(
                        pin = pin,
                         scale = scale,
                         offset = offset,
                         isSelected = selectedPin?.id == pin.id,
                         onPinClick = { selectedPin = if (selectedPin?.id == pin.id) null else pin },
                         xOverride = pin.x,
                         yOverride = pin.y
                     )
                 }

                 // Show popup for selected pin (still inside transformable so it moves with map)
                 if (selectedPin != null) {
                     Box(
                         modifier = Modifier
                             .align(Alignment.BottomCenter)
                             .padding(12.dp)
                             .clickable { selectedPin = null }
                     ) {
                         Card(
                             modifier = Modifier
                                 .fillMaxWidth(0.6f)
                                 .clickable { },
                             elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                             shape = RoundedCornerShape(8.dp)
                         ) {
                             Column(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .padding(12.dp),
                                 horizontalAlignment = Alignment.Start
                             ) {
                                 Text(
                                     text = if (isGerman) selectedPin!!.name else selectedPin!!.nameEn,
                                     style = MaterialTheme.typography.titleMedium,
                                     modifier = Modifier.fillMaxWidth()
                                 )
                                 Spacer(modifier = Modifier.height(6.dp))
                                 Text(
                                     text = if (isGerman) "Klick zum Schließen" else "Click to close",
                                     style = MaterialTheme.typography.bodySmall,
                                     color = MaterialTheme.colorScheme.secondary,
                                     modifier = Modifier.fillMaxWidth()
                                 )
                             }
                         }
                     }
                 }
             }
         }
     }
 }

 @Composable
 fun AnimalPinMarker(
     pin: AnimalPin,
     scale: Float,
     offset: Offset,
     isSelected: Boolean = false,
     onPinClick: () -> Unit,
     xOverride: Float? = null,
     yOverride: Float? = null
 ) {
     Box(
         modifier = Modifier.fillMaxSize(),
         contentAlignment = Alignment.TopStart
     ) {
         Box(
             modifier = Modifier
                 .fillMaxSize()
                 .graphicsLayer(
                     scaleX = scale,
                     scaleY = scale,
                     translationX = offset.x,
                     translationY = offset.y,
                     clip = true
                 ),
             contentAlignment = Alignment.TopStart
         ) {
             // Pin circle (noch etwas kleiner) - nutzt jetzt isSelected
             val pinSize = if (isSelected) 26.dp else 20.dp
             val pinColor = if (isSelected) Color(0xFFEA5252) else Color(0xFFFF6B6B)

             val drawX = xOverride ?: pin.x
             val drawY = yOverride ?: pin.y

             Box(
                 modifier = Modifier
                     .padding(start = drawX.dp, top = drawY.dp)
                     .size(pinSize)
                     .background(
                         color = pinColor,
                         shape = CircleShape
                     )
                     .clickable(onClick = onPinClick),
                 contentAlignment = Alignment.Center
             ) {
                 Text(
                     text = "📍",
                     style = MaterialTheme.typography.bodySmall
                 )
             }
         }
     }
 }
