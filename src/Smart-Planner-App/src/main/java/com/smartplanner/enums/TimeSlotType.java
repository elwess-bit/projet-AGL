package com.smartplanner.enums;

/**
 * Types of availability/time slots for tasks
 */
public enum TimeSlotType {
    RETARD_TRANSPORT("Transportation Delay"),
    PAUSE_ENTRE_COURS("Break between classes"),
    CHANGEMENT_EMPLOIS_DU_TEMPS("Schedule change"),
    ACTIVITE_PERSONNELLE("Personal activity");

    private final String description;

    TimeSlotType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
