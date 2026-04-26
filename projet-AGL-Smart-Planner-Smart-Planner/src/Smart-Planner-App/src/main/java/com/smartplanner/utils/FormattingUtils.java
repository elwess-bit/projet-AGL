package com.smartplanner.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for formatting and displaying information
 */
public class FormattingUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMATTER);
    }

    public static String formatTime(LocalDateTime dateTime) {
        return dateTime.format(TIME_FORMATTER);
    }

    public static String formatDuration(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        if (hours > 0) {
            return hours + "h " + mins + "min";
        }
        return mins + "min";
    }

    public static void printSeparator() {
        System.out.println("=".repeat(80));
    }

    public static void printHeader(String text) {
        printSeparator();
        System.out.println(text.toUpperCase());
        printSeparator();
    }

    public static void printSuccess(String message) {
        System.out.println("✓ " + message);
    }

    public static void printError(String message) {
        System.out.println("✗ " + message);
    }

    public static void printWarning(String message) {
        System.out.println("⚠ " + message);
    }

    public static void printInfo(String message) {
        System.out.println("ℹ " + message);
    }
}
