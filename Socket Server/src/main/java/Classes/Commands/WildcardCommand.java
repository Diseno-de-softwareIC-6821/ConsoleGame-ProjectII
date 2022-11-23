package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class WildcardCommand extends Command {

    public WildcardCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args) {

        //args[length-1] = nombre del jugador que envio el mensaje

        if(!this.player.getWildCardIsReady()){
            try {
                server.notifyObserver(args[args.length-1], "You don't have a wildcard yet");
            } catch (Exception e) {
                System.out.println("Wildcard server error");
            }
            return 0;
        }
        //else use the wildcard

        return 1;
    }

}
