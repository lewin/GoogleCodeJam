import java.io.*;
import java.util.*;

public class speedlimit
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "C-large-practice";
    public static long mod = 1000000007;

    private static void main2 () throws IOException {
        int n = in.nextInt (), m = in.nextInt (),
            X = in.nextInt (), Y = in.nextInt (), Z = in.nextInt ();
        int [] A = new int [m];
        for (int i = 0; i < m; i++) A [i] = in.nextInt ();
        PriorityQueue <Pair> pq = new PriorityQueue <Pair> ();
        int c = 0;
        for (int i = 1; i <= n; i++) {
            pq.add (new Pair (A [c], i));
            A [c] = (int)(((long)((long)X * (long)A [c] + (long)Y * (long)i)) % Z);
            c++; if (c == m) c = 0;
        }
        
        int [] tree = new int [n + 1];
        long sum = 0;
        while (pq.size () > 0) {
            Pair p = pq.poll ();
            long get = 1;
            for (int idx = p.b; idx > 0; idx -= (idx & -idx)) {
                get += tree [idx];
                if (get >= mod) get -= mod;
            }
            sum += get; if (sum >= mod) sum -= mod;
            for (int idx = p.b; idx <= n; idx += (idx & -idx)) {
                tree [idx] += get;
                if (tree [idx] >= mod) tree [idx] -= mod;
            }
        }
        
        out.println (sum);
    }
    
    static class Pair implements Comparable <Pair> {
        public int a, b;
        
        public Pair (int _a, int _b) {
            a = _a; b = _b;
        }
        
        public int compareTo (Pair other) {
            return a == other.a ? other.b - b : a - other.a;
        }
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