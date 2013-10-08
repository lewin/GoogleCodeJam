import java.util.*;
import java.io.*;

public class descending {
	private static BufferedReader in;
	private static PrintWriter out;
	public static final String NAME = "D-small-attempt1";

	private static void main2() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		int R = Integer.parseInt(st.nextToken()), C = Integer.parseInt(st
				.nextToken());

		char[][] grid = new char[R][C];
		for (int i = 0; i < R; i++)
			grid[i] = in.readLine().toCharArray();

		boolean[] seen = new boolean[10];
		int[] count = new int[10];
		boolean[] lucky = new boolean[10];
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (Character.isDigit(grid[i][j])) {
					seen[grid[i][j] - '0'] = true;
					boolean[][] reach = new boolean[R][C];
					LinkedList<Pos> queue = new LinkedList<Pos>();
					queue.add(new Pos(i, j));
					reach[i][j] = true;
					count[grid[i][j] - '0']++;
					while (queue.size() > 0) {
						Pos p = queue.removeFirst();
						if (grid[p.x - 1][p.y] != '#' && !reach[p.x - 1][p.y]) {
							reach[p.x - 1][p.y] = true;
							queue.add(new Pos(p.x - 1, p.y));
							count[grid[i][j] - '0']++;
						}
						if (grid[p.x][p.y - 1] != '#' && !reach[p.x][p.y - 1]) {
							reach[p.x][p.y - 1] = true;
							queue.add(new Pos(p.x, p.y - 1));
							count[grid[i][j] - '0']++;
						}
						if (grid[p.x][p.y + 1] != '#' && !reach[p.x][p.y + 1]) {
							reach[p.x][p.y + 1] = true;
							queue.add(new Pos(p.x, p.y + 1));
							count[grid[i][j] - '0']++;
						}
					}
					
					boolean[] ok = new boolean[C];
					for (int k = 0; k < i; k++)
						for (int l = 0; l < C; l++) {
							if (reach[k][l] && grid[k][l + 1] == '#') {
								int p;
								for (p = l; grid[k][p] != '#'; p--)
									ok[l - p] |= reach[k + 1][p];
								for (int c = l - p; c < C; c++)
									ok[c] |= reach[k + 1][p + 1];
							}
						}
					for (int k = 0; k < i; k++)
						for (int l = 0; l < C; l++) {
							if (reach[k][l] && grid[k][l + 1] == '#') {
								int p;
								for (p = l; grid[k][p] != '#'; p--)
									ok[l - p] &= !(grid [k + 1][p] != '#' && !reach[k + 1][p]);
								for (int c = l - p; c < C; c++)
									ok[c] &= !(grid [k + 1][p + 1] != '#' && !reach[k + 1][p + 1]);
							}
						}
					for (int l = 0; l < C; l++) {
						if (reach[i][l] && grid[i][l + 1] == '#') {
							int p;
							for (p = l; grid[i][p] != '#'; p--)
								ok[l - p] &= grid[i + 1][p] == '#';
							for (int c = l - p; c < C; c++)
								ok[c] &= grid[i + 1][p + 1] == '#';
						}
					}
					boolean found = false;
					for (int k = 0; k < C; k++)
						if (ok[k])
							found = true;
					ok = new boolean[C];
					for (int k = 0; k < i; k++)
						for (int l = 0; l < C; l++) {
							if (reach[k][l] && grid[k][l - 1] == '#') {
								int p;
								for (p = l; grid[k][p] != '#'; p++)
									ok[p - l] |= reach[k + 1][p];
								for (int c = p - l; c < C; c++)
									ok[c] |= reach[k + 1][p - 1];
							}
						}
					for (int k = 0; k < i; k++)
						for (int l = 0; l < C; l++) {
							if (reach[k][l] && grid[k][l - 1] == '#') {
								int p;
								for (p = l; grid[k][p] != '#'; p++)
									ok[p - l] &= !(grid [k + 1][p] != '#' && !reach[k + 1][p]);
								for (int c = p - l; c < C; c++)
									ok[c] &= !(grid [k + 1][p - 1] != '#' && !reach[k + 1][p - 1]);
							}
						}
					for (int l = 0; l < C; l++) {
						if (reach[i][l] && grid[i][l - 1] == '#') {
							int p;
							for (p = l; grid[i][p] != '#'; p++)
								ok[p - l] &= grid[i + 1][p] == '#';
							for (int c = p - l; c < C; c++)
								ok[c] &= grid[i + 1][p - 1] == '#';
						}
					}
					for (int k = 0; k < C; k++)
						if (ok[k])
							found = true;
					if (count[grid[i][j] - '0'] == 1)
						found = true;
					lucky[grid[i][j] - '0'] = found;
				}
			}
		}

		for (int i = 0; i < 10; i++) {
			if (!seen[i])
				break;
			out.printf("%d: %d %s\n", i, count[i], lucky[i] ? "Lucky"
					: "Unlucky");
		}
	}

	static class Pos {
		public int x, y;

		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader (System.in));//new FileReader(NAME + ".in"));
		out = new PrintWriter(System.out);//new BufferedWriter(new FileWriter(NAME + ".out")));

		int numCases = Integer.parseInt(in.readLine());
		for (int test = 1; test <= numCases; test++) {
			out.println("Case #" + test + ":");
			main2();
		}

		out.close();
		System.exit(0);
	}
}
