package Data;

import Classes.Character;
import Classes.Item;

public class Database {
    private static Database instance;

    private Character[] characters;
    private Item[] items;

    private Database() {
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
        // load characters from file
    }

    public void loadItems() {
        // load items from file
    }

    public Item[] getItems() {
        return items;
    }

    public Character[] getCharacters() {
        return characters;
    }
}
