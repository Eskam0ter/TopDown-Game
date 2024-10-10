package Model;

import java.io.Serializable;

/**
 * Represents a 2D vector with integer coordinates.
 */
public class Vector2D implements Serializable {
    private int x;
    private int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds another vector to this vector.
     * @param vector The vector to be added.
     * @return The resulting vector after addition.
     */
    public Vector2D add(Vector2D vector) {
        return new Vector2D(x + vector.getX(), y + vector.getY());
    }

    /**
     * Subtracts another vector from this vector.
     * @param vector The vector to be subtracted.
     * @return The resulting vector after subtraction.
     */
    public Vector2D subtract(Vector2D vector) {
        return new Vector2D(x - vector.getX(), y - vector.getY());
    }

    /**
     * Multiplies this vector by another vector component-wise.
     * @param vector The vector to be multiplied.
     * @return The resulting vector after multiplication.
     */
    public Vector2D multiply(Vector2D vector) {
        return new Vector2D(x * vector.getX(), y * vector.getY());
    }

    /**
     * Checks if this vector is less than another vector.
     * @param other The other vector to compare with.
     * @return True if this vector is less than the other vector, otherwise false.
     */
    public boolean lessThan(Vector2D other) {
        return (this.x < other.x && this.y < other.y);
    }

    /**
     * Checks if this vector is less than or equal to another vector.
     * @param other The other vector to compare with.
     * @return True if this vector is less than or equal to the other vector, otherwise false.
     */
    public boolean lessThanOrEqual(Vector2D other) {
        return (this.x <= other.x && this.y <= other.y);
    }

    /**
     * Checks if this vector is greater than another vector.
     * @param other The other vector to compare with.
     * @return True if this vector is greater than the other vector, otherwise false.
     */
    public boolean greaterThan(Vector2D other) {
        return (this.x > other.x && this.y > other.y);
    }

    /**
     * Checks if this vector is greater than or equal to another vector.
     * @param other The other vector to compare with.
     * @return True if this vector is greater than or equal to the other vector, otherwise false.
     */
    public boolean greaterThanOrEqual(Vector2D other) {
        return (this.x >= other.x && this.y >= other.y);
    }

    /**
     * Checks if this vector is equal to another vector.
     * @param other The other vector to compare with.
     * @return True if this vector is equal to the other vector, otherwise false.
     */
    public boolean equals(Vector2D other) {
        return (this.x == other.x && this.y == other.y);
    }


    /**
     * Normalizes this vector.
     * @return The normalized vector.
     */
    public Vector2D normalize() {
        int gcd = gcd(Math.abs(x), Math.abs(y));
        if (gcd != 0) {
            x /= gcd;
            y /= gcd;
            return new Vector2D(x, y);
        }
        return new Vector2D(0, 0);
    }


    /**
     * Finds the greatest common divisor (GCD) of two numbers.
     * @param a The first number.
     * @param b The second number.
     * @return The greatest common divisor of the two numbers.
     */
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
