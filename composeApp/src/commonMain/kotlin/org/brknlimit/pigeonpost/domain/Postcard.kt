package org.brknlimit.pigeonpost.domain

data class Postcard(
    val id: String,
    val senderId: String,
    val recipientId: String,
    val message: String,
    val imageUrl: String? = null,
    val sentAt: Long,
    val estimatedArrivalAt: Long
)
