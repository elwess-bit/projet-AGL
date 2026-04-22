# Smart Daily Planner - Architecture Documentation

## System Architecture Overview

The Smart Daily Planner follows a **layered architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                      USER INTERFACE LAYER                    │
│                  (Main.java - Console UI)                    │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    APPLICATION LAYER                         │
│            (SmartPlannerApplication.java)                    │
│         Orchestrates services and business logic             │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                      SERVICE LAYER                           │
│  ┌──────────────┬─────────────┬──────────────────────────┐  │
│  │ UserService  │TaskService  │AvailabilityService      │  │
│  ├──────────────┼─────────────┼──────────────────────────┤  │
│  │PreferenceS.  │PlanningS.   │NotificationService      │  │
│  └──────────────┴─────────────┴──────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                      MODEL LAYER                             │
│  ┌────────────────┬───────────┬──────────────┬────────────┐ │
│  │  Utilisateur   │   Tache   │Disponibilite │ Contrainte │ │
│  ├────────────────┼───────────┼──────────────┼────────────┤ │
│  │  Notification  │Preference │   Planning   │ElementPlan.│ │
│  └────────────────┴───────────┴──────────────┴────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                       DATA LAYER                             │
│           (In-Memory Collections - Can be extended)          │
└─────────────────────────────────────────────────────────────┘
```

## Architectural Patterns

### 1. **Service-Oriented Architecture (SOA)**
Each service is responsible for a specific domain:
- **UserService**: User lifecycle management
- **TaskService**: Task CRUD operations
- **AvailabilityService**: Time slots and constraints
- **PlanningService**: Schedule generation algorithm
- **NotificationService**: User notifications
- **PreferenceService**: Configuration management

### 2. **Facade Pattern**
`SmartPlannerApplication` acts as a facade, providing a simplified interface to the complex subsystem of services.

### 3. **Factory Pattern**
Services create instances (Utilisateur, Tache, Planning, etc.) and manage collections.

### 4. **Strategy Pattern**
Different scheduling strategies could be implemented through the `PlanningService` interface.

## Component Interactions

### User Registration Flow
```
User Interface
    ↓
SmartPlannerApplication.registerUser()
    ↓
UserService.creerUtilisateur()
    ↓
PreferenceService.sauvegarderPreference()
    ↓
Return Utilisateur object
```

### Schedule Generation Flow
```
User Interface
    ↓
SmartPlannerApplication.generateSchedule()
    ↓
TaskService.obtenirTachesUtilisateur()
AvailabilityService.obtenirDisponibilites()
AvailabilityService.obtenirContraintes()
    ↓
PlanningService.genererPlanning()
    ├─ Build available slots
    ├─ Sort tasks by priority
    ├─ Assign tasks to slots
    └─ Create Planning object
    ↓
NotificationService.envoyerNotification()
    ↓
Return Planning object
```

### Task Completion Flow
```
User Interface
    ↓
SmartPlannerApplication.completeTask()
    ↓
TaskService.marquerTacheTerminee()
    ↓
NotificationService.envoyerNotification()
    ↓
Update Dashboard
```

## Data Flow

### Input Processing
1. User provides data through console interface
2. Input validation (format, bounds checking)
3. Service methods process validated data
4. Models store and represent data
5. In-memory collections maintain state

### Output Generation
1. Services query and process data
2. Models format information
3. Application layer aggregates results
4. UI layer displays formatted output

## Scheduling Algorithm

### Step 1: Task Analysis
```
Input: List of tasks, each with:
- Description
- Duration (minutes)
- Priority Level
- Task Type

Process: Sort by priority (descending) and type
Output: Sorted task list
```

### Step 2: Availability Analysis
```
Input: Disponibilite objects for each day and time

Process:
1. Create time slots for each available period
2. Expand weekly schedule across date range
3. Create slot map: Date → List<TimeSlot>

Output: Map<LocalDate, List<TimeSlot>>
```

### Step 3: Constraint Application
```
Input: List of Contrainte objects

Process:
1. For each active constraint
2. Identify affected date and time
3. Remove conflicting slots
4. Update availability map

Output: Filtered slot map
```

### Step 4: Task Assignment
```
Input: Sorted tasks, filtered slots

Process:
For each task:
1. Find best available slot
   - Prefer morning for concentration tasks
   - Match duration requirements
2. Create ElementPlanning
3. Update task with schedule
4. Remove slot from availability
5. Add to planning

Output: List<ElementPlanning>
```

### Step 5: Planning Creation
```
Input: ElementPlanning list, user info, date range

Process:
1. Create Planning object
2. Assign all ElementPlanning objects
3. Set status
4. Generate notification

