# Ping Android App – Master’s Project

**Program:** M.S. Computer Science  
**Semester:** Spring 2024

---

## Overview
This Android app helps users check if a server or website is reachable using the ping command. It's a simple tool built as part of my master’s project.

---

## Features
- Enter an IP address or domain and tap to ping
- View ping results with latency and success/failure
- Background ping service to monitor continuously
- Simple user interface with 2 screens

---

## Tech Stack
- Kotlin
- Android Studio
- ViewBinding
- Native shell ping command (`/system/bin/ping`)

---
## Files
MainActivity.kt      # User input
SecondActivity.kt    # Show ping results
PingService.kt       # Background ping
res/layout/          # UI layouts


## How to Run
```bash
git clone https://github.com/godwin26/Ping-App.git
cd Ping-App
./gradlew installDebug
adb shell monkey -p com.example.ping 1
