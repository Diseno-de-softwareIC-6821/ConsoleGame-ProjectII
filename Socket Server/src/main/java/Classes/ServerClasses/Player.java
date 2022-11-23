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

    private final String id;
    private final String name;

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


    public Player(String id ,Socket socket) throws Exception {
        this.id = id;
        this.clientSocket = socket;
        this.name = "";
        this.server = Server.getInstance();
        commands = new HashMap<>(
                Map.of(
                        "attack", new AttackCommand(),
                        "chat", new ChatCommand(),
                        "dm", new DirectMessageCommand(),
                        "info", new PlayerInformationCommand(),
                        "reload", new ReloadCommand(),
                        "skip", new SkipCommand(),
                        "surrender", new SurrenderCommand(),
                        "tie", new TieCommand(),
                        "wildcard", new WildcardCommand(),
                        "setCharacteristics", new SetPlayerCharacteristics()
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

            this.server.setPlayerName(this.name, this.id);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] inputLine;


        try {
            this.sendMessage(this.id);
            while (true) {


                inputLine = (in.readLine()+" "+this.id).split(" ");
                String command = inputLine[0];
                String[] args = copyOfRange(inputLine, 1, inputLine.length);

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
                    case "setCharacteristics" -> {

                        this.commands.get("setCharacteristics").execute(args);
                    }

                    default -> {
                        Server.getInstance().sendToAll("User " + inputLine[0] + " connected");
                        System.out.println("User " + inputLine[0] + " connected");
                    }
                }
            }


        } catch (Exception e) {
            try {
                Server.getInstance().removeClient(this.id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public String getPlayerId() {
        return this.id;
    }
}
