package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class ChatCommand extends Command {

    public ChatCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args, Player player) {

        // args[0 to length-1] = mensaje
        // args[length-1] = nombre del jugador que envio el mensaje

        System.out.println(args[args.length-1]);
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < args.length-1; i++) {
            message.append(args[i]).append(" ");
        }

        try {
            server.notifyAllObservers(message.toString());

        } catch (Exception e) {
            System.out.println("Chat server error");
            return 0;
        }

        return 1;

    }
}
