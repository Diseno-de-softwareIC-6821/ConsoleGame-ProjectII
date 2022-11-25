package Classes.Commands;

import Classes.Abstract.Command;
import Classes.Character;
import Classes.GameObjects.GameCharacter;
import Classes.GameObjects.GameWeapon;
import Classes.ServerClasses.Player;
import Classes.ServerClasses.Server;
import Enumerators.eType;
import Interfaces.iCommand;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class SetPlayerCharacteristics extends Command {



    public SetPlayerCharacteristics() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {

        //args[0] = nuevo nombre del jugador;

        //args[1] = json con los personajes y sus armas;

        if(this.server.getPlayerByName(args[0]) != null){
            try {
                // le envia al cliente un 0 porque ya existe un jugador con ese nombre
                // y cierra la conexion
                player.update("setCharacteristics 0");

            } catch (Exception e) {
                System.out.println("Name already exists; server error");
            }
            return "setCharacteristics " +args[0]+ " name already exists";
        }


        try {

            player.setPlayerName(args[0]);
            args[1] = args[1].replace("_", " ");
            JSONArray charactersJson = new JSONArray(args[1]);


            for(int i = 0; i < charactersJson.length(); i++){

                JSONObject character = charactersJson.getJSONObject(i);

                GameCharacter.GameCharacterBuilder newCharacter = new GameCharacter.GameCharacterBuilder();

                String characterName = character.getString("name");
                eType characterType = eType.valueOf(character.getString("type").toUpperCase());
                String image = character.getString("image");

                newCharacter.setName(characterName);
                newCharacter.setType(characterType);
                newCharacter.setCurrentTexture(image);

                JSONArray weaponList = character.getJSONArray("weapons");

                for(int weaponIndex = 0; weaponIndex < weaponList.length(); weaponIndex++){
                    GameWeapon.GameWeaponBuilder newWeapon = new GameWeapon.GameWeaponBuilder();

                    newWeapon.setName(weaponList.getString(weaponIndex));
                    newWeapon.randomizeDamage();
                    newWeapon.setType(eType.NONE);

                    newCharacter.addItem(newWeapon.build());
                }

                player.addCharacter(characterName, newCharacter.build());

            }


            this.server.addObserver(args[0], player);

            player.update("1");
        } catch (Exception e) {
            System.out.println(e);
            return "setCharacteristics error";
        }
        return "1";
    }
}
