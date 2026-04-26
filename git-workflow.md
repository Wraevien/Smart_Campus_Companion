# Git Workflow Documentation

## 1. Branching Strategy: Gitflow
To maintain a stable codebase, the team utilizes the **Gitflow** workflow. All development occurs in feature branches and is merged into `develop` before reaching `main`.

### Core Branches:
* **main:** Contains the production-ready code. Only updated during official releases.
* **develop:** The integration branch for features. All team members merge their completed tasks here.
* **release/v2.0-final:** Used for final polishing, bug fixes, and metadata preparation before the final submission.
* **hotfix/:** Created from `main` to fix critical bugs found in the "production" version.

### Feature Branches:
Each member works on a dedicated branch prefix:
* `feature/ui-polish` (Brillante)
* `feature/user-roles` (Brutas)
* `feature/backend-data` (Candelario)
* `feature/settings-notifications` (Caylas)

## 2. Integration Protocol
1.  **Pull:** Always pull the latest `develop` branch before starting work.
2.  **Commit:** Use descriptive commit messages (e.g., `feat: add dark mode toggle logic`).
3.  **Push:** Push feature branches to the remote repository.
4.  **Pull Request (PR):** Open a PR to `develop`.
5.  **Review:** The Git Manager (Brillantes) reviews the code for conflicts and quality before merging.

## 3. Versioning
The final version will be tagged as:
`git tag -a v2.0-final -m "Finals Release: Smart Campus Companion Phase 3"`
