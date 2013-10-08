import java.io.*;
import java.util.*;

public class uglynum
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "B-large-practice (1)";

    private static void main2 () throws IOException {
        String digits = in.readLine ();
        long [][] dp = new long [digits.length ()][210];
        for (int i = 0; i < digits.length (); i++) {
            int sum = 0, ten = 1;
            for (int j = i; j >= 0; j--) {
                sum += ten * (digits.charAt (j) - '0');
                ten *= 10; ten %= 210;
                sum %= 210;
                if (j == 0) {
                    dp [i][sum]++;
                } else {
                    for (int k = 0; k < 210; k++) {
                        dp [i][k] += dp [j - 1][(k + sum) % 210];
                        dp [i][k] += dp [j - 1][(k + 210 - sum) % 210];
                    }
                }
            }
        }
        
        long count = 0;
        for (int i = 0; i < 210; i++)
            if (i % 2 == 0 || i % 3 == 0 || i % 5 == 0 || i % 7 == 0)
                count += dp [digits.length () - 1][i];
          
        out.println (count);
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