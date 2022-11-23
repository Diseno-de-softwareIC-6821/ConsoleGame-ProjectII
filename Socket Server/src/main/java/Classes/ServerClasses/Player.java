package Classes.ServerClasses;

import Classes.Character;
import Classes.Commands.*;
import Interfaces.iCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.copyOfRange;

public class Player extends Thread{

    private String name;

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private HashMap<String, iCommand> commands;
    private ArrayList<Character> characters;

    private Server server;

    // 0: max [1,2,3,4]
    // 1: cornejo
    // 2: sambucci
    // 3: esteban


    public Player(Socket socket) throws Exception {

        this.clientSocket = socket;
        this.name = "";
        this.server = Server.getInstance();
        commands = new HashMap<>(
                Map.of(
                        "attack", new AttackCommand(this),
                        "chat", new ChatCommand(this),
                        "dm", new DirectMessageCommand(this),
                        "info", new PlayerInformationCommand(this),
                        "reload", new ReloadCommand(this),
                        "skip", new SkipCommand(this),
                        "surrender", new SurrenderCommand(this),
                        "tie", new TieCommand(this),
                        "wildcard", new WildcardCommand(this),
                        "setCharacteristics", new SetPlayerCharacteristics(this)
                )
        );
    }

    public void sendMessage(String message) throws Exception {
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
                //"dm esteban hola esto es un test para esteban max"

                inputLine = (in.readLine()+" "+this.name).split(" ");
                String command = inputLine[0];
                String[] args = copyOfRange(inputLine, 1, inputLine.length);

                if(this.name.equals("") && command.equals("setCharacteristics")){
                    this.commands.get("setCharacteristics").execute(args);
                    continue;
                }

                switch (command) {
                    case "attack" -> {

                        this.commands.get("attack").execute(args);
                        this.sendMessage("An attack has been made ");
                    }
                    case "chat" -> {
                        this.commands.get("chat").execute(args);
                        this.sendMessage("message sent");
                    }
                    case "dm" -> {
                        this.commands.get("dm").execute(args);
                        this.sendMessage("A dm message has been sent");
                    }
                    case "info" -> {

                        this.sendMessage("Player info has been requested");
                    }
                    case "reload" -> {

                        this.sendMessage("Weapons have been reloaded");
                    }
                    case "skip" -> {

                        this.sendMessage("Turn has been skipped");
                    }
                    case "surrender" -> {

                        this.server.sendToAll(this.name + " has surrendered");
                    }
                    case "tie" -> {

                        this.server.sendToAll("A tie has been requested");
                    }
                    case "wildcard" -> {

                        this.server.sendToAll(this.name + "has used a wildcard");
                    }
                    default -> {
                        Server.getInstance().sendToAll("User " + inputLine[0] + " connected");
                        System.out.println("User " + inputLine[0] + " connected");
                    }
                }
            }


        } catch (Exception e) {
            try {
                Server.getInstance().removeClient(this.name);
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
    public Socket getSocket() {
        return this.clientSocket;
    }
}
