package com.mytest.demo;

import org.junit.Test;

public class ConcurrentTest {

    @Test
    public void LockTest(){
        Object lock = new Object();
        new Thread(new LockRunable(lock),"number").start();
        new Thread(new LockRunable(lock),"letter").start();
    }

    @Test
    public void SemaphoreTest(){
        new Thread(new SemaphoreRunnable(),"number").start();
        new Thread(new SemaphoreRunnable(),"letter").start();
    }

}
