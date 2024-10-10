package Model;

import java.util.List;

/**
 * Represents the data structure for storing level information from a JSON file.
 */
public class LevelData {
    private int width;
    private int height;
    private List<List<Integer>> tiles;

    public LevelData() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<List<Integer>> getTiles() {
        return tiles;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTiles(List<List<Integer>> tiles) {
        this.tiles = tiles;
    }
}
