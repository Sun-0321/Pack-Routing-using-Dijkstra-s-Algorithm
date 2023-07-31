import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*  ;

import java.io.Serializable;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
//
// must implement Serializable in order to be sent
class Message implements Serializable {

    int destination;
    String path;
    String text;

    public void display() {
        System.out.println("\nDestination : " + destination);
        System.out.println("Message : " + text);
        System.out.println("Path : " + path);
    }

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

class proxy4 {

    //Listening and Sending Ports
    static int port1 = 2004;//will be set to correct port when the constructor is called
    static int port2 = 0;


    static Scanner sc = new Scanner(System.in);
    static List<Message> messages = new ArrayList<>();
    static Socket socket1;
    static Socket socket2;



    static int router = 4;
    static int[] routingTable = null;
    static int n;

    static int dest;


    public proxy4(int p) {
        port1 = p;
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void read() {
        try {

            FileReader file = new FileReader("RouteTable.txt");
            String val = new String();
            BufferedReader br = new BufferedReader(file);
            String[] temp;
            val = br.readLine();

            temp = val.split(" ");
            n = temp.length;

            routingTable = new int[n + 1];

            br.close();
            file.close();


            file = new FileReader("RouteTable.txt");
            val = new String();
            br = new BufferedReader(file);

            int i = 1;
            while ((val = br.readLine()) != null) {
                String[] temp1;
                temp1 = val.split(" ");
                if (i == router) {

                    for (int k = 1; k <= n; k++ ) {
                        routingTable[k] =  Integer.parseInt(temp1[k - 1]);
                    }
                    break;
                }

                i++;
            }
            br.close();
            file.close();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("File Not Found, Please Enter a Valid File !");
        }

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        proxy4 pr1 = new proxy4(2000 + router);
        //System.out.println(port1);
        pr1.read();



        pr1.listening();

        System.out.print("Switching from Port " + port1 + " to " + port2 + '\n');

        pr1.sending();


    }
    public void printHypen(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }

    }

    void listening() throws IOException, ClassNotFoundException {

        System.out.println("Router" + router + " IS READY\n");


        int cnt = 0;
        boolean flag = true;
        while (cnt <= 1) {

            try {

                socket1 = new Socket("127.0.0.1", port1);
                printHypen(71); System.out.println();
                System.out.println("Connected to " + socket1.getInetAddress().getHostName() + " at Port " + port1);
                cnt = 2;

            } catch (Exception e) {
                if (cnt == 0 && flag) {
                    System.out.println("The Server Might be Busy!!! Waiting for a server to connect ");
                    flag = false;
                }

                cnt = (cnt + 1) % 2;
            }
        }

        InputStream inputStream = socket1.getInputStream();

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);


        messages = (List<Message>) objectInputStream.readObject();

        dest = messages.get(0).destination - 2000;

        messages.get(0).path += String.valueOf(router);

        wait(3000);
        messages.get(0).display();

        messages.get(0).path += " --> ";

        if (dest == router) {
            port2 = 8000;
            System.out.println("Next Hop is To Client ");
        } else {
            port2 = routingTable[dest] + 2000;
            System.out.println("Next Hop is To Router " + routingTable[dest]);
        }
        
        printHypen(34);
        System.out.print("XXX");
        printHypen(34);
        System.out.println("\n\n");

        socket1.close();

    }

    void sending() throws IOException {

        ServerSocket ss = new ServerSocket(port2);

        wait(3000);
        System.out.println("\n\n");
        printHypen(71); System.out.println();
        System.out.println("Waiting for NEXT HOP Connection...\n");


        socket2 = ss.accept();
        System.out.println("Connected to " + socket2.getInetAddress().getHostName() + " at Port " + port2);

        ss.close();
        OutputStream outputStream = socket2.getOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.flush();


        System.out.println("Sending messages to the Port " + port2);
        objectOutputStream.writeObject(messages);




        printHypen(34);
        System.out.print("XXX");
        printHypen(34);
        System.out.println("\n\n");


        socket2.close();

    }
}