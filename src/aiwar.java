import java.io.*;
import java.util.*;

public class aiwar
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "D-small-practice";

    private static int [] eadj, elast, eprev, esize;
    private static int eidx;
    
    private static void addEdge (int a, int b) {
        eadj [eidx] = b; eprev [eidx] = elast [a]; elast [a] = eidx++;
        eadj [eidx] = a; eprev [eidx] = elast [b]; elast [b] = eidx++;
        esize [a]++; esize [b]++;
    }
    
    
    
    private static void main2 () throws IOException {
        int P = in.nextInt(), W = in.nextInt();
        
        eadj = new int [2 * W];
        elast = new int [P];
        eprev = new int [2 * W];
        esize = new int [P];
        eidx = 0;
        Arrays.fill (elast, -1);
        
        String [] input = in.readLine().split (" ");
        for (int i = 0; i < W; i++) {
            String [] nums = input [i].split (",");
            addEdge (Integer.parseInt (nums [0]), Integer.parseInt (nums [1]));
        }
        
        int [] queue = new int [P];
        dist = new int [P];
        Arrays.fill (dist, -1);
        int front = 0, back = 0;
        queue [back++] = 0;
        dist [0] = 0;
        while (front < back) {
            int cur = queue [front++];
            for (int e = elast [cur]; e != -1; e = eprev [e]) {
                if (dist [eadj [e]] != -1) continue;
                dist [eadj [e]] = dist [cur] + 1;
                queue [back++] = eadj [e];
            }
        }
        
        out.print (dist [1] - 1);
        
        recurse (1, new boolean [P]);
        out.println (" " + max);
    }
    
    private static int max = 0;
    private static int [] dist;
    private static void recurse (int node, boolean [] vis) {
        if (node == 0) {
            int count = 0;
            for (int i = 0; i < vis.length; i++)
                if (vis [i]) count++;
            if (count > max) max = count;
        }
        for (int e = elast [node]; e != -1; e = eprev [e]) { 
            if (dist [eadj [e]] == dist [node] - 1) {
                boolean [] temp = new boolean [vis.length];
                System.arraycopy (vis, 0, temp, 0, vis.length);
                for (int p = elast [eadj [e]]; p != -1; p = eprev [p]) temp [eadj [p]] = true;
                recurse (eadj [e], temp);
            }
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
        byte [] buf = new byte [100000];
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