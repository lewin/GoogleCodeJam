import java.util.*;
import java.io.*;

public class profnormal {
    private static Reader in;
    private static PrintWriter out;
    public static final String NAME = "D-small-attempt2";
    
    private static int [] dx = new int [] {-1, 0, 1, 0}, dy = new int [] {0, -1, 0, 1};
    private static int N, M;

    private static void main2() throws IOException {
        N = in.nextInt(); M = in.nextInt();
        
        long [][] grid = new long [N + 2][M + 2];
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= M; j++) {
                grid [i][j] = in.nextLong();
            }
        
        boolean [][] active = new boolean [N + 2][M + 2];
        for (int i = 0; i <= N + 1; i++)
            Arrays.fill (active [i], true);
        for (int i = 0; i <= N + 1; i++)
            active [i][0] = active [i][M + 1] = false;
        for (int j = 0; j <= M + 1; j++)
            active [0][j] = active [N + 1][j] = false;
        
        long iter = 0;
        boolean inf = true; long count = 0;
        while (true) {
            long res = nextGraph (grid, active);
            if (res < 0) {
                count = -res;
                inf = true;
                break;
            } else if (res == 0) {
                inf = false;
                break;
            } else {
                iter += res;
            }
        }
        
        out.println (inf ? (count + " children will play forever") : (iter + " turns"));
    }
    
    private static long nextGraph (long [][] grid, boolean [][] active) {
        int [][] neighbors = new int [N + 2][M + 2];
        
        boolean found = false;
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= M; j++) {
                if (grid [i][j] < 12) {
                    active [i][j] = false;
                    grid [i][j] = 0;
                }
            }
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= M; j++) {
                if (active [i][j]) {
                    for (int k = 0; k < 4; k++) 
                        if (active [i + dx [k]][j + dy [k]])
                            neighbors [i][j]++;
                    
                    if (neighbors [i][j] == 0) {
                        grid [i][j] = 0;
                        active [i][j] = false;
                    } else {
                        found = true;
                    }
                }
            }
        
        if (!found) return 0;
        
        long [][] nflow = new long [N + 2][M + 2];
        
        long mintime = Long.MAX_VALUE;
        int count = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (active [i][j]) {
                    count++;
                    nflow [i][j] = -12;
                    for (int k = 0; k < 4; k++) {
                        if (active [i + dx [k]][j + dy [k]]) {
                            nflow [i][j] += 12 / neighbors [i + dx [k]][j + dy [k]];
                        }
                    }
                    if (nflow [i][j] < 0) {
                        long ttime = -(grid [i][j] - 12) / nflow [i][j] + 1;
                        if (ttime < mintime)
                            mintime = ttime;
                    }
                }
            }
        }
        
        if (mintime == Integer.MAX_VALUE) return -count;
        
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= M; j++)
                if (active [i][j])
                    grid [i][j] += nflow [i][j] * mintime;
        
        return mintime;
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
