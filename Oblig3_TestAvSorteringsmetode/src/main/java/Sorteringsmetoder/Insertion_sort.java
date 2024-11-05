package Sorteringsmetoder;

public class Insertion_sort {
    public static void insertionSort(int A[]) {
        // Innstikksortering av array med heltall

        int n = A.length;
        int key;

        for (int i = 1; i < n; i++) {
            // A er sortert t.o.m. indeks i-1
            key = A[i];
            int j = i;
            // Setter element nummer i pÃ¥ riktig plass
            // blant de i-1 fÃ¸rste elementene
            while (j > 0 && A[j - 1] > key) {
                A[j] = A[j - 1];
                j--;
            }
            A[j] = key;
        }
    }
}