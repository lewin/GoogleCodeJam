import java.io.*;
import java.util.*;

public class centerofmass
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "B-small-practice";
    private static double cx, cy, cz, cvx, cvy, cvz;

    private static void main2 () throws IOException {
        int N = in.nextInt ();
        cx = 0; cy = 0; cz = 0; cvx = 0; cvy = 0; cvz = 0;
        for (int i = 0; i < N; i++) {
            int x = in.nextInt (), y = in.nextInt (), z = in.nextInt (),
               vx = in.nextInt (), vy = in.nextInt (), vz = in.nextInt ();
            cx += (double)x / N;
            cy += (double)y / N;
            cz += (double)z / N;
            cvx += (double)vx / N;
            cvy += (double)vy / N;
            cvz += (double)vz / N;
        }
        
        double lo = 0, hi = 0000;
        while (hi - lo > 1e-8) {
            double mid1 = (2. * lo + hi) / 3., mid2 = (lo + 2. * hi) / 3.;
            if (dist (to (mid1)) < dist (to (mid2)))
                hi = mid2;
            else
                lo = mid1;
        }
        
        out.printf ("%.6f %.6f\n", Math.sqrt (dist (to (lo))), lo);
    }
    
    private static double [] to (double time) {
        return new double [] {cx + cvx * time, cy + cvy * time, cz + cvz * time};
    }
    
    private static double dist (double... x) {
        double sum = 0;
        for (double y : x) sum += y * y;
        return sum;
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