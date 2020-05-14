package com.mytest.demo;

import java.util.concurrent.Semaphore;

public class SemaphoreRunnable implements Runnable {

    public static Semaphore semaphore1 = new Semaphore(1);
    public static Semaphore semaphore2 = new Semaphore(1);

    @Override
    public void run() {
        for (int i = 0; i < 26; i++) {
            if (Thread.currentThread().getName().equals("number")) {
                try {
                    this.semaphore1.acquire();
                    if (i == 0) {
                        this.semaphore2.acquire();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i + 1);
                this.semaphore2.release();
            } else {
                try {
                    this.semaphore2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println((char) ('A' + i));
                this.semaphore1.release();
            }
        }
    }
}
