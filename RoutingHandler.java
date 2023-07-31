import dijkstra.*;


import java.util.Scanner;
import java.io.*;
import static dijkstra.Dijkstra.MAX;




class Handler {
    static Scanner in = new Scanner(System.in);


    static Dijkstra dij = new Dijkstra();

    static public void write() {
        try {

            



            FileWriter file = new FileWriter("RouteTable.txt");
            BufferedWriter br = new BufferedWriter(file);


            for (int i = 1; i <= dij.n; i++) {
                for (int j = 1; j <= dij.n; j++) {
                    br.write(String.valueOf(dij.routingTable[i][j]) + " ");
                    //br.write(routingTable[i][j] + " ");
                }
                br.newLine();
            }

            br.close();
            file.close();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("File Not Found, Please Enter a Valid File !");
        }

    }
    public static void main(String[] args) {


        while (true) {



            System.out.println("\n1. Construct Topology");
            System.out.println("2. Generate the Routing Table using Routing Algorithm");
            System.out.println("3. Find Shortest Path between 2 Routers");
            System.out.println("4. Display the Routing Table");
            System.out.println("5. Display the Shortest Path between all pair of routers");
            System.out.println("6. Exit the Network Topology Manager\n\n");
            System.out.print("Enter Your Choice : ");

            //menu driven program
            int choice;
            choice = in.nextInt();


            switch (choice) {
            case 1: {
                dij.createRouters();
                long startTime = System.nanoTime();
                
                for (int i = 1; i <= dij.n; i++) {

                    dij.dijsktra(i);
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("Time Taken to calculate Shortest Distance : "+((float)duration/1000000)+" ms ");

                System.out.println("\nThe Shortest Distance Between every 2 routers ");

                for (int i = 1; i <= dij.n ; i++ ) {
                    for (int j = 1; j <= dij.n; j++) {
                        System.out.print(dij.shortestDist[i][j] + " ");
                    }
                    System.out.println();


                }


                System.out.println("\nThe topology has been constructed");
                break;
            }

            case 2: {



                long startTime = System.nanoTime();

                for (int i = 1; i <= dij.n; i++) {
                    for (int j = 1; j <= dij.n; j++) {

                        int path[] = new int[MAX];
                        int len = dij.getPath(i, j, dij.parent[i], path);

                        dij.routingTable[i][j] = path[1];

                    }
                }
                write();


                for (int i = 1; i <= dij.n; i++) {
                    dij.displayRoutingTable(i);
                    System.out.println();
                }
                System.out.println("\nCopying the Routing Table to File \"RouteTable.txt\"");

                long endTime = System.nanoTime();
                long duration = endTime-startTime;

                System.out.println("Time Taken to generate Routing Table : "+((float)duration/1000000)+" ms ");


                break;
            }

            case 3: {
                System.out.println("Enter the source and destination nodes : ");
                dij.src_node = in.nextInt();
                dij.dest_node = in.nextInt();
                dij.getPathUsingRoutingTable(dij.src_node, dij.dest_node);
                break;
            }

            case 4: {

                int src_node, dest_node;
                System.out.print("Enter the Node for fetching Routing Table : ");
                src_node = in.nextInt();

                dij.displayRoutingTable(src_node);
                break;

            }
            case 5: {
                for (int i = 1; i <= dij.n; i++) {
                    for (int j = 1; j <= dij.n; j++) {
                        dij.getPathUsingRoutingTable(i, j);
                    }
                    System.out.println();
                }
                break;
            }
            case 6: {
                System.out.println("Exiting the Network Topology Manager");
                System.exit(0);
            }


            }
            System.out.println("\n-------------------------------------------------------\n\n\n\n\n\n");

        }
    }
}
