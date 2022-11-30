package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class SurrenderCommand extends Command {

    public SurrenderCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        // args[length-1] = nombre del jugador que envio el mensaje

        try {

            this.server.notifyObserver(args[args.length-1], "surrender You_surrendered");
            this.server.removePlayerFromGameQueue(args[args.length-1]);

            server.notifyAllObservers("surrender " + args[args.length-1] +":_"+ "surrendered");
        } catch (Exception e) {
            System.out.println("Surrender server error");
        }

        return "surrender " + args[args.length-1] + " surrendered";
    }
}

