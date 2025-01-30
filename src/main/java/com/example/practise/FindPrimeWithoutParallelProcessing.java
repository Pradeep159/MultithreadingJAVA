package com.example.practise;

public class FindPrimeWithoutParallelProcessing {
//    Requirement
//    1 to 1,000,000, count all the prime numbers
//    return integer


//    When 10000000(10 million)
//    Total no. of prime no. are:- 664579
//    Total time taken:- 14816miliseconds

//    When 100000000(100 million)
//    Total no. of prime no. are:- 5761455
//    Total time taken:- 383681miliseconds

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


    public static void main(String[] args)
    {
        FindPrimeWithoutParallelProcessing t=new FindPrimeWithoutParallelProcessing();
        int count=0;
        int number = 10000000;
        int noOfbatchs = 10;
        int batchSize = number / noOfbatchs;

        long begin = System.currentTimeMillis();

        for(int i=0;i<noOfbatchs;i++)
        {
            count=count+t.countPrime(i*batchSize+1,(i+1)*batchSize);
        }

        long end = System.currentTimeMillis();
        long timetaken = end-begin;

        System.out.println("Total no. of prime no. are:- "+count);
        System.out.println("Total time taken:- "+timetaken+"miliseconds");
    }
}
