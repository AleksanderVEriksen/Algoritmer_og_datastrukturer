package Oppgave3;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class Hash_Kjeding
{

    private class hashNode
    {
        // Data, en tekststreng
        String data;
        // Neste node i listen
        hashNode neste;


        public hashNode(String S, hashNode hN)
        {
            data = S;
            neste = hN;
        }
    }


    private int hashLengde;


    private hashNode hashTabell[];

    private int n;


    private int antKollisjoner;

    public Hash_Kjeding(int lengde)
    {
        hashLengde = lengde;
        hashTabell = new hashNode[lengde];
        n = 0;
        antKollisjoner = 0;
    }


    public float loadFactor()
    {
        return ((float) n)/hashLengde;
    }


    public int antData()
    {
        return n;
    }

    public int antKollisjoner()
    {
        return antKollisjoner;
    }

    // Hashfunksjon
    int hash(String S)
    {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

   public void removeHash(String S){
        // Lager node object av String -> indeksen som stringen skal ligge i
        hashNode hN = hashTabell[hash(S)];
        // Lager en temp node som brukes om hashnoden har noder etter seg
        hashNode temp = null;
        // Om den blir slettet
        boolean deleted = false;
       // Leter gjennom listen
       while (hN != null) {
               System.out.println("HashNode: " + hN);
               // Ligger den der?
               if (hN.data.compareTo(S) == 0) {
                   // Finnes det en hashNode etter?(Som er koblet til denne hashnoden)
                   if (hN.neste != null) {
                       //Ta vare på noden
                       temp = hN.neste;
                       // Fjerner den som skal fjernes
                       hashTabell[hash(S)] = null;
                       // Setter neste  hashNode til den som inneholdt stringen som skulle fjernes(Neste node tar dens plass)
                       hashTabell[hash(S)] = temp;
                       // dekrementerer elementer som finnes
                       n--;
                       // Forteller boolean verdien at den er slettet
                       deleted = true;
                       // Hopper ut av loopen
                       break;

                   } else {
                       // Hashnoden hadde ingen andre hashnoder etter seg
                       // Sletter den bare
                       hashTabell[hash(S)] = null;
                       n--;
                       deleted = true;
                       break;
                   }

               }

               // Om hashnoden vi undersøker ikke holdt den korrekte string dataen, sjekk neste
               hN = hN.neste;
           }
            // Om noden ble fjernet, skriv ut en melding
           if(!deleted)
            System.out.println("Finner ikke strengen, har kommet til slutten av listen");
    }


    void insert(String S)
    {
        // Beregner hashverdien
        int h = hash(S);

        // Ã˜ker antall elementer som er lagret
        n++;

        // Sjekker om kollisjon
        if (hashTabell[h] != null)
            antKollisjoner++;

        // Setter inn ny node fÃ¸rst i listen
        hashTabell[h] = new hashNode(S, hashTabell[h]);

        System.out.println(hashTabell[h].data);
    }
    public void printHash(){
        for(hashNode s : hashTabell){
            if(s!=null)
            System.out.println("Node: " + s + " with " + s.data);
        }
    }

    // SÃ¸king etter tekststreng i hashtabell med kjeding
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S)
    {
        // Finner listen som S skal ligge i
        hashNode hN = hashTabell[hash(S)];

        // Leter gjennom listen
        while (hN != null)
        {
            // Har vi funnet tekststrengen?
            if (hN.data.compareTo(S) == 0) {
                System.out.println((hashTabell[hash(S)].data) + " " + hN.data);
                return true;
            }
            // PrÃ¸ver neste
            hN = hN.neste;
        }
        // Finner ikke strengen, har kommet til slutten av listen
        return false;
    }

    // Enkelt testprogram:
    //
    // * Hashlengde gis som input pÃ¥ kommandolinjen
    //
    // * Leser tekststrenger linje for linje fra standard input
    //   og lagrer dem i hashtabellen
    //
    // * Skriver ut litt statistikk etter innsetting
    //
    // * Tester om sÃ¸k fungerer for et par konstante verdier
    //
    public static void main(String argv[])
    {
        // Hashlengde leses fra kommandolinjen
        int hashLengde = 0;
        Scanner input = new Scanner(System.in);
        try
        {
            if (argv.length != 1)
                throw new IOException("Feil: Hashlengde mÃ¥ angis");
            hashLengde = Integer.parseInt(argv[0]);
            if (hashLengde < 1 )
                throw new IOException("Feil: Hashlengde mÃ¥ vÃ¦re stÃ¸rre enn 0");
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }

        // Lager ny hashTabell
        Hash_Kjeding hC = new Hash_Kjeding(hashLengde);

        // Leser input og hasher alle linjer
        while (input.hasNext())
        {
            hC.insert(input.nextLine());
        }

        /*
        hC.insert("Yooo");
        hC.insert("Booo");

        hC.insert("Aooo");
        hC.insert("Cooo");
        hC.insert("Gooo");
        hC.insert("Dooo");
        */
        System.out.println("--------------------");
        hC.printHash();
        System.out.println("--------------------");
        System.out.println("Finns Gooo? -> " + hC.search("Gooo"));
        hC.removeHash("Gooo");
        System.out.println("Finns Gooo? -> " + hC.search("Gooo"));
        System.out.println("--------------------");
        hC.printHash();
        System.out.println("--------------------");
        hC.removeHash("Booo");
        System.out.println("Finns Booo? -> " + hC.search("Booo"));
        System.out.println("--------------------");
        hC.printHash();
        System.out.println("--------------------");
        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hC.antData());
        System.out.printf( "Load factor : %5.3f\n",  hC.loadFactor());
        System.out.println("Kollisjoner : " + hC.antKollisjoner());

        // Et par enkle sÃ¸k
        String S = "Volkswagen Karmann Ghia";
        if (hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        S = "Il Tempo Gigante";
        if (!hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

    }
}
