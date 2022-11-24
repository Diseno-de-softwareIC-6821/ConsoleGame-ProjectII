package Data;

import Classes.Character;
import Classes.GameObjects.GameCharacter;
import Classes.Item;

import java.util.ArrayList;

public class Database {
    private static Database instance;

    private ArrayList<GameCharacter> characters;
    private ArrayList<Item> items;

    private Database() {
        this.characters = new ArrayList<>();
        this.items = new ArrayList<>();
        loadCharacters();
        loadItems();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void loadCharacters() {

    }

    public void loadItems() {
        // load items from file
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<GameCharacter> getCharacters() {
        return characters;
    }
}
