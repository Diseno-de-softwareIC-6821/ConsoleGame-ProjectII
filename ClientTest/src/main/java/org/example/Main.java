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