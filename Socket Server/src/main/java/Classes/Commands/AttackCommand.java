package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Classes.ServerClasses.Server;


public class AttackCommand extends Command {

    public AttackCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args) {
        //args[0] = personaje que ataca;
        //args[1] = arma que usa para atacar;
        //args[2] = nombre del jugador atacado;

        //args[length-1] = nombre del jugador que ataca;

        return -1;
    }
}
