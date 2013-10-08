import java.io.*;
import java.util.*;

public class booleantree
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "A-large-practice (1)";
    
    public static final int INF = 100000;
    
    private static boolean [] andgate, canchange, val;
    private static int M;
    private static int [][] cache;

    private static void main2 () throws IOException {
        M = in.nextInt ();
        boolean V = in.nextInt () == 1;
        
        andgate = new boolean [M + 1];
        canchange = new boolean [M + 1];
        val = new boolean [M + 1];
        
        for (int i = 1; i <= (M - 1) / 2; i++) {
            int a = in.nextInt (), b = in.nextInt ();
            andgate [i] = a == 1;
            canchange [i] = b == 1;
        }
        
        for (int i = 1; i <= (M + 1) / 2; i++)
            val [i + (M - 1) / 2] = in.nextInt () == 1;
            
        computeTree (1);
        cache = new int [M + 1][2];
        int ans = rec (1, V);
        out.println (ans >= INF ? "IMPOSSIBLE" : ans);
    }
    
    private static int rec (int node, boolean want) {
        if (val [node] == want) return 0;
        if (2 * node > M) return INF;
        if (cache [node][want ? 1 : 0] != 0)
            return cache [node][want ? 1 : 0];
        int min = Integer.MAX_VALUE;
        if (andgate [node]) {
            if (want)
                min = rec (2 * node, true) + rec (2 * node + 1, true);
            else
                min = Math.min (rec (2 * node, false), rec (2 * node + 1, false));
        } else {
            if (want)
                min = Math.min (rec (2 * node, true), rec (2 * node + 1, true));
            else
                min = rec (2 * node, false) + rec (2 * node + 1, false);
        }
        if (canchange [node]) {
            if (andgate [node]) {
                if (want)
                    min = Math.min (1 + Math.min (rec (2 * node, true), rec (2 * node + 1, true)), min);
                else
                    min = Math.min (1 + rec (2 * node, false) + rec (2 * node + 1, false), min);
            } else {
                if (want)
                    min = Math.min (1 + rec (2 * node, true) + rec (2 * node + 1, true), min);
                else
                    min = Math.min (1 + Math.min (rec (2 * node, false), rec (2 * node + 1, false)), min);
            }
        }
        
        return cache [node][want ? 1 : 0] = min;
    }
    
    private static boolean computeTree (int curNode) {
        if (2 * curNode > M) return val [curNode];
        computeTree (2 * curNode);
        computeTree (2 * curNode + 1);
        return val [curNode] = andgate [curNode] ? 
                val [2 * curNode] && val [2 * curNode + 1] :
                val [2 * curNode] || val [2 * curNode + 1] ;
    }

    public static void main (String [] args) throws IOException {
        in = new Reader (NAME + ".in");
        out = new PrintWriter (new BufferedWriter (new FileWriter (NAME + ".out")));

        int numCases = in.nextInt ();
        for (int test = 1; test <= numCases; test++) {
            out.print ("Case #" + test + ": ");
            main2 ();
        }
        
        out.close ();
        System.exit (0);
    }
    
    
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte [] buffer;
    private int bufferPointer, bytesRead;
    
    public Reader () {
        din = new DataInputStream (System.in);
        buffer = new byte [BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }
    
    public Reader (String file_name) throws IOException {
        din = new DataInputStream (new FileInputStream (file_name));
        buffer = new byte [BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }
    
    public String readLine () throws IOException {
        byte [] buf = new byte [1024];
        int cnt = 0, c;
        while ((c = read ()) != -1) {
            if (c == '\n') break;
            buf [cnt++] = (byte)c;
        }
        return new String (buf, 0, cnt);
    }
    
    public int nextInt () throws IOException {
        int ret = 0;
        byte c = read ();
        while (c <= ' ') c = read ();
        boolean neg = (c == '-');
        if (neg) c = read ();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read ()) >= '0' && c <= '9');
        if (neg) return -ret;
        else return ret;
    } 
    
    public long nextLong () throws IOException {
        long ret = 0;
        byte c = read ();
        while (c <= ' ') c = read ();
        boolean neg = (c == '-');
        if (neg) c = read ();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read ()) >= '0' && c <= '9');
        if (neg) return -ret;
        else return ret;
    }        
        
    public double nextDouble () throws IOException {
        double ret = 0, div = 1;
        byte c = read ();
        while (c <= ' ') c = read ();
        boolean neg = (c == '-');
        if (neg) c = read ();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read ()) >= '0' && c <= '9');
        if (c == '.') {
            while ((c = read ()) >= '0' && c <= '9')
                ret += (c-'0') / (div *= 10);
        }
        if (neg) return -ret; 
        else return ret;
    }
    
    public char nextChar () throws IOException {
        char ret = 0;
        byte c = read ();
        while (c <= ' ') c = read ();
        return (char) c;
    }
        
    private void fillBuffer () throws IOException {
        bytesRead = din.read (buffer, bufferPointer=0, BUFFER_SIZE);
        if(bytesRead == -1) buffer [0] = -1;
    }
            
    private byte read () throws IOException {
        if (bufferPointer == bytesRead) fillBuffer ();
        return buffer [bufferPointer++];
    }
    
    public void close ()throws IOException {
        if (din == null) return;
        din.close ();
    }
}