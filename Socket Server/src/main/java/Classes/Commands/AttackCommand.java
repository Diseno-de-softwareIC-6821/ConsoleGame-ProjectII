package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Server;


public class AttackCommand extends Command {

    public AttackCommand() throws Exception {
        super();
    }

    @Override
    public void execute(String[] args) {
        //args[0] = personaje que ataca;
        //args[1] = arma que usa para atacar;
        //args[2] = nombre del jugador atacado;

        //args[length-1] = id del jugador que ataca;


        String id = this.server.getPlayerByName(args[3]).getPlayerId();

        System.out.println("Atacando a " + id);
    }
}

