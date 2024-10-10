package Controller;

import Model.*;

import java.awt.*;
import java.util.Iterator;
/**
 * The CollisionManager class is responsible for managing collisions between game entities.
 * It detects collisions between the player, NPCs, zombies, bullets, and environmental objects such as walls and doors.
 */
public class CollisionManager {
    Level level;
    Player player;
    public CollisionManager(Level level, Player player) {
        this.level = level;
        this.player = player;
    }

    /**
     * Calculates the new position of a MoveableCharacter after applying a given direction.
     * @param character The character to move.
     * @param direction The direction of movement.
     * @return The new position of the character after applying the movement.
     */
    private Vector2D getNewPosition(CharacterMoving character, Vector2D direction) {
        Vector2D displacement = direction.multiply(character.getVelocity());
        return character.getCurrentPosition().add(displacement);
    }

    /**
     * Checks if the player is near any NPC in the level.
     * @return The NPC object if player is near one, otherwise null.
     */
    public NPC isPlayerNearNpc() {
        for (NPC npc : level.getNpcs()) {
            if (player.isNear(npc))
                return npc;
        }
        return null;
    }

    /**
     * Checks collision for the player with walls and doors based on the given direction.
     * @param direction The direction of player movement.
     * @return True if collision detected, false otherwise.
     */
    public boolean checkPlayerCollision(Vector2D direction) {
        Vector2D newPlayerPosition = getNewPosition(player, direction);
        Rectangle nextPosPlayerRect = new Rectangle(newPlayerPosition.getX(),
                                            newPlayerPosition.getY(),
                                            Constants.CHARACTER_SIZE,
                                            Constants.CHARACTER_SIZE);
        if (newPlayerPosition.getX() < 0 ||
            newPlayerPosition.getY() < 0 ||
            newPlayerPosition.getX() >= level.getLevelWidth() ||
            newPlayerPosition.getY() >= level.getLevelHeight())
        {
            return true;
        }
        for (Tile wall : level.getWalls()) {
            if (nextPosPlayerRect.intersects(wall.getBoundingBox())) {
                return true;
            }
        }
        return checkPlayerCollisionWithZombie(nextPosPlayerRect) || checkPlayerCollisionWithDoor(nextPosPlayerRect);
    }

    /**
     * Checks collision for the player with zombies and performs necessary actions.
     * @param playerRect The rectangle representing the player's bounding box.
     * @return True if collision detected with a zombie, false otherwise.
     */
    public boolean checkPlayerCollisionWithZombie(Rectangle playerRect) {
        boolean collisionOccurred = false;

        for (Zombie zombie : level.getZombies()) {
            if (playerRect.intersects(zombie.getBoundingBox())) {
                zombie.attack(player);
                collisionOccurred = true;
            }
        }

        return collisionOccurred;
    }

    /**
     * Checks collision for the player with items.
     * @param player The player object.
     * @param itemCheck The item to check collision with.
     * @return The item collided with, or the initial item if no collision.
     */
    public Item checkPlayerCollisionWithItem(Player player, Item itemCheck) {
        Rectangle playerRect = player.getBoundingBox();
        for (Item item : level.getItems()) {
            if (playerRect.intersects(item.getBoundingBox())) {
                return item;
            }
        }
        return itemCheck;
    }

