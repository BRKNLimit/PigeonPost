package org.brknlimit.pigeonpost.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.brknlimit.pigeonpost.domain.Friend

object FriendRadarManager {
    private val _friends = MutableStateFlow<List<Friend>>(
        listOf(
            Friend("101", "Alice", 5),
            Friend("102", "Bob", 42),
            Friend("103", "Charlie", 120)
        )
    )
    val friends: StateFlow<List<Friend>> = _friends
}
