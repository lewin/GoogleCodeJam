import java.util.*;
import java.io.*;

public class mountain {
	private static Reader in;
	private static PrintWriter out;
	public static final String NAME = "C-large";

	private static int N;
	private static int[] conn;
	private static long[] height, maxHeight, minHeight;

	private static void main2() throws IOException {
		N = in.nextInt();
		conn = new int[N];
		for (int i = 1; i < N; i++) {
			conn[i] = in.nextInt();
		}
		if (solve(1, N)) {
			do {
				height = new long[N + 1];
				Arrays.fill(height, -1);
				maxHeight = new long[N + 1];
				Arrays.fill(maxHeight, 1l << 50);
				minHeight = new long [N + 1];
				height [1] = (long)(Math.random() * maxHeight[1]); 
			} while (!randSolve(1, N));
			
			out.print(height[1]);
			for (int i = 2; i <= N; i++)
				out.print(" " + height[i]);
			out.println();
		} else {
			out.println("Impossible");
		}
	}

	private static long val(long x1, long y1, long x2, long y2, long x) {
		return (long) ((double) (y2 - y1) / (double) (x2 - x1) * (x - x1) + y1);
	}

	private static boolean solve(int begin, int end) {
		if (begin == end) 
			return true;
		if (conn[begin] > end)
			return false;
		return solve(begin + 1, conn[begin]) && solve(conn[begin], end);
	}
	
	private static long randHeight(int idx) {
		return (long)(Math.random() * (maxHeight[idx] - minHeight[idx])) + minHeight[idx];
	}
	
	static class State {
		public int begin, end;
		
		public State (int begin, int end) {
			this.begin = begin;
			this.end = end;
		}
	}
	
	private static boolean randSolve (int s, int e) {
		LinkedList<State> queue = new LinkedList<State> ();
		queue.add (new State (s, e));
		while (queue.size() > 0) {
			State st = queue.removeFirst();
			int begin = st.begin, end = st.end;
		
			if (begin == end) {
				if (height[begin] == -1)
					height[begin] = randHeight(begin);
				continue;
			}
			if (height[begin] == -1)
				height[begin] = randHeight(begin);
			if (height [conn[begin]] == -1) {
				for (int i = begin + 1; i <= N; i++) {
				if (height [i] != -1)
					minHeight [conn[begin]] = Math.max (minHeight[conn[begin]],
							val (i, height [i], begin, height [begin], conn[begin]) + 1);
				}
				if (minHeight [conn[begin]] > maxHeight [conn[begin]]) return false;
				height[conn[begin]] = randHeight(conn[begin]);
			}
	
			for (int i = begin + 1; i < conn[begin]; i++)
				maxHeight[i] = Math.min(maxHeight[i],
						val(begin, height[begin], conn[begin], height[conn[begin]], i) - 1);
			for (int i = conn[begin] + 1; i <= end; i++)
				maxHeight[i] = Math.min(maxHeight[i],
						val(begin, height[begin], conn[begin], height[conn[begin]], i));
			queue.add(new State (begin + 1, conn[begin]));
			queue.add(new State (conn[begin], end));
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		in = new Reader(NAME + ".in");
		out = new PrintWriter(new BufferedWriter(new FileWriter(NAME + ".out")));

		int numCases = in.nextInt();
		for (int test = 1; test <= numCases; test++) {
			out.print("Case #" + test + ": ");
			main2();
			System.out.println(test);
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
