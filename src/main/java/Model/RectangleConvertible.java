package Model;

import java.awt.*;

/**
 * Represents an object that can be converted into a bounding box.
 */
public interface RectangleConvertible {
    /**
     * Retrieves the bounding box of the object.
     * @return The bounding box of the object.
     */
    Rectangle getBoundingBox();
}
