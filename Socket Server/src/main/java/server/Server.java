package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;


public class Server {
    private static Server instance;
    private final ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientList;

    public Server(int port) throws Exception {
        this.clientList = new ArrayList<>();
        this.serverSocket = new ServerSocket(port);

    }

    public void start() throws Exception {

        while(true){
            ClientHandler client = new ClientHandler(serverSocket.accept());
            clientList.add(client);
            client.start();
            System.out.println("New client connected");
        }
    }

    // Singleton
    public static Server getInstance() throws Exception {
        if (instance == null) {
            instance = new Server(8080);
            instance.start();

        }
        return instance;
    }

    public void sendToAll(String message) throws Exception {
        for (ClientHandler client : this.clientList) {
            client.sendMessage(message);
        }
    }


    public void stop() throws Exception {
        serverSocket.close();
    }




    private static class ClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
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
                throw new RuntimeException(e);
            }
        }
    }

}