    /**
     * Checks collision for a bullet with zombies, walls, and doors.
     * @param bulletCheck The bullet to check collision with.
     * @return True if collision detected, false otherwise.
     */
    public boolean checkBulletCollision(Bullet bulletCheck) {
        Rectangle bulletRect = bulletCheck.getBoundingBox();
        Iterator<Zombie> zombieIterator = level.getZombies().iterator();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            if (bulletRect.intersects(zombie.getBoundingBox())) {
                zombie.takeDamage(Constants.BULLET_DAMAGE);
                zombie.setDamaged(true);
                if (zombie.checkDeath())
                    zombieIterator.remove();;
                return true;
            }
        }
        Iterator<Tile> wallIterator = level.getWalls().iterator();
        while (wallIterator.hasNext()) {
            Tile wall = wallIterator.next();
            if (bulletRect.intersects(wall.getBoundingBox())) {
                return true;
            }
        }
        for (Tile door : level.getDoors()) {
            if (bulletRect.intersects(door.getBoundingBox())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks collision for the player with a door and handles door interaction.
     * @param playerRect The rectangle representing the player's bounding box.
     * @return True if collision detected with a door, false otherwise.
     */
    public boolean checkPlayerCollisionWithDoor(Rectangle playerRect) {
        for (Tile door : level.getDoors()) {
            if (playerRect.intersects(door.getBoundingBox())) {
                for (Integer key : player.getInventory().getItems().keySet()) {
                    if (player.getInventory().getItems().get(key).getItemType() == ObjectType.ITEM_KEY) {
                        if (door.getSurfaceType() == ObjectType.DOOR_END_OF_LVL) {
                            door.setLevelFinished(true);
                        }
                        player.getInventory().removeItem(key);
                        door.setSurfaceType(ObjectType.DOOR_OPENED);
                        level.getDoors().remove(door);
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    /**
     * Checks collision for a zombie with walls, other zombies, and doors based on the given direction.
     * @param zombie The zombie to check collision for.
     * @param direction The direction of zombie movement.
     * @return True if collision detected, false otherwise.
     */
    public boolean checkZombieCollision(Zombie zombie, Vector2D direction) {
        Vector2D newZombiePosition = getNewPosition(zombie, direction);
        if (newZombiePosition.getX() < 0 ||
                newZombiePosition.getY() < 0 ||
                newZombiePosition.getX() >= level.getLevelWidth() ||
                newZombiePosition.getY() >= level.getLevelHeight())
        {
            return true;
        }
        Rectangle nextPosZombieRect = new Rectangle(newZombiePosition.getX(),
                newZombiePosition.getY(),
                Constants.CHARACTER_SIZE,
                Constants.CHARACTER_SIZE);
        for (Tile wall : level.getWalls()) {
            if (nextPosZombieRect.intersects(wall.getBoundingBox())) {
                return true;
            }
        }
        if (checkZombieCollisionWithZombie(nextPosZombieRect, zombie)) {
            return true;
        }
        return checkZombieCollisionWithPlayer(nextPosZombieRect, zombie) || checkZombieCollisionWithDoor(nextPosZombieRect);
    }

    /**
     * Checks collision for a zombie with doors in the level.
     * @param zombieRect The rectangle representing the zombie's bounding box.
     * @return True if collision detected with a door, false otherwise.
     */
    public boolean checkZombieCollisionWithDoor(Rectangle zombieRect) {
        for (Tile door : level.getDoors()) {
            if (zombieRect.intersects(door.getBoundingBox()))
                return true;
            }
        return false;
    }

    /**
     * Checks collision for a zombie with other zombies in the level.
     * @param zombieRect The rectangle representing the zombie's bounding box.
     * @param ourZombie The zombie to check collision for.
     * @return True if collision detected with another zombie, false otherwise.
     */
    public boolean checkZombieCollisionWithZombie(Rectangle zombieRect, Zombie ourZombie) {
        for (Zombie zombie : level.getZombies()) {
            if (zombieRect.intersects(zombie.getBoundingBox()) && !ourZombie.equals(zombie)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks collision for a zombie with the player and performs attack if necessary.
     * @param zombieRect The rectangle representing the zombie's bounding box.
     * @param ourZombie The zombie to check collision for.
     * @return True if collision detected with the player, false otherwise.
     */
    public boolean checkZombieCollisionWithPlayer(Rectangle zombieRect, Zombie ourZombie) {
        if (ourZombie.getBoundingBox().intersects(player.getBoundingBox())) {
            ourZombie.attack(player);
            return true;
        }
        return false;
    }
}
