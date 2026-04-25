# QA Testing Report

This document contains the manual testing conducted on the Smart Companions core functionalities.

---

## Test Cases

## Test Case 1 (UI/UX)

| Feature | Test Scenario | Expected Result | Actual Result | Status |
|--------|-------------|----------------|--------------|--------|
| View Register Screen| User clicks Register Button| Show Register Screen| Show Register Screen| Passed|
| View Login Screen| User clicks Login Button| Show Login Screen| Show Login Screen| Passed|
| Display Campus Info| User navigates to Campus Info| Displays Campus Info Screen| Displays Campus Info Screen| Passed|
| Display Task Manager Screen| User navigates to Task Manager Screen| Display Task Manager Screen| Display Task Manager Screen| Passed|
| Display Announcement Screen| User navigates to Announcement Screen| Display Announcement Screen| Display Announcement Screen| Passed|
| Display Profile Screen| User navigates to Profile Screen| Display Profile Screen| Display Profile Screen| Passed|
---

## Issues Found

- 
- 
- 

---

## Retesting Results

| Issue | Status |
|------|--------|
|      |        |
|      |        |

---

## Test Case 2 (User Roles)

| Feature | Test Scenario | Expected Result | Actual Result | Status |
|--------|-------------|----------------|--------------|--------|
| Student Login| Login as student account| View Student Dashboard/Features| View Student Dashboard/Features| Passed|
| Admin Login| Login as admin account| View Admin Dashboard/Features| View Admin Dashboard/Features| Passed|
| View Student Announcements| Student navigates to the announcement section| Display Announcement section with posted announcements| Display Announcement section with posted announcements|Passed|
| Post New Announcement| Admin posts a new announcement| Announcement gets posted| Announcement gets posted| Passed|
---

## Issues Found

- Student Tasks are shared through all students.
- When creating a new announcement as an admin and immediately click back button, it goes back to the login screen.
- All mark as read announcement status are shared through all students.

## Retesting Results

| Issue | Status |
|------|--------|
| Student Tasks are shared through all students| Resolved|
|  When creating a new announcement as an admin and immediately click back button, it goes back to the login screen| Resolved|
| All mark as read announcement status are shared through all students| Resolved|

---

## Test Case 3 (Backend Data Integration)

| Feature | Test Scenario | Expected Result | Actual Result | Status |
|--------|-------------|----------------|--------------|--------|
| Store Student Account in Firestore| Creating a Student Account| Account reflects in Firestore Database| Account Reflected in Firestore Database| Passed|
| Store Admin Account in Firestore| Creating an Admin Account| Account reflects in Firestore Database| Account Reflected in Firestore Database| Passed|
| Store Created Tasks in Firestore| Creating a Task| Created Task reflects in Firestore Database| Task Reflected in Firestore Database| Passed|
| Store Created Announcements in Firestore| Create an Announcement| Created Announcement reflects in Firestore Database| Created Announcement Reflecteed in Firestore Database| Passed|
| Display Created announcement to students| Student checks announcement module| Display Admin Created Announcement| Displays Admin Created Announcemnet| Passed|
---

## Issues Found

- Cannot create account if the username textfield has spaces


## Retesting Results

| Issue | Status |
|------|--------|

---
