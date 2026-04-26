package com.smartplanner.utils;

/**
 * Lightweight logger replacement for environments without SLF4J.
 */
public class SimpleLogger {
    private final String className;

    private SimpleLogger(Class<?> clazz) {
        this.className = clazz.getSimpleName();
    }

    public static SimpleLogger getLogger(Class<?> clazz) {
        return new SimpleLogger(clazz);
    }

    public void info(String message, Object... args) {
        log("INFO", message, args);
    }

    public void warn(String message, Object... args) {
        log("WARN", message, args);
    }

    public void error(String message, Object... args) {
        log("ERROR", message, args);
    }

    public void debug(String message, Object... args) {
        log("DEBUG", message, args);
    }

    private void log(String level, String message, Object... args) {
        System.out.println("[" + level + "] " + className + " - " + format(message, args));
    }

    private String format(String message, Object... args) {
        if (message == null) {
            return "null";
        }
        if (args == null || args.length == 0) {
            return message;
        }

        StringBuilder result = new StringBuilder();
        int start = 0;
        int argIndex = 0;
        int placeholderIndex;

        while (argIndex < args.length && (placeholderIndex = message.indexOf("{}", start)) != -1) {
            result.append(message, start, placeholderIndex);
            result.append(args[argIndex] != null ? args[argIndex].toString() : "null");
            start = placeholderIndex + 2;
            argIndex++;
        }

        result.append(message.substring(start));
        return result.toString();
    }
}
