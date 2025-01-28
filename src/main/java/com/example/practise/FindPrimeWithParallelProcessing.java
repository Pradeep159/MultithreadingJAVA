package com.example.practise;

import java.util.concurrent.atomic.AtomicInteger;

public class FindPrimeWithParallelProcessing implements Runnable {

    private int start;
    private int end;
    private int count;
    // Shared resource to store the sum of outputs
    static AtomicInteger sum = new AtomicInteger(0);

    public FindPrimeWithParallelProcessing(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run()
    {
       count=countPrime(start,end);
       sum.addAndGet(count);    // Atomically add to the shared sum
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


//    Requirement
//    1 to 1,000,000, count all the prime numbers
//    return integer

//    When 100000000(10 million)
//    Total no. of prime no. are:- 664579
//    Total time taken:- 6431miliseconds

//    When 1000000000(100 million)
//    Total no. of prime no. are:- 5761455
//    Total time taken:- 165994miliseconds

    public static void main(String[] args){
        int count = 0;
        long begin = System.currentTimeMillis();
        int n = 1000000;
        Thread t = null;
        for (int i = 0; i < 10; i++) {
            t = new Thread(new FindPrimeWithParallelProcessing(i * n + 1, (i + 1) * n));
            t.start();
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

}
