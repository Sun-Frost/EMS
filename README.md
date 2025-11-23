Employee Management System
Project Overview

The Employee Management System is a JavaFX-based desktop application designed to manage employee information, departmental details, and company operations efficiently. The application provides a user-friendly interface with a sidebar for easy navigation between different functionalities.

Features

Login System:
User authentication with username and password using BCrypt.
Redirects to Dashboard upon successful login.

Dashboard:
Displays departmental information, manager details, employee count, top performers, and mood tracking.
Sidebar navigation for easy access to different sections.

Sidebar:
Collapsible/expandable sidebar with icons and labels.
Clickable items to switch between scenes (Dashboard, Schedule, Leave Requests, Profile).

Scene Management:
Centralized SceneController for consistent scene switching.
Each scene maintains the same size (800x600).

Sign-Up Page:
Allows creation of new user accounts.

Tech Stack
Java 17+
JavaFX for GUI

Git for version control

EMS/
├── controllers/                 # Application Controllers
│   ├── ApproveRequestsController
│   ├── DashboardController
│   ├── LeaveRequestController
│   ├── LoginController
│   ├── MoodController
│   ├── ProfileController
│   ├── SceneController
│   ├── ScheduleController
│   ├── SignUpController
│   └── ViewAllEmployeesController
│
├── models/                     # Data Models
│   ├── Department
│   ├── LeaveRequest
│   ├── Mood
│   └── User
│
├── views/                      # User Interface Views
│   ├── ApproveRequestsView
│   ├── DashboardView
│   ├── LeaveRequestView
│   ├── LoginView
│   ├── ProfileView
│   ├── ScheduleView
│   ├── SidebarView
│   ├── SignUpView
│   └── ViewAllEmployeesView
│
└── db/                         # Database Configuration
    └── DBConnection
    
Future Improvements

Integrate a database for real user management.

Add CRUD operations for employee data.

Implement reporting and analytics.

Improve UI/UX with animations and better styling.
