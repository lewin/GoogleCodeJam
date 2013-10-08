/**
PROG: squarefields
ID: lg52931
LANG: JAVA
 */

import java.io.*;
import java.util.*;

public class squarefields
{
    private static Reader in;
    private static PrintWriter out;
    private static int [][] cache;
    private static int [] bitcount;

    public static void main (String [] args) throws IOException {
        in = new Reader ("B-large-practice.in");
        out = new PrintWriter (new BufferedWriter (new FileWriter ("answer.out")));
        bitcount = new int [1 << 15];
        for (int i = 0; i < 1 << 15; i++) {
            int t = i;
            while (t > 0) {
                if ((t & 1) == 1)
                    bitcount [i]++;
                t >>= 1;
            }
        }
        int N = in.nextInt ();
        for (int p = 1; p <= N; p++) {
            System.out.printf ("Case #%d: ", p);
            out.printf ("Case #%d: ", p);
            int n = in.nextInt (), k = in.nextInt ();
            int [] x = new int [n], y = new int [n];
            for (int i = 0; i < n; i++) {
                x [i] = in.nextInt ();
                y [i] = in.nextInt ();
            }int a;
            cache = new int [1 << x.length][k + 1];
            out.println (a=find (x, y, 0, k));
            System.out.println (a);
        }
    }
    
    private static int find (int [] x, int [] y, int used, int left) {
        if (cache [used][left] != 0) return cache [used][left];
        if (bitcount [used] + left == x.length) return 0;
        if (used == (1 << x.length) - 1)
            return -1;
        if (left == 1) {
            int mask = ((1 << x.length) - 1) ^ used;
            int minx = 70000, miny = 70000, maxx = -1, maxy = -1;
            for (int i = 0; i < x.length; i++) {
                if ((mask & (1 << i)) > 0) {
                    if (x [i] < minx) minx = x [i];
                    if (y [i] < miny) miny = y [i];
                    if (x [i] > maxx) maxx = x [i];
                    if (y [i] > maxy) maxy = y [i];
                }
            }
            return cache [used][left] =  Math.max (maxx - minx, maxy - miny);
        }
        int side = Integer.MAX_VALUE;
        for (int i = 1; i < 1 << x.length; i++) {
            if ((i ^ used) == i + used && (i ^ used) != (1 << x.length) - 1) {
                int minx = 70000, miny = 70000, maxx = -1, maxy = -1;
                for (int j = 0; j < x.length; j++) {
                    if ((i & (1 << j)) > 0) {
                        if (x [j] < minx) minx = x [j];
                        if (y [j] < miny) miny = y [j];
                        if (x [j] > maxx) maxx = x [j];
                        if (y [j] > maxy) maxy = y [j];
                    }
                }
                int f = find (x, y, used ^ i, left - 1);
                if (f == -1) continue;
                side = Math.min (Math.max (Math.max (maxx - minx, maxy - miny), f), side);
            }
        }
        return cache [used][left] = side;
    }
}

//////////////////////////////
//////////////////////////////
/// PRE-WRITTEN CODE BELOW ///
//////////////////////////////
//////////////////////////////

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE=1<<16;private DataInputStream din;
    private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name)throws IOException{din=new DataInputStream(new FileInputStream(file_name));
        buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine()throws IOException{byte[] buf=new byte[1024];int cnt=0,c;
        while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt()throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong()throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble()throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();
        boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer()throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);
            if(bytesRead==-1)buffer[0]=-1;}
    private byte read()throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close()throws IOException{if(din==null) return;din.close();}
}