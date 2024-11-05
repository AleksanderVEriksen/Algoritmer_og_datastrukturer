package Oppgave1;

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
public class LCFS
{
    // Hashlengde
    private int hashLengde;

    // Hashtabell
    private String hashTabell[];

    // Antall elementer lagret i tabellen
    private int n;

    // Antall probes ved innsetting
    private int antProbes;

    // KonstruktÃ¸r
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public LCFS(int lengde)
    {
        hashLengde = lengde;
        hashTabell = new String[lengde];
        n = 0;
        antProbes = 0;
    }

    // Returnerer load factor
    public float loadFactor()
    {
        return ((float) n)/hashLengde;
    }

    // Returnerer antall data i tabellen
    public int antData()
    {
        return n;
    }

    // Returnerer antall probes ved innsetting
    public int antProbes()
    {
        return antProbes;
    }

    // Hashfunksjon
    int hash(String S)
    {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Innsetting av tekststreng med lineÃ¦r probing
    // Avbryter med feilmelding hvis ledig plass ikke finnes
    //
    void insert(String S)
    {
        // Beregner hashverdien
        int h = hash(S);
        System.out.println("Hashverdi for "+ S + " er " + h);
        // LineÃ¦r probing
        int current = h;
        // Lagre nåværende tekst
        String eksisterende = hashTabell[current];
        // Mens eksisterende String ikke er null
        while (eksisterende != null)
        {
            // Ny probe
            antProbes++;
            System.out.println("Nåværende indeks for " + eksisterende + " er " + current );
            current++;
            System.out.println("Kollisjon skjer");
            System.out.println("Neste indeks for " + eksisterende + " er " + current );

            if (current >= hashLengde)
                current = 0;

            if (current == h)
            {
                System.err.println("\nHashtabell full, avbryter");
                System.exit(0);
            }
            // Lagre String verdi til høyre for eksisterende midlertidig
            String temp = hashTabell[current];
            System.out.println("Temps verdi: " + temp);
            // Sette nåværende til eksisterende (flytte til høyre)
            hashTabell[current] = eksisterende;
            // Sette eksisterende til den Stringen vi tok vare på(Lagret)
            eksisterende = temp;

        }
            // Jobbet med Tarik Jelin(Bare denne oppgaven)
            // Hjalp han med denne oppgaven ved å snakke om logikken bak denne koden
            // Hadde gjort den før jeg hjalp han

        // Setter den nye verdien på den indeksen den fikk med en gang
        hashTabell[h] = S;

        n++;

        // Printe ut alle elementer så langt
        for (int i =0; i<hashTabell.length; i++) {
            System.out.println( i + ": " + hashTabell[i]);
        }
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
            if (hashTabell[neste].compareTo(S) == 0)
                return true;

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
        LCFS hL = new LCFS(hashLengde);

        // Leser input og hasher alle linjer
         while (input.hasNext())
        {
            hL.insert(input.nextLine());
        }
        /*
        hL.insert("ge");
        hL.insert("Hei");
        hL.insert("ell");
        hL.insert("Hei2");               // Testet input, kommenterte ut da while-løkken over
        hL.insert("Bro");
        hL.insert("ial");
        hL.insert("ki");
        hL.insert("ki2");
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
