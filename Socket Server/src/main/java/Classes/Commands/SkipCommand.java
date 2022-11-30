package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class SkipCommand extends Command {

    public SkipCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {

        if(server.getCurrentTurn().equals(args[args.length-1])){
            try {
                server.nextTurn();
                server.notifyObserver(args[args.length-1], "skip Turn_skipped");
            } catch (Exception e) {
                System.out.println("Skip server error");
            }
            return "skip " + args[args.length-1] + " turn skipped";
        }
        else{
            try {
                server.notifyObserver(args[args.length-1], "skip It's_not_your_turn");
            } catch (Exception e) {
                System.out.println("Skip server error");
            }
            return "skip " + args[args.length-1] + " not its turn";
        }

    }

}
