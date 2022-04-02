import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    private static final String hostname = "localhost";
    private static final int port = 12321;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println(getMyExternalIP());

        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");


            System.out.println(socket.getLocalAddress());
            System.out.println(socket.getRemoteSocketAddress());
            System.out.println(socket.toString());

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String userInput;
            while (!(userInput = scanner.nextLine()).equals("exit")) {
                writer.println(userInput);
            }

        } catch (Exception e) {
            System.out.println("Connecting to the host is failed: ");
            e.printStackTrace();
        }
    }

    public static String getMyExternalIP() {
        URL urlAddress = null;
        String myIP = "";
        try {
            urlAddress = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(urlAddress.openStream()));
            myIP = in.readLine(); //you get the IP as a String
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myIP;
    }
}
