import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class ticketswap {
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "A-large";

    public static final long mod = 1000002013;
    
    private static long sumsq (long N) {
        return (N * (N - 1) / 2) % mod;
    }
    
    static class Interval implements Comparable <Interval> {
        public int t, s, p;
        public Interval (int t, int s, int p) {
            this.t = t;
            this.s = s;
            this.p = p;
        }
        
        public int compareTo (Interval other) {
            return t == other.t ? other.s - s : t - other.t;
        }
    }
    
    static class Passenger implements Comparable <Passenger> {
        public long t, p;
        
        public Passenger (long t, long p) {
            this.t = t;
            this.p = p;
        }
        
        public int compareTo (Passenger other) {
            return (int)Math.signum(t == other.t ? other.p - p : t - other.t);
        }
    }
    
    private static void main2() throws IOException {
        int N = in.nextInt(), M = in.nextInt();
        PriorityQueue <Interval> pq = new PriorityQueue <Interval> ();
        long pcost = 0;
        for (int i = 0; i < M; i++) {
            int s = in.nextInt(), e = in.nextInt(), p = in.nextInt();
            pq.add (new Interval (s, 1, p));
            pq.add (new Interval (e, -1, p));
            pcost += (sumsq (e - s) * p) % mod;
        }
        
        long off = 0;
        PriorityQueue <Passenger> pq2 = new PriorityQueue <Passenger> ();
        long ptime = -1;
        long ncost = 0;
        while (pq.size() > 0) {
            Interval b = pq.poll();
            if (ptime == -1) ptime = b.t;
            off += b.t - ptime;
            if (b.s == -1) { // passengers getting off
                // take tickets that have been in queue the shortest
                while (b.p > 0) {
                    Passenger c = pq2.poll();
                    long tt = c.t + off;
                    long ap = Math.min (b.p, c.p);
                    ncost += (sumsq (tt) * ap) % mod;
                    b.p -= ap;
                    if (ap < c.p) {
                        pq2.add (new Passenger (c.t, c.p - ap));
                    }
                }
                
            } else { // passengers getting on
                pq2.add (new Passenger (-off, b.p));
            }
            ptime = b.t;
        }
        
        out.println ((ncost - pcost + mod) % mod);
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
