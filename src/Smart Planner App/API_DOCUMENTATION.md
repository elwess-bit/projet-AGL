# Smart Daily Planner - API Documentation

## Overview

This document provides detailed API documentation for the Smart Daily Planner application. The application uses a service-oriented architecture with the following main services:

- **UserService**: User management
- **TaskService**: Task operations
- **AvailabilityService**: Availability and constraint management
- **PlanningService**: Schedule generation
- **NotificationService**: Notification handling
- **PreferenceService**: User preferences

---

## User Service API

### Register User
```java
Utilisateur registerUser(String nom, String email, String niveauEtude)
```
**Parameters:**
- `nom` (String): User's full name
- `email` (String): User's email address
- `niveauEtude` (String): Study level (L1, L2, L3, M1, M2)

**Returns:** `Utilisateur` object with generated ID

**Example:**
```java
Utilisateur user = app.registerUser("Ahmed", "ahmed@univ.tn", "L3");
```

### Get User by ID
```java
Utilisateur obtenirUtilisateur(String utilisateurId)
```
**Returns:** User object or null if not found

### Get User by Email
```java
Utilisateur obtenirUtilisateurParEmail(String email)
```
**Returns:** User object or null if not found

### Update User Profile
```java
Utilisateur mettreAJourUtilisateur(String utilisateurId, String nom, String niveauEtude)
```
**Returns:** Updated user object

### Delete User
```java
void supprimerUtilisateur(String utilisateurId)
```

---

## Task Service API

### Add Task
```java
Tache ajouterTache(String utilisateurId, String description, int dureeMinutes,
                   PriorityLevel priorite, TaskType type)
```
**Parameters:**
- `utilisateurId` (String): User ID
- `description` (String): Task description
- `dureeMinutes` (int): Duration in minutes
- `priorite` (PriorityLevel): HAUTE, MOYENNE, or BASSE
- `type` (TaskType): COURT, LONG, or PRIORITAIRE

**Returns:** Created `Tache` object

**Example:**
```java
Tache task = app.addTask(userId, "Study Algorithms", 120, 
    PriorityLevel.HAUTE, TaskType.LONG);
```

### Get User's Tasks
```java
List<Tache> obtenirTachesUtilisateur(String utilisateurId)
```
**Returns:** List of all tasks for the user

### Get Pending Tasks
```java
List<Tache> obtenirTachesPendantes(String utilisateurId)
```
**Returns:** List of incomplete tasks

### Get Completed Tasks
```java
List<Tache> obtenirTachesTerminees(String utilisateurId)
```
**Returns:** List of completed tasks

### Get Tasks by Priority
```java
List<Tache> obtenirTachesParPriorite(String utilisateurId, PriorityLevel priorite)
```
**Returns:** Filtered task list

### Get Tasks by Type
```java
List<Tache> obtenirTachesParType(String utilisateurId, TaskType type)
```
**Returns:** Filtered task list

### Mark Task Complete
```java
void marquerTacheTerminee(String tacheId)
```

### Update Task
```java
Tache mettreAJourTache(String tacheId, String description, int dureeMinutes,
                      PriorityLevel priorite, TaskType type)
```
**Returns:** Updated task object

### Delete Task
```java
void supprimerTache(String tacheId)
```

### Get Task by ID
```java
Tache obtenirTache(String tacheId)
```

### Calculate Total Study Time
```java
int calculerTempsEtudeTotal(String utilisateurId)
```
**Returns:** Total minutes of pending tasks

---

## Availability Service API

### Add Availability
```java
Disponibilite ajouterDisponibilite(String utilisateurId, LocalTime heureDebut,
                                   LocalTime heureFin, String jour)
```
**Parameters:**
- `utilisateurId` (String): User ID
- `heureDebut` (LocalTime): Start time (e.g., LocalTime.of(9, 0))
- `heureFin` (LocalTime): End time
- `jour` (String): Day name (MONDAY, TUESDAY, etc.)

**Returns:** Created `Disponibilite` object

**Example:**
```java
app.addAvailability(userId, LocalTime.of(9, 0), LocalTime.of(12, 0), "MONDAY");
```

### Get User's Availability
```java
List<Disponibilite> obtenirDisponibilites(String utilisateurId)
```

### Get Availability for Specific Day
```java
List<Disponibilite> obtenirDisponibilitesJour(String utilisateurId, String jour)
```

### Update Availability
```java
Disponibilite mettreAJourDisponibilite(String disponibiliteId, LocalTime heureDebut,
                                      LocalTime heureFin, String jour)
```

### Delete Availability
```java
void supprimerDisponibilite(String disponibiliteId)
```

### Add Constraint
```java
Contrainte ajouterContrainte(String utilisateurId, TimeSlotType type,
                            LocalDateTime dateHeure, int dureeMinutes, String description)
```
**Parameters:**
- `type` (TimeSlotType): RETARD_TRANSPORT, PAUSE_ENTRE_COURS, CHANGEMENT_EMPLOIS_DU_TEMPS, or ACTIVITE_PERSONNELLE
- `dateHeure` (LocalDateTime): When the constraint occurs
- `dureeMinutes` (int): Duration of the constraint
- `description` (String): Details about the constraint

