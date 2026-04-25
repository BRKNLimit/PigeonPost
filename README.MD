# 🕊️ PigeonPost

PigeonPost is a gamified, location-based social messenger that brings the charm of historical carrier pigeons into the digital age. Instead of instant messaging, users communicate by dispatching digital pigeons carrying "postcards" (text or images) to their friends. 

## ✨ Core Features

* **Live Map:** View your current real-world GPS location on a minimalist map.
* **Privacy-First Friend Radar:** See the approximate distance to your friends, but *never* their exact location or coordinates.
* **Digital Carrier Pigeons:** Send messages using your personal flock of virtual pigeons.
* **RPG & Progression System:** Your pigeons aren't static; they need to be trained! 
    * Send pigeons on journeys to earn experience points (XP).
    * Leveling up increases a pigeon's **Speed** (reduced delivery time), **Endurance** (maximum distance they can fly without a rest period), and **Capacity** (allowance for longer texts or image attachments).
* **Postcards:** Messages are formatted as stylized postcards, adding a personal and nostalgic touch.

## 🚀 How it Works

1.  **Draft a Postcard:** Write a message or snap a picture.
2.  **Select a Pigeon:** Choose a pigeon from your aviary. Keep an eye on their stats—a Level 1 pigeon might need to rest on a 500km journey, while a Level 10 pigeon will fly there non-stop!
3.  **Dispatch:** Watch your pigeon take off on your map. 
4.  **Wait for Delivery:** The delivery time is dynamically calculated based on the real-world distance between you and your friend, divided by your pigeon's current speed stat.

## 🛠 Tech Stack (Kotlin Multiplatform)
* **Architecture:** Kotlin Multiplatform (KMP) for sharing business logic, networking, and state management across iOS and Android.
* **UI Framework:** Compose Multiplatform (or native Jetpack Compose for Android / SwiftUI for iOS depending on specific map integrations).
* **Networking:** Ktor Client for API communication.
* **Backend:** Node.js, Spring Boot, or Supabase (for user data, pigeon stats, and message queues).
* **Maps:** Mapbox SDK or Google Maps SDK (utilizing expect/actual patterns for platform-specific map rendering).
