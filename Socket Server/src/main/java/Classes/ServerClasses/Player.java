package Classes.ServerClasses;

import Classes.Character;
import Classes.Commands.*;
import Classes.GameObjects.GameCharacter;
import Functional.Proxy;
import Interfaces.iCommand;
import Interfaces.iObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.copyOfRange;

public class Player extends Thread implements iObserver {

    private String name;

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Proxy commandProxy;
    private HashMap<String, GameCharacter> characters;

    private boolean wildCardIsReady;

    private Server server;

    // 0: max [1,2,3,4]
    // 1: cornejo
    // 2: sambucci
    // 3: esteban


    public Player(Socket socket) throws Exception {

        this.clientSocket = socket;
        this.name = "";
        this.server = Server.getInstance();
        this.wildCardIsReady = false;
        this.characters = new HashMap<>();
        this.commandProxy = new Proxy();

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

    public GameCharacter getCharacter(String characterName) {
        return this.characters.get(characterName);
    }

    public Socket getSocket() {
        return this.clientSocket;
    }


}
