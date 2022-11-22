package client;

import java.io.*;

import java.net.Socket;

public class Client  {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws Exception {
        clientSocket = new Socket(ip, port);

        try{
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.startMessageListener();

    }

    public void sendMessage(String msg) throws Exception {
        out.println(msg);
    }

    public void stopConnection() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }
    public void startMessageListener() {
        new Thread(() -> {
            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null) {



                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                System.out.println("Connection closed");
            }
        }).start();
    }

}
