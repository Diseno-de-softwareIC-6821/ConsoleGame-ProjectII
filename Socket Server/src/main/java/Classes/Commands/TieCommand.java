package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class TieCommand extends Command {

    public TieCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args, Player player) {

        //args[length-1] = nombre del jugador que envio el mensaje

        try {
            server.notifyAllObservers("Player " + args[args.length-1] + " wants to tie");
        } catch (Exception e) {
            System.out.println("Tie server error");
            return 0;
        }


        return 1;
    }
}

