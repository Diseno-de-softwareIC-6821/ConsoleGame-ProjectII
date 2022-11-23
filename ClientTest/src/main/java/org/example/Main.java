package org.example;


import client.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {

        Client client = new Client();
        client.startConnection("localhost", 8080);

        System.out.println("1. Greet server");
        System.out.println("2. Notify all clients");
        System.out.println("3. Exit");

        // Read user input


        while (true) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();

            switch (input) {
                case "1" -> {
                    client.sendMessage("chat hola esto es un test para todos");
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