package Classes.Commands;

import Classes.ServerClasses.Server;
import Interfaces.iCommand;

public class SetPlayerCharacteristics implements iCommand {

    private final Server server;

    public SetPlayerCharacteristics() throws Exception {
        this.server = Server.getInstance();
    }

    @Override
    public void execute(String[] args) {

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

        this.server.getPlayerById(args[1]).setName(args[0]);

    }
}
