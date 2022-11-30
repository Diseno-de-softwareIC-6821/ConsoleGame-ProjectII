package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class TieCommand extends Command {

    public TieCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        //args[0] = y/n (si el jugador acepta o no la propuesta de empate | Solo si no es el jugador que envio la propuesta)
        //args[length-1] = nombre del jugador que envio el mensaje

        if(server.getCurrentTurn().equals(args[args.length-1]) && !(args[0].equals("y") || args[0].equals("n"))){
            try{
                server.notifyAllObservers("tie " + args[args.length-1]+":_Asked_for_a_tie(y/n)");
                server.addPlayerToTieQueue(args[args.length-1]);
            }
            catch (Exception e) {
                System.out.println("couldn't send notification to all");
            }
            System.out.println("Tie created");
            return "tie " + args[args.length-1] + " asked for a tie";

        }

        else{

            if(server.isTieQueueEmpty()){
                try{
                    server.notifyObserver(args[args.length-1], "tie No_one_asked_for_a_tie");
                }
                catch (Exception e) {
                    System.out.println("couldn't send notification to" + args[args.length-1]);
                }
                return "tie " + args[args.length-1] + " no tie proposal";
            }

            if(args[0].equalsIgnoreCase("y")){
                try{
                    server.notifyAllObservers("tie " + args[args.length-1]+":_Accepted_the_tie");
                    server.addPlayerToTieQueue(args[args.length-1]);
                }
                catch (Exception e) {
                    System.out.println("couldn't send notification");
                }
                return "tie " + args[args.length-1] + " accepted the tie";

            }
            if(args[0].equalsIgnoreCase("n")){
                try{
                    server.notifyAllObservers("tie " + args[args.length-1]+":_Rejected_the_tie");
                    server.cancelTie();
                }
                catch (Exception e) {
                    System.out.println("couldn't send notification");
                }
                return "tie " + args[args.length-1] + " cancelled the tie";
            }
        }


        return "tie " + args[args.length-1] + " args error";
    }
}

