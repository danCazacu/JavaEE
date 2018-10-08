package com.company;

import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        MyThread threads[] = new MyThread[500];
        for(int i = 0; i<threads.length; i++){
            threads[i] = new MyThread(latch);
        }
        for(int i = 0; i<threads.length; i++){
            threads[i].start();
        }
        latch.countDown();
        for(int i = 0; i<threads.length; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Sleeeeeeeping....");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch = new CountDownLatch(1);
        MyThread threads2[] = new MyThread[500];
        for(int i = 0; i<threads2.length; i++){
            threads2[i] = new MyThread(latch);
        }
        for(int i = 0; i<threads2.length; i++){
            threads2[i].start();
        }
        latch.countDown();
    }
}
