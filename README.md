# Smart Campus Companion (Prelim)

## App Description
Smart Campus Companion is a mobile application designed to assist university students in accessing campus-related information and managing basic app navigation. This project is developed incrementally across academic periods.

## Prelim Features Implemented
- Mock Login screen with hardcoded credentials
- Session persistence using SharedPreferences
- Dashboard route with navigation menu (placeholder)
- Campus Information route (placeholder)
- Navigation implemented using Jetpack Compose Navigation

## Demo Credentials
- Username: admin
- Password: admin

## Git Workflow (Prelim)
- `main` branch contains the stable demo version
- `develop` branch is used for active development
- All members commit directly to `develop` during the Prelim phase
- Each member is required to have at least 5 meaningful commits

## Team Roles (Prelim)
- Team Leader: Brillante
- Git Manager: Brillantes 
- UI/UX Developer: Brutas 
- Feature Developer: Candelario
- QA / Documenter: Caylas

## Prelim Task Breakdown
- Login UI: Brillantes
- Dashboard UI: Caylas
- Campus Information UI: Candelario
- UI Components/Theme polish: Brutas
- Session Handling & Navigation Flow: Brillante

## Notes
During the Prelim phase, placeholder screens were used for the Dashboard and Campus Information modules while the navigation flow and session logic were implemented. Full UI implementations will follow in the next development phase.


# Smart Campus Companion (Midterm)

## App Description
Smart Campus Companion is a mobile application designed to assist university students in managing their academic life. In this Midterm phase, the app has transitioned from static placeholders to a robust, architecture-driven system featuring dynamic task management and persistent local storage.

## Midterm Feature Expansion (Phase 2)
Building upon the navigation and session logic from Prelim, the following functional requirements have been implemented:

### 1. Task & Schedule Manager
* **CRUD Operations:** Users can add, edit, and delete academic tasks integrated with date pickers for scheduling deadlines. 
* **Dynamic Lists:** Implementation of **LazyColumn** (or RecyclerView) for optimized data rendering.

### 2. Campus Announcements Module
* **Data Source:** Announcements are stored using **Room Database**.
* **Interaction:** Functionality to mark announcements as "read" to manage notifications.

### 3. Local Data Persistence
* **Room Persistence Library:** Full implementation of local SQLite storage.
* **Core Components:** Utilization of **DAO**, **Entity**, and **ViewModel** for lifecycle-aware data handling.

## Technical Focus
* **Architecture:** MVVM (Model-View-ViewModel)
* **State Management:** ViewModel & LiveData / StateFlow
* **Database:** Room Database
* **UI Patterns:** RecyclerView / LazyColumn patterns

## Git Workflow (Midterm)
The workflow has evolved into a feature-branch model to simulate professional development:
* **Feature Branches:**
    * `feature/task-manager`
    * `feature/announcements`
* **Pull Requests:** PRs were required for merging code.
* **Conflict Management:** 1 documented merge conflict resolution.


## Team Roles (Midterm)
- Team Leader: Brillante
- Git Manager: Brillantes
- UI/UX Developer: Brutas
- Feature Developer: Candelario
- QA / Documenter: Caylas

## Prelim Task Breakdown
- Task & Schedule Manager: Brillantes
- Campus Announcement Module: Candelario & Caylas
- Local Data Persistence: Brillante
- UI/UX: Brutas

## Notes
Phase 2 focuses on moving away from hardcoded data. By implementing the MVVM pattern and Room Database, the app now maintains a "Single Source of Truth," ensuring that user tasks and campus data persist even after the app is closed.

