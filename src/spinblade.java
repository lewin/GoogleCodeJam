import java.io.*;
import java.util.*;

public class spinblade
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "B-large";

    
    private static long [][] grid;
    private static void main2 () throws IOException {
        int R = in.nextInt (), C = in.nextInt (), D = in.nextInt ();
        
        grid = new long [R][C];
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++)
                grid [i][j] = (in.nextChar() - '0') + D;
                
                
        for (int size = Math.min (R, C) - 1; size >= 2; size--) {
            for (int i = 0; i < R - size; i++)
                for (int j = 0; j < C - size; j++)
                    if (ok (i, j, size)) {
                        out.println (size + 1);
                        return;
                    }
        }
        
        out.println ("IMPOSSIBLE");
    }
    
    private static boolean ok (int x, int y, int size) {
        long xc = 0, yc = 0;
        for (int i = x; i <= x + size; i++)
            for (int j = y; j <= y + size; j++) {
                if (i == x && j == y) continue;
                if (i == x && j == y + size) continue;
                if (i == x + size && j == y) continue;
                if (i == x + size && j == y + size) continue;
                
                xc += grid [i][j] * (2 * i - (2 * x + size));
                yc += grid [i][j] * (2 * j - (2 * y + size));
            }
        return xc == 0 && yc == 0;
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