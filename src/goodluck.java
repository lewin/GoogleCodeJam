import java.util.*;
import java.io.*;

public class goodluck {
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "C-small-2-attempt0";

    private static int [] arr;
    private static int [][] pow;
    private static int R, N, M, K;
    private static void main2() throws IOException {
        R = in.nextInt();
        N = in.nextInt();
        M = in.nextInt();
        K = in.nextInt();
        out.println();
        set = new int [N];
        while (R-- > 0) {
            arr = new int [K];
            pow = new int [K][4];
            for (int i = 0; i < K; i++) {
                arr [i] = in.nextInt();
                int T = arr [i];
                while (T % 2 == 0) {
                    pow [i][0]++;
                    T /= 2;
                }
                while (T % 3 == 0) {
                    pow [i][1]++;
                    T /= 3;
                }
                while (T % 5 == 0) {
                    pow [i][2]++;
                    T /= 5;
                }
                while (T % 7 == 0) {
                    pow [i][3]++;
                    T /= 7;
                }
            }
            
            found = false;
            generate (0, 2);
            
            out.print(set [0]);
            for (int i = 1; i < N; i++)
                out.print (set [i]);
            out.println();
        }
        
    }
    
    private static int [] set;

    private static boolean found;
    private static void generate (int idx, int cur) {
        if (idx == N) {
            if (check())
                found = true;
            return;
        }
        if (found) return;
        for (int i = cur; i <= M; i++) {
            set [idx] = i;
            generate (idx + 1, i);
            if (found) return;
        }
    }
    
    private static boolean check() {
        for (int i = 0; i < K; i++) {
            if (!match (arr [i], 0, 1))
                return false;
        }
        return true;
    }
    
    private static boolean match (int goal, int idx, int prod) {
        if (goal == prod) return true;
        if (prod > goal) return false;
        if (idx == N) return false;
        return (goal % set [idx] == 0 && match (goal, idx + 1, prod * set [idx])) || match (goal, idx + 1, prod);
    }

    public static void main(String[] args) throws IOException {
        in = new Reader(NAME + ".in");
        out = new PrintWriter(new BufferedWriter(new FileWriter(NAME + ".out")));

        int numCases = in.nextInt();
        for (int test = 1; test <= numCases; test++) {
            out.print("Case #" + test + ": ");
            main2();
        }

        out.close();
        System.exit(0);
    }

    /** Faster input **/
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[1024];
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            else
                return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            else
                return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9')
                    ret += (c - '0') / (div *= 10);
            }
            if (neg)
                return -ret;
            else
                return ret;
        }

        public char nextChar() throws IOException {
            char ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            return (char) c;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}
