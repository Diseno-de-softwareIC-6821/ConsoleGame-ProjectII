package Data;

import Classes.ServerClasses.Server;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Log {
    private static String SAVE_PATH = "log/";
    private static String name = "";
    private static File file;
    private PrintWriter pw;
    private static BufferedReader br;
    private static Log instance;
    private HashMap<String,Stadistics> playersStadistics = new HashMap<>();

    //singleton

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }
    private Log(){}

    public static void startLog(String name1){
        //SAVE_PATH = SAVE_PATH + name; //CONCATS THE NAME OF THE LOG FILE
        name = name1;
        //file = new File(SAVE_PATH);

        file = new File(SAVE_PATH+name);
    }
    public void closeLog(){
        try {
            pw.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void print(String message) throws IOException {
        if (file != null) {
            pw = new PrintWriter(new FileWriter(file));
            pw.println(message);
            pw.close();
        }

    }
    public static ArrayList<String> getLogs()throws IOException {
        Scanner myReader = new Scanner(file);
        ArrayList<String> fileContent = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            fileContent.add(data);
            System.out.println(data);
        }
        System.out.println(fileContent.toString());
        return fileContent;
        /*
        if (file != null) {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                System.out.println(data);
                ///
                //Continuue implemented in the next version
                //falta agregar las estadisticas  / parsearlo básicamente
            }

        }*/
    }
    public static ArrayList<String> open(String name) throws IOException {
        startLog(name);
        return getLogs();
    }
/*
    private void addPlayer(String name){
        playersStadistics.put(name, new Stadistics(name));
    }
    public LinkedList<Stadistics> getRanking() {
        LinkedList<Stadistics> list = new LinkedList<>();
        for (Stadistics stadistics : playersStadistics.values()) {
            list.add(stadistics);
        }
        for (int i = 0; i < list.size(); i++) { //a simple bubble sort
            for (int j = 0; j < list.size(); j++) {
                if (list.get(i).getWins() > list.get(j).getWins()) {
                    Stadistics temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        return list;
    }*/
    public static void main(String[] args) throws Exception {
        open("log.txt");




    }
}
