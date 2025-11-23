package org.hackatum.zoolens

import kotlinx.serialization.Serializable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Serializable
class ChatRequest(val message: String)
