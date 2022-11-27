package Classes.Commands;

import Classes.Abstract.Command;
import Classes.GameObjects.GameCharacter;
import Classes.GameObjects.GameWeapon;
import Classes.Item;
import Classes.ServerClasses.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ReloadCommand extends Command {


    public ReloadCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {

        //args[length-1] = nombre del jugador que ejecuto el comando;

        HashMap<String, GameCharacter> characters = player.getCharacters();

        for(GameCharacter character : characters.values()){

            ArrayList<Item> items = character.getItems();

            for(Item item : items){

                GameWeapon characterWeapon = (GameWeapon) item;
                characterWeapon.setAvailable(true);

            }
        }
        String notification = "reload All weapons reloaded";

        try{
            server.notifyObserver(args[args.length-1], notification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //<comando> mensaje
        // reload All weapons reloaded
        return "reload "+args[args.length-1];
    }

}
