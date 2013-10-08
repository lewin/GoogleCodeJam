import java.util.*;
import java.io.*;

public class moist {
    private static BufferedReader in;
    private static PrintWriter out;
    public static final String NAME = "A-small1-attempt0";

    private static void main2() throws IOException {
        int N = Integer.parseInt (in.readLine());
        String [] arr = new String [N];
        for (int i = 0; i < N; i++)
            arr [i] = in.readLine();
        
        int cost = 0;
        for (int j = 1; j < N; j++) {
            if (arr [j].compareTo (arr [j - 1]) < 0) {
                cost++;
                arr [j] = arr [j - 1];
            }
        }
        
        out.println (cost);
    }

    public static void main(String[] args) throws IOException {
        in = new BufferedReader (new FileReader (new File (NAME + ".in")));
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
