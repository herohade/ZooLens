package org.hackatum.zoolens

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform