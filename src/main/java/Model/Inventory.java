package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents an inventory in the game.
 */
public class Inventory implements Serializable {
    private static final Logger logger = Logger.getLogger(Inventory.class.getName());
    private static final long serialVersionUID = 1L;
    private Map<Integer, Item> items;
    private int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new HashMap<Integer, Item>();
    }


    /**
     * Adds an item to the inventory.
     * @param item The item to add.
     * @return True if the item was successfully added, false otherwise.
     */
    public boolean addItem(Item item) {
        if (items.size() < capacity) {
            items.put(items.size(), item);
            logger.log(Level.INFO, "Added item: {0} at position {1}", new Object[]{item.getName(), items.size() - 1});
            return true;
        } else {
            logger.log(Level.WARNING, "Failed to add item: {0}. Inventory is full.", item.getName());
            return false;
        }
    }

    /**
     * Adds an item to the inventory at the specified position.
     * @param item The item to add.
     * @param place The position to add the item.
     * @return True if the item was successfully added, false otherwise.
     */
    public boolean addItem(Item item, Integer place) {
        if (items.size() < capacity) {
            items.put(place, item);
            logger.log(Level.INFO, "Added item: {0} at position {1}", new Object[]{item.getName(), place});
            return true;
        } else {
            logger.log(Level.WARNING, "Failed to add item: {0} at position {1}. Inventory is full.", new Object[]{item.getName(), place});
            return false;
        }
    }


    /**
     * Removes an item from the inventory at the specified position.
     * @param key The position of the item to remove.
     */
    public void removeItem(Integer key) {
        Item removedItem = items.remove(key);
        if (removedItem != null) {
            logger.log(Level.INFO, "Removed item: {0} from position {1}", new Object[]{removedItem.getName(), key});
        } else {
            logger.log(Level.WARNING, "No item found at position {0} to remove.", key);
        }
    }

    public Map<Integer, Item> getItems() {
        return items;
    }


    public int getCapacity() {
        return capacity;
    }

    public int getItemCount() {
        return items.size();
    }

    public void setItems(Map<Integer, Item> items) {
        this.items = items;
    }
}

