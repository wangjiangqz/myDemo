package com.mytest.demo;

public class LockRunable implements Runnable {

    private  Object lock;

    public LockRunable(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < 26; i++) {
                if (Thread.currentThread().getName().equals("number")) {
                    System.out.print(i + 1);
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.print((char) ('A' + i));
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
