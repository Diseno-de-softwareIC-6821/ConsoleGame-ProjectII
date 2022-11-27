package Classes.ServerClasses;

import Interfaces.iObservable;
import Interfaces.iObserver;

import java.net.*;
import java.util.HashMap;


public class Server implements iObservable {
    private static Server instance;
    private final ServerSocket serverSocket;
    private final HashMap<String, iObserver> players;


    public Server(int port) throws Exception {
        this.players  = new HashMap<>();

        this.serverSocket = new ServerSocket(port);

    }

    public void start() throws Exception {

        while(true){
            PlayerStatistics stats = new PlayerStatistics();
            Player client = new Player(serverSocket.accept(), stats);

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

    @Override
    public void notifyObserver(String name, String message) throws Exception {
        players.get(name).update(message);
    }
    @Override
    public void notifyAllObservers(String message) throws Exception {
        for (iObserver player : players.values()) {
            player.update(message);
        }
    }

    @Override
    public void addObserver(String name, iObserver player) {
        players.put(name, player);
    }

    @Override
    public void removeObserver(String name) {
        players.remove(name);
    }

    public Player getPlayerByName(String name){
        return (Player) this.players.get(name);
    }


    public void stop() throws Exception {
        serverSocket.close();
    }

}
