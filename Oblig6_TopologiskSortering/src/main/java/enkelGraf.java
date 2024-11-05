import java.io.*;
import java.util.Scanner;

// Enkel implementasjon av uvektet graf med nabomatrise
// Nodene har bare en tegnstreng som data
//
public class enkelGraf
{
    int n;              // Antall noder i grafen
    boolean nabo[][];   // Nabomatrise
    node data[];      // Data array av type node

    public enkelGraf(String filNavn)
    {
        les(filNavn);
        System.out.println("\nSkriver ut data med naboer");
        System.out.println("--------------------");
        skriv();
        System.out.println("--------------------");
    }
    // Node klasse
    public class node{
        int inngrad;
        String data;
        public node(String data){
            this.data = data;
            inngrad = 0;
        }
    }
    // Metode som returnerer antall noder
    //
    public int antallNoder()
    {
        return n;
    }

    public void les(String filNavn)
    {


        try
        {
            Scanner in = new Scanner(new File(filNavn));

            // Leser antall noder i grafen
            n = in.nextInt();

            // Oppretter arrayene som lagrer grafen
            nabo = new boolean[n][n];
            data = new node[n];

            // Initierer hele nabomatrisen til false, men med true pÃ¥
            // hoveddiagonalen (nodene er "nabo med seg selv")
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    nabo[i][j] = (i == j) ? true : false;
                }
            }
            // Leser en linje med data for hver grafnode
            for (int i = 0; i < n; i++)
            {
                // Leser nodenummeret og data i noden
                int nodeNr = in.nextInt();

                // Opprette ny node og legger den inn i arrayet
                data[nodeNr] = new node(in.next());

                // Leser antall naboer for denne noden
                int antNaboer = in.nextInt();
                // Sette inngraden lik naboene
                data[nodeNr].inngrad = antNaboer;
                // Leser og legger inn alle naboene i nabomatrisen
                for (int j = 0; j < antNaboer; j++)
                {
                    int naboNr = in.nextInt();
                    nabo[nodeNr][naboNr] = true;
                }
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }
    }

    // Utskrift av grafen
    //
    public void skriv()
    {
        for (int i = 0; i < n; i++)
        {
            System.out.print(data[i].data + ": ");
            for (int j = 0; j < n; j++)
                if (nabo[i][j] && i!=j)
                    System.out.print(data[j].data + " ");
            System.out.println();
        }
    }

    // Testprogram
    //
    public static void main(String args[])
    {
        // Leser navnet pÃ¥ en fil med grafdata som input fra
        // kommandolinjen
        String filNavn = " ";
        try
        {
            if (args.length != 1)
                throw new IOException("Mangler filnavn");
            filNavn = args[0];
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }

        // Oppretter ny graf
        enkelGraf G = new enkelGraf(filNavn);

        // Skriver ut innholdet av grafen
        G.skriv();
    }
}