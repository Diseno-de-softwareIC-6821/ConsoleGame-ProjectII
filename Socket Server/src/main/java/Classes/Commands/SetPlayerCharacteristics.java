package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Classes.ServerClasses.Server;
import Interfaces.iCommand;

import java.io.IOException;

public class SetPlayerCharacteristics extends Command {



    public SetPlayerCharacteristics(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args) {

        //args[0] = nuevo nombre del jugador;

        //args[1] = personaje 1;
        //args[2 hasta 6] = armas del personaje 1;
        //args[7] = personaje 2;
        //args[8 hasta 13] = armas del personaje 2;
        //args[14] = personaje 3;
        //args[15 hasta 20] = armas del personaje 3;
        //args[21] = personaje 4;
        //args[22 hasta 27] = armas del personaje 4;

        //args[length-1] = id del jugador;

        if(this.server.getPlayerByName(args[0]) != null){
            try {
                // le envia al cliente un 0 porque ya existe un jugador con ese nombre
                // y cierra la conexion
                this.player.update("0");
                //this.player.getSocket().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return 0;
        }

        this.player.setPlayerName(args[0]);
        this.server.addObserver(args[0], player);

        return 1;
    }
}
