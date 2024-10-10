package Model;

/**
 * Represents a weapon item in the game.
 */
public class Weapon extends Item  {
    private int damage;
    private int bulletsLeft;
    private String imagePath;
    private Vector2D postion;

    public Weapon(String name, String description, String image, Vector2D position, int capacity) {
        super(name, description, ObjectType.ITEM_KEY);
        this.damage = 50;
        this.bulletsLeft = 10;
    }

    /**
     * Fires a bullet from the weapon.
     * @param position The position from which the bullet is fired.
     * @param direction The direction in which the bullet is fired.
     * @return The fired bullet.
     */
    Bullet fire(Vector2D position, Vector2D direction) {
        if (this.bulletsLeft > 0) {
            this.bulletsLeft--;
            return new Bullet(position, direction, this.damage);
        }
        else return null;
    }

    public int getBulletsLeft() {
        return bulletsLeft;
    }

    public void setBulletsLeft(int bulletsLeft) {
        this.bulletsLeft = bulletsLeft;
    }


    /**
     * Adds bullets to the weapon.
     * @param bullets The number of bullets to add.
     */
    public void addBullets(int bullets) {
        this.bulletsLeft = this.bulletsLeft + bullets;
    }
}
