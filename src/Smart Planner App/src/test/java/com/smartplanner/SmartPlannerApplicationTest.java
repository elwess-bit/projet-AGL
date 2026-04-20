package com.smartplanner;

import com.smartplanner.enums.PriorityLevel;
import com.smartplanner.enums.TaskType;
import com.smartplanner.enums.TimeSlotType;
import com.smartplanner.models.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test suite for Smart Daily Planner application
 */
public class SmartPlannerApplicationTest {
    private SmartPlannerApplication app;
    private String userId;

    @Before
    public void setUp() {
        app = new SmartPlannerApplication();
        
        // Create a test user
        Utilisateur user = app.registerUser("Test User", "test@example.com", "L1");
        userId = user.getId();
    }

    @Test
    public void testUserRegistration() {
        Utilisateur user = app.getUserService().obtenirUtilisateur(userId);
        assertNotNull(user);
        assertEquals("Test User", user.getNom());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testTaskCreation() {
        Tache task = app.addTask(userId, "Study Mathematics", 120, 
            PriorityLevel.HAUTE, TaskType.LONG);
        
        assertNotNull(task);
        assertEquals("Study Mathematics", task.getDescription());
        assertEquals(120, task.getDureeMinutes());
        assertEquals(PriorityLevel.HAUTE, task.getPriorite());
        assertEquals(TaskType.LONG, task.getType());
    }

    @Test
    public void testAvailabilityManagement() {
        Disponibilite availability = app.addAvailability(userId, 
            LocalTime.of(9, 0), LocalTime.of(12, 0), "MONDAY");
        
        assertNotNull(availability);
        assertEquals(180, availability.getDurationMinutes()); // 3 hours
        assertEquals("MONDAY", availability.getJour());
    }

    @Test
    public void testConstraintManagement() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
        Contrainte constraint = app.addConstraint(userId, TimeSlotType.RETARD_TRANSPORT, 
            dateTime, 30, "Bus delay");
        
        assertNotNull(constraint);
        assertTrue(constraint.isEstActive());
        assertEquals(30, constraint.getDureeMinutes());
    }

    @Test
    public void testPreferenceManagement() {
        Preference pref = app.getPreferenceService().obtenirPreference(userId);
        
        assertNotNull(pref);
        assertTrue(pref.isNotificationsActivees());
        assertEquals(15, pref.getMinutesRappelAvant());
    }

    @Test
    public void testScheduleGeneration() {
        // Add some tasks
        app.addTask(userId, "Task 1", 60, PriorityLevel.HAUTE, TaskType.COURT);
        app.addTask(userId, "Task 2", 120, PriorityLevel.MOYENNE, TaskType.LONG);
        
        // Add availability
        app.addAvailability(userId, LocalTime.of(9, 0), LocalTime.of(17, 0), "MONDAY");
        
        // Generate schedule
        LocalDate today = LocalDate.now();
        Planning planning = app.generateSchedule(userId, today, today.plusDays(1));
        
        assertNotNull(planning);
        assertEquals(2, planning.getElements().size());
    }

    @Test
    public void testNotificationSystem() {
        app.sendTaskReminder(userId, "Study Mathematics");
        
        List<Notification> notifications = app.getUnreadNotifications(userId);
        assertTrue(notifications.size() > 0);
    }

    @Test
    public void testTaskCompletion() {
        Tache task = app.addTask(userId, "Test Task", 60, PriorityLevel.BASSE, TaskType.COURT);
        
        assertFalse(task.isCompletee());
        
        app.completeTask(task.getId());
        
        Tache completedTask = app.getTaskService().obtenirTache(task.getId());
        assertTrue(completedTask.isCompletee());
    }

    @Test
    public void testGetUserTasks() {
        app.addTask(userId, "Task 1", 60, PriorityLevel.HAUTE, TaskType.COURT);
        app.addTask(userId, "Task 2", 120, PriorityLevel.MOYENNE, TaskType.LONG);
        
        List<Tache> tasks = app.getTaskService().obtenirTachesPendantes(userId);
        assertEquals(2, tasks.size());
    }

    @Test
    public void testGetUserNotifications() {
        app.sendTaskReminder(userId, "Task 1");
        app.sendTaskReminder(userId, "Task 2");
        
        List<Notification> notifications = app.getUnreadNotifications(userId);
        assertEquals(2, notifications.size());
    }
}
