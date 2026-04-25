# 🧠 Project Context for Gemini AI

## Role & Objective
You are acting as the lead AI developer and architect for **PigeonPost**. Your goal is to help build this gamified, location-based mobile application. Whenever generating code, designing database schemas, or writing logic, you must strictly adhere to the domain rules and mechanics outlined below.

## 📐 Core App Mechanics & Rules

### 1. Location & Privacy (Strict Rule)
* **Self:** The user's device tracks and stores their exact GPS coordinates to display on their local map.
* **Friends:** The app **must never** expose the exact coordinates of a user to their friends.
* **Distance Calculation:** The backend must calculate the Haversine distance between User A and User B. Only the integer distance (e.g., "45 km away") is sent to the client. Do not send coordinates over the API to other clients.

### 2. The Pigeon Object (Data Model)
Every pigeon is an entity tied to a user with the following dynamic attributes:
* `id`: UUID
* `name`: String
* `level`: Integer (Starts at 1)
* `xp`: Integer
* `status`: Enum (`IDLE`, `FLYING`, `RESTING`)

### 3. Pigeon Progression System (The Math)
When writing logic for pigeon travel, use these baseline scaling rules (feel free to write helper functions to calculate these dynamically based on the `level`):
* **Speed:** Determines delivery time.
    * *Base (Lvl 1):* 20 km/h
    * *Scale:* +5 km/h per level.
* **Endurance (Max Non-Stop Flight):**
    * *Base (Lvl 1):* 50 km
    * *Scale:* +25 km per level.
    * *Mechanic:* If the total distance exceeds the Endurance stat, the pigeon must enter a `RESTING` state midway through the journey (e.g., adding a flat 2-hour penalty to the delivery time).
* **Capacity (Payload):**
    * *Level 1-3:* Max 140 characters of text. No images.
    * *Level 4-6:* Max 500 characters of text. No images.
    * *Level 7+:* Unlimited text + 1 Image attachment.

### 4. Experience Points (XP) Calculation
* Pigeons gain XP based on the distance traveled. 
* *Formula suggestion:* `1 XP per 10 km traveled`.
* Leveling up should require progressively more XP (e.g., Level 2 requires 100 XP, Level 3 requires 250 XP, etc.).

### 5. Code Style & Architecture Guidelines
* Write modular, clean, and well-commented code.
* Prioritize mobile-first UI components.
* When generating frontend code, ensure map components are lightweight and visually minimalist.
* When generating backend code, assume the use of background jobs (e.g., CRON or task queues) to handle the state transitions of pigeons (`FLYING` -> `RESTING` -> `DELIVERED`).

## 💬 Prompting the AI
When I ask you to build a feature, refer to these rules. If a request contradicts the privacy rule (e.g., "show the friend on the map"), you must refuse and suggest a compliant alternative (e.g., "I will display a directional radar or just the distance instead").
