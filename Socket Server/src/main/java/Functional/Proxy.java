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
    public String execute(String[] args, Player player) {

        for(String arg : args){
            System.out.println(arg);
        }

        String command = args[0];
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        Command executableCommand = CommandManger.getInstance().getCommand(command);

        String action = executableCommand.execute(commandArgs, player);
        try {
            Log.getInstance().print("Command executed: " + action);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
}
