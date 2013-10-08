import java.io.*;
import java.util.*;

public class milkshake
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "B-large-practice (1)";

    public static void main (String [] args) throws IOException {
        in = new Reader (NAME + ".in");
        out = new PrintWriter (new BufferedWriter (new FileWriter (NAME + ".out")));

        int numCases = in.nextInt ();
        for (int test = 1; test <= numCases; test++) {
            out.print ("Case #" + test + ": ");
            
            int N = in.nextInt (), M = in.nextInt ();
            ArrayList <Integer> [] inv = new ArrayList [N];
            for (int i = 0; i < N; i++) inv [i] = new ArrayList <Integer> ();
            int [] f = new int [M], size = new int [M];
            for (int i = 0; i < M; i++) {
                f [i] = -1;
                int T = in.nextInt ();
                size [i] = T;
                while (T-- > 0) {
                    int a = in.nextInt () - 1, b = in.nextInt ();
                    if (b == 1) f [i] = a;
                    if (b == 0) inv [a].add (i);
                }
            }
            boolean [] isMalted = new boolean [N];
            
            int [] queue = new int [M];
            int front = 0, back = 0;
            for (int i = 0; i < M; i++) 
                if (size [i] == 1 && f [i] != -1 && !isMalted [f [i]]) {
                    queue [back++] = f [i];
                    isMalted [f [i]] = true;
                }
               
            boolean ok = true;
            all : while (front < back) {
                int cur = queue [front++];
                for (int i : inv [cur]) {
                    size [i]--;
                    if (size [i] == 1 && f [i] != -1 && !isMalted [f [i]]) {
                        queue [back++] = f [i];
                        isMalted [f [i]] = true;
                    }
                    if (size [i] == 0) {
                        ok = false;
                        break all;
                    }
                }
            }
            
            if (ok) {
                out.print ((isMalted [0] ? "1" : "0"));
                for (int i = 1; i < N; i++)
                    out.print (" " + (isMalted [i] ? "1" : "0"));
                out.println ();        
            } else {
                out.println ("IMPOSSIBLE");
            }
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