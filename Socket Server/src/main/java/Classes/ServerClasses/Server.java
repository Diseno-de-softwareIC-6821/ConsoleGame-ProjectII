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
            Player client = new Player(serverSocket.accept());

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

    public void sendToPlayer(String name, String message) throws Exception {

        for(Player player : players.values()){
            System.out.println(player.getPlayerName());
        }

        players.get(name).sendMessage(message);
    }
    public void sendToAll(String message) throws Exception {
        for (Player player : players.values()) {
            player.sendMessage(message);
        }
    }

    public Player getPlayerByName(String name){
        return this.players.get(name);
    }

    public void removeClient(String id) {
        players.remove(id);
    }

    public void stop() throws Exception {
        serverSocket.close();
    }


    public void addPlayer(String name, Player player) {
        players.put(name, player);
    }
}