**Returns:** Created `Contrainte` object

**Example:**
```java
app.addConstraint(userId, TimeSlotType.RETARD_TRANSPORT, 
    LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)), 30, "Bus delay");
```

### Get Constraints
```java
List<Contrainte> obtenirContraintes(String utilisateurId)
```
**Returns:** All active constraints for the user

### Get Constraints by Type
```java
List<Contrainte> obtenirContraintesParType(String utilisateurId, TimeSlotType type)
```

### Deactivate Constraint
```java
void desactiverContrainte(String contrainteId)
```

### Calculate Available Hours
```java
int calculerHeuresDisponiblesParJour(String utilisateurId)
```
**Returns:** Total available minutes per day

---

## Planning Service API

### Generate Schedule
```java
Planning genererPlanning(String utilisateurId, LocalDate dateDebut, LocalDate dateFin,
                        List<Tache> tachesUtilisateur, List<Disponibilite> disponibilitesUtilisateur,
                        List<Contrainte> contraintesUtilisateur)
```
**Parameters:**
- `utilisateurId` (String): User ID
- `dateDebut` (LocalDate): Schedule start date
- `dateFin` (LocalDate): Schedule end date
- `tachesUtilisateur` (List<Tache>): Tasks to schedule
- `disponibilitesUtilisateur` (List<Disponibilite>): Available time slots
- `contraintesUtilisateur` (List<Contrainte>): Constraints to respect

**Returns:** Generated `Planning` object

**Example:**
```java
Planning planning = app.generateSchedule(userId, LocalDate.now(), 
    LocalDate.now().plusDays(7));
```

### Get Planning by ID
```java
Planning getPlanningById(String id)
```

### Get User's Plannings
```java
List<Planning> getPlanningsByUser(String userId)
```

---

## Notification Service API

### Send Notification
```java
void envoyerNotification(String utilisateurId, String titre, String message, String typeMessage)
```

### Send Delay Notification
```java
void envoyerNotificationRetard(String utilisateurId, String description, int minutesRetard)
```

### Send Schedule Change Notification
```java
void envoyerNotificationChangement(String utilisateurId, String changement)
```

### Send Task Reminder
```java
void envoyerRappelTache(String utilisateurId, String nomTache, int minutesAvant)
```

### Mark as Read
```java
void marquerCommeLue(String notificationId)
```

### Get Unread Notifications
```java
List<Notification> getNotificationsNonLues(String utilisateurId)
```

### Get All Notifications
```java
List<Notification> getNotifications(String utilisateurId)
```

### Get Notifications by Type
```java
List<Notification> getNotificationsByType(String utilisateurId, String typeMessage)
```

### Delete Notification
```java
void supprimerNotification(String notificationId)
```

### Clear All Notifications
```java
void effacerNotifications(String utilisateurId)
```

---

## Preference Service API

### Get User Preferences
```java
Preference obtenirPreference(String utilisateurId)
```
**Returns:** User's preference object (creates default if not exists)

### Save Preferences
```java
Preference sauvegarderPreference(Preference preference)
```

### Enable/Disable Notifications
```java
void activerNotifications(String utilisateurId, boolean actif)
```

### Change Theme
```java
void changerTheme(String utilisateurId, String theme)
```
**Parameters:**
- `theme` (String): "CLAIR" or "SOMBRE"

### Change Language
```java
void changerLangue(String utilisateurId, String langue)
```
**Parameters:**
- `langue` (String): "FRANCAIS", "ARABE", or "ANGLAIS"

### Change Reminder Time
```java
void changerTempsRappel(String utilisateurId, int minutesAvant)
```

### Change Study Goal
```java
void changerTempsEtudeIdeal(String utilisateurId, int minutes)
```

---

## Main Application API

### Generate Schedule (High-level)
```java
Planning generateSchedule(String userId, LocalDate startDate, LocalDate endDate)
```
Orchestrates the complete scheduling process.

### Complete Task
```java
void completeTask(String taskId)
```

### Send Task Reminder
```java
void sendTaskReminder(String userId, String taskName)
```

### Handle Transportation Delay
```java
void handleTransportationDelay(String userId, int delayMinutes, String description)
```

### Get User's Planning
```java
Planning getUserPlanning(String userId)
```

### Get Unread Notifications
```java
List<Notification> getUnreadNotifications(String userId)
```

### Display Dashboard
```java
void displayUserDashboard(String userId)
```

---

## Data Models

### Utilisateur
```java
public class Utilisateur {
    String id;           // Unique identifier
    String nom;          // Full name
    String email;        // Email address
    String niveauEtude;  // Study level
}
```

