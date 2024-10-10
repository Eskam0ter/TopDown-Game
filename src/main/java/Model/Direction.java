package Model;

import java.io.Serializable;

/**
 * Enum representing directions: UP, DOWN, LEFT, RIGHT.
 * Each direction has a corresponding 2D vector.
 */
public enum Direction implements Serializable {
    UP(new Vector2D(0, -1)),
    DOWN(new Vector2D(0, 1)),
    LEFT(new Vector2D(-1, 0)),
    RIGHT(new Vector2D(1, 0));

    private final Vector2D vector;

    Direction(Vector2D vector) {
        this.vector = vector;
    }



    /**
     * Retrieves the Direction enum corresponding to the given vector.
     * @param vector The vector to match with a direction.
     * @return The Direction enum corresponding to the given vector.
     */
    public static Direction fromVector(Vector2D vector) {
        for (Direction direction : Direction.values()) {
            if (direction.getVector().equals(vector)) {
                return direction;
            }
        }
        return null; // Handle case when vector doesn't match any direction
    }

    /**
     * Retrieves the 2D vector corresponding to the given direction.
     * @param direction The direction to retrieve the vector for.
     * @return The 2D vector corresponding to the given direction.
     */

    public Vector2D getVector() {
        return vector;
    }
}
