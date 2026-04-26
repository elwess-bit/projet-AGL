package com.smartplanner.enums;

/**
 * Task priority levels for scheduling
 */
public enum PriorityLevel {
    HAUTE(3),
    MOYENNE(2),
    BASSE(1);

    private final int value;

    PriorityLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
