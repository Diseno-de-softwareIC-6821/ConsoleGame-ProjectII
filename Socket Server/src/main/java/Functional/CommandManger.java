package Functional;

import Classes.Abstract.Command;
import Classes.Commands.*;
import Classes.ServerClasses.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandManger {
   private HashMap <String, Command> commandsMap= new HashMap<>();
   private static CommandManger instance;
   public  static CommandManger getInstance() {
       if (instance == null) {
           instance = new CommandManger();
       }
       return instance;
   }

    public void addCommand(String commandName, Command command){
       commandsMap.put(commandName, command);
   }
   public Command getCommand(String commandName){
       return commandsMap.get(commandName);
   }

   public void setBasicsCommands( ) throws Exception {
       addCommand("attack", new AttackCommand();
       addCommand("chat", new ChatCommand());
       addCommand("dm", new DirectMessageCommand());
       addCommand("info", new PlayerInformationCommand());
       addCommand("reload", new ReloadCommand());
       addCommand("skip", new SkipCommand());
       addCommand("surrender", new SurrenderCommand());
       addCommand("tie", new TieCommand());
       addCommand("wildcard", new WildcardCommand());
       addCommand( "setCharacteristics", new SetPlayerCharacteristics());
   }

}
