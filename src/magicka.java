import java.io.*;
import java.util.*;

public class magicka
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "B-large";

    private static void main2 () throws IOException {
        int C = in.nextInt ();
        char [][] combine = new char [26][26];
        for (int i = 0; i < C; i++) {
            char a = in.nextChar (), b = in.nextChar (), c = in.nextChar ();
            combine [toIdx (a)][toIdx (b)] = combine [toIdx (b)][toIdx (a)] = c;
        }
        int D = in.nextInt ();
        boolean [][] opposed = new boolean [26][26];
        for (int i = 0; i < D; i++) {
            char a = in.nextChar (), b = in.nextChar ();
            opposed [toIdx (a)][toIdx (b)] = true;
            opposed [toIdx (b)][toIdx (a)] = true;
        }
        
        int N = in.nextInt ();
        char [] seq = new char [2 * N];
        int idx = 0;
        for (int i = 0; i < N; i++) {
            char c = in.nextChar ();
            boolean combined = false;
            if (idx > 0 && combine [toIdx (seq [idx - 1])][toIdx (c)] != 0) {
                seq [idx - 1] = combine [toIdx (seq [idx - 1])][toIdx (c)];
                combined = true;
            }
            boolean cleared = false;
            if (!combined) {
                for (int j = 0; j < idx; j++) {
                    if (opposed [toIdx (c)][toIdx (seq [j])]) {
                        idx = 0;
                        cleared = true;
                    }
                }
            }
            if (!cleared && !combined) seq [idx++] = c;
        }
        
        out.print ("[");
        for (int i = 0; i < idx - 1; i++) out.print (seq [i] + ", ");
        if (idx > 0) out.print (seq [idx - 1]);
        out.print ("]\n");
    }
    
    private static int toIdx (char c) {
        return (int)(c - 'A');
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
    
    public String nextString () throws IOException {
        byte [] buf = new byte [1024];
        int cnt = 0, c;
        while ((c = read ()) != -1) {
            if (c <= ' ') break;
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