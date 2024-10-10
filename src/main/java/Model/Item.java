package Model;

import java.awt.*;
import java.io.Serializable;

import static Model.Constants.*;

/**
 * Represents an item in the game.
 */
public class Item implements RectangleConvertible, Serializable {
    private String name;
    private String description;
    private Vector2D position;
    private ObjectType itemType;

    /**
     * Constructs an item with the specified name, description, position, and type.
     * @param name The name of the item.
     * @param description The description of the item.
     * @param position The position of the item.
     * @param itemType The type of the item.
     */
    public Item(String name, String description, Vector2D position, ObjectType itemType) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.itemType = itemType;
    }


    /**
     * Constructs an item with the specified name, description, and type, and default position.
     * @param name The name of the item.
     * @param description The description of the item.
     * @param itemType The type of the item.
     */
    public Item(String name, String description, ObjectType itemType) {
        this.name = name;
        this.description = description;
        this.position = new Vector2D(-100,-100);
        this.itemType = itemType;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public ObjectType getItemType() {
        return itemType;
    }

    // Метод для получения названия предмета
    public String getName() {
        return name;
    }

    // Метод для получения описания предмета
    public String getDescription() {
        return description;
    }


    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle((int) position.getX(), (int) position.getY(), ITEM_SIZE, ITEM_SIZE);
    }
}
