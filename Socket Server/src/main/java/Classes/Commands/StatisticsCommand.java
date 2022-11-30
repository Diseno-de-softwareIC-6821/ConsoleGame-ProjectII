package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class StatisticsCommand extends Command {

    public StatisticsCommand () throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        // args[length-1] = nombre del jugador que envio el mensaje

        String notification = "statistics " + args[args.length-1] + ":_" + player.getPlayerStats().getJsonStats().replace(" ", "_");

        try {
            server.notifyObserver(args[args.length-1], notification);
        } catch (Exception e) {
            System.out.println("Statistics server error");
        }

        return notification;

    }
}
