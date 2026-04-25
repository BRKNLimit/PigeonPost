package org.brknlimit.pigeonpost.domain

import kotlin.math.*

object TravelCalculator {

    private const val REST_PENALTY_MS = 2 * 60 * 60 * 1000L // 2 hours in ms

    fun calculateDeliveryTimeMs(distanceKm: Double, pigeon: Pigeon): Long {
        val speedKmh = pigeon.speed.toDouble()
        val flightTimeHours = distanceKm / speedKmh
        var totalTimeMs = (flightTimeHours * 60 * 60 * 1000).toLong()

        if (distanceKm > pigeon.endurance) {
            totalTimeMs += REST_PENALTY_MS
        }

        return totalTimeMs
    }

    /**
     * Calculates the Haversine distance between two points in kilometers.
     */
    fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0 // Radius of the earth in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }
}
