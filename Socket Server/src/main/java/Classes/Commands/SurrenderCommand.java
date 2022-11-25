package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class SurrenderCommand extends Command {

    public SurrenderCommand() throws Exception {
        super();
    }

    @Override
    public int execute(String[] args, Player player) {
        // args[length-1] = nombre del jugador que envio el mensaje

        try {

            server.notifyObserver(args[args.length-1], "You surrendered");
            server.removeObserver(args[args.length-1]);
            player.getSocket().close();

            server.notifyAllObservers("Player " + args[args.length-1] + " surrendered");
        } catch (Exception e) {
            System.out.println("Surrender server error");
            return 0;
        }

        return -1;
    }
}

