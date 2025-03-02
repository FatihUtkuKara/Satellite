# 📡 Satellite App

A satellite tracking application that displays satellite information, positions, and details. This project is built using **Kotlin, MVVM architecture, Hilt for Dependency Injection, Room Database, and Coroutines**.

## 🚀 Features
✅ List of satellites with active/inactive status indicators  
✅ Satellite search functionality  
✅ Caching mechanism to avoid repeated JSON parsing  
✅ Real-time satellite position updates every 3 seconds  
✅ Detail screen with satellite specifications  
✅ ViewBinding for UI interactions  
✅ Unit & UI tests with JUnit, MockK, and Espresso


## 🛠 Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM
- **Dependency Injection:** Hilt
- **Database:** Room
- **Asynchronous Processing:** Coroutines & Flow
- **Testing:** JUnit, MockK, Espresso
- **UI:** Material Design 3, ViewBinding

## 🔧 Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/FatihUtkuKara/Satellite.git
   cd satellite
2. Open in Android Studio
3. Sync Gradle and Run

## 👨‍💻 How It Works
- Satellite List: Displays satellites fetched from satellites.json
- Detail Page: Loads cached details or fetches from satellite-detail.json
- Position Updates: Fetches from positions.json and updates every 3 seconds

## 📬 Contact
If you have any questions, feel free to reach out:
📧 fatihutkukara@gmail.com