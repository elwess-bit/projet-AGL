# Smart Daily Planner

An intelligent scheduling application designed to help Tunisian students efficiently organize their daily tasks by automatically generating optimized schedules.

## Overview

The Smart Daily Planner is a sophisticated Java-based application that combines task management, constraint handling, and intelligent scheduling algorithms to create realistic and optimized daily plans. It's specifically adapted to the context of Tunisian students, incorporating local constraints like university class schedules, transportation delays, and break times.

## Key Features

### 1. **Intelligent Schedule Generation**
- Automatically creates optimized schedules based on tasks, availability, and constraints
- Uses priority-based and time-slot optimization algorithms
- Considers task type (short, long, priority tasks) for optimal placement

### 2. **User Management**
- Register and manage multiple users
- Support for different study levels (L1, L2, L3, M1, M2)
- User profile management

### 3. **Task Management**
- Create tasks with specific durations and priorities
- Categorize tasks (short, long, priority)
- Track task completion status
- View tasks by priority or type

### 4. **Availability Management**
- Define available time slots for each day
- Support for recurring weekly schedules
- Calculate total available hours

### 5. **Constraint Handling**
- Transportation delays
- Breaks between classes
- Schedule changes
- Personal activities
- Dynamic constraint management

### 6. **Notification System**
- Task reminders with customizable lead time
- Delay notifications for transportation issues
- Schedule change alerts
- Unread notification tracking

### 7. **Preference System**
- Customize notification settings
- Theme selection (light/dark mode)
- Language preferences (French/Arabic/English)
- Set ideal daily study goals

### 8. **Planning Visualization**
- View generated schedules with task timing
- Track task status (planned, in progress, completed, skipped)
- Monitor scheduling efficiency

## Project Structure

```
smart-daily-planner/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/smartplanner/
    │   │   ├── Main.java                           # Application entry point
    │   │   ├── SmartPlannerApplication.java        # Main orchestrator
    │   │   ├── enums/
    │   │   │   ├── PriorityLevel.java             # Task priority levels
    │   │   │   ├── TaskType.java                  # Task categorization
    │   │   │   └── TimeSlotType.java              # Constraint types
    │   │   ├── models/
    │   │   │   ├── Utilisateur.java               # User model
    │   │   │   ├── Tache.java                     # Task model
    │   │   │   ├── Disponibilite.java             # Availability model
    │   │   │   ├── Contrainte.java                # Constraint model
    │   │   │   ├── Notification.java              # Notification model
    │   │   │   ├── Preference.java                # User preference model
    │   │   │   ├── Planning.java                  # Schedule model
    │   │   │   └── ElementPlanning.java           # Scheduled element model
    │   │   ├── services/
    │   │   │   ├── UserService.java               # User management
    │   │   │   ├── TaskService.java               # Task management
    │   │   │   ├── AvailabilityService.java       # Availability & constraints
    │   │   │   ├── PlanningService.java           # Schedule generation
    │   │   │   ├── NotificationService.java       # Notification handling
    │   │   │   └── PreferenceService.java         # Preference management
    │   │   └── utils/
    │   │       └── FormattingUtils.java           # Display utilities
    │   └── resources/
    └── test/
        └── java/                                   # Test classes
```

## System Architecture

### Core Components

#### 1. **User Service**
Manages user registration, authentication, and profile management.

```java
UserService userService = new UserService();
Utilisateur user = userService.creerUtilisateur("Ahmed", "ahmed@example.com", "L1");
```

#### 2. **Task Service**
Handles all task-related operations including creation, retrieval, and status updates.

```java
TaskService taskService = new TaskService();
Tache task = taskService.ajouterTache(userId, "Study Mathematics", 120, 
    PriorityLevel.HAUTE, TaskType.LONG);
```

#### 3. **Availability Service**
Manages available time slots and constraints (transportation delays, schedule changes, etc.).

```java
AvailabilityService availService = new AvailabilityService();
availService.ajouterDisponibilite(userId, LocalTime.of(9, 0), 
    LocalTime.of(12, 0), "MONDAY");
availService.ajouterContrainte(userId, TimeSlotType.RETARD_TRANSPORT, 
    LocalDateTime.now(), 30, "Bus delay");
```

#### 4. **Planning Service**
Core scheduling engine that generates optimized plans using intelligent algorithms.

```java
PlanningService planningService = new PlanningService();
Planning planning = planningService.genererPlanning(userId, startDate, endDate, 
    tasks, availability, constraints);
```

#### 5. **Notification Service**
Sends and manages notifications for reminders, delays, and schedule changes.

