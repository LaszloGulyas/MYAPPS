import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatHost {

    private static final int port = 12321;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);


            while (true) {
                System.out.println(serverSocket.getInetAddress());
                System.out.println(serverSocket.getLocalPort());


                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String receivedText;
                while (!(receivedText = reader.readLine()).equals("exit")) {
                    System.out.println(receivedText);
                }

                break;
            }

        } catch (Exception ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }

    }
}

