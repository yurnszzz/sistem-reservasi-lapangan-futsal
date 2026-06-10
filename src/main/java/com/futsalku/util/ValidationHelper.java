package com.futsalku.util;

/**
 * ValidationHelper — Utility class untuk validasi input
 */
public class ValidationHelper {

    /**
     * Validasi apakah string tidak kosong
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validasi format email sederhana
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Validasi format nomor telepon (Indonesia)
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^08[0-9]{8,12}$");
    }

    /**
     * Validasi angka positif
     */
    public static boolean isPositiveNumber(double value) {
        return value > 0;
    }
}