```java
NotificationService notifService = new NotificationService();
notifService.envoyerRappelTache(userId, "Study Mathematics", 15);
```

#### 6. **Preference Service**
Manages user preferences including notification settings and display options.

```java
PreferenceService prefService = new PreferenceService();
Preference pref = prefService.obtenirPreference(userId);
pref.setNotificationsActivees(true);
prefService.sauvegarderPreference(pref);
```

## Scheduling Algorithm

The scheduling algorithm uses a greedy optimization approach:

1. **Task Sorting**: Tasks are sorted by priority and type
2. **Slot Analysis**: Available time slots are identified for each day
3. **Constraint Filtering**: Slots are reduced based on active constraints
4. **Task Assignment**: Each task is assigned to the best available slot
5. **Optimization**: Higher priority tasks get earlier time slots

## Data Models

### Task (Tache)
- **ID**: Unique identifier
- **Description**: Task name
- **Duration**: Time needed (minutes)
- **Priority**: Level (HAUTE, MOYENNE, BASSE)
- **Type**: Category (COURT, LONG, PRIORITAIRE)
- **Status**: Completion status
- **Schedule**: Start and end times

### Availability (Disponibilite)
- **ID**: Unique identifier
- **Time Slot**: Start and end hours
- **Day**: Day of the week
- **Available**: Boolean flag

### Constraint (Contrainte)
- **ID**: Unique identifier
- **Type**: Constraint category
- **DateTime**: When it occurs
- **Duration**: How long it lasts
- **Description**: Details

### Planning
- **ID**: Unique identifier
- **Elements**: List of scheduled tasks
- **Period**: Start and end dates
- **Status**: Generation/execution status

## Usage Examples

### Example 1: Complete User Workflow

```java
// Initialize application
SmartPlannerApplication app = new SmartPlannerApplication();

// Register user
Utilisateur user = app.registerUser("Fatima", "fatima@univ.tn", "M1");
String userId = user.getId();

// Add tasks
app.addTask(userId, "Étude Algorithmique", 120, PriorityLevel.HAUTE, TaskType.LONG);
app.addTask(userId, "Lecture chapitre 3", 60, PriorityLevel.MOYENNE, TaskType.COURT);
app.addTask(userId, "Exercices pratiques", 90, PriorityLevel.HAUTE, TaskType.PRIORITAIRE);

// Add availability (9 AM to 12 PM, Monday through Friday)
for (String day : Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY")) {
    app.addAvailability(userId, LocalTime.of(9, 0), LocalTime.of(12, 0), day);
    app.addAvailability(userId, LocalTime.of(14, 0), LocalTime.of(18, 0), day);
}

// Add constraint (lunch break)
app.addConstraint(userId, TimeSlotType.PAUSE_ENTRE_COURS, 
    LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)), 60, "Lunch break");

// Generate schedule
LocalDate today = LocalDate.now();
Planning planning = app.generateSchedule(userId, today, today.plusDays(4));

// Display results
app.displayUserDashboard(userId);
```

## Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Build
```bash
cd smart-daily-planner
mvn clean package
```

### Run
```bash
mvn exec:java -Dexec.mainClass="com.smartplanner.Main"
```

Or run directly:
```bash
java -jar target/smart-daily-planner-1.0.0.jar
```

## Console UI Features

The application provides an interactive console interface with:

1. **Main Menu**
   - User registration
   - User login
   - Exit application

2. **User Menu**
   - Dashboard view
   - Task management
   - Availability settings
   - Constraint management
   - Schedule generation and viewing
   - Notification management
   - Preference configuration

## Future Enhancements

1. **Database Integration**
   - Persistent storage with MySQL/PostgreSQL
   - Data backup and recovery

2. **Advanced Algorithms**
   - Machine learning for task prediction
   - Adaptive scheduling based on user behavior
   - Performance analytics

3. **UI Improvements**
   - JavaFX desktop GUI
   - Web interface with Spring Boot
   - Mobile app (Android/iOS)

4. **Collaboration Features**
   - Group schedule sharing
   - Team task management
   - Synchronized calendars

5. **Integration**
   - Email notifications
   - SMS alerts
   - Calendar sync (Google Calendar, Outlook)
   - University timetable import

6. **Analytics**
   - Task completion statistics
   - Time management insights
   - Productivity reports

## Dependencies

- **Gson** (2.10.1): JSON serialization
- **SLF4J** (2.0.5): Logging framework
- **JUnit** (4.13.2): Unit testing

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Author

Created as an intelligent scheduling solution for Tunisian students.

## Support

For issues, questions, or suggestions, please open an issue on GitHub.

---

**Smart Daily Planner** - Making time management smarter! ⏱️
