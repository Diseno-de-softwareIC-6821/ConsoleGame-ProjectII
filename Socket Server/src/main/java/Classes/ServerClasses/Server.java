package Classes.ServerClasses;

import Interfaces.iObservable;
import Interfaces.iObserver;

import java.net.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;


public class Server implements iObservable {
    private static Server instance;
    private final ServerSocket serverSocket;
    private final HashMap<String, iObserver> players;
    private Queue<String> playerQueue;
    private ArrayList<String> tieQueue;


    public Server(int port) throws Exception {
        this.players  = new HashMap<>();
        this.playerQueue = new ArrayDeque<>();
        this.serverSocket = new ServerSocket(port);
        this.tieQueue = new ArrayList<>();

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

    public String getCurrentTurn(){
        return this.playerQueue.peek();
    }

    public void nextTurn() throws Exception {

        String temp = playerQueue.poll();
        playerQueue.add(temp);
        this.notifyAllObservers("It's " + playerQueue.peek() + "'s turn");
    }

    public void addPlayerToGameQueue(String name){
        this.playerQueue.add(name);
    }

    public void removePlayerFromGameQueue(String name){
        this.playerQueue.remove(name);
    }

    public void checkWin() throws Exception {
        if(this.playerQueue.size() == 1){
            this.notifyAllObservers("game Over " + this.playerQueue.peek() + " wins");
            //ads win to player
            this.getPlayerByName(this.playerQueue.peek()).getPlayerStats().addWin();
            this.playerQueue.clear();
        }
    }

    public Boolean isTieQueueEmpty(){
        return this.tieQueue.isEmpty();
    }

    public void addPlayerToTieQueue(String name) throws Exception {
        this.tieQueue.add(name);

        if(this.checkSurrender()){
            this.notifyAllObservers("Game over");
            this.playerQueue.clear();
        }

    }

    public void cancelTie() throws Exception {
        this.tieQueue.clear();
    }
    public boolean checkSurrender(){

        ArrayList<String> playerQueueList = new ArrayList<>(this.playerQueue);

        return (playerQueueList.containsAll(this.tieQueue) && this.tieQueue.containsAll(playerQueueList));
    }

    public void stop() throws Exception {
        serverSocket.close();
    }

}
