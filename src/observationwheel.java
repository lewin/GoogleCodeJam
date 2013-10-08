import java.util.*;
import java.io.*;

public class observationwheel {
    private static BufferedReader in;
    private static PrintWriter out;
    public static final String NAME = "D-small-attempt0";

    private static void main2() throws IOException {
        // 0 is free, 1 is occupied;
        
        
        String s = in.readLine();
        int len = s.length();
        int mask = 0;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == 'X')
                mask |= 1 << i;
        }
        
        out.printf ("%.15f\n", exp [len][mask]);
    }
    
    private static double [][] exp;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new FileReader (new File (NAME + ".in")));
        out = new PrintWriter(new BufferedWriter(new FileWriter(NAME + ".out")));

        exp = new double [21][];
        for (int i = 1; i <= 20; i++) {
            exp [i] = new double [1 << i];
            exp [i][(1 << i) - 1] = 0;
            for (int j = (1 << i) - 2; j >= 0; j--) {
                double sum = 0;
                for (int k = 0; k < i; k++) {
                    int step = 0;
                    int l = k;
                    while ((j & (1 << l)) != 0) {
                        l++;
                        step++;
                        if (l == i) l = 0;
                    }
                    sum += i - step + exp [i][j | (1 << l)];
                }
                exp [i][j] = sum / (double)i;
            }
        }
        
        int numCases = Integer.parseInt (in.readLine());
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
