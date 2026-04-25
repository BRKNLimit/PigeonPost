package org.brknlimit.pigeonpost.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.brknlimit.pigeonpost.domain.Pigeon
import org.brknlimit.pigeonpost.domain.PigeonStatus

object AviaryManager {
    private val _pigeons = MutableStateFlow<List<Pigeon>>(
        listOf(
            Pigeon("1", "Pip", level = 1),
            Pigeon("2", "Sky", level = 5, xp = 150),
            Pigeon("3", "Ace", level = 10, xp = 2000)
        )
    )
    val pigeons: StateFlow<List<Pigeon>> = _pigeons

    fun updatePigeon(updatedPigeon: Pigeon) {
        _pigeons.value = _pigeons.value.map {
            if (it.id == updatedPigeon.id) updatedPigeon else it
        }
    }
}
