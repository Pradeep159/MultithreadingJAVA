package com.example.practise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
public class PrintNoUsingThread implements Runnable{

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    @Override
    public void run() {
        printno();
    }

    public void printno()
    {
        if(lock.isLocked())
        {
            System.out.println(Thread.currentThread().getName()+" please wait another thread is running");
        }
        lock.lock();  // Acquire the lock
        try {
            System.out.println("Currently running:- "+Thread.currentThread().getName());

            // Task execution
            long begin = System.currentTimeMillis();
            for (int i = 0; i <= 100; i++) {
                System.out.println(i);
            }
            long end = System.currentTimeMillis();
            long timetaken = end-begin;
            System.out.println("Time taken by "+Thread.currentThread().getName()+" is:- "+timetaken+" milliseconds");

            // Notify waiting threads
            condition.signal();
        } finally {
            lock.unlock();  // Always release the lock, even if an exception occurs
        }
    }

    public static void main(String[] args)
    {

        SpringApplication.run(PrintNoUsingThread.class, args);

        PrintNoUsingThread p = new PrintNoUsingThread();
        for(int i=0;i<10;i++)
        {
            Thread t = new Thread(p);
            t.start();
        }

    }

}
