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
Phase 2 focuses on moving away from hardcoded data. By implementing the MVVM pattern and Room Database, the app now maintains a "Single Source of 


# Smart Campus Companion (Finals)

## App Description
Smart Campus Companion has evolved into a production-grade academic assistant. In the Final phase, the focus shifts from local management to **Cloud Integration**, **Role-Based Access Control (RBAC)**, and a highly polished **User Experience (UX)**. The system now utilizes a "Cloud-First" approach for data synchronization and administrative oversight.

## Finals Feature Expansion (Phase 3)

### 1. Backend & Cloud Integration
* **Cloud Connectivity:** Migration from purely local Room storage to a cloud-based architecture (Firebase or Retrofit/REST API).
* **Data Synchronization:** Implementation of the **Repository Pattern** to manage data flow between local cache and remote sources.

### 2. Identity & Access Management (RBAC)
* **Mock User Roles:** Implementation of Student and Admin personas.
* **Dynamic UI Logic:** Conditional rendering where Admins gain "Create/Edit" permissions for announcements, while Students retain "View-Only" access.

### 3. Advanced Settings & System Feedback
* **Settings Module:** Introduction of **Jetpack DataStore** for persistent user preferences (Dark Mode, Notification toggles).
* **System Notifications:** Implementation of local and push notification triggers for task reminders and campus announcements.
* **UX Polish:** Integration of "Empty States," shimmer loading effects, and graceful error handling.

## Technical Focus
* **Architecture:** Clean Architecture with Repository Pattern
* **Networking:** Firebase / Retrofit for Cloud Data
* **Persistence:** Jetpack DataStore (Settings) + Room (Cache)
* **UI/UX:** Material Design 3, Dark Theme, and Notification Manager

---

## Task Assignment (Finals Period)

### Brillante – UI/UX Design & App Polish
* **Main Responsibility:** Improve the overall interface and user experience.
* **Key Tasks:** Refine all major screens (Login, Dashboard, Tasks, etc.), implement empty states/error messages, and polish dark mode.
* **Branch:** `feature/ui-polish`

### Brutas – User Roles & Access Control
* **Main Responsibility:** Implement mock role-based behavior for Student and Admin users.
* **Key Tasks:** Restrict app behavior based on role; allow Admins to post announcements while Students view only.
* **Branch:** `feature/user-roles`

### Candelario – Backend Data Integration
* **Main Responsibility:** Connect the app to cloud/backend data source and manage data flow.
* **Key Tasks:** Implement Firebase or Retrofit, connect user role and announcement data, and organize flow using a repository structure.
* **Branch:** `feature/backend-data`

### Caylas – Settings Module & Notifications
* **Main Responsibility:** Build settings features and handle app notification behavior.
* **Key Tasks:** Implement Dark Mode and Notification toggles using DataStore; trigger reminders for tasks and announcements.
* **Branch:** `feature/settings-notifications`

### Brillantes – Git Manager, QA, Release, and Documentation
* **Main Responsibility:** Manage collaboration workflow, testing, and final release.
* **Key Tasks:** Oversee GitFlow (main, develop, release, hotfix), review PRs, resolve conflicts, maintain `Changelog.md`, and generate the final APK.
* **Branch:** `release/v2.0-final`

---

## Git Workflow (Finals)
A strict **GitFlow** model is adopted to ensure a stable release:
* **`main`**: Production-ready code.
* **`develop`**: Integration branch for features.
* **`feature/`**: Individual task branches.
* **`release/v2.0-final`**: Final stabilization and bug fixes.
* **`hotfix/`**: Critical patches post-release.

## Final Deliverables
1. **Version Tagged:** `v2.0-final`
2. **Release Artifact:** Signed Production APK
3. **Documentation:** Complete Architecture Diagram, Git Contribution Report, and Changelog.Truth," ensuring that user tasks and campus data persist even after the app is closed.

APK: https://drive.google.com/drive/folders/1FFq6TMyfJybbus2JkqhhOhBX7n4V6ZGA?usp=sharing
