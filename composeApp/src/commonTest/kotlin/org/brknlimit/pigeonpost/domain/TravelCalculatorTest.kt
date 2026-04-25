package org.brknlimit.pigeonpost.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TravelCalculatorTest {

    @Test
    fun testCalculateDeliveryTimeNoRest() {
        val pigeon = Pigeon(id = "1", name = "Fast", level = 1) // Speed 20, Endurance 50
        val distance = 40.0
        val timeMs = TravelCalculator.calculateDeliveryTimeMs(distance, pigeon)
        
        // 40km / 20kmh = 2 hours = 7,200,000 ms
        assertEquals(7200000L, timeMs)
    }

    @Test
    fun testCalculateDeliveryTimeWithRest() {
        val pigeon = Pigeon(id = "1", name = "Fast", level = 1) // Speed 20, Endurance 50
        val distance = 60.0
        val timeMs = TravelCalculator.calculateDeliveryTimeMs(distance, pigeon)
        
        // 60km / 20kmh = 3 hours = 10,800,000 ms
        // + 2 hours rest penalty = 7,200,000 ms
        // Total = 18,000,000 ms
        assertEquals(18000000L, timeMs)
    }

    @Test
    fun testHaversineDistance() {
        // Distance between Berlin and Munich is roughly 500km
        val berlinLat = 52.5200
        val berlinLon = 13.4050
        val munichLat = 48.1351
        val munichLon = 11.5820
        
        val distance = TravelCalculator.haversineDistance(berlinLat, berlinLon, munichLat, munichLon)
        assertTrue(distance > 490 && distance < 510)
    }
}
