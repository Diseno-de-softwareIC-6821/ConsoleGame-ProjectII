package org.example;


import client.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Client client = new Client();
        client.startConnection("localhost", 8080);

        System.out.println("1. test");
        System.out.println("2. test2");
        System.out.println("3. attack");

        String testString = "setCharacteristics daniel [{\"name\":\"Penny_Wise\",\"image\":\"src/main/java/Images/PennyWise.jpg\",\"type\":\"MAGIABLANCA\",\"weapons\":[\"Glove\",\"Knife\",\"Pistol\",\"Grenade\",\"Magic\"]},{\"name\":\"Michael_Myers\",\"image\":\"src/main/java/Images/MichaelMyers.jpg\",\"type\":\"MAGIANEGRA\",\"weapons\":[\"Knife\",\"Hands\",\"Shotgun\",\"Rocket_Launcher\",\"Sing\"]},{\"name\":\"Chayanne\",\"image\":\"src/main/java/Images/Chayanne.jpg\",\"type\":\"HIERRO\",\"weapons\":[\"Fists\",\"Six-Pack\",\"Torero\",\"Smile\",\"Paquetote\"]},{\"name\":\"Toledo\",\"image\":\"src/main/java/Images/Toledo.jpg\",\"type\":\"ACIDO\",\"weapons\":[\"Estar_Ebrio\",\"El_Puro\",\"El_Chopo\",\"Rap\",\"Flow\"]}]";
        String testString2 = "setCharacteristics esteban [{\"name\":\"Penny_Wise\",\"image\":\"src/main/java/Images/PennyWise.jpg\",\"type\":\"MAGIABLANCA\",\"weapons\":[\"Glove\",\"Knife\",\"Pistol\",\"Grenade\",\"Magic\"]},{\"name\":\"Michael_Myers\",\"image\":\"src/main/java/Images/MichaelMyers.jpg\",\"type\":\"MAGIANEGRA\",\"weapons\":[\"Knife\",\"Hands\",\"Shotgun\",\"Rocket_Launcher\",\"Sing\"]},{\"name\":\"Chayanne\",\"image\":\"src/main/java/Images/Chayanne.jpg\",\"type\":\"HIERRO\",\"weapons\":[\"Fists\",\"Six-Pack\",\"Torero\",\"Smile\",\"Paquetote\"]},{\"name\":\"Toledo\",\"image\":\"src/main/java/Images/Toledo.jpg\",\"type\":\"ACIDO\",\"weapons\":[\"Estar_Ebrio\",\"El_Puro\",\"El_Chopo\",\"Rap\",\"Flow\"]}]";


        // Read user input


        while (true) {

            System.out.println("Enter a number: ");
            String input = reader.readLine();

            switch (input) {
                case "1" -> {
                    client.sendMessage(testString);
                }
                case "2" -> {
                    client.sendMessage(testString2);
                }
                case "3" -> {
                    client.sendMessage("attack esteban Penny_Wise Knife");

                }
                default -> System.out.println("?");
            }
        }



    }
}