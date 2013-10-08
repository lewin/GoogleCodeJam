import java.io.*;
import java.util.*;

public class expdinner
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "C-small-attempt1";

    private static int [] ansInc, ansDec;
    private static int [] factors;
    private static boolean [] prime;
    private static void calculate() {
        prime = new boolean [1001];
        factors = new int [1001];
        Arrays.fill (prime, true);
        prime [0] = prime [1] = false;
        prime [2] = true;
        for (int i = 4; i <= 1000; i += 2) prime [i] = false;
        for (int i = 3; i <= 1000; i += 2)
            if (prime [i])
                for (int j = i + i; j <= 1000; j += i)
                    prime [j] = false;
        
        ansInc = new int [1001];
        ansInc [1] = 1;
        for (int i = 2; i <= 1000; i++) {
            boolean add = false;
            int b = i;
            for (int j = 2; j * j <= b; j++) {
                if (!prime [j]) continue;
                int t = 0;
                while (b % j == 0) {b /= j; t++;}
                if (factors [j] < t) {
                    factors [j] = t;
                    add = true;
                }
            }
            if (prime [b] && factors [b] == 0) {
                factors [b]++; add = true;
            }
            ansInc [i] = ansInc [i - 1];
            if (add) ansInc [i]++;
        }
        
        ansDec = new int [1001];
        ansDec [1] = 1;
        for (int i = 2; i <= 1000; i++) {
            int count = 0;
            factors = new int [1001];
            for (int j = i; j >= 2; j--) {
                int b = j;
                boolean add = false;
                for (int k = 2; k * k <= b; k++) {
                    if (!prime [k]) continue;
                    int t = 0;
                    while (b % k == 0) {b /= k; t++;}
                    if (factors [k] < t) {
                        factors [k] = t;
                        add = true;
                    }
                }
                if (prime [b]) {
                    if (factors [b] == 0) {
                        factors [b] = 1;
                        add = true;
                    }
                }
                if (add) count++;
            }
            ansDec [i] = count;
            System.out.println (i + " " + ansInc [i] + " " + ansDec [i]);
        }
    }
    
    private static void main2 () throws IOException {
        int N = in.nextInt ();
        out.println (ansInc [N] - ansDec [N]);
    }

    public static void main (String [] args) throws IOException {
        in = new Reader (NAME + ".in");
        out = new PrintWriter (new BufferedWriter (new FileWriter (NAME + ".out")));

        calculate();
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