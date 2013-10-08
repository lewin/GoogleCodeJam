import java.io.*;
import java.util.*;

public class killerword
{
    private static BufferedReader in;
    private static PrintWriter out;
    public static final String NAME = "B-small-practice";
    
    private static String [] words;

    private static void main2 () throws IOException {
        StringTokenizer st = new StringTokenizer (in.readLine ());
        int N = Integer.parseInt (st.nextToken ()), M = Integer.parseInt (st.nextToken ());
        
        ArrayList <String> [] words = new ArrayList [11];
        
        for (int i = 0; i < N; i++) {
            String s = in.readLine ();
            words [s.length()].add (s);
        }
//         
//         words = new String [N];
//         for (int i = 0; i < N; i++)
//             words [i] = in.readLine ();
            
        for (int i = 0; i < M; i++) {
            String pat = in.readLine ();
            int idx = 0, max = -1;
            
            for (int j = 0; j < N; j++) {
                int C = go (pat, j);
                if (C > max) {
                    max = C; idx = j;
                }
            }
            
            if (i != 0) out.print (" ");
            out.print (words [idx]);
        }
        
        out.println ();
    }
    
    private static int go (String pat, int word) {
        String ans = words [word];
        String template = "";
        while (template.length() < ans.length()) template += ".";
        int ret = 0;
        boolean [] cantUse = new boolean [26], 
                   canUse = reduceVocab (template, cantUse);
        if (numWords == 1) return ret;
        for (char c : pat.toCharArray ()) {
            if (ans.contains ("" + c)) {
                for (int i = 0; i < ans.length(); i++)
                    if (ans.charAt (i) == c)
                        template = template.substring (0, i) + c + template.substring (i + 1);
            }
            else if (canUse [(int)(c - 'a')]) {
                cantUse [(int)(c - 'a')] = true;
                ret++;
            }
            System.arraycopy (reduceVocab (template, cantUse), 0, canUse, 0, 26);
            if (numWords == 1) return ret;
        }
        return ret;
    }
    
    private static int numWords;
    private static boolean [] reduceVocab (String pattern, boolean [] cantUse) {
        boolean [] canUse = new boolean [26];
        numWords = 0;
        for (String s : words)
            if (match (pattern, s, cantUse)) {
                numWords++;
                for (char c : s.toCharArray ())
                    canUse [(int)(c - 'a')] = true;
            }
        return canUse;
    }
    
    private static boolean match (String pat, String word, boolean [] cantUse) {
        if (pat.length () != word.length ()) return false;
        for (int i = 0; i < word.length(); i++)
            if (cantUse [(int)(word.charAt (i) - 'a')] || 
                (word.charAt (i) != pat.charAt (i) && pat.charAt (i) != '.'))
                    return false;
        return true;
    }

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new FileReader (NAME + ".in"));
        out = new PrintWriter (new BufferedWriter (new FileWriter (NAME + ".out")));

        int numCases = Integer.parseInt (in.readLine ());
        for (int test = 1; test <= numCases; test++) {
            out.print ("Case #" + test + ": ");
            main2 ();
        }
        
        out.close ();
        System.exit (0);
    }
}