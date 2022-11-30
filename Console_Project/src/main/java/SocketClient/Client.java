package SocketClient;

import GUI.GameScreen;
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
    public GameScreen gameScreen;

    public Client() {
    }

    public Client(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
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
                    String[] newLine = inputLine.split(" ");
                    String command = newLine[0];
                    System.out.println("COMMAND SENDED");
                    //meter funciones para que haga cosas
                    switch(command){
                        case "setCharacteristics" ->{
                            if (newLine[1].equals("0")){
                                System.out.println("TEST");
                                this.gameScreen.flag = false;
                            }
                            else{
                                System.out.println("SUCCESFULL");
                                this.gameScreen.actualizarTablaArmas(inputLine, "");
                                this.gameScreen.flag = true;
                            }
                            
                        }
                        
                        case "attacking" ->{
                            System.out.println("ENTERED ATTACKING");
                            gameScreen.actualizarAttacking(newLine[1]);
                        }
                        
                        case "attackedBy" ->{
                            System.out.println("ENTERED ATTACKING");
                            gameScreen.actualizarAttackedBy(newLine[1]);
                        }
                        
                        case "chat" ->{
                            System.out.println("ENTERED CHAT");
                            gameScreen.sendMessageChat(newLine[1].replace("_", " "));
                        }
                        
                        default ->{
                            gameScreen.sendMessageConsole(inputLine);
                           
                        }
                    }
                    
                    
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                System.out.println("Connection closed");
            }
        }).start();
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
    

}