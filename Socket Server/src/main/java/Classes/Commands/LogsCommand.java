package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Data.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LogsCommand extends Command {

    public LogsCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {

        // args[length-1] = nombre del jugador que envio el mensaje

        JSONObject logs = new JSONObject();
        JSONArray logsArray = new JSONArray();
        try {
            for(String s: Log.getLogs()){
                logsArray.put(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logs.put("logs", logsArray); // public static ArrayList<String> getLogs()

        String notification = "logs " + logs.toString().replace(" ", "_");
        try{
            server.notifyObserver(args[args.length-1], notification);
        } catch (Exception e) {
            System.out.println("Logs server error");
        }
        //{logs:[log1\n, log2\n, log3\n]}
        return "logs " + notification;
    }
}
