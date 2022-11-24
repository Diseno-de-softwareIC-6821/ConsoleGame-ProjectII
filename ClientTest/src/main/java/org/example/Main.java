package org.example;


import client.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String name = reader.readLine();

        String characteristics = "setCharacteristics "+name;

        Client client = new Client();
        client.startConnection("localhost", 8080);
        client.sendMessage(characteristics);

        System.out.println("1. Greet server");
        System.out.println("2. Notify all clients");
        System.out.println("3. Exit");

        String testString = "setCharacteristics Daniel [{\"name\":\"Penny Wise\",\"image\":\"src\\main\\java\\Images\\PennyWise.jpg\",\"type\":\"White Magic\",\"weapons\":[\"Glove\",\"Knife\",\"Pistol\",\"Grenade\",\"Magic\"]},{\"name\":\"Michael Myers\",\"image\":\"src\\main\\java\\Images\\MichaelMyers.jpg\",\"type\":\"Black Magic\",\"weapons\":[\"Knife\",\"Hands\",\"Shotgun\",\"Rocket Launcher\",\"Sing\"]},{\"name\":\"Chayanne\",\"image\":\"src\\main\\java\\Images\\Chayanne.jpg\",\"type\":\"Iron\",\"weapons\":[\"Fists\",\"Six-Pack\",\"Torero\",\"Smile\",\"Paquetote\"]},{\"name\":\"Toledo\",\"image\":\"src\\main\\java\\Images\\Toledo.jpg\",\"type\":\"Acid\",\"weapons\":[\"Estar Ebrio\",\"El Puro\",\"El Chopo\",\"Rap\",\"Flow\"]}]";
        // Read user input


        while (true) {


            String input = reader.readLine();

            switch (input) {
                case "1" -> {
                    client.sendMessage("dm esteban hola esto es un test para esteban");
                }
                case "2" -> {
                    client.sendMessage("02");
                }
                case "3" -> {
                    client.sendMessage("00");
                    client.stopConnection();
                    return;
                }
                default -> System.out.println("?");
            }
        }



    }
}