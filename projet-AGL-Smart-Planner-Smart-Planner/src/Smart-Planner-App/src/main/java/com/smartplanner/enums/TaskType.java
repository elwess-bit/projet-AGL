package com.smartplanner.enums;

/**
 * Types of tasks for categorization and scheduling
 */
public enum TaskType {
    COURS(1, true),      // Requires concentration
    REVISION(2, true),   // Requires concentration
    PERSONNELLE(1, false); // Personal or admin task

    private final int baseDuration;
    private final boolean requiresConcentration;

    TaskType(int baseDuration, boolean requiresConcentration) {
        this.baseDuration = baseDuration;
        this.requiresConcentration = requiresConcentration;
    }

    public int getBaseDuration() {
        return baseDuration;
    }

    public boolean requiresConcentration() {
        return requiresConcentration;
    }
}
