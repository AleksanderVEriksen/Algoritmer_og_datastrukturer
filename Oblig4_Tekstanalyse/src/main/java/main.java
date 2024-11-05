import Tree.binTre;
import Tree.treeNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
        binTre tre = new binTre();
        ArrayList<String> liste = new ArrayList<String>();

        // Tok den fra http://www.it.hiof.no/algdat/oblig/words.java
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer input = new StreamTokenizer(r);
        System.out.println("-----------------------------");
        System.out.println("Skriv så mange ord du kan");
        System.out.println("Quit loopen ved å skrive et tall");
            try {
                int x = input.nextToken();
                while (x != input.TT_EOF) {
                    if (input.ttype == input.TT_WORD) {
                       // Legger ordene i en liste
                        liste.add(input.sval);
                    }
                    // Hopper ut av loopen om et tall skrives
                    if(input.ttype == input.TT_NUMBER){
                        break;
                    }

                    x = input.nextToken();

                }
            } catch (IOException e) {
            }

            // Gjør om alle ordene i listen til store bokstaver
            up(liste);
            // Setter inn ordene i det binære treet
            for (String s : liste) {
                tre.insert(s);
            }
            // Printer ut treet
            tre.printInorder();

    }
    public static void up(ArrayList<String> s){
        s.replaceAll(String::toUpperCase);
    }
}