package Functional;

import Classes.Abstract.Command;
import Classes.GameObjects.GameCharacter;
import Classes.ServerClasses.Player;
import Data.Log;
import Interfaces.iCommand;

import java.util.Arrays;

public class Proxy implements iCommand {

    public Proxy() throws Exception {
        CommandManger.getInstance().setBasicsCommands();
        Log.getInstance().startLog("log.txt");
    }

    @Override
    public int execute(String[] args, Player player) {

        System.out.println("Command to execute: " + Arrays.toString(args));

        String command = args[0];
        Command command1 = CommandManger.getInstance().getCommand(command);

        command1.execute(args, player);
        try {
            Log.getInstance().print("Command executed: " + Arrays.toString(args));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
