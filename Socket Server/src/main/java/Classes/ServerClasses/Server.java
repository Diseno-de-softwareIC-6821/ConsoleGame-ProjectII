package Classes.ServerClasses;

import java.net.*;
import java.util.HashMap;


public class Server {
    private static Server instance;
    private final ServerSocket serverSocket;
    private final HashMap<String, Player> players;

    public Server(int port) throws Exception {
        this.players  = new HashMap<>();
        this.serverSocket = new ServerSocket(port);

    }

    public void start() throws Exception {

        while(true){
            String id = String.valueOf(java.util.UUID.randomUUID());
            Player client = new Player(id, serverSocket.accept());
            players.put(id, client);

            client.start();

            System.out.println("New client connected");
        }
    }

    public static Server getInstance() throws Exception {
        if (instance == null) {
            instance = new Server(8080);
            instance.start();

        }
        return instance;
    }

    public void sendToAll(String message) throws Exception {
        for (Player player : players.values()) {
            player.sendMessage(message);
        }
    }

    public void removeClient(String id) {
        players.remove(id);
    }

    public void stop() throws Exception {
        serverSocket.close();
    }


}
