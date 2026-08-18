package com.raniya.raniyamart.util;

import com.raniya.raniyamart.exception.ValidationException;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Invalid email address format.");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long.");
        }
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty.");
        }
    }

    public static void validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be greater than 0.");
        }
    }

    public static String sanitize(String input) {
        if (input == null) return null;
        return input.trim().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
