package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class PlayerInformationCommand extends Command {

    public PlayerInformationCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        // args[0] = nombre del jugador buscado
        // args[length-1] = nombre del jugador que envio el mensaje

        Player searchedPlayer = server.getPlayerByName(args[0]);

        if (player == null) {
            try {
                server.notifyObserver(args[args.length-1], "Player not found");
            } catch (Exception e) {
                System.out.println("Player not found server error");
            }
            return args[args.length-1]+": "+ "info player not found";
        }

        try {
            server.notifyObserver(args[args.length-1], searchedPlayer.getPlayerStats().getJsonStats());
        } catch (Exception e) {
            System.out.println("Player information server error");
            return "Player information couldn't be sent";
        }


        //<comando> <nombre del jugador buscado> <json de estadisticas>
        // info max {"wins": 0, "losses": 0, ...} (revisar clase PlayerStatistics para ver el json)
        return "info " + args[0] + " " + searchedPlayer.getPlayerStats().getJsonStats();
    }

}
