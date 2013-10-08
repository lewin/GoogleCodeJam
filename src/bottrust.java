import java.io.*;
import java.util.*;

public class bottrust
{
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "A-large";

    private static void main2 () throws IOException {
        int N = in.nextInt ();
        char [] move = new char [N];
        int [] pos = new int [N];
        for (int i = 0; i < N; i++) {
            move [i] = in.nextChar ();
            pos [i] = in.nextInt ();
        }
        int nexto = 1, nextb = 1;
        boolean seenO = false, seenB = false;
        int idxo = -1, idxb = -1;
        for (int i = 0; i < N; i++) {
            if (move [i] == 'O' && !seenO) {
                seenO = true;
                nexto = pos [i];
                idxo = i;
            }
            if (move [i] == 'B' && !seenB) {
                seenB = true;
                nextb = pos [i];
                idxb = i;
            }
            if (seenO && seenB) break;
        }
        
        int time = 0;
        int curo = 1, curb = 1;
        int curidx = 0;
        while (true) {
            boolean pushed = false;
            if (nexto == curo && curidx == idxo) {
                for (int i = idxo + 1; i < N; i++) {
                    if (move [i] == 'O') {
                        idxo = i;
                        nexto = pos [i];
                        break;
                    }
                }
                curidx++;
                pushed = true;
            } 
            else if (nexto > curo) curo++;
            else if (nexto < curo) curo--;
            
            if (!pushed && nextb == curb && curidx == idxb) {
                for (int i = idxb + 1; i < N; i++) {
                    if (move [i] == 'B') {
                        idxb = i;
                        nextb = pos [i];
                        break;
                    }
                }
                curidx++;
            }
            else if (nextb > curb) curb++;
            else if (nextb < curb) curb--;
            
            time++;
            if (curidx == N) break;
        }
        
        out.println (time);
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