package org.hackatum.zoolens.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun loadAnimalsJsonString(context: Any?): String {
    val androidContext = context as? android.content.Context
        ?: error("Android Context required for loading animals.json")
    return withContext(Dispatchers.IO) {
        androidContext.assets.open("files/animals.json").bufferedReader().use { it.readText() }
    }
}

