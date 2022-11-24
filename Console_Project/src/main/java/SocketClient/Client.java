package SocketClient;

import GUI.Menu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client  {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Client() {
    }

    public Client(Menu menuScreen) {
    }
    
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

                    //meter funciones para que haga cosas
                    switch(inputLine){
                        
                        
                        default ->{
                            if (inputLine.equals("0")){
                                //menuScreen.registerName = false;
                            }
                            else{
                                //menuScreen.registerName = true;
                                //this.menuScreen.addPlayerConnected(inputLine);
                            }
                           
                        }
                    }
                    
                    
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                System.out.println("Connection closed");
            }
        }).start();
    }

}