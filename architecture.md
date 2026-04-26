# Architecture Documentation: Smart Campus Companion (Phase 3)

## 1. Architectural Pattern
The application follows the **Clean Architecture** principles combined with the **MVVM (Model-View-ViewModel)** pattern. This ensures that the UI logic is separated from the business logic and data sources.

### Layers:
* **UI Layer (Presentation):** Handled via Jetpack Compose. Components include Screens, ViewModels, and UI States.
* **Domain Layer (Business Logic):** Contains Use Cases and Repository Interfaces that define how data should be handled regardless of the source.
* **Data Layer (Infrastructure):** Implements the Repositories. It manages data flow from **Firebase/Retrofit** and local storage (**DataStore/SharedPreferences**).

## 2. Technical Stack
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material 3)
* **Dependency Injection:** Hilt (or manual constructor injection)
* **Backend:** Firebase (Real-time Database/Firestore) or Retrofit (REST API)
* **Local Storage:** Jetpack DataStore (for user preferences and settings)
* **Async Operations:** Kotlin Coroutines and Flow

## 3. Key Modules
* **Settings & Preference Module:** A centralized module for theme switching (Dark Mode) and notification toggles using a repository pattern to persist user choices.
* **Role-Based Access Control (RBAC):** Logic residing in the Domain layer that filters UI components and API permissions based on the authenticated user's role (Admin vs. Student).
* **Notification Engine:** Integrates with Firebase Cloud Messaging (FCM) or local WorkManager to trigger alerts based on backend updates.
