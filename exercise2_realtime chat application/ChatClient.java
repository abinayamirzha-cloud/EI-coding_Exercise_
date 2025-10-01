import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// Adapter Interface
interface ClientConnection {
    void send(String message);
    String receive() throws IOException;
    void close();
}

// TCP Socket Adapter
class SocketClientAdapter implements ClientConnection {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public SocketClientAdapter(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    @Override
    public void send(String message) { out.println(message); out.flush(); }

    @Override
    public String receive() throws IOException { return in.readLine(); }

    @Override
    public void close() { try { socket.close(); } catch (IOException ignored) {} }
}

// Chat Client using Adapter
public class ChatClient {
    private final ClientConnection connection;

    public ChatClient(ClientConnection connection) {
        this.connection = connection;
    }

    public void start() throws IOException {
        // Reader thread
        new Thread(() -> {
            try {
                String line;
                while ((line = connection.receive()) != null) {
                    System.out.println(line);
                }
            } catch (IOException ignored) {}
        }).start();

        // Writer (main thread)
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine();
            connection.send(input);
            if (input.equalsIgnoreCase("/exit")) break;
        }

        connection.close();
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 12345;
        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);

        ClientConnection conn = new SocketClientAdapter(host, port); // TCP Adapter
        ChatClient client = new ChatClient(conn);
        client.start();
    }
}
