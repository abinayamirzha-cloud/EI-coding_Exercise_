 **Real-time Chat Application Programming Exercise**
 
** Problem Statement**

 Create a simple real-time chat application where users can join different chat rooms or create
 their own chat rooms. Users should be able to send and receive messages in real-time.
 
**Available Commands**
/join <roomId> → Join or create a chat room

/users → List active users in the current room

/history → Show chat history of the room

/pm <username> <message> → Send a private message

/exit → Leave chat and disconnect

features

 1. Allow users to create/join chat rooms by entering a unique room ID.
    <img width="988" height="219" alt="image" src="https://github.com/user-attachments/assets/2cb52e71-f02b-4986-9140-55de383c299a" />
     <img width="989" height="232" alt="image" src="https://github.com/user-attachments/assets/08debf08-ad2b-461b-a055-63b9d2190677" />

 2. Enable users to send and receive messages in real-time within a chat room.
    <img width="1170" height="290" alt="image" src="https://github.com/user-attachments/assets/386d4492-7d5a-4e7a-ad88-d20d6b00445a" />
    <img width="1217" height="267" alt="image" src="https://github.com/user-attachments/assets/3577b947-3657-49b7-b6a6-2c86a6cc490c" />
 3. Display a list of active users in the chat room.
    <img width="848" height="67" alt="image" src="https://github.com/user-attachments/assets/9ff0644c-de3a-4aac-b6b9-27847c8ab7c5" />

 4. Optional: Implement a private messaging feature between users.
     <img width="637" height="60" alt="image" src="https://github.com/user-attachments/assets/ce7ddb85-5656-418b-a44d-55db48739f09" />

 5. Optional: Implement message history, so the chat persists even if the user leaves and rejoins
     <img width="874" height="179" alt="image" src="https://github.com/user-attachments/assets/dc734297-597e-452f-a30a-83bf2f90522c" />
     when new person joins it will show the history
     <img width="949" height="346" alt="image" src="https://github.com/user-attachments/assets/3eb467f7-1be3-4b02-8e14-27b32517d72d" />

 6. switch between the chatrooms
  <img width="689" height="176" alt="image" src="https://github.com/user-attachments/assets/57db73c6-5f3c-4433-83db-2c3f3e8b2f9c" />
  

        
**design pattern focused:**

   
    **1**  Singleton (ChatRoom)
Only one instance per room exists. If a room already exists, it is reused.
  
class ChatRoom {

    private static final Map<String, ChatRoom> INSTANCES = new HashMap<>();
    
    private final String roomId;
    
    private ChatRoom(String roomId) { this.roomId = roomId; }
    // Singleton 
    
    public static ChatRoom getInstance(String roomId) {
        return INSTANCES.computeIfAbsent(roomId, ChatRoom::new);
    }
    }

Ensures memory efficiency & prevents duplicate rooms.
Example: /join Room1 → Always gives the same Room1 instance.


**2** Observer (ClientHandler listens to ChatRoom)

Clients subscribe to a room, and when someone sends a message, all observers get notified.


interface ChatObserver {

    void update(String message);  // Observer receives updates
}


Each ClientHandler implements ChatObserver.
So when broadcast is called → every connected client receives the message.


**3** Adapter (SocketClientAdapter for ClientConnection)

Hides Socket details, making the client code independent of networking implementation.

// Adapter Interface
interface ClientConnection {

    void send(String message);
    
    String receive() throws IOException;
}
// Concrete Adapter
class SocketClientAdapter implements ClientConnection {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public SocketClientAdapter(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String msg) { out.println(msg); }
    public String receive() throws IOException { return in.readLine(); }
}

Now the client (ChatClient) can work with any connection type (Socket, HTTP, WebSocket) just by swapping adapters.

     
     
