package com.urise.webapp.util;

public class EnumUtil {
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) throws IllegalArgumentException {
        if (c != null && string != null) {
            return Enum.valueOf(c, string.trim().toUpperCase());
        }
        return null;
    }
}
