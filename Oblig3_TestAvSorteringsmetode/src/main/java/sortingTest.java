import Sorteringsmetoder.Insertion_sort;
import Sorteringsmetoder.MergeSort;
import Sorteringsmetoder.QuickSort;
import Sorteringsmetoder.RadixSort;

import java.util.Scanner;

import java.io.*;
import java.util.*;

public class sortingTest
{
    // Simple performance comparison of sorting algorithms

    public static void main(String[] args)
    {


        int[] A;
        long time = 0;
        Scanner user = new Scanner(System.in);
        System.out.println("Enter size of array: ");
        int n = user.nextInt();
        // Read number of elements to be sorted from command line
        int maxDigits = (int)Math.ceil((Math.log10(2*n)));

        A = new int[n];
        System.out.println("What sorting method do you want to use? ");
        System.out.println("1 for Insertion sort");
        System.out.println("2 for quicksort");
        System.out.println("3 for mergesort");
        System.out.println("4 for radix sort");
        System.out.println("5 if you want to quit");

        int input = user.nextInt();

        // Switch that keeps track of what type of sorting method you chose or if you quit
        switch(input) {
            case 1:
            // Timing of insertion sort
                if(askUser() == 1) {
                    randomize(A);
                    time = System.currentTimeMillis();
                    Insertion_sort.insertionSort(A);
                    time = System.currentTimeMillis() - time;
                    System.out.printf("Insertion sort\t: %6.3f s\n", time / 1000.0);
                }
                else{
                    randomize(A);
                    time = System.currentTimeMillis();
                    Insertion_sort.insertionSort(A);
                    time = System.currentTimeMillis() - time;
                    double C = time/(Math.pow(n,2));
                    System.out.println("The estimated workload for constant C is " + C);
                }
                break;
            case 2:
            // Timing of quicksort
                if(askUser() == 1) {
                    randomize(A);
                    time = System.currentTimeMillis();
                    QuickSort.quickSort(A, 0, n - 1);
                    time = System.currentTimeMillis() - time;
                    System.out.printf("Quicksort\t: %6.3f s\n", time / 1000.0);

                }
                else{
                    randomize(A);
                    time = System.currentTimeMillis();
                    QuickSort.quickSort(A, 0, n - 1);
                    time = System.currentTimeMillis() - time;
                    double C = time/(n*Math.log(n));
                    System.out.println("The estimated workload for constant C is " + C);
                }
                break;
            case 3:
            // Timing of merge sort
                if(askUser() == 1) {
                    randomize(A);
                    time = System.currentTimeMillis();
                    MergeSort.mergeSort(A, 0, n - 1);
                    time = System.currentTimeMillis() - time;
                    System.out.printf("Merge sort\t: %6.3f s\n", time / 1000.0);
                }
                else{
                    randomize(A);
                    time = System.currentTimeMillis();
                    MergeSort.mergeSort(A, 0, n - 1);
                    time = System.currentTimeMillis() - time;
                    double C = time/(n*Math.log(n));
                    System.out.println("The estimated workload for constant C is " + C);
                }
                break;
            case 4:

                if(askUser() == 1) {
                    randomize(A);
                    time = System.currentTimeMillis();
                    RadixSort.radixSort(A, maxDigits);
                    time = System.currentTimeMillis() - time;
                    System.out.printf("Radix sort\t: %6.3f s\n", time / 1000.0);
                }
                else{
                    randomize(A);
                    time = System.currentTimeMillis();
                    RadixSort.radixSort(A, maxDigits);
                    time = System.currentTimeMillis() - time;
                    System.out.println(time + " " + maxDigits + " " + n);
                    double calc =maxDigits*n;
                    double C =  (time/calc);
                    System.out.println("The estimated workload for constant C is " + C);
                }
                break;
            case 5:
                    System.exit(1);
        }
    }

    // Fills array with unsorted random numbers up to 2*n
    public static void randomize(int[] A)
    {
        Random r = new Random();
        int n = A.length;
        for (int i = 0; i < n; i++)
            A[i] = r.nextInt(2*n);
    }
    // Aks user what type of test you want to preform with the chosen sorting method
    public static int askUser(){
        Scanner input = new Scanner(System.in);
        System.out.println("What sorting method? ");
        System.out.println("1 for regular sorting with timer");
        System.out.println("2 for estimate the constant of the workload");

        return input.nextInt();
    }
}