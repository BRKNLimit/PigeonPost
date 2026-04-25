package org.brknlimit.pigeonpost.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.brknlimit.pigeonpost.domain.Pigeon
import org.brknlimit.pigeonpost.domain.PigeonStatus
import org.brknlimit.pigeonpost.domain.Postcard
import org.brknlimit.pigeonpost.domain.TravelCalculator

object MessageManager {
    private val _outbox = MutableStateFlow<List<Postcard>>(emptyList())
    val outbox: StateFlow<List<Postcard>> = _outbox

    fun sendPostcard(senderId: String, recipientId: String, message: String, pigeon: Pigeon, distanceKm: Double) {
        val sentAt = System.currentTimeMillis()
        val travelTimeMs = TravelCalculator.calculateDeliveryTimeMs(distanceKm, pigeon)
        val arrivalAt = sentAt + travelTimeMs

        val postcard = Postcard(
            id = (System.currentTimeMillis()).toString(),
            senderId = senderId,
            recipientId = recipientId,
            message = message,
            sentAt = sentAt,
            estimatedArrivalAt = arrivalAt
        )

        _outbox.value += postcard

        // Update pigeon status
        AviaryManager.updatePigeon(pigeon.copy(status = PigeonStatus.FLYING))
        
        // In a real app, the backend would handle the arrival and pigeon return.
        // For this demo, we can simulate the pigeon returning and gaining XP if needed.
    }
}
