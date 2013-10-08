import java.io.*;
import java.util.*;

public class airwalk
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "A-large";

    static class Pair implements Comparable <Pair> {
        public double distance, speed;
        
        public Pair (double distance, double speed) {
            this.distance = distance;
            this.speed = speed;
        }
        
        public int compareTo (Pair other) {
            return (int)(Math.signum (speed - other.speed));
        }
    }
    
    private static void main2 () throws IOException {
        int X = in.nextInt (), S = in.nextInt (), R = in.nextInt (); 
        double t = in.nextInt (); int N = in.nextInt ();
            
        int diff = R - S;
        PriorityQueue <Pair> pq = new PriorityQueue <Pair> ();
        int all = X;
        for (int i = 0; i < N; i++) {
            int B = in.nextInt (), E = in.nextInt (), w = in.nextInt ();
            pq.add (new Pair (E - B, S + w));
            all -= E - B;
        }
        pq.add (new Pair (all, S));
        
        ArrayList <Pair> speeds = new ArrayList <Pair> ();
        while (pq.size() > 0 && t > 0) {
            Pair b = pq.poll();
            double nt = b.distance / (b.speed + diff);
            if (nt <= t) {
                t -= nt;
                speeds.add (new Pair (b.distance, b.speed + diff));
            } else {
                double d = (b.speed + diff) * t;
                speeds.add (new Pair (d, b.speed + diff));
                speeds.add (new Pair (b.distance - d, b.speed));
                t = 0;
            }
        }
        while (pq.size() > 0) speeds.add (pq.poll());
        
        double time = 0;
        for (Pair c : speeds)
            time += c.distance / c.speed;
        System.out.println();
            
        out.printf ("%.6f\n", time);        
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