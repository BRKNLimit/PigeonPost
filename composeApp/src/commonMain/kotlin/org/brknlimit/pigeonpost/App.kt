package org.brknlimit.pigeonpost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.brknlimit.pigeonpost.data.AviaryManager
import org.brknlimit.pigeonpost.data.FriendRadarManager
import org.brknlimit.pigeonpost.data.MessageManager
import org.brknlimit.pigeonpost.domain.Friend
import org.brknlimit.pigeonpost.domain.Pigeon
import org.brknlimit.pigeonpost.domain.PigeonStatus
import org.brknlimit.pigeonpost.domain.Postcard

@Composable
fun App() {
    val pigeons by AviaryManager.pigeons.collectAsState()
    val friends by FriendRadarManager.friends.collectAsState()
    val outbox by MessageManager.outbox.collectAsState()
    
    var selectedTab by remember { mutableStateOf(0) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("PigeonPost") })
            },
            bottomBar = {
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                        Text("Aviary", modifier = Modifier.padding(16.dp))
                    }
                    Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                        Text("Radar", modifier = Modifier.padding(16.dp))
                    }
                    Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                        Text("Outbox", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                when (selectedTab) {
                    0 -> AviaryScreen(pigeons)
                    1 -> RadarScreen(friends, pigeons)
                    2 -> OutboxScreen(outbox)
                }
            }
        }
    }
}

@Composable
fun AviaryScreen(pigeons: List<Pigeon>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pigeons) { pigeon ->
            PigeonCard(pigeon)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RadarScreen(friends: List<Friend>, pigeons: List<Pigeon>) {
    var selectedFriend by remember { mutableStateOf<Friend?>(null) }
    var selectedPigeon by remember { mutableStateOf<Pigeon?>(null) }
    var messageText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Nearby Friends", style = MaterialTheme.typography.h6)
        LazyColumn(
            modifier = Modifier.weight(1f).padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(friends) { friend ->
                Card(
                    backgroundColor = if (selectedFriend == friend) Color.LightGray else Color.White,
                    onClick = { selectedFriend = friend },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("${friend.name} - ${friend.distanceKm} km away", modifier = Modifier.padding(8.dp))
                }
            }
        }

        if (selectedFriend != null) {
            Divider()
            Text("Select a Pigeon", style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(top = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pigeons.filter { it.status == PigeonStatus.IDLE }.forEach { pigeon ->
                    Button(
                        onClick = { selectedPigeon = pigeon },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (selectedPigeon == pigeon) MaterialTheme.colors.primary else MaterialTheme.colors.surface
                        )
                    ) {
                        Text(pigeon.name)
                    }
                }
            }

            if (selectedPigeon != null) {
                TextField(
                    value = messageText,
                    onValueChange = { if (it.length <= selectedPigeon!!.maxCharacters) messageText = it },
                    placeholder = { Text("Write your message...") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                Text(
                    "${messageText.length} / ${if (selectedPigeon!!.maxCharacters == Int.MAX_VALUE) "∞" else selectedPigeon!!.maxCharacters}",
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.caption
                )
                
                Button(
                    onClick = {
                        MessageManager.sendPostcard(
                            "Me",
                            selectedFriend!!.id,
                            messageText,
                            selectedPigeon!!,
                            selectedFriend!!.distanceKm.toDouble()
                        )
                        messageText = ""
                        selectedFriend = null
                        selectedPigeon = null
                    },
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Dispatch")
                }
            }
        }
    }
}

@Composable
fun OutboxScreen(outbox: List<Postcard>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(outbox) { postcard ->
            Card(elevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("To: ${postcard.recipientId}")
                    Text("Message: ${postcard.message}")
                    Text("ETA: ${java.util.Date(postcard.estimatedArrivalAt)}")
                }
            }
        }
    }
}

@Composable
fun PigeonCard(pigeon: Pigeon) {
    Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = pigeon.name, style = MaterialTheme.typography.h6)
                Text(text = "Lvl ${pigeon.level}", style = MaterialTheme.typography.subtitle1)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status: ${pigeon.status}")
            Text(text = "Speed: ${pigeon.speed} km/h")
            Text(text = "Endurance: ${pigeon.endurance} km")
            Text(text = "XP: ${pigeon.xp}")
            
            val nextLevelXp = Pigeon.xpRequiredForLevel(pigeon.level + 1)
            LinearProgressIndicator(
                progress = pigeon.xp.toFloat() / nextLevelXp.toFloat(),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
            Text(
                text = "${pigeon.xp} / $nextLevelXp XP",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
