package Classes.Commands;

import Classes.Abstract.Command;
import Interfaces.iCommand;

public class ChatCommand extends Command {

    public ChatCommand() throws Exception {
        super();
    }

    @Override
    public void execute(String[] args) {

        // args[0 to length-1] = mensaje
        // args[length-1] = id del jugador que envio el mensaje

        System.out.println(args[args.length-1]);
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < args.length-1; i++) {
            message.append(args[i]).append(" ");
        }

        try {

            server.sendToAll(message.toString());
        } catch (Exception e) {
            System.out.println("Chat server error");
        }

    }
}
