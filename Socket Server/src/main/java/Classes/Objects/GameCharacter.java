package Classes.Objects;

import Classes.Character;
import Classes.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCharacter extends Character {

    public GameCharacter(HashMap<Integer, String> textureMap, String currentTexture, ArrayList<Item> items, Item selectedItem, String name, int level, int experience, int health, int damage, int defense, int speed, int dps, int cost, int spawnLevel, int housingSpace, int positionX, int positionY) {
        super(textureMap, currentTexture, items, selectedItem, name, level, experience, health, damage, defense, speed, dps, cost, spawnLevel, housingSpace, positionX, positionY);
    }

//    builder
}
