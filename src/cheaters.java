import java.util.*;
import java.io.*;

public class cheaters {
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "A-small-attempt9";

    private static void main2() throws IOException {
        long amt = in.nextLong();
        int players = in.nextInt();
        System.out.println (amt + " " + players);
        bets = new long [37];
        for (int i = 0; i < players; i++)
            bets [i] = in.nextLong();
        Arrays.sort (bets);
        System.out.println (Arrays.toString (bets));
        
        double exp = 0;
        for (int i = 1; i < 37; i++) {
            if (bets [i] == 0) continue;
            long b = bet (bets [i] - 1);
            if (b <= amt) {
                exp = Math.max (exp, prof (bets [i] - 1));
            } else {
                int c = count (bets [i]);
                long d = bet (bets [i - 1]);
                long diff = (amt - d) / c;
                exp = Math.max (exp, prof (bets[i - 1] + diff));
                break;
            }
        }
        
        out.printf ("%.10f\n", exp);
    }
    
    private static long [] bets;
    
    private static long bet (long m) {
        long sum = 0;
        for (int i = 0; i < 37; i++)
            if (bets [i] < m) {
                sum += m - bets [i];
            }
        return sum;
    }
    
    private static int count (long m) {
        int count = 0;
        for (int i = 0; i < 37; i++)
            if (bets [i] < m)
                count++;
        return count;
    }
    
    private static double prof (long m) {
        long sum = 0;
        int count = 0;
        for (int i = 0; i < 37; i++)
            if (bets [i] <= m) {
                sum += m - bets[i];
                count++;
            }
        if (count == 0) return 0;
        return sum * (36. / (double)count - 1);
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
