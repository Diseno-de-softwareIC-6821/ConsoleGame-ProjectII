package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class DirectMessageCommand extends Command {


    public DirectMessageCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {

        //esteban hola esto es un test para esteban max"
        //args[0] = nombre del receptor;
        //args[1 hasta args.length-1] = mensaje;
        //args[args.length-1] = nombre del jugador que envio el mensaje;

        StringBuilder message = new StringBuilder();
        message.append(args[args.length-1]).append(" ");

        for (int i = 1; i < args.length-1; i++) {
            message.append(args[i]).append(" ");
        }

        try {
            server.notifyObserver(args[0], message.toString());
        } catch (Exception e) {
            System.out.println("Direct message server error");
            return "0";
        }

        //envia al receptor
        //<comando ejecutado> <nombre del emisor> mensaje

        return "dm " + message;
    }

}
