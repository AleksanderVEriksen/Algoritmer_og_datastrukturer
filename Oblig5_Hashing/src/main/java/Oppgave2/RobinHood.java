package Oppgave2;

import java.io.*;
import java.util.Scanner;

// Hashing av tekststrenger med lineÃ¦r probing
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell
// - Tilbyr bare innsetting og sÃ¸king
//
public class RobinHood
{
    // Hashlengde
    private final int hashLengde;

    // Hashtabell
    private final nyVerdi[] hashTabell;                 // Hashtabellen er av type nyVerdi objekt

    // Antall elementer lagret i tabellen
    private int n;

    // Antall probes ved innsetting
    private int antProbes;

    public static class nyVerdi {                       // Objekt av String verdien
        String S;
        int flytt;
        public nyVerdi(String S, int flytt){
            this.S = S;
            this.flytt = flytt;
        }
    }

    public RobinHood(int lengde)
    {
        hashLengde = lengde;
        hashTabell = new nyVerdi[lengde];           // Hashtabellen inneholder da nyVerdi objekter
        n = 0;
    }

    public float loadFactor()
    {
        return ((float) n)/hashLengde;
    }

    public int antData()
    {
        return n;
    }

    public int antProbes()
    {
        return antProbes;
    }


    int hash(Object S)
    {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Innsetting av tekststreng med lineÃ¦r probing
    // Avbryter med feilmelding hvis ledig plass ikke finnes
    //
    void insert(String g)
    {
        // Lager String objektet newValue
        nyVerdi newValue = new nyVerdi(g, 0);
        int originalIndex = hash(newValue.S);

        int Index = originalIndex;

        // En check om LCFS eller lineær probing var brukt
        boolean check = true;

        System.out.println("Ny String verdi: " + newValue.S);
        System.out.println("Dens hashVerdi: " + hash(newValue.S));

        // While løkke som går til neste index er null
        while (hashTabell[Index] != null)
        {
            antProbes++;
                                                                            //System.out.println(hashTabell[Index].S + " " + hashTabell[Index].flytt + " < " + newValue.S + " " + newValue.flytt);
              // Har String objektet som finnes fra før av flyttet mer enn den nye verdien
            if(hashTabell[Index].flytt < newValue.flytt) {
                // LCFS blir brukt
                check = false;
                 // Beholder objektet som finnes                                                //System.out.println("Current verdi: " + Index + " " + hashTabell[Index].S);
                nyVerdi tempValue = hashTabell[Index];
                // Setter nåværende objekt til den nye Verdin
                hashTabell[Index] = newValue;
                // Setter nyVerdi til den som var der før                                      //System.out.println("Etter swap: " + Index + " " + hashTabell[Index].S);
                newValue = tempValue;
                // Basically la inn den nye verdien der det eksisterende objektet var, og det eksisterende objektet blir nå vårt nye sammenligner
            }
            Index++;
            // Teller flytt
            newValue.flytt++;
            if (Index >= hashLengde)
                Index = 0;

            if (Index == originalIndex) {
                System.err.println("\nHashtabell full, avbryter");
                System.exit(0);
            }
        }
        hashTabell[Index] = newValue;

        // Printer ut om det var LCFS eller lineær probing som ble brukt
        if(check)
            System.out.println("Linear probing");
        else
            System.out.println("LCFS");

        n++;
        // Printer ut elementer som finnes til nå
        System.out.println("----------------");
        for (nyVerdi s : hashTabell) {
            if(s != null)
            System.out.println( hash(s.S) + ": " + s.S + " med " + s.flytt + " flytt");
            else
                System.out.println(s);
        }
        System.out.println("----------------");
    }


    // SÃ¸king etter tekststreng med lineÃ¦r probing
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S)
    {
        // Beregner hashverdien
        int h = hash(S);

        // LineÃ¦r probing
        int neste = h;

        while (hashTabell[neste] != null)
        {
            // Har vi funnet tekststrengen?
            if (hashTabell[neste].S.equals(S)) {
                return true;
            }
            // PrÃ¸ver neste mulige
            neste++;

            // Wrap-around
            if (neste >= hashLengde)
                neste = 0;

            // Hvis vi er kommet tilbake til opprinnelig hashverdi,
            // finnes ikke strengen i tabellen
            if (neste == h)
                return false;
        }

        // Finner ikke strengen, har kommet til en probe som er null
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
    public static void main(String[] argv)
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
        RobinHood hL = new RobinHood(hashLengde);

        // Leser input og hasher alle linjer
         while (input.hasNext())
        {
            hL.insert(input.nextLine());
        }


        /*
        hL.insert("Hei");               // Testet input, kommenterte ut da while-løkken over
        hL.insert("ke");
        hL.insert("ke2");
        hL.insert("ke4");
        hL.insert("Hei2");
        */

        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hL.antData());
        System.out.printf( "Load factor : %5.3f\n",  hL.loadFactor());
        System.out.println("Probes      : " + hL.antProbes());

        // Et par enkle sÃ¸k
        String S = "Volkswagen Karmann Ghia";
        if (hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        S = "Il Tempo Gigante";
        if (!hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

    }
}