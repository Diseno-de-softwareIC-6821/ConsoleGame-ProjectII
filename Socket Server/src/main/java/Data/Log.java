package Data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Log {
    private String SAVE_PATH = "log/";
    private String name = "";
    private File file;
    private PrintWriter pw;
    private BufferedReader br;
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
    public void startLog(String name){
        SAVE_PATH = SAVE_PATH + name; //CONCATS THE NAME OF THE LOG FILE
        this.name = name;
        file = new File(SAVE_PATH);
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
    public void readLog() throws IOException {
        if (file != null) {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                ///
                //Continuue implemented in the next version
                //falta agregar las estadisticas  / parsearlo básicamente
            }
        }
    }
    public void open(String name) throws IOException {
        startLog(name);
        readLog();
    }

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
    }

}
