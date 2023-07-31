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


class server {

    static int port2 = 0;
    static Scanner sc = new Scanner(System.in);
    static List<Message> messages = new ArrayList<>();
    static Message msg = new Message(" ");
    static Socket socket;

    static int dest;




    public static void main(String[] args) throws IOException, ClassNotFoundException {

        server ser = new server();

        System.out.print("Enter the Port that the Server is connected to : ");
        port2 = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the Port that the Client is connected to : ");
        msg.destination = sc.nextInt();
        dest = msg.destination - 2000;


        sc.nextLine();
        msg.path = "Server --> ";



        ser.sending();



    }
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    void sending() throws IOException {


        ServerSocket ss = new ServerSocket(port2);
        System.out.println("ServerSocket awaiting connections...");

        socket = ss.accept();
        System.out.println("\nConnected to " + socket.getInetAddress().getHostName() + " at Port " + port2 + '\n');

        OutputStream outputStream = socket.getOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.flush();


        System.out.print("Enter the Message to be Sent : ");
        String t = (sc.nextLine());
        msg.text = t;


        messages.add(msg);

        System.out.println("\nSending messages to Port " + port2);
        messages.get(0).display();
        objectOutputStream.writeObject(messages);




        System.out.println("Closing sockets.");
        ss.close();
        socket.close();



    }

}