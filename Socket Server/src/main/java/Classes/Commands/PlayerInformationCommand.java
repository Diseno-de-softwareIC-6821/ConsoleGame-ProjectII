package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class PlayerInformationCommand extends Command {

    public PlayerInformationCommand() throws Exception {
        super();
    }

    @Override
    public int execute(String[] args, Player player) {
        // args[0] = nombre del jugador buscado
        // args[length-1] = nombre del jugador que envio el mensaje

        Player searchedPlayer = server.getPlayerByName(args[0]);

        if (player == null) {
            try {
                server.notifyObserver(args[args.length-1], "Player not found");
            } catch (Exception e) {
                System.out.println("Player not found server error");
            }
            return 0;
        }

        try {
            server.notifyObserver(args[args.length-1], searchedPlayer.toString());
        } catch (Exception e) {
            System.out.println("Player information server error");
            return 0;
        }


        return 1;
    }

}
