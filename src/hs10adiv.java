import java.io.*;
import java.util.*;

public class hs10adiv
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        char [] s = in.readLine ().toCharArray ();
        int b = in.nextInt ();
        int st = 0, c = 1;
        for (int i = s.length - 1; i >= 0; i--) {
            if (s [i] == '1')
                st = (st + c) % b;
            c = (c << 1) % b;
        }
        int [] dp = new int [b];
        Arrays.fill (dp, 1000);
        dp [st] = 0;
        int res = 1;
        for (int i = s.length - 1; i >= 0; i--) {
            if (dp [0] == 0 || dp [0] == 1) break;
            int [] temp = new int [b];
            if (s [i] == '1') {
                for (int j = 0; j < b - res; j++)
                    temp [j] = dp [j + res] + 1;
                for (int j = b - res; j < b; j++)
                    temp [j] = dp [j + res - b] + 1;
            } else {
               for (int j = 0; j < res; j++)
                    temp [j] = dp [b + j - res] + 1;
               for (int j = res; j < b; j++)
                    temp [j] = dp [j - res] + 1; 
            }
            for (int j = 0; j < b; j++)
                if (temp [j] < dp [j])
                    dp [j] = temp [j];
            res = (res << 1) % b;
            System.out.println (res + " " + Arrays.toString (dp));
        }
        out = new PrintWriter (System.out, true);
        out.println (dp [0]);
        System.exit (0);
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name)throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine()throws IOException{byte[] buf=new byte[1024];int cnt=0,c;
        while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt()throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong()throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble()throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer()throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read()throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close()throws IOException{if(din==null) return;din.close();}
}