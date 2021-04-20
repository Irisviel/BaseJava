package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static final Object LOCK = new Object();
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    private final AtomicInteger atomicCounter = new AtomicInteger();
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
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc4();
                    System.out.println(threadLocal.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println();
        System.out.println("counter: " + mainConcurrency.counter);
        System.out.println("atomicCounter: " + mainConcurrency.atomicCounter.get());
        //endregion
    }

    private void Concurrency() {
        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc3();
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

    private void inc4() {
        WRITE_LOCK.lock();
        try {
            counter++;
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    private void inc5() {
        atomicCounter.incrementAndGet();
    }
}