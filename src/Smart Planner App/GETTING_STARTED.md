# Getting Started with Smart Daily Planner

## Quick Start Guide

### Step 1: Installation

#### Using Maven
```bash
# Clone or download the project
cd smart-daily-planner

# Build the project
mvn clean package

# Run the application
mvn exec:java -Dexec.mainClass="com.smartplanner.Main"
```

#### Direct JAR Execution
```bash
java -jar target/smart-daily-planner-1.0.0.jar
```

### Step 2: First Time Setup

When you run the application, you'll see the main menu:

```
╔════════════════════════════════════════════════════════════════════════════════╗
║                        SMART DAILY PLANNER                                   ║
║              Intelligent Scheduling for Tunisian Students                    ║
╚════════════════════════════════════════════════════════════════════════════════╝

--- MAIN MENU ---
1. Register New User
2. Login
3. Exit
Enter choice:
```

**Option 1: Register New User**

1. Enter your name: `Ahmed Ben Ali`
2. Enter your email: `ahmed@univ.tn`
3. Enter your study level: `L3` (or L1, L2, M1, M2)

The system will create your account and automatically configure default preferences.

**Option 2: Login**

1. Enter your email: `ahmed@univ.tn`
2. Access your personalized dashboard

### Step 3: Add Your Tasks

From the User Menu, select **"Add Task"**:

```
--- ADD NEW TASK ---
Description: Study Algorithms
Duration (minutes): 120
Priority: 1=BASSE, 2=MOYENNE, 3=HAUTE
Select priority: 3
Task Type: 1=COURT, 2=LONG, 3=PRIORITAIRE
Select type: 2
```

Add multiple tasks:
- Study Algorithms (120 min, High Priority, Long)
- Read Chapter 5 (60 min, Medium Priority, Short)
- Solve Exercise Set (90 min, High Priority, Priority)
- Review Notes (45 min, Low Priority, Short)

### Step 4: Define Your Availability

From the User Menu, select **"Add Availability"**:

```
--- ADD AVAILABILITY ---
Day (e.g., MONDAY, TUESDAY): MONDAY
Start time (HH:mm): 09:00
End time (HH:mm): 12:00
✓ Availability added successfully!
```

Repeat for other days and time slots:
- **Monday to Friday**: 09:00 - 12:00 (3 hours each)
- **Monday to Friday**: 14:00 - 18:00 (4 hours each)
- **Saturday**: 10:00 - 13:00 (3 hours)
- **Saturday**: 15:00 - 19:00 (4 hours)

### Step 5: Add Constraints (Optional)

From the User Menu, select **"Add Constraint"** for any obstacles:

```
--- ADD CONSTRAINT ---
Type: 1=TRANSPORTATION_DELAY, 2=BREAK_BETWEEN_CLASSES, 3=SCHEDULE_CHANGE, 4=PERSONAL_ACTIVITY
Select type: 1
Date and time (yyyy-MM-dd HH:mm): 2024-04-19 12:00
Duration (minutes): 30
Description: Bus delay from campus
✓ Constraint added successfully!
```

### Step 6: Generate Your Schedule

From the User Menu, select **"Generate Schedule"**:

```
--- GENERATE SCHEDULE ---
Start date (yyyy-MM-dd): 2024-04-19
End date (yyyy-MM-dd): 2024-04-26
✓ Schedule generated successfully!
Planning ID: abc-def-ghi-jkl
Tasks scheduled: 4
```

### Step 7: View Your Schedule

From the User Menu, select **"View Planning"**:

```
--- VIEW PLANNING ---
Planning ID: abc-def-ghi-jkl
Period: 2024-04-19 to 2024-04-26

Scheduled Tasks:
• Study Algorithms
  Time: 2024-04-22 09:00 to 2024-04-22 11:00
  Duration: 120 minutes
  Status: PLANIFIEE

• Read Chapter 5
  Time: 2024-04-22 14:00 to 2024-04-22 15:00
  Duration: 60 minutes
  Status: PLANIFIEE

...
```

## Using the Dashboard

Select **"View Dashboard"** to see an overview:

```
SMART DAILY PLANNER - USER DASHBOARD
User: Ahmed Ben Ali
Email: ahmed@univ.tn
Study Level: L3

Pending Tasks: 3
  - Study Algorithms (120min) [Priority: HAUTE]
  - Read Chapter 5 (60min) [Priority: MOYENNE]
  - Solve Exercise Set (90min) [Priority: HAUTE]

Unread Notifications: 1
  - Planning généré: Votre planning a été généré avec 4 tâches planifiées

Current Planning: abc-def-ghi-jkl
Scheduled Tasks: 4
  - Study Algorithms from 2024-04-22T09:00 to 2024-04-22T11:00
  - Read Chapter 5 from 2024-04-22T14:00 to 2024-04-22T15:00
  ...
```

## Managing Preferences

From the User Menu, select **"Manage Preferences"**:

```
--- MANAGE PREFERENCES ---
Current Preferences:
1. Notifications: Enabled
2. Reminder time: 15 minutes before
3. Theme: CLAIR
4. Language: FRANCAIS
5. Daily study goal: 4 hours

0. Back
Select option to modify:
```

### Available Preference Settings

| Setting | Options | Default |
|---------|---------|---------|
| Notifications | Enabled/Disabled | Enabled |
| Reminder Time | Minutes before task | 15 |
| Theme | CLAIR (Light), SOMBRE (Dark) | CLAIR |
| Language | FRANCAIS, ARABE, ANGLAIS | FRANCAIS |
| Daily Study Goal | Hours | 4 |

## Key Features Overview

### 1. Priority Levels
- **HAUTE (High)**: Urgent, important tasks
- **MOYENNE (Medium)**: Regular tasks
- **BASSE (Low)**: Nice-to-do tasks

### 2. Task Types
- **COURT (Short)**: 15-60 minutes, flexible placement
- **LONG (Long)**: 60-180 minutes, morning preferred
- **PRIORITAIRE (Priority)**: Focus tasks, prime time slots

### 3. Constraint Types
- **RETARD_TRANSPORT**: Transportation delays
- **PAUSE_ENTRE_COURS**: Breaks between classes
- **CHANGEMENT_EMPLOIS_DU_TEMPS**: Schedule changes
- **ACTIVITE_PERSONNELLE**: Personal activities

## Workflow Example

### Daily Student Schedule Setup

1. **Register**: Create account with study level
2. **Input Tasks**: Enter all assignments and study goals for the week
3. **Set Availability**: Define free time slots
4. **Note Constraints**: Add class times, breaks, transportation time
5. **Generate Schedule**: Let AI create optimal plan
6. **Review**: Check the generated schedule
7. **Execute**: Follow the plan and mark tasks as complete
8. **Adjust**: Manually reschedule if needed

### Weekly Review

- Check how many tasks were completed
- Identify time management patterns
- Adjust preferences for next week
- Update availability based on new constraints

## Tips for Best Results

### ✅ Do's
- Update your availability regularly
- Add all constraints and obstacles
- Keep task durations realistic
- Review and adjust your schedule weekly
- Use preferences to personalize your experience

### ❌ Don'ts
- Don't add too many high-priority tasks
- Don't set unrealistic time estimates
- Don't forget to mark tasks complete
- Don't ignore constraints
- Don't set conflicting availability times

## Troubleshooting

### "No tasks found for user"
**Solution**: Make sure you've added tasks before generating a schedule.

### "No availability found for user"
**Solution**: Define your free time slots before generating a schedule.

### "Could not find slot for task"
**Solution**: Either increase your available time or reduce task duration estimates.

### "User not found. Please register first."
**Solution**: Use the email you registered with or create a new account.

## Sample Data

Here's a complete example setup:

### Tasks
```
Task 1: Study Linear Algebra
- Duration: 120 minutes
- Priority: HAUTE
- Type: LONG

Task 2: Read Discrete Math Chapter
- Duration: 60 minutes
- Priority: MOYENNE
- Type: COURT

Task 3: Problem Set Solutions
- Duration: 90 minutes
- Priority: HAUTE
- Type: PRIORITAIRE
```

### Availability
```
Monday-Friday: 09:00-12:00, 14:00-18:00
Saturday: 10:00-13:00, 15:00-19:00
Sunday: Off
```

### Constraints
```
Monday-Friday: 12:00-13:00 (Lunch break)
Wednesday: 10:30-11:30 (Class)
Friday: 16:00-17:00 (Transportation buffer)
```

## Next Steps

After mastering the basic features:

1. Explore notification settings
2. Experiment with different preferences
3. Track your productivity patterns
4. Adjust constraints based on real experience
5. Share feedback for future improvements

## Getting Help

If you encounter any issues:
1. Check the README.md for detailed documentation
2. Review error messages in the console
3. Verify your input format (especially dates/times)
4. Try with sample data if unsure

## Further Learning

To integrate with other systems or extend functionality:
- Review the source code in `src/main/java/com/smartplanner/`
- Check the project structure in README.md
- Run unit tests with `mvn test`
- Modify services in `services/` package for customization

---

**Happy Planning! 🎓⏱️**
