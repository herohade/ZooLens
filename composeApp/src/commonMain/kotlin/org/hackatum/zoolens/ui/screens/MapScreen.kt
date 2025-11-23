package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import org.hackatum.zoolens.i18n.LocalStrings
import org.jetbrains.compose.resources.painterResource
import zoolens.composeapp.generated.resources.tierparkplan
import zoolens.composeapp.generated.resources.Res




@Composable
fun MapScreen() {
    val strings = LocalStrings.current
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 4f)
        val limit = 800f * scale
        offset = Offset(
            x = (offset.x + panChange.x).coerceIn(-limit, limit),
            y = (offset.y + panChange.y).coerceIn(-limit, limit)
        )
    }

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

        // Map content
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
        }
    }
}
