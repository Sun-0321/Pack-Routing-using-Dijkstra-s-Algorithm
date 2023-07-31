import java.util.*;

class floyd {

	static final int MAX = 1000;


	static int INF = (int) 1e7;

	static int [][]dist = new int[MAX][MAX];
	static int [][]nextHop = new int[MAX][MAX];



	static void construct(int V, int [][] graph) {

		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				dist[i][j] = graph[i][j];



				if (graph[i][j] == INF)
					nextHop[i][j] = -1;
				else
					nextHop[i][j] = j;
			}
		}
	}



	static Vector<Integer> fetchPath(int u, int v) {

		if (nextHop[u][v] == -1)
			return null;


		Vector<Integer> path = new Vector<Integer>();
		path.add(u);

		while (u != v) {
			u = nextHop[u][v];
			path.add(u);
		}
		return path;
	}





	static void floydWarshall(int V) {

		for (int k = 0; k < V; k++) {
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {

					if (dist[i][k] == INF || dist[k][j] == INF)
						continue;

					if (dist[i][j] > dist[i][k] + dist[k][j]) {

						dist[i][j] = dist[i][k] + dist[k][j];
						nextHop[i][j] = nextHop[i][k];
					}
				}
			}
		}
	}


	static void printPath(Vector<Integer> path) {
		int n = path.size();
		for (int i = 0; i < n - 1; i++)
			System.out.print(path.get(i) + 1 + " -> ");
		System.out.print(path.get(n - 1) + 1 + "\n");
	}


	public static void main(String[] args) {

		int V;
		System.out.print("Enter the number of Routers in the topology : ");
		Scanner sc = new Scanner(System.in);
		V = sc.nextInt();

		int [][] graph = new int[V][V];
		System.out.println("Enter the edge weight between every 2 routers ");

		for (int i = 0; i < V; i++) {

			for (int j = 0; j < V; j++) {

				int val = sc.nextInt();

				if (val == -1)
					graph[i][j] = INF;
				else
					graph[i][j] =  val;
			}
		}



		construct(V, graph);



		floydWarshall(V);
		Vector<Integer> path;

		System.out.println("\n\nPrinting the shortest paths between every 2 routers \n\n");
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				System.out.print("Shortest path from Router " + (i + 1) + " to Router " + (j + 1) + " : ");
				path = fetchPath(i, j);
				printPath(path);
			}
			System.out.println("\n");
		}


	}
}
