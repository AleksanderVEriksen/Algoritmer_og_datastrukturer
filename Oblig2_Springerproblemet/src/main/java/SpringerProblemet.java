import java.util.Arrays;
import java.util.Scanner;

public class SpringerProblemet {
    int boardSize;

    public SpringerProblemet(int n){
        this.boardSize = n;
    }
        // Finner vei fra x og y posisjon
    boolean finnVei(int[][] currentBoard,int count, int x, int y)
    {
        // Stopper om alle posisjoner er fyllt og printer boardet
        if (count > boardSize*boardSize)
        {
            return true;
        }

        // Mulige trekk
        int[] dI = { -2,   2,   2,  -2,  1,  -1,  -1,  1};
        int[] dJ = { -1,  -1,   1,   1,  2,   2,  -2, -2};

        for (int k = 0; k < 8; k++)
        {
            int nyI = x + dI[k];
            int nyJ = y + dJ[k];
            // Sjekker om det er lovlig å gå til ny posisjon
            if (isValidMove(nyI, nyJ) && currentBoard[nyI][nyJ] == 0)
            {
               // Om det kan, så setter vi count til posisjonen den er i.
                currentBoard[nyI][nyJ] = count;
                // Prøver å finne vei videre rekursivt
                if (finnVei(currentBoard,count +1 ,nyI, nyJ))
                {
                    return true;
                }
                // Backtracking om if-en over returnerer false
                currentBoard[nyI][nyJ] = 0;
               // showBoard(currentBoard);
            }

        }
        return false;
    }
    // Check if move is valid
    public boolean isValidMove(int x, int y){
        return x < boardSize && x >= 0 && y < boardSize && y >= 0;
    }

    //Printing the board
    public void showBoard(int[][] n) {
        StringBuilder result = new StringBuilder("\n");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                result.append(n[i][j]).append(" ");
            }
            result.append("\n");
        }
        System.out.println(result);
    }
    // Testprogram
    public static void main(String[] argv)

    {
        // Leser stÃ¸rrelsen pÃ¥ labyrinten og prosentandel blokkerte ruter
        Scanner scanner = new Scanner(System.in);
        System.out.print("Board Size?(n x n): ");
        int n = scanner.nextInt();
        System.out.print("Start x posisjon?: ");
        int x = scanner.nextInt();
        System.out.print("Start y posisjon?: ");
        int y = scanner.nextInt();

        int[][] Visited = new int[n][n];
        // Oppretter ny labyrint
        SpringerProblemet lab = new SpringerProblemet(n);
        Visited[x][y] = 1;
        // Leter etter vei fra Ã¸vre venstre hjÃ¸rne
        boolean funnetVei = lab.finnVei(Visited, 2,x, y);

        // Skriver ut labyrinten (og evt. funnet vei)
        lab.showBoard(Visited);
        if (!funnetVei)
            System.out.println("Fant ingen vei gjennom labyrinten med startposisjon (" + x + "," + y + ")");
        else
            System.out.println("\nDu har funnet en løsning med startposisjon (" + x + "," + y + ")");
    }
}
