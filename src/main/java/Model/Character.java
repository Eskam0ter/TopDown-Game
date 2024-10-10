package Model;

import java.awt.*;
import java.io.Serializable;

/**
 * Represents an abstract character in the game.
 */
public abstract class Character implements RectangleConvertible, Serializable {
    private String name;
    private int hp;
    private Vector2D currentPosition;

    /**
     * Constructs a Character object with specified hit points and position.
     *
     * @param hp               The hit points of the character.
     * @param currentPosition The current position of the character.
     */
    public Character(int hp, Vector2D currentPosition) {
        this.hp = hp;
        this.currentPosition = currentPosition;
    }

    /**
     * Reduces the hit points of the character by the specified amount of damage.
     *
     * @param damage The amount of damage to be inflicted on the character.
     */
    public void takeDamage(int damage) {
        if (this.hp - damage <= 0) {
            this.hp = 0;
        }
        else {
            this.hp -= damage;
        }
        System.out.println(this.hp);
    }

    /**
     * Checks if the character has died (hit points reach zero or below).
     *
     * @return true if the character is dead, false otherwise.
     */
    public boolean checkDeath() {
        return this.hp <= 0;
    }

    public int getHp() {
        return hp;
    }

    public Vector2D getCurrentPosition() {
        return currentPosition;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setCurrentPosition(Vector2D currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(currentPosition.getX(), currentPosition.getY(), Constants.CHARACTER_SIZE, Constants.CHARACTER_SIZE);
    }
}
