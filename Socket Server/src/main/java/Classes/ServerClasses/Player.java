package Classes.ServerClasses;

import Classes.GameObjects.GameCharacter;
import Functional.Proxy;
import Interfaces.iObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import static java.util.Arrays.copyOfRange;

public class Player extends Thread implements iObserver {

    private String name;

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Proxy commandProxy;
    private HashMap<String, GameCharacter> characters;
    private PlayerStatistics playerStats;

    private boolean wildCardIsReady;

    private Server server;

    // 0: max [1,2,3,4]
    // 1: cornejo
    // 2: sambucci
    // 3: esteban


    public Player(Socket socket, PlayerStatistics stats) throws Exception {

        this.clientSocket = socket;
        this.name = "";
        this.server = Server.getInstance();
        this.wildCardIsReady = false;
        this.characters = new HashMap<>();
        this.commandProxy = new Proxy();
        this.playerStats = stats;

    }

    @Override
    public void update(String message) throws Exception {
        out.println(message);
    }

    @Override
    public void run() throws RuntimeException{

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] inputLine;


        try {

            while (true) {

                inputLine = (in.readLine()+" "+this.name).split(" ");
                String command = inputLine[0];
                String[] args = copyOfRange(inputLine, 1, inputLine.length);

                if(this.name.equals("") && command.equals("setCharacteristics")){
                    this.commandProxy.execute(inputLine, this);
                    continue;
                }
                this.commandProxy.execute(inputLine, this);
            }


        } catch (Exception e) {
            try {
                this.server.removeObserver(this.name);
                this.server.removePlayerFromGameQueue(this.name);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public String getPlayerName() {
        return this.name;
    }
    public void setPlayerName(String name) {
        this.name = name;
    }

    public Boolean getWildCardIsReady() {
        return this.wildCardIsReady;
    }

    public void setWildCardIsReady(Boolean wildCardIsReady) {
        this.wildCardIsReady = wildCardIsReady;
    }

    public void addCharacter(String characterName,GameCharacter character) {
        this.characters.put(characterName, character);
    }

    public GameCharacter getCharacterByName(String characterName) {
        return this.characters.get(characterName);
    }
    public HashMap<String, GameCharacter> getCharacters() {
        return this.characters;
    }

    public Socket getSocket() {
        return this.clientSocket;
    }

    public PlayerStatistics getPlayerStats() {
        return this.playerStats;
    }

}
