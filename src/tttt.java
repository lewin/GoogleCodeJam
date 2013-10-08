import java.util.*;
import java.io.*;

public class tttt {
    private static BufferedReader in;
    private static PrintWriter out;
    public static final String NAME = "A-large";
    
    private static String X = "X won", O = "O won", D = "Draw", G = "Game has not completed";
    
    private static void main2() throws IOException {
        char [][] grid = new char [4][4];
        for (int i = 0; i < 4; i++)
            grid [i] = in.readLine().toCharArray();
        in.readLine();
                
        for (int i = 0; i < 4; i++) {
            if (win ('X', grid [i][0], grid [i][1], grid [i][2], grid [i][3])) {
                out.println (X);
                return;
            }
            if (win ('O', grid [i][0], grid [i][1], grid [i][2], grid [i][3])) {
                out.println (O);
                return;
            }
            if (win ('X', grid [0][i], grid [1][i], grid [2][i], grid [3][i])) {
                out.println (X);
                return;
            }
            if (win ('O', grid [0][i], grid [1][i], grid [2][i], grid [3][i])) {
                out.println (O);
                return;
            }
        }
        if (win ('X', grid [0][0], grid [1][1], grid [2][2], grid [3][3])) {
            out.println (X);
            return;
        }
        if (win ('O', grid [0][0], grid [1][1], grid [2][2], grid [3][3])) {
            out.println (O);
            return;
        }
        if (win ('X', grid [0][3], grid [1][2], grid [2][1], grid [3][0])) {
            out.println (X);
            return;
        }
        if (win ('O', grid [0][3], grid [1][2], grid [2][1], grid [3][0])) {
            out.println (O);
            return;
        }
        
        boolean d = true;
        outer : for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (grid [i][j] == '.') {
                    d = false;
                    break;
                }
        
        out.println (d ? D : G);
    }
    
    private static boolean win (char x, char a, char b, char c, char d) {
        int count = 0;
        if (x == a) count++;
        if (x == b) count++;
        if (x == c) count++;
        if (x == d) count++;
        return count == 4 || (count == 3 && (a == 'T' || b == 'T' || c == 'T' || d == 'T'));
    }

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new FileReader (new File (NAME + ".in")));
        out = new PrintWriter(new BufferedWriter(new FileWriter(NAME + ".out")));

        int numCases = Integer.parseInt (in.readLine());
        for (int test = 1; test <= numCases; test++) {
            out.print("Case #" + test + ": ");
            main2();
        }

        out.close();
        System.exit(0);
    }
}
