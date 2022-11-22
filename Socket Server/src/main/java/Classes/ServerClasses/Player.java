package Classes.ServerClasses;

import Classes.Character;
import Classes.Commands.AttackCommand;
import Interfaces.iCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Thread{

    private final String id;
    private final String name;

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private HashMap<String, iCommand> commands;
    private ArrayList<Character> characters;
    // 0: max [1,2,3,4]
    // 1: cornejo
    // 2: sambucci
    // 3: esteban


    public Player(String id ,Socket socket) {
        this.id = id;
        this.clientSocket = socket;
        this.name = "";
        commands = new HashMap<>(
                Map.of(
                        "attack", new AttackCommand()
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

        String inputLine;

        try {
            this.sendMessage(this.id);
            while (true) {

                // out.println(); Send message to current client
                // Server.getInstance().sendToAll(); Send message to all clients connected to the server instance

                inputLine = in.readLine();

                switch (inputLine) {
                    case "attack" -> {
                        this.sendMessage("An attack has been made");
                    }
                    case "02" -> {
                        out.println("Hello from the server");
                    }
                    case "00" -> {
                        out.println("bye");
                        in.close();
                        out.close();
                        clientSocket.close();
                        return;
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
}
