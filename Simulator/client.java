import java.io.Serializable;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.*;
// must implement Serializable in order to be sent
class Message implements Serializable {

    int destination;
    String path;
    String text;

    public void display() {
        System.out.println("Destination : " + destination);
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


class client {


    //Listening and Sending Ports
    static int port1 = 8000;



    static Scanner sc = new Scanner(System.in);
    static List<Message> messages = new ArrayList<>();
    static Socket socket1;



//
    static int router = 1;
    static int[] routingTable = null;
    static int n;

    static int dest;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        client cl = new client();
        cl.listening();


    }
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void printHypen(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }

    }
    void listening() throws IOException, ClassNotFoundException {

        System.out.println("Client IS READY\n");


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

        messages.get(0).path += "Client";

        wait(3000);
        messages.get(0).display();

        messages.get(0).path += " --> ";

        

        System.out.println("\nClosing Listening Socket.");
        printHypen(34);
        System.out.print("XXX");
        printHypen(34);
        System.out.println("\n\n");

        socket1.close();

    }
}
