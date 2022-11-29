package Classes.Commands;

import Classes.Abstract.Command;
import Classes.GameObjects.GameCharacter;
import Classes.GameObjects.GameWeapon;
import Classes.ServerClasses.Player;
import Enumerators.eType;
import org.json.JSONArray;
import org.json.JSONObject;

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
                player.update("setCharacteristics 0");

            } catch (Exception e) {
                System.out.println("server error");
            }
            return "setCharacteristics " +args[0]+ " name already exists";
        }

        try {

            player.setPlayerName(args[0]);
            args[1] = args[1].replace("_", " ");

            JSONArray charactersFromPlayer = new JSONArray(args[1]);
            JSONArray characterListForPlayer = new JSONArray();

            for(int i = 0; i < charactersFromPlayer.length(); i++){

                JSONObject character = charactersFromPlayer.getJSONObject(i);
                JSONObject characterForPlayer = new JSONObject();

                GameCharacter.GameCharacterBuilder newCharacter = new GameCharacter.GameCharacterBuilder();

                String characterName = character.getString("name").replace(' ', '_');;
                eType characterType = eType.valueOf(character.getString("type").toUpperCase());
                String image = character.getString("image");

                newCharacter.setName(characterName);
                newCharacter.setType(characterType);
                newCharacter.setCurrentTexture(image);
                newCharacter.setHealth(250);

                JSONArray weaponList = character.getJSONArray("weapons");

                characterForPlayer.put("name", characterName);
                for(int weaponIndex = 0; weaponIndex < weaponList.length(); weaponIndex++){
                    GameWeapon.GameWeaponBuilder newWeapon = new GameWeapon.GameWeaponBuilder();

                    String weaponName = weaponList.getString(weaponIndex).replace(' ', '_');;

                    newWeapon.setName(weaponName);
                    newWeapon.randomizeDamage();
                    newWeapon.setType(eType.NONE);

                    GameWeapon builtWeapon = newWeapon.build();

                    JSONObject weaponDamage = new JSONObject();
                    weaponDamage.put("damage", builtWeapon.getDamageList());
                    characterForPlayer.put(weaponName, weaponDamage);

                    newCharacter.addItem(builtWeapon);

                }

                characterListForPlayer.put(characterForPlayer);
                player.addCharacter(characterName, newCharacter.build());

            }

            this.server.addObserver(args[0], player);
            this.server.addPlayerToGameQueue(args[0]);
            player.update("setCharacteristics " + characterListForPlayer);
        } catch (Exception e) {
            System.out.println(e);
            return "setCharacteristics error";
        }


        //<comando> <json con el daño de las armas de cada personaje>
        // setCharacteristics [{"name":"Toledo", "El Sarpe":[0.2,0.4,0.3,0.6]}, {"name":"Chayanne", "Torero":[0.2,0.4,0.3,0.6]}]
        return "setCharacteristics " + args[0];
    }
}
