package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class TieCommand extends Command {

    public TieCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        //args[0] = y/n (si el jugador acepta o no la propuesta de empate)
        //args[length-1] = nombre del jugador que envio el mensaje

        if(server.getCurrentTurn().equals(args[args.length-1])){
            try{
                server.notifyAllObservers("tie " + args[args.length-1] + " asked for a tie");
                server.addPlayerToTieQueue(args[args.length-1]);
            }
            catch (Exception e) {
                System.out.println("Tie server error");
            }
            return "tie " + args[args.length-1] + " asked for a tie";

        }

        else{
            if(args[0].equalsIgnoreCase("y")){
                try{
                    server.notifyAllObservers("tie " + args[args.length-1] + " accepted the tie");
                    server.addPlayerToTieQueue(args[args.length-1]);
                }
                catch (Exception e) {
                    System.out.println("Tie server error");
                }
                return "tie " + args[args.length-1] + " accepted the tie";

            }
            if(args[0].equalsIgnoreCase("n")){
                try{
                    server.notifyAllObservers("tie " + args[args.length-1] + " rejected the tie");
                    server.cancelTie();
                }
                catch (Exception e) {
                    System.out.println("Tie server error");
                }
                return "tie " + args[args.length-1] + " cancelled the tie";
            }
        }


        return "tie " + args[args.length-1] + " args error";
    }
}

