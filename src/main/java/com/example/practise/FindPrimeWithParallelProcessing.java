package com.example.practise;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FindPrimeWithParallelProcessing implements Runnable {

//    Requirement
//    1 to 1,000,000, count all the prime numbers
//    return integer

//    When 10000000(10 million)
//    Total no. of prime no. are:- 664579
//    Total time taken:- 6431miliseconds

//    When 100000000(100 million)
//    Total no. of prime no. are:- 5761455
//    Total time taken:- 165994miliseconds

    private int start;
    private int end;
    private int countPrime;
    static Thread t = null;
    // Shared resource to store the sum of outputs
    static AtomicInteger sum = new AtomicInteger(0);
    private int startNo=1;

    public FindPrimeWithParallelProcessing(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    public FindPrimeWithParallelProcessing() {

    }

    @Override
    public void run()
    {
       countPrime=countPrime(start,end);
       System.out.println("countPrime:-"+countPrime+"startNo:"+start+" endNo:"+end+" sum:"+sum.get());
       sum.addAndGet(countPrime);    // Atomically add to the shared sum
        System.out.println("sum2:"+sum.get());
    }

    public Boolean isPrime(int n)
    {
        if (n < 2){
            return false;
        }
        for (int i=2; i<=Math.sqrt(n); i++)
        {
            if (n%i==0)
            {
                return false;
            }
        }
        return true;
    }

    public int countPrime(int start, int end)
    {
        int count = 0;
        for(int i=start;i<=end;i++)
        {
            isPrime(i);
            if(isPrime(i))
            {
                count++;
            }
        }
        return count;
    }

    public void makeThreads(int no, int thread)
    {
        int number = no;
        int threads = thread;
        int baseBatchSize = number / threads;
        int remainingNumbers = number % threads;
//        resetting for every iteration
        sum.set(0);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < threads; i++)
        {
            int batchSize = baseBatchSize + (i < remainingNumbers ? 1 : 0);
            int endNo = startNo + batchSize - 1;
            t = new Thread(new FindPrimeWithParallelProcessing(startNo, endNo));
//            System.out.println("startNo:" + startNo + " endNo:" + endNo);
            t.start();
            startNo = endNo + 1;
        }

        // Wait for all threads to complete
        try {
            t.join();   // Main thread waits for the thread to finish
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // This block/code will execute only after all threads have finished
        long end = System.currentTimeMillis();
        long timetaken = end - begin;

        System.out.println("Total no. of prime no. are:- "+sum.get());  // Print the combined output
        System.out.println("Total time taken:- " + timetaken + "miliseconds");
    }


    public static void main(String[] args)
    {
        FindPrimeWithParallelProcessing p1 = new FindPrimeWithParallelProcessing();
        FindPrimeWithParallelProcessing p2 = new FindPrimeWithParallelProcessing();
        FindPrimeWithParallelProcessing p3 = new FindPrimeWithParallelProcessing();

        p1.makeThreads(100,14);
        p2.makeThreads(10000,10);
//        p3.makeThreads(10000000,100);

    }

}
