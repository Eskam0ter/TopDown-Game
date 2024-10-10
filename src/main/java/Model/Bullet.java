package Model;

import java.awt.*;
import java.io.Serializable;

/**
 * Represents a bullet fired by a weapon.
 */
public class Bullet implements RectangleConvertible, Serializable {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D direction;
    private int damage;

    /**
     * Constructs a Bullet object with specified position, direction, and damage.
     *
     * @param position  The initial position of the bullet.
     * @param direction The direction in which the bullet is traveling.
     * @param damage    The damage inflicted by the bullet.
     */
    public Bullet(Vector2D position,Vector2D direction, int damage) {
        this.position = position;
        this.velocity = Constants.BULLET_VELOCITY;
        this.direction = direction;
        this.damage = damage;
        move();
    }

    /**
     * Moves the bullet based on its velocity and direction.
     */
    public void move() {
        this.position = this.position.add(this.direction.multiply(this.velocity));
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getDirection() {
        return direction;
    }

    /**
     * Gets the bounding box of the bullet.
     *
     * @return The bounding box of the bullet as a Rectangle.
     */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(position.getX(), position.getY(), 10, 10);
    }
}
