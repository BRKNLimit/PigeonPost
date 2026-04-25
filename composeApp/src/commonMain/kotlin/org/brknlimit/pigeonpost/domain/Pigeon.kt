package org.brknlimit.pigeonpost.domain

enum class PigeonStatus {
    IDLE, FLYING, RESTING
}

data class Pigeon(
    val id: String,
    val name: String,
    val level: Int = 1,
    val xp: Int = 0,
    val status: PigeonStatus = PigeonStatus.IDLE
) {
    val speed: Int
        get() = 20 + (level - 1) * 5

    val endurance: Int
        get() = 50 + (level - 1) * 25

    val maxCharacters: Int
        get() = when {
            level <= 3 -> 140
            level <= 6 -> 500
            else -> Int.MAX_VALUE
        }

    val canSendImages: Boolean
        get() = level >= 7

    fun withExperience(distanceKm: Int): Pigeon {
        val xpGained = distanceKm / 10
        var newXp = xp + xpGained
        var newLevel = level
        
        while (newXp >= xpRequiredForLevel(newLevel + 1)) {
            newLevel++
        }
        
        return copy(level = newLevel, xp = newXp)
    }

    companion object {
        fun xpRequiredForLevel(level: Int): Int {
            if (level <= 1) return 0
            // Formula: 100 * (level-1) + 50 * (level-1)^2 ?
            // Let's use something simple but progressive:
            // Lvl 2: 100
            // Lvl 3: 250 (100 + 150)
            // Lvl 4: 450 (250 + 200)
            return (level - 1) * 100 + (level - 1) * (level - 2) * 25
        }
    }
}
