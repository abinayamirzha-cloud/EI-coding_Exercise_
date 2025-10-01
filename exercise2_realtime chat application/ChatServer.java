// ChatServer.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public ChatRoom getOrCreateRoom(String roomId) {
        return ChatRoom.getInstance(roomId);
    }

    public void removeRoomIfEmpty(String roomId) {
        ChatRoom r = ChatRoom.getInstance(roomId);
        if (r.isEmpty()) {
            ChatRoom.removeInstance(roomId);
        }
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("ChatServer listening on port " + port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(this, clientSocket)).start();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 12345;
        if (args.length > 0) port = Integer.parseInt(args[0]);
        new ChatServer(port).start();
    }
}

/** Observer interface for ChatRoom notifications */
interface ChatObserver {
    void update(String message);
}

/** Singleton/Flyweight ChatRoom implementing Observable */
class ChatRoom {
    private static final ConcurrentMap<String, ChatRoom> INSTANCES = new ConcurrentHashMap<>();
    private final String roomId;
    private final Set<ChatObserver> observers = ConcurrentHashMap.newKeySet();
    private final List<String> history = Collections.synchronizedList(new ArrayList<>());

    private ChatRoom(String roomId) { this.roomId = roomId; }

    public static ChatRoom getInstance(String roomId) {
        return INSTANCES.computeIfAbsent(roomId, ChatRoom::new);
    }

    public static void removeInstance(String roomId) { INSTANCES.remove(roomId); }

    public String getRoomId() { return roomId; }

    // Observer management
    public void addObserver(ChatObserver o) { observers.add(o); }
    public void removeObserver(ChatObserver o) { observers.remove(o); }

    private void notifyObservers(String line) {
        for (ChatObserver o : observers) {
            o.update(line);
        }
    }

    // Room operations
    public void join(ClientHandler client) {
        addObserver(client);
        broadcastSystem(client.getUsername() + " joined the room.");
        sendHistoryTo(client);
    }

    public void leave(ClientHandler client) {
        removeObserver(client);
        broadcastSystem(client.getUsername() + " left the room.");
    }

    public boolean isEmpty() { return observers.isEmpty(); }

    public Set<String> activeUsernames() {
        Set<String> names = new HashSet<>();
        for (ChatObserver o : observers) {
            if (o instanceof ClientHandler c) names.add(c.getUsername());
        }
        return names;
    }

    public void addToHistory(String line) {
        synchronized (history) {
            history.add(line);
            if (history.size() > 500) history.remove(0);
        }
    }

    public List<String> getHistorySnapshot() {
        synchronized (history) { return new ArrayList<>(history); }
    }

    public void sendHistoryTo(ClientHandler client) {
        List<String> hist = getHistorySnapshot();
        if (!hist.isEmpty()) {
            client.update("[history start]");
            for (String h : hist) client.update(h);
            client.update("[history end]");
        }
    }

    public void broadcast(String from, String message) {
        String line = String.format("[%s] %s: %s", roomId, from, message);
        addToHistory(line);
        notifyObservers(line);
    }

    public void broadcastSystem(String message) {
        String line = String.format("[%s] SYSTEM: %s", roomId, message);
        addToHistory(line);
        notifyObservers(line);
    }

    public boolean sendPrivate(String from, String toUsername, String message) {
        boolean sent = false;
        for (ChatObserver o : observers) {
            if (o instanceof ClientHandler c && c.getUsername().equalsIgnoreCase(toUsername)) {
                String line = String.format("[%s] %s -> %s: %s", roomId, from, toUsername, message);
                addToHistory(line);
                c.update(line);
                // also send copy to sender
                for (ChatObserver s : observers) {
                    if (s instanceof ClientHandler ch && ch.getUsername().equalsIgnoreCase(from))
                        ch.update(line);
                }
                sent = true;
                break;
            }
        }
        return sent;
    }
}

/** Handles a single client connection and observes ChatRoom */
class ClientHandler implements Runnable, ChatObserver {
    private final ChatServer server;
    private final Socket socket;
    private String username = null;
    private ChatRoom joinedRoom = null;
    private BufferedReader in;
    private PrintWriter out;
    private volatile boolean running = true;

    public ClientHandler(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public String getUsername() { return username == null ? "Unknown" : username; }

    @Override
    public void update(String message) {
        sendRaw(message);
    }

    public void sendRaw(String line) {
        out.println(line);
        out.flush();
    }

    private void joinRoom(String roomId) {
        if (joinedRoom != null) {
            joinedRoom.leave(this);
            server.removeRoomIfEmpty(joinedRoom.getRoomId());
        }
        joinedRoom = server.getOrCreateRoom(roomId);
        joinedRoom.join(this);
        sendRaw("Joined room: " + roomId);
    }

    private void handleLine(String line) {
        if (line == null) { disconnect(); return; }
        line = line.trim();
        if (line.isEmpty()) return;

        if (line.startsWith("/")) {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0].toLowerCase();

            switch (cmd) {
                case "/join":
                    if (parts.length >= 2) joinRoom(parts[1]);
                    else sendRaw("Usage: /join <roomId>");
                    break;
                case "/users":
                    if (joinedRoom != null) sendRaw("Users: " + String.join(", ", joinedRoom.activeUsernames()));
                    else sendRaw("Not in a room. Use /join <roomId>");
                    break;
                case "/history":
                    if (joinedRoom != null) sendHistoryToSelf();
                    else sendRaw("Not in a room.");
                    break;
                case "/pm":
                    if (parts.length >= 3) {
                        if (joinedRoom != null) {
                            String target = parts[1];
                            String msg = parts[2];
                            boolean ok = joinedRoom.sendPrivate(username, target, msg);
                            if (!ok) sendRaw("User '" + target + "' not found in room.");
                        } else sendRaw("Not in a room.");
                    } else sendRaw("Usage: /pm <username> <message>");
                    break;
                case "/exit":
                    sendRaw("Bye!");
                    disconnect();
                    break;
                default:
                    sendRaw("Unknown command: " + cmd);
            }
        } else {
            if (joinedRoom != null) {
                joinedRoom.broadcast(username, line);
            } else {
                sendRaw("Join a room first: /join <roomId>");
            }
        }
    }

    private void sendHistoryToSelf() {
        List<String> hist = joinedRoom.getHistorySnapshot();
        sendRaw("[history start]");
        for (String h : hist) sendRaw(h);
        sendRaw("[history end]");
    }

    private void disconnect() {
        running = false;
        try {
            if (joinedRoom != null) {
                joinedRoom.leave(this);
                server.removeRoomIfEmpty(joinedRoom.getRoomId());
            }
            socket.close();
        } catch (IOException ignored) {}
    }

    @Override
    public void run() {
        try {
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.println("Welcome! Please enter username:");
            username = in.readLine();
            if (username == null || username.trim().isEmpty()) {
                out.println("Invalid username. Closing.");
                disconnect();
                return;
            }
            username = username.trim();

            out.println("Enter room to join (or /join <room>):");
            String line = in.readLine();
            if (line == null) { disconnect(); return; }
            line = line.trim();
            if (line.startsWith("/join")) {
                String[] p = line.split(" ", 2);
                if (p.length >= 2) joinRoom(p[1].trim());
                else joinRoom("DefaultRoom");
            } else if (!line.isEmpty()) {
                joinRoom(line);
            } else {
                joinRoom("DefaultRoom");
            }

            while (running) {
                String input = in.readLine();
                if (input == null) break;
                handleLine(input);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected abruptly: " + e.getMessage());
        } finally {
            disconnect();
        }
    }
}
