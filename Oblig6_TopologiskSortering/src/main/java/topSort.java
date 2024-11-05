import java.io.*;
import java.util.*;

public class topSort extends enkelGraf {

    boolean oppsokt[];

    private ArrayList<node> topology;


    public topSort(String filNavn) {
        super(filNavn);
        oppsokt = new boolean[n];
    }

    // Jobbet sammen med Tarik Jelin

    public void findAndPrint(){
        int N = antallNoder()-1;
        // To lister
        topology = new ArrayList<>(N);
        ArrayList<node> S = new ArrayList<>(N);

        System.out.println("\nSkriver ut noder med antall naboer");
        System.out.println("--------------------");
        // Loop gjennom nodene i data arrayen
        for(int start = 0; start<=N; start++){
            System.out.println(data[start].data + " har " + data[start].inngrad + " naboer");
            // Om inngraden til en node er lik 0, legg til i liste S
            if(data[start].inngrad == 0)
                S.add(data[start]);
        }
        System.out.println("--------------------");
        System.out.println("\nTopologisk sortering");
        System.out.println("--------------------");
        // Loop som går mens liste S ikke er tom
        while(S.size()!=0){

            System.out.print(S.get(0).data + " ");
            for(int i=0; i< n; i++) {
                for(int j=0; j<n; j++)
                    // Nesta for loop som sjekker om en node har naboer, og om den naboen er lik noden som finnes i liste S.
                    // Sier også at de ikke skal være like
                if (nabo[i][j] && data[j].data.equals(S.get(0).data) && i!=j) {
                    // Finnes den, så fjerner vi den som nabo og reduserer inngrad med 1
                    data[i].inngrad--;
                    // Om inngraden til noden nå er 0, så legges den til i liste S
                    if(data[i].inngrad == 0)
                        S.add(data[i]);
                }
            }
            // Legger til node som skal fjernes i liste Topology
            topology.add(S.get(0));
            // Fjerner noden fra liste S
            S.remove(0);
        }
        // Sorterer listen
        System.out.println("\n--------------------");
        System.out.println("Sortert fra høyre til venstre");
        for(int i = topology.size()-1; i>=0; i--){
            System.out.print(topology.get(i).data + " ");
        }
    }


    public static void main(String args[]) {

        String filNavn = " ";
        try {
            if (args.length != 1)
                throw new IOException("Mangler filnavn");
            filNavn = args[0];
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        // Oppretter ny graf
        topSort G = new topSort(filNavn);
        G.findAndPrint();
    }
}