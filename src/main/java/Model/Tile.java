package Model;

import java.awt.*;

/**
 * Represents a tile in the game.
 */
public class Tile implements RectangleConvertible{
    private ObjectType objectType;
    private int posX;
    private int posY;
    private boolean levelFinished;

    public Tile(ObjectType objectType, int posX, int posY) {
        this.objectType = objectType;
        this.posX = posX;
        this.posY = posY;
        this.levelFinished = false;
    }

    public boolean isLevelFinished() {
        return levelFinished;
    }

    public void setLevelFinished(boolean levelFinished) {
        this.levelFinished = levelFinished;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public ObjectType getSurfaceType() {
        return objectType;
    }

    public void setSurfaceType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(posX, posY, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }
}
