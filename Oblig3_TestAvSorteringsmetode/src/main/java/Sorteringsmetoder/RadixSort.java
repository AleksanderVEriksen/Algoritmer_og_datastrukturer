package Sorteringsmetoder;

import java.util.LinkedList;
import java.util.Queue;

public class RadixSort {
    public static void radixSort(int[] a, int maksAntSiffer) {
        {
            {
                // Radixsortering av en array a med desimale heltall
                // maksAntSiffer: Maksimalt antall siffer i tallene

                int ti_i_m = 1; // Lagrer 10^m
                int n = a.length;

                // Oppretter 10 tomme kÃ¸er
                Queue<Integer>[] Q = (Queue<Integer>[]) (new Queue[10]);

                for (int i = 0; i < 10; i++)
                    Q[i] = new LinkedList();

                // Sorterer pÃ¥ et og et siffer, fra hÃ¸yre mot venstre

                for (int m = 0; m < maksAntSiffer; m++) {
                    // Fordeler tallene i 10 kÃ¸er
                    for (int i = 0; i < n; i++) {
                        int siffer = (a[i] / ti_i_m) % 10;
                        Q[siffer].add(new Integer(a[i]));
                    }

                    // TÃ¸mmer kÃ¸ene og legger tallene fortlÃ¸pende tilbake i a
                    int j = 0;
                    for (int i = 0; i < 10; i++)
                        while (!Q[i].isEmpty())
                            a[j++] = (int) Q[i].remove();

                    // Beregner 10^m for neste iterasjon
                    ti_i_m *= 10;
                }
            }
        }
    }
}
