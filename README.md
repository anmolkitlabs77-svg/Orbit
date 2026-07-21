# 🚀 Orbit

**Orbit** is an Android application that brings the wonders of space to your fingertips. Built entirely with **Jetpack Compose**, the app leverages **NASA's public APIs** to deliver stunning imagery, real-time space data, and educational content about our universe.

---

## ✨ Features

- 🌌 **Astronomy Picture of the Day (APOD)** — View NASA's daily curated space image or video, complete with detailed explanations.
- 🔴 **Mars Rover Photos** — Browse photos captured by NASA's Mars rovers (Curiosity, Opportunity, Perseverance, and Spirit).
- ☄️ **Near-Earth Objects (NEO)** — Track asteroids and comets making close approaches to Earth.
- 🌍 **EPIC Earth Imagery** — See real-time images of Earth captured from the DSCOVR satellite.
- 🔍 **Search & Filter** — Search through space imagery archives by date, keyword, or category.
- ❤️ **Favorites** — Save your favorite images and data for offline viewing.
- 🌗 **Dark Mode** — Fully supports light and dark themes for comfortable viewing.

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI Toolkit | Jetpack Compose |
| Architecture | MVVM (Model-View-ViewModel) |
| Networking | Retrofit + OkHttp |
| Async | Kotlin Coroutines & Flow |
| Dependency Injection | Hilt |
| Image Loading | Coil |
| Local Storage | Room Database |
| Navigation | Jetpack Navigation Compose |

---

## 📡 APIs Used

This app is powered by [NASA Open APIs](https://api.nasa.gov/), including:

- APOD (Astronomy Picture of the Day)
- Mars Rover Photos
- NeoWs (Near Earth Object Web Service)
- EPIC (Earth Polychromatic Imaging Camera)

> You'll need a free API key from [api.nasa.gov](https://api.nasa.gov/) to run this project.

---

## 📲 Screenshots

<!-- Add your app screenshots here -->
| Home | Details | Mars Rover |
|---|---|---|
| screenshot1.png | screenshot2.png | screenshot3.png |

---

## ⚙️ Getting Started

### Prerequisites

- Android Studio (latest stable version recommended)
- Minimum SDK: 24 (Android 7.0)
- A NASA API key ([get one here](https://api.nasa.gov/))

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/orbit.git
   ```

2. **Add your NASA API key**

   Create a `local.properties` file in the root directory (if it doesn't exist) and add:
   ```properties
   NASA_API_KEY=your_api_key_here
   ```

3. **Open in Android Studio**

   Open the project folder in Android Studio and let Gradle sync.

4. **Run the app**

   Select a device/emulator and hit ▶️ Run.

---

## 📁 Project Structure

```
orbit/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourpackage/orbit/
│   │   │   │   ├── data/          # Repositories, API services, models
│   │   │   │   ├── di/            # Hilt modules
│   │   │   │   ├── ui/            # Composables, screens, theme
│   │   │   │   ├── viewmodel/     # ViewModels
│   │   │   │   └── navigation/    # Navigation graph
│   │   │   └── res/               # Resources
├── build.gradle.kts
└── README.md
```

---

## 🗺️ Roadmap

- [ ] Widget support for daily APOD
- [ ] Push notifications for close asteroid approaches
- [ ] Offline caching improvements
- [ ] Wear OS companion app

---

## 🤝 Contributing

Contributions are welcome! Please open an issue first to discuss what you'd like to change.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgements

- [NASA Open APIs](https://api.nasa.gov/) for providing free access to space data
- [Jetpack Compose](https://developer.android.com/jetpack/compose) documentation and community
