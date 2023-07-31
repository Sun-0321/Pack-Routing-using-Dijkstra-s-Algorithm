package dijkstra;

import java.util.Scanner;



class node {
    int nodeId, hop;
    public node(int nd, int h) {
        nodeId = nd;
        hop = h;
    }

    public node(node heapDatum) {
        this.hop = heapDatum.hop;
        this.nodeId = heapDatum.nodeId;
    }


    void printNode() {
        System.out.println("[" +  nodeId + " " + hop + "]" );
    }
}

class Heap {
    int size;
    final int MAX = 10;
    node[] heapData = new node[MAX];

    public Heap() {
        size = 0;
    }



    void heapUp(int i) { // used for insert and moving up
        if (i == 0)
            return;
        int par = (i - 1) / 2;

        if (heapData[par].hop > heapData[i].hop) {

            node temp = new node(heapData[i]);
            heapData[i] = heapData[par];
            heapData[par] = temp;


            heapUp(par);
        }
    }

    void heapDown(int i, int size) { // for deletion and building
        int left = 2 * i + 1;
        int right = 2 * (i + 1);
        int smallIndex = i, smaller = heapData[i].hop;

        if (left < size && heapData[left].hop < smaller) {
            smallIndex = left;
            smaller = heapData[left].hop;
        }
        if (right < size && heapData[right].hop < smaller) {
            smallIndex = right;
            smaller = heapData[right].hop;
        }
        if (smallIndex != i) {
            node temp = new node(heapData[i]);
            heapData[i] = heapData[smallIndex];
            heapData[smallIndex] = temp;
            heapDown(smallIndex, size);
        }
    }

    void buildHeap() {
        for (int i = size / 2; i >= 0; i--) {
            heapDown(i, size);
            displayHeap();
        }
    }

    void displayHeap() {
        System.out.print("[ ");

        for (int i = 0; i < size; i++) {
            heapData[i].printNode();
        }
        System.out.println(" ]");

    }

    void push(node newNode) {
        heapData[size] = newNode;
        size++;
        heapUp(size - 1);
        //System.out.println("Inserting " + newNode.nodeId + " into the heap");

    }

    node pop() {
        node front = heapData[0];
        heapData[0] = heapData[size - 1];
        --size;

        heapDown(0, size);

        //System.out.print("Popping the smallest element : ");
        //front.printNode();


        return front;
    }

    boolean isEmpty() {
        return size <= 0;
    }

}



public class Dijkstra {
    static  int inf = 0x3f3f3f3f;
    public static int MAX = 10;

    public static int[][] shortestDist = new int[MAX][MAX];
    public static int[][] parent = new int[MAX][MAX];
    public static int[][] routingTable = new int[MAX][MAX];




    public int n, src_node, dest_node;
    public static  int[][] adj = new int[MAX][MAX];
    public static Scanner in = new Scanner(System.in);


    public void createRouters() {


        System.out.print("\nEnter the number of nodes : ");
        n = in.nextInt();

        System.out.println("\nEnter the Network Topology as an Adjacency Matrix");

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {

                adj[i][j] = in.nextInt();
                if (adj[i][j] == -1) {
                    adj[i][j] = inf;
                }
            }
        }

        System.out.println("\nThe topology has been constructed");
    }



    public void dijsktra(int src) {
        int[] visited = new int[n + 1]; //elem are to be 0 or bool
        int dist[] = new int[n + 1];
        int par[] = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            visited[i] = 0;
            par[i] = -1;
            dist[i] = inf;
        }


        Heap queue = new Heap(); // priority_queue for getting the smallest value out
        node temp_insert  = new node(src, 0);
        dist[src] = 0;

        queue.push(temp_insert);
        while (!queue.isEmpty()) {
            node front = queue.pop();
            int ndId = front.nodeId, hop = front.hop;
            //front.printNode();
            if (hop > dist[ndId])
                continue;

            visited[ndId] = 1;

            for (int i = 1; i <= n; i++) {
                if (visited[i] == 0 && adj[ndId][i] != inf && dist[i] > dist[ndId] + adj[ndId][i]) {
                    par[i] = ndId;
                    dist[i] = dist[ndId] + adj[ndId][i];
                    node a = new node(i, dist[i]);
                    queue.push(a);
                }
            }
        }

        //System.out.println("Distances are ");
        for (int i = 1; i <= n; i++) {
            shortestDist[src][i] = dist[i];
            parent[src][i] = par[i];
            //System.out.print(dist[i] + " ");

        }
        //System.out.println();
    }



    public int getPath(int src, int dest, int par[], int path[]) {
        int len = 0;

        for (int a = dest; a != -1; a = par[a]) {

            path[len] = a;
            len++;
        }
        for (int i = 0; i < len / 2; i++) {
            int temp = path[i];
            path[i] = path[len - i - 1];
            path[len - i - 1] = temp;
        }
        return len;
    }


    public void getPathUsingRoutingTable(int src_node, int dest_node) {
        System.out.print("Path from " + src_node + " to " + dest_node + " : ");

        for (int i = src_node; i != 0; i = routingTable[i][dest_node]) {
            if (i != src_node) System.out.print("-->");
            System.out.print(i);
        }
        System.out.println();
    }







    public void displayRoutingTable(int src_node) {


        System.out.println("\n\nFetching the Routing Table for Router " + src_node + "\n\n");

        System.out.println("Destination       Next Hop Router");
        for (int i = 1; i <= n; i++) {
            System.out.print("          " + i + "       ");
            if (i == src_node) System.out.println("-");
            else System.out.println(routingTable[src_node][i]);
        }

    }
    public static void main(String[] args) {

    }


}

