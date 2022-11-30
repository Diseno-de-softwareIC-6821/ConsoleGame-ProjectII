package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class ChatCommand extends Command {

    public ChatCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {

        // args[0 to length-1] = mensaje
        // args[length-1] = nombre del jugador que envio el mensaje

        System.out.println(args[args.length-1]);
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < args.length-1; i++) {
            message.append(args[i]).append(" ");
        }

        String notification = "chat "+args[args.length-1]+":_"+message.toString().replace(" ", "_");

        try {
            server.notifyAllObservers(notification);

        } catch (Exception e) {
            System.out.println("Chat server error");
            return args[args.length-1] + ": " + "chat couldn't be sent";
        }


        //envia a todos
        //<comando ejecutado> mensaje
        // chat max:_hola_como_estan

        return "chat " + message;

    }
}
