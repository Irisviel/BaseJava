package com.urise.webapp.util;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    // initialization-on-demand holder
    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
}
