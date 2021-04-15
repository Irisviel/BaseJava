package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static final Object LOCK = new Object();
    private int counter;

    public static void main(String[] args) throws InterruptedException {

        //region Init order example
        System.out.println("Main thread name: " + Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println("thread0 state: " + thread0.getState());
        //endregion

        //region Concurrency
        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mainConcurrency.counter);
        //endregion
    }

    // Results in 1000000
    private synchronized void inc() {
        counter++;
    }

    // Results most likely in <1000000
    private void inc2() {
        counter++;
    }

    // Results in 1000000
    private void inc3() {
        // Some heavy processing operation
        double a = Math.sin(13.);
        //synchronized (MainConcurrency.class) {
        //synchronized (this) {
        synchronized (LOCK) {
            counter++;
        }
    }
}