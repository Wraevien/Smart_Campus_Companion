# Smart Campus Companion (Midterm)

Updated repository:
https://github.com/Wraevien/Smart_Campus_Companion.git


## Architecture Diagram
<img width="775" height="451" alt="Image" src="https://github.com/user-attachments/assets/e8e6ee90-7fd0-4cef-a521-9029f6279496" />

### 1. UI Layer (blue box — left)

Contains the application's user interface screens built using Jetpack Compose:

- DashboardScreen.kt — main dashboard of the application
- LoginScreen.kt — user login screen
- RegisterScreen.kt — user registration screen
- TaskScreen.kt — task management screen
- AnnouncementScreen.kt — announcements display screen

This layer handles user interaction and navigation. The screens send user actions to the ViewModel and observe UI state updates.


### 2. ViewModel Layer (green box — middle)

Contains the ViewModels responsible for managing UI logic and state:

- TaskViewModel — manages task-related operations
- AnnouncementViewModel — manages announcement data

The ViewModels receive events from the UI and request or update data through the Repository layer.


### 3. Repository Layer (yellow box — middle right)

Acts as the data manager between the ViewModel and the database:

- TaskRepository — handles task data operations
- AnnouncementRepository — handles announcement data operations

This layer performs data requests and CRUD operations through DAO interfaces.


### 4. Room Database Layer (purple box — right)

Handles the application's local data storage using Room Database:

- AppDatabase — main database instance
- TaskDao — database queries for tasks
- AnnouncementDao — database queries for announcements
- TaskEntity — database table structure for tasks
- AnnouncementEntity — database table structure for announcements

This layer is responsible for storing, retrieving, and managing application data.



## Git Challenges

### Committing the .idea folder

One of our early mistakes was committing the .idea folder into version control. This caused conflicts in IDE configuration files unrelated to our actual code.

We fixed this by:

- Updating .gitignore to exclude .idea
- Running git rm --cached -r .idea to stop tracking it
- Also excluding build/ and local.properties


### Branch divergence from master

Both feature branches ended up 3 commits behind master because we did not consistently pull from master before starting new work sessions. This caused the branches to drift apart over time.

This highlighted the importance of regularly syncing with master to avoid larger conflicts later.


### Infrequent and overly large commits

Many commits bundled multiple changes together, such as updating several screens in a single push. This made it harder to trace bugs and review code.

Going forward, we plan to commit changes per screen or per logical concern so each commit represents one clear unit of work.


### Working across overlapping files

Since both features share common files like the navigation graph and Dashboard screen, multiple members were editing the same files from different branches simultaneously. This made merge conflicts almost inevitable whenever branches were merged.



## Branch Management and Synchronization

During the midterm development phase, the group encountered several Git-related challenges while working collaboratively on the Smart Campus Companion project.

Multiple members were assigned to different modules, so feature branches such as:

- feature/task-manager
- feature/announcements

were used.

One major challenge was maintaining proper synchronization between local branches and the remote repository. Members committed and pushed changes at different times, which required frequent pulling, fetching, and branch updates before merging.



## Establishing a Standardized Workflow

At first, there was confusion regarding when to commit to develop and when to use feature branches.

As the team progressed, it became clearer that:

- All module-specific work should be committed to the corresponding feature branch first
- Then merged into develop

This improved the overall organization of the repository and made collaboration more manageable.



## Consistency in Documentation and Task Division

The team experienced minor difficulties in keeping commit messages consistent and meaningful.

Since each member was required to contribute several commits, it was important to ensure that each commit reflected a specific and understandable change.

This required better coordination and clearer division of tasks among members.



## Conflict Resolution

### Where conflicts occurred

The most frequent conflicts appeared in:

- nav_graph.xml
- Dashboard layout file
- MainActivity.kt

Both feature branches modified these files independently, and when merging into master, Git could not automatically reconcile the overlapping changes.


### Pair-based resolution

To resolve conflicts, the developers involved worked together using Android Studio’s built-in three-way merge tool.

This allowed them to compare both versions side by side and combine changes correctly without losing work.


### Keeping both branches in sync

After resolving major conflicts, the team adopted new practices:

- Merge master back into feature branches regularly
- Assign informal file ownership
- Coordinate before modifying shared files


### Lessons from the .idea conflicts

The .idea conflicts were not code conflicts but IDE metadata conflicts that still consumed time to resolve.

The key lesson learned was:

A proper .gitignore should be set up before the very first commit, not after problems appear.