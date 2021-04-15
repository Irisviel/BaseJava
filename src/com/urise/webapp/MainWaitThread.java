package com.urise.webapp;

public class MainWaitThread implements Runnable {
    final static private Object shared = new Object();
    private  int type;

    public MainWaitThread(int i) {
        type = i;
    }

    public static void main(String[] s) {
        MainWaitThread w1 = new MainWaitThread(1);
        new Thread(w1).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainWaitThread w2 = new MainWaitThread(2);
        new Thread(w2).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainWaitThread w3 = new MainWaitThread(3);
        new Thread(w3).start();
    }

    public void run() {
        if (type == 1 || type == 2) {
            synchronized (shared) {
                try {
                    shared.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + type + " after wait()");
            }
        } else {
            synchronized (shared) {
                shared.notifyAll();
                System.out.println("Thread " + type + " after notifyAll()");
            }
        }
    }
}