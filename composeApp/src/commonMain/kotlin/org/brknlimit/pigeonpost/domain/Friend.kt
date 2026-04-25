package org.brknlimit.pigeonpost.domain

data class Friend(
    val id: String,
    val name: String,
    val distanceKm: Int // Integer distance as per privacy rules
)
