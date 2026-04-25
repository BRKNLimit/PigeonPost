package org.brknlimit.pigeonpost.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PigeonTest {

    @Test
    fun testPigeonStatsLevel1() {
        val pigeon = Pigeon(id = "1", name = "Pidgey", level = 1)
        assertEquals(20, pigeon.speed)
        assertEquals(50, pigeon.endurance)
        assertEquals(140, pigeon.maxCharacters)
        assertFalse(pigeon.canSendImages)
    }

    @Test
    fun testPigeonStatsLevel5() {
        val pigeon = Pigeon(id = "1", name = "Pidgey", level = 5)
        // Speed: 20 + (5-1)*5 = 40
        assertEquals(40, pigeon.speed)
        // Endurance: 50 + (5-1)*25 = 150
        assertEquals(150, pigeon.endurance)
        // Capacity: Lvl 5 -> 500 chars
        assertEquals(500, pigeon.maxCharacters)
        assertFalse(pigeon.canSendImages)
    }

    @Test
    fun testPigeonStatsLevel10() {
        val pigeon = Pigeon(id = "1", name = "Pidgey", level = 10)
        // Speed: 20 + (10-1)*5 = 65
        assertEquals(65, pigeon.speed)
        // Endurance: 50 + (10-1)*25 = 275
        assertEquals(275, pigeon.endurance)
        // Capacity: Lvl 10 -> Unlimited
        assertEquals(Int.MAX_VALUE, pigeon.maxCharacters)
        assertTrue(pigeon.canSendImages)
    }

    @Test
    fun testXpGainAndLevelUp() {
        var pigeon = Pigeon(id = "1", name = "Pidgey", level = 1, xp = 0)
        
        // Gain 50 XP (500 km)
        pigeon = pigeon.withExperience(500)
        assertEquals(50, pigeon.xp)
        assertEquals(1, pigeon.level)
        
        // Gain another 50 XP (500 km) -> Total 100 XP -> Level 2
        pigeon = pigeon.withExperience(500)
        assertEquals(100, pigeon.xp)
        assertEquals(2, pigeon.level)
        
        // Gain 150 XP (1500 km) -> Total 250 XP -> Level 3
        pigeon = pigeon.withExperience(1500)
        assertEquals(250, pigeon.xp)
        assertEquals(3, pigeon.level)
    }

    @Test
    fun testMultiLevelUp() {
        val pigeon = Pigeon(id = "1", name = "Pidgey", level = 1, xp = 0)
        
        // Gain 1000 XP at once (10,000 km)
        val leveledPigeon = pigeon.withExperience(10000)
        assertEquals(1000, leveledPigeon.xp)
        // Lvl 2: 100
        // Lvl 3: 250
        // Lvl 4: 2*100 + 2*1*25 = 200 + 50 = 250? Wait my formula is (level-1)*100 + (level-1)*(level-2)*25
        // Lvl 4: 3*100 + 3*2*25 = 300 + 150 = 450
        // Lvl 5: 4*100 + 4*3*25 = 400 + 300 = 700
        // Lvl 6: 5*100 + 5*4*25 = 500 + 500 = 1000
        assertEquals(6, leveledPigeon.level)
    }
}
