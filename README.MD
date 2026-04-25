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

## 🛠 Tech Stack Suggestions (For the Developer)
* **Frontend:** React Native / Expo or Flutter (for seamless iOS/Android GPS and Map integration).
* **Backend:** Firebase, Supabase, or Node.js with PostgreSQL (for user data, pigeon stats, and message queues).
* **Maps:** Mapbox GL or Google Maps SDK with custom, minimalist map styling.
* **Background Tasks:** Background workers to calculate pigeon arrival times and send push notifications upon delivery.
