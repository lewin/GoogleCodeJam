import java.util.*;
import java.io.*;
import java.math.*;

public class fairandsquare {
    private static BufferedReader in;
    private static PrintWriter out;
    public static final String NAME = "A-large";
    
    private static ArrayList <String> generate (int digits) {
        ArrayList <String> nums = new ArrayList <String> ();
        if (digits == 1) {nums.add ("1"); nums.add ("4"); nums.add ("9"); return nums;}
        if (digits % 2 == 0) {
            
        }
        
        return nums;
    }
    
    private static void main2() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new FileReader (NAME + ".in"));
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
