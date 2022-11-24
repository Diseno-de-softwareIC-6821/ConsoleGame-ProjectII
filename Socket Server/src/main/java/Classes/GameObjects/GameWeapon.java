package Classes.GameObjects;

import Classes.Item;
import Enumerators.eType;
import Enums.eItemClass;
import Enums.eItemEffect;

import java.util.HashMap;

public class GameWeapon extends Item {

    private HashMap<eType, Double> damageMap;
    private eType type;

    private boolean available;
    private GameWeapon(HashMap<Integer, String> textureMap, eItemClass itemClass, eItemEffect itemEffect, String name,
                       int level, int range, int coolDown, int damage, int explosionRadius, int ammo,
                       HashMap<eType, Double> damageMap, eType type, boolean available) {
        super(textureMap, itemClass, itemEffect, name,level,range,coolDown,damage,explosionRadius,ammo);
        this.damageMap = damageMap;
        this.type = type;
        this.available = available;
    }


    public void reload(){
        this.available = true;
    }
    public void offAmmo(){
        this.available = false;
    }
    public boolean isAvailable(){
        return this.available;
    }
    public double getDamage(eType effect){
        return this.damageMap.get(effect);
    }

    public static class GameWeaponBuilder {
        private HashMap<eType, Double> damageMap = new HashMap<eType, Double>();

        private eType type;
        private boolean available = true;

        private HashMap<Integer, String> textureMap;
        private String currentTexture = "default";
        private eItemClass itemClass = eItemClass.NONE;
        private eItemEffect itemEffect = eItemEffect.NONE;
        private String name = "default";
        private int  level = 0;
        private int range= 0;
        private int coolDown= 0;
        private int damage = 0;
        private int explosionRadius= 0;
        private int ammo= 0;
        public GameWeaponBuilder(){}
        public GameWeaponBuilder addDamage(eType effect, double damage){
            damageMap.put(effect, damage);
            return this;
        }

        public GameWeaponBuilder randomizeDamage(){
            for(int i = 0; i < 10; i++){

                //create a random value between 0.2 and 1
                double randomValue = 0.2 + (Math.random() * (1 - 0.2));
                this.damageMap.put(eType.values()[i], randomValue);

            };
            return this;
        }

        public GameWeaponBuilder setType(eType type){
            this.type = type;
            return this;
        }
        public GameWeaponBuilder setName(String name){
            this.name = name;
            return this;
        }
        public GameWeapon build() {
            return new GameWeapon(textureMap, itemClass, itemEffect, name, level, range, coolDown, damage,
                    explosionRadius, ammo, damageMap, type, available);
        }
    }

}
