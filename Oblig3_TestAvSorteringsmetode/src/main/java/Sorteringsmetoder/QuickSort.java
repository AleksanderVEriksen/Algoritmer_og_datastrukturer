package Sorteringsmetoder;

public class QuickSort
{
    public static void quickSort(int[] A, int min, int max) {
        // Quicksort av array med heltall
        int indexofpartition;

        if(max -min  >0)

        {
            // Partisjonerer array
            indexofpartition = findPartition(A, min, max);

            // Sorterer venstre del
            quickSort(A, min, indexofpartition - 1);

            // Sorterer hÃ¸yre del
            quickSort(A, indexofpartition + 1, max);
        }
    }
    private static int findPartition (int[] A, int min, int max)
    {
        int left, right;
        int temp, partitionelement;

        // Bruker *fÃ¸rste* element til Ã¥ dele opp
        partitionelement = A[min];

        left = min;
        right = max;

        // GjÃ¸r selve partisjoneringen
        while (left < right)
        {
            // Finn et element som er stÃ¸rre enn part.elementet
            while (A[left] <= partitionelement && left < right)
                left++;

            // Finn et element som er mindre enn part.elementet
            while (A[right] > partitionelement)
                right--;

            // Bytt om de to hvis ikke ferdig
            if (left < right)
            {
                temp = A[left];
                A[left] = A[right];
                A[right] = temp;
            }
        }

        // Sett part.elementet mellom partisjoneringene
        temp = A[min];
        A[min] = A[right];
        A[right] = temp;

        // Returner indeksen til part.elementet
        return right;
    }
}