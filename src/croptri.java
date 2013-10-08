import java.io.*;
import java.util.*;

public class croptri
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "A-large-practice (2)";

    private static void main2 () throws IOException {
        int [][] ways = new int [3][3];
        int n = in.nextInt (), A = in.nextInt (), B = in.nextInt (),
            C = in.nextInt (), D = in.nextInt (), x0 = in.nextInt (),
            y0 = in.nextInt (), M = in.nextInt ();
        ways [x0 % 3][y0 % 3]++;
        for (int i = 1; i <= n - 1; i++) {
            x0 = (int)(((long)A * x0 + B) % M);
            y0 = (int)(((long)C * y0 + D) % M);
            ways [x0 % 3][y0 % 3]++;
        }
        
        long numways = 0;
        for (int x = 0; x <= 2; x++)
            for (int y = 0; y <= 2; y++)
                numways += (long) ways [x][y] * (ways [x][y] - 1) / 2 * (ways [x][y] - 2) / 3;
                
        for (int x = 0; x <= 2; x++)
            numways += (long) ways [0][x] * ways [1][x] * ways [2][x] + (long) ways [x][0] * ways [x][1] * ways [x][2];
           
        int [][] perm = new int [][] { {0, 1, 2}, {0, 2, 1}, {1, 0, 2}, {1, 2, 0}, {2, 0, 1}, {2, 1, 0} };
        
        long temp = 0;
        for (int x = 0; x < 6; x++)
            for (int y = 0; y < 6; y++)
                temp += (long) ways [perm [x][0]][perm [y][0]] * ways [perm [x][1]][perm [y][1]] * ways [perm [x][2]][perm [y][2]];
        numways += temp / 6;
            
        out.println (numways);
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