package Classes.GameObjects;

import Classes.Character;
import Classes.Item;
import Enumerators.eType;

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

    public void receiveDamage(int damage){
        this.setHealth(this.getHealth() - damage);
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
