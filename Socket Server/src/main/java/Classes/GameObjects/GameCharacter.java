package Classes.GameObjects;

import Classes.Character;
import Classes.Item;
import Enumerators.eType;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCharacter extends Character {


    private eType type;

    public GameCharacter(eType type){
        this.type = type;
    }

    public void receiveDamage(int damage){
        this.setHealth(this.getHealth() - damage);
    }

//    builder
}
