package Classes.GameObjects;

import Classes.Character;
import Classes.Item;
import Enumerators.eType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCharacter extends Character {
    private eType type;

    private GameCharacter(HashMap<Integer, String> textureMap, String currentTexture, ArrayList<Item> items,
                         Item selectedItem, String name, int level, int experience, int health, int damage,
                         int defense, int speed, int dps, int cost, int spawnLevel, int housingSpace, int positionX,
                         int positionY, eType type){
        super( textureMap,  currentTexture, items, selectedItem,  name,  level,  experience,  health,  damage,
                defense,  speed,  dps,  cost,  spawnLevel,  housingSpace,  positionX, positionY);
        this.type = type;
    }
    public eType getType() {
        return type;
    }

    public int receiveDamage(int damage){
        this.setHealth(this.getHealth() - damage);
        return this.getHealth();
    }

    public Item getItemByName(String name){
        for (Item item: this.getItems()) {
            if(item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }

    public String getJson(){

        JSONObject character = new JSONObject();
        character.put("name", this.getName());
        character.put("texture", this.getCurrentTexture());
        character.put("health", Integer.toString(this.getHealth()));

        JSONArray weapons = new JSONArray();

        for(Item item : this.getItems()){
            GameWeapon weapon = (GameWeapon) item;
            JSONObject weaponJson = new JSONObject();

            weaponJson.put("name", weapon.getName());
            weaponJson.put("damage", weapon.getDamageList());

            weapons.put(weaponJson);
        }

        character.put("weapons", weapons);

        return character.toString();
    }

    public static class GameCharacterBuilder {
        private CharacterBuilder characterB = new Character.CharacterBuilder().setDamage(0);
        private eType type;
        public GameCharacterBuilder(){}
        public GameCharacterBuilder setCurrentTexture(String texture){
            characterB.setCurrentTexture(texture);
            return this;
        }
        public GameCharacterBuilder setType(eType type){
            this.type = type;
            return this;
        }
        public GameCharacterBuilder addItem(Item item){
            characterB.addItem(item);
            return this;
        }
        public GameCharacterBuilder setSelectedItem(Item item){
            characterB.setSelectedItem(item);
            return this;
        }
        public GameCharacterBuilder setName(String name){
            characterB.setName(name);
            return this;
        }
        public GameCharacterBuilder setHealth(int health){
            characterB.setHealth(health);
            return this;
        }
        public GameCharacter build() {
            Character character = characterB.build();
            return new GameCharacter(character.getTextureMap(), character.getCurrentTexture(), character.getItems(),
                    character.getSelectedItem(), character.getName(), character.getLevel(), character.getExperience(),
                    character.getHealth(), character.getDamage(), character.getDefense(), character.getSpeed(),
                    character.getDps(), character.getCost(), character.getSpawnLevel(), character.getHousingSpace(),
                    character.getPositionX(), character.getPositionY(), type);
        }
    }

//    builder
}