Output: Planning object
```

## Service Interactions

### UserService
- **Depends on**: None
- **Used by**: SmartPlannerApplication, Main
- **Responsibilities**:
  - User registration and authentication
  - Profile management
  - User retrieval

### TaskService
- **Depends on**: None
- **Used by**: SmartPlannerApplication, PlanningService
- **Responsibilities**:
  - Task CRUD operations
  - Task filtering and sorting
  - Completion tracking

### AvailabilityService
- **Depends on**: None
- **Used by**: SmartPlannerApplication, PlanningService
- **Responsibilities**:
  - Time slot management
  - Constraint tracking
  - Availability calculations

### PlanningService
- **Depends on**: TaskService, AvailabilityService (indirectly)
- **Used by**: SmartPlannerApplication
- **Responsibilities**:
  - Schedule generation
  - Algorithm execution
  - Planning storage

### NotificationService
- **Depends on**: None
- **Used by**: SmartPlannerApplication, PlanningService
- **Responsibilities**:
  - Notification creation
  - Notification retrieval
  - Notification management

### PreferenceService
- **Depends on**: None
- **Used by**: SmartPlannerApplication, NotificationService
- **Responsibilities**:
  - Preference storage
  - Preference retrieval
  - User customization

## Data Models Relationships

```
Utilisateur (1) ─── (many) Tache
         │
         ├── (many) Disponibilite
         ├── (many) Contrainte
         ├── (many) Notification
         └── (1) Preference

Planning (1) ─── (many) ElementPlanning
         └── references ─ Tache
```

## State Management

### In-Memory Storage
Currently, the application uses Java Collections for state:
```java
List<Utilisateur> utilisateurs;
List<Tache> taches;
List<Disponibilite> disponibilites;
List<Contrainte> contraintes;
List<Planning> plannings;
List<Notification> notifications;
List<Preference> preferences;
```

### State Transitions for Tasks
```
CREATED → PLANIFIEE → EN_COURS → TERMINEE
                   ↘
                    SAUTEE
```

### State Transitions for Planning
```
GENERE → EN_COURS → TERMINE
    ↘
     ANNULE
```

## Extensibility Points

### 1. **Algorithm Enhancement**
Extend `PlanningService.findBestSlot()` for:
- Machine learning-based task placement
- User preference-aware scheduling
- Constraint-aware optimization

### 2. **Storage Layer**
Replace in-memory collections with:
- Relational database (MySQL, PostgreSQL)
- NoSQL database (MongoDB)
- File-based persistence (JSON, XML)

### 3. **Notification Channel**
Extend `NotificationService` with:
- Email notifications
- SMS alerts
- Push notifications
- Webhook integration

### 4. **UI Layer**
Implement alternative UIs:
- JavaFX desktop application
- Web interface (Spring Boot + React)
- Mobile application (Android/iOS)

### 5. **Integration Points**
Add connectors for:
- Google Calendar
- Microsoft Outlook
- University timetable APIs
- Weather services

## Performance Considerations

### Current Performance
- User registration: O(1)
- Task search: O(n) where n = number of tasks
- Schedule generation: O(t×s) where t = tasks, s = slots
- Notification query: O(n) where n = notifications

### Optimization Opportunities
1. **Indexing**: Add HashMaps for ID-based lookups
2. **Caching**: Cache frequently accessed queries
3. **Lazy Loading**: Load data on-demand
4. **Database**: Replace in-memory with indexed database
5. **Algorithm**: Implement more efficient scheduling (dynamic programming)

## Security Considerations

### Current Implementation
- No authentication required
- Data stored in-memory (no persistence)
- No encryption
- No input validation

### Recommended Enhancements
1. **Authentication**: User login with password hashing
2. **Authorization**: Role-based access control
3. **Validation**: Input sanitization and validation
4. **Encryption**: Sensitive data encryption
5. **Audit Logging**: Track all operations
6. **Data Privacy**: GDPR compliance

## Testing Strategy

### Unit Tests
- Test each service in isolation
- Mock dependencies
- Cover happy path and error cases

### Integration Tests
- Test service interactions
- Verify data flows
- Test complete workflows

### System Tests
- End-to-end testing
- Performance testing
- Load testing

### Manual Tests
- UI testing
- User workflow validation
- Edge case verification

## Deployment Architecture

### Current
- Single-tier console application
- In-memory data storage
- Local execution

### Recommended
```
┌─────────────┐
│   Client    │ (Web browser or Mobile app)
└──────┬──────┘
       │ HTTP/REST
┌──────▼────────┐
│  API Server   │ (Spring Boot)
└──────┬────────┘
       │ JDBC
┌──────▼────────┐
│   Database    │ (MySQL/PostgreSQL)
└───────────────┘
```

## Technology Stack

### Current
- **Language**: Java 11+
- **Build**: Maven
- **Logging**: SLF4J
- **Serialization**: Gson
- **Testing**: JUnit

### Recommended Future
- **Web Framework**: Spring Boot
- **Database**: JPA/Hibernate
- **API**: REST (Spring Web)
- **Frontend**: React or Angular
- **Deployment**: Docker
- **CI/CD**: Jenkins/GitHub Actions

## Scalability

### Current Limitations
- Single-instance only
- No load balancing
- No horizontal scaling
- Limited concurrent users

### Scalability Solutions
1. **Stateless services**: Design services for horizontal scaling
2. **Database clustering**: Multi-node database support
3. **Caching layer**: Redis for distributed caching
4. **Message queue**: RabbitMQ for asynchronous operations
5. **Microservices**: Decompose into smaller services

---

**For implementation details, see source code comments and API documentation.**