### Tache
```java
public class Tache {
    String id;               // Unique identifier
    String description;      // Task description
    int dureeMinutes;        // Duration in minutes
    PriorityLevel priorite;  // HAUTE, MOYENNE, BASSE
    TaskType type;           // COURT, LONG, PRIORITAIRE
    String utilisateurId;    // Owner
    boolean completee;       // Completion status
    String dateDebut;        // Start datetime
    String dateFin;          // End datetime
}
```

### Disponibilite
```java
public class Disponibilite {
    String id;              // Unique identifier
    String utilisateurId;   // Owner
    LocalTime heureDebut;   // Start time
    LocalTime heureFin;     // End time
    String jour;            // Day of week
    boolean estLibre;       // Available flag
}
```

### Contrainte
```java
public class Contrainte {
    String id;                // Unique identifier
    String utilisateurId;     // Owner
    TimeSlotType type;        // Type of constraint
    LocalDateTime dateHeure;  // When it occurs
    int dureeMinutes;         // Duration
    String description;       // Details
    boolean estActive;        // Active flag
}
```

### Notification
```java
public class Notification {
    String id;               // Unique identifier
    String utilisateurId;    // Recipient
    String titre;            // Title
    String message;          // Content
    LocalDateTime dateHeure; // Timestamp
    boolean estLue;          // Read status
    String typeMessage;      // Type of notification
}
```

### Preference
```java
public class Preference {
    String id;                              // Unique identifier
    String utilisateurId;                   // Owner
    boolean notificationsActivees;          // Enable notifications
    int minutesRappelAvant;                 // Reminder lead time
    String theme;                           // CLAIR or SOMBRE
    String languePrefere;                   // Preferred language
    int tempsEtudeIdealParJour;             // Daily study goal (minutes)
    boolean activerNotificationsRetard;     // Enable delay notifications
}
```

### Planning
```java
public class Planning {
    String id;                   // Unique identifier
    String utilisateurId;        // Owner
    LocalDate dateDebut;         // Start date
    LocalDate dateFin;           // End date
    List<ElementPlanning> elements;  // Scheduled tasks
    String statut;               // Status
    LocalDateTime dateGeneration; // Creation time
}
```

### ElementPlanning
```java
public class ElementPlanning {
    String id;                   // Unique identifier
    String planningId;           // Parent planning
    String tacheId;              // Associated task
    LocalDateTime heureDebut;    // Start datetime
    LocalDateTime heureFin;      // End datetime
    String statut;               // PLANIFIEE, EN_COURS, TERMINEE, SAUTEE
    String description;          // Task description
    int priorite;                // Priority level
}
```

---

## Enumerations

### PriorityLevel
```java
enum PriorityLevel {
    BASSE(1),      // Low priority
    MOYENNE(2),    // Medium priority
    HAUTE(3)       // High priority
}
```

### TaskType
```java
enum TaskType {
    COURT(1),          // Short task (15-60 min)
    LONG(2),           // Long task (60-180 min)
    PRIORITAIRE(3)     // Priority task
}
```

### TimeSlotType
```java
enum TimeSlotType {
    RETARD_TRANSPORT,              // Transportation delay
    PAUSE_ENTRE_COURS,             // Break between classes
    CHANGEMENT_EMPLOIS_DU_TEMPS,   // Schedule change
    ACTIVITE_PERSONNELLE           // Personal activity
}
```

---

## Error Handling

The application handles errors gracefully:

- **Invalid user input**: Returns null or default values
- **Missing data**: Logs warnings and continues
- **Scheduling conflicts**: Logs and attempts fallback scheduling
- **Database issues**: Graceful degradation (in-memory storage)

---

## Best Practices

1. **Always validate user input** before passing to services
2. **Check for null returns** when fetching data
3. **Use appropriate exception handling** in calling code
4. **Update constraints regularly** for accurate scheduling
5. **Review and adjust preferences** periodically

---

## Examples

### Complete Workflow
```java
// Initialize
SmartPlannerApplication app = new SmartPlannerApplication();

// Register user
Utilisateur user = app.registerUser("Ahmed", "ahmed@univ.tn", "L3");
String userId = user.getId();

// Add tasks
Tache task1 = app.addTask(userId, "Study", 120, PriorityLevel.HAUTE, TaskType.LONG);
Tache task2 = app.addTask(userId, "Exercise", 60, PriorityLevel.MOYENNE, TaskType.COURT);

// Add availability
Disponibilite avail = app.addAvailability(userId, 
    LocalTime.of(9, 0), LocalTime.of(17, 0), "MONDAY");

// Add constraint
Contrainte constraint = app.addConstraint(userId, TimeSlotType.RETARD_TRANSPORT,
    LocalDateTime.now(), 30, "Bus delay");

// Generate schedule
LocalDate today = LocalDate.now();
Planning planning = app.generateSchedule(userId, today, today.plusDays(7));

// View results
app.displayUserDashboard(userId);
```

---

**For more information, see README.md and GETTING_STARTED.md**
