package Model;

import java.io.Serializable;
import java.util.HashMap;

import static Model.Constants.*;
import static Model.Constants.INVENTORY_CAPACITY;
import static Model.ObjectType.*;

/**
 * Represents the player character in the game.
 */
public class Player extends CharacterMoving {
    private String name;
    private Inventory inventory;
    private Weapon currentWeapon;


    public Player(String name, int hp, Vector2D position, Vector2D velocity,String animationPath, Inventory inventory, Weapon currentWeapon) {
        super(hp, position, velocity, animationPath);
        this.name = name;
        this.inventory = inventory;
        this.currentWeapon = currentWeapon;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public static Player createAbama(int x, int y) {
        return new Player("Abama",
                PLAYER_HP,
                new Vector2D(x, y),
                PLAYER_VELOCITY,
                PLAYER_ANIMATIONS_PATH,
                new Inventory(INVENTORY_CAPACITY),
                new Weapon("","","",new Vector2D(0,0),
                        10));
    }


    /**
     * Moves the player character in the specified direction.
     * @param direction The direction in which to move the player.
     */
    @Override
    public void move(Vector2D direction) {
        Vector2D displacement = direction.multiply(getVelocity());
        setCurrentPosition(getCurrentPosition().add(displacement));
        this.setDirection(Direction.fromVector(direction));
        switch (this.getDirection()) {
            case UP:
                setCurrentAnimation(getUpAnimation());
                break;
            case DOWN:
                setCurrentAnimation(getDownAnimation());
                break;
            case LEFT:
                setCurrentAnimation(getLeftAnimation());
                break;
            case RIGHT:
                 setCurrentAnimation(getRightAnimation());
                break;
        }
        getCurrentAnimation().play();
        getCurrentAnimation().update();

    }

    /**
     * Drops the item held in the left hand of the player.
     * @return The dropped item, or null if no item is held.
     */
    public Item dropLeftItem() {
        if (inventory.getItems().get(0) != null) {
            Item droppedItem = inventory.getItems().get(0);
            droppedItem.setPosition(this.getCurrentPosition());
            inventory.removeItem(0);
            return droppedItem;
        }
        return new Item("","",EMPTY);
    }

    /**
     * Drops the item held in the right hand of the player.
     * @return The dropped item, or null if no item is held.
     */
    public Item dropRightItem() {
        if (inventory.getItems().get(1) != null) {
            Item droppedItem = inventory.getItems().get(1);
            droppedItem.setPosition(this.getCurrentPosition());
            inventory.removeItem(1);
            return droppedItem;
        }
        return null;
    }

    /**
     * Crafts a new item from existing items in the player's inventory.
     */
    public void craft() {
        int potionCounter = 0;
        for (Integer key : inventory.getItems().keySet()) {
            if (inventory.getItems().get(key).getItemType() == ITEM_POTION)
                potionCounter++;
        }
        if (potionCounter == 2) {
            inventory.setItems(new HashMap<>() {
            });
            inventory.addItem(new Item("Medicine", "Restores 70 hp", ITEM_MEDICINE));
        }
    }

    /**
     * Equips the specified weapon to the player.
     * @param weapon The weapon to equip.
     */
    public void equipWeapon(Weapon weapon) {
        currentWeapon = weapon;
    }

    private void useItem(int pos) {
        if (inventory.getItems().get(pos) != null) {
            switch (inventory.getItems().get(pos).getItemType()) {
                case ITEM_POTION:
                    this.setHp(Math.min(this.getHp() + 20, 100));
                    inventory.getItems().remove(pos);
                    break;
                case ITEM_MEDICINE:
                    this.setHp(Math.min(this.getHp() + 70, 100));
                    inventory.getItems().remove(pos);
                    break;
                case ITEM_AMMO:
                    currentWeapon.addBullets(10);
                    inventory.getItems().remove(pos);
                    break;
            }
        }
    }


    /**
     * Uses the item held in the left hand of the player.
     */
    public void useLeftItem() {
        useItem(0);
    }

    /**
     * Uses the item held in the right hand of the player.
     */
    public void useRightItem() {
        useItem(1);
    }


    /**
     * Checks if the player is near a given NPC.
     * @param npc The NPC to check proximity with.
     * @return True if the player is near the NPC, otherwise false.
     */
    public boolean isNear(NPC npc) {
        int playerX = this.getCurrentPosition().getX();
        int playerY = this.getCurrentPosition().getY();
        int npcX = npc.getCurrentPosition().getX();
        int npcY = npc.getCurrentPosition().getY();

        // Определяем радиус, в котором считаем, что игрок находится рядом с NPC
        int radius = Constants.CHARACTER_SIZE; // Можете настроить значение в зависимости от размеров вашего персонажа и NPC

        // Проверяем, находится ли игрок в радиусе от NPC
        return Math.abs(playerX - npcX) <= radius && Math.abs(playerY - npcY) <= radius;

    }

    /**
     * Uses the current weapon of the player.
     * @return The bullet fired by the weapon, or null if no weapon is equipped.
     */
    public Bullet useCurrentWeapon() {
        if (currentWeapon != null)
           return currentWeapon.fire(this.getCurrentPosition(), this.getDirection().getVector());
        else return null;
    }
    



}
