package Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Player extends Thread{
    private final String id;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Player(String id ,Socket socket) {
        this.id = id;
        this.clientSocket = socket;
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
                    case "01" -> {
                        out.println("Hello from the server");
                    }
                    case "02" -> {
                        Server.getInstance().sendToAll("New message");
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
