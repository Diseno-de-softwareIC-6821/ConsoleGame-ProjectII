package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Classes.ServerClasses.Server;
import Interfaces.iCommand;
import org.json.JSONArray;

import java.io.IOException;

public class SetPlayerCharacteristics extends Command {



    public SetPlayerCharacteristics(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args) {

        //args[0] = nuevo nombre del jugador;

        //args[1] = json con los personajes y sus armas;


        JSONArray characters = new JSONArray(args[1]);


        if(this.server.getPlayerByName(args[0]) != null){
            try {
                // le envia al cliente un 0 porque ya existe un jugador con ese nombre
                // y cierra la conexion
                this.player.update("0");

            } catch (Exception e) {
                System.out.println("Name already exists; server error");
            }
            return 0;
        }

        this.player.setPlayerName(args[0]);
        this.server.addObserver(args[0], player);

        try {
            this.player.update("1");
        } catch (Exception e) {
            System.out.println("Success code couldn't be sent; server error");
        }
        return 1;
    }
}
