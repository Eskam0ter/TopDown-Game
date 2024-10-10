package Model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Model.Constants.*;
import static Model.ObjectType.*;

/**
 * Represents a level in the game.
 */
public class Level {
    private List<Tile> firstLayer;
    private List<Tile> secondLayer;
    private List<Item> items;
    private List<NPC> npcs;
    private List<Zombie> zombies;
    private List<Tile> walls;
    private List<Tile> doors;
    private final Player player;
    private int levelWidth;
    private int levelHeight;
    private Tile endOfLevel;
    private List<Bullet> bullets;

    /**
     * Constructs a level with the specified parameters.
     * @param firstLayer The tiles of the first layer.
     * @param secondLayer The tiles of the second layer.
     * @param items The items in the level.
     * @param npcs The NPCs in the level.
     * @param zombies The zombies in the level.
     * @param walls The wall tiles in the level.
     * @param doors The door tiles in the level.
     * @param levelWidth The width of the level.
     * @param levelHeight The height of the level.
     * @param player The player object.
     * @param endOfLvl The tile marking the end of the level.
     */
    public Level(List<Tile> firstLayer, List<Tile> secondLayer, List<Item> items, List<Model.NPC> npcs, List<Zombie> zombies, List<Tile> walls, List<Tile> doors, int levelWidth, int levelHeight, Player player, Tile endOfLvl) {
        this.firstLayer = firstLayer;
        this.secondLayer = secondLayer;
        this.items = items;
        this.npcs = npcs;
        this.zombies = zombies;
        this.walls = walls;
        this.doors = doors;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.player = player;
        this.endOfLevel = endOfLvl;
        this.bullets = new ArrayList<>();
    }

    public List<Bullet> getBullets() {
        return bullets;
    }


    public Tile getEndOfLevel() {
        return endOfLevel;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public List<Model.NPC> getNpcs() {
        return npcs;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Tile> getDoors() {
        return doors;
    }

    public List<Tile> getWalls() {
        return walls;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public List<Tile> getSecondLayer() {
        return secondLayer;
    }

    public List<Tile> getFirstLayer() {
        return firstLayer;
    }

    public Player getPlayer() {
        return player;
    }


    /**
     * Loads a level from a JSON file.
     * @param filePath The path to the JSON file.
     * @return The loaded level.
     */
    public static Level loadLevelFromJson(String filePath) {
        InputStream is = Level.class.getResourceAsStream(filePath);
        try (InputStreamReader reader = new InputStreamReader(is)) {
            Gson gson = new Gson();
            LevelData levelData = gson.fromJson(reader, LevelData.class);

            int width = levelData.getWidth();
            int height = levelData.getHeight();

            int widthInPixels = width * TILE_SIZE;
            int heightInPixels = height * TILE_SIZE;

            List<Tile> firstLayer = new ArrayList<>();
            List<Tile> secondLayer = new ArrayList<>();
            List<Item> items = new LinkedList<>();
            List<NPC> npcs = new ArrayList<>();
            List<Zombie> zombies = new LinkedList<>();
            List<Tile> walls = new ArrayList<>();
            List<Tile> doors = new ArrayList<>();
            Player player = DEFAULT_PLAYER;
            Tile endOfLvl = new Tile(EMPTY, 0, 0);

            List<List<Integer>> tilesData = levelData.getTiles();
            Tile[][] tiles = new Tile[height][width];

            for (int y = 0; y < height; y++) {
                List<Integer> row = tilesData.get(y);
                for (int x = 0; x < width; x++) {
                    int tileType = row.get(x);
                    Tile tile = createTile(tileType, x * TILE_SIZE, y * TILE_SIZE);
                    addTileToRightList(tile, firstLayer, secondLayer, items, npcs, zombies, walls, doors);
                    if (tile.getSurfaceType() == PLAYER) {
                        player = Player.createAbama(tile.getPosX(), tile.getPosY());
                    }
                    if (tile.getSurfaceType() == DOOR_END_OF_LVL) {
                        endOfLvl = tile;
                    }
                }
            }

            return new Level(firstLayer, secondLayer, items, npcs, zombies, walls, doors, widthInPixels, heightInPixels, player, endOfLvl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a tile based on the provided type and position.
     *
     * @param tileType The type of the tile.
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The created tile.
     */
    private static Tile createTile(int tileType, int x, int y) {
        ObjectType objectType;
        switch (tileType) {
            case 0:
                objectType = FLOOR;
                break;
            case 1:
                objectType = WALL_HORIZONTAL_UP;
                break;
            case 2:
                objectType = WALL_VERTICAL_LEFT;
                break;
            case 3:
                objectType = WALL_VERTICAL_RIGHT;
                break;
            case 4:
                objectType = WALL_CORNER_LEFT;
                break;
            case 5:
                objectType = WALL_CORNER_RIGHT;
                break;
            case 6:
                objectType = WALL_HORIZONTAL_DOWN;
                break;
            case 7:
                objectType = ZOMBIE;
                break;
            case 8:
                objectType = ITEM_KEY;
                break;
            case 9:
                objectType = ITEM_POTION;
                break;
            case 10:
                objectType = ITEM_AMMO;
                break;
            case 11:
                objectType = ITEM_MEDICINE;
                break;
            case 12:
                objectType = NPC;
                break;
            case 13:
                objectType = DOOR;
                break;
            case 15:
                objectType = PLAYER;
                break;
            case 16:
                objectType = DOOR_END_OF_LVL;
                break;
            default:
                objectType = EMPTY;
                break;
        }
        return new Tile(objectType, x, y);
        }

    /**
     * Adds a tile to the appropriate lists based on its surface type.
     *
     * @param tile The tile to add.
     * @param firstLayer The list of tiles for the first layer.
     * @param secondLayer The list of tiles for the second layer.
     * @param items The list of items in the level.
     * @param npcs The list of NPCs in the level.
     * @param zombies The list of zombies in the level.
     * @param walls The list of wall tiles in the level.
     * @param doors The list of door tiles in the level.
     */
    private static void addTileToRightList(Tile tile, List<Tile> firstLayer,List<Tile> secondLayer,List<Item> items,List<NPC> npcs,List<Zombie> zombies,List<Tile> walls,List<Tile> doors){
        ObjectType sf = tile.getSurfaceType();
        Tile floor = new Tile(FLOOR,tile.getPosX(),tile.getPosY());
        switch (sf){
            case FLOOR:
                firstLayer.add(tile);
                break;
            case WALL_HORIZONTAL_UP:
            case WALL_VERTICAL_RIGHT:
            case WALL_VERTICAL_LEFT:
            case WALL_CORNER_LEFT:
            case WALL_CORNER_RIGHT:
            case WALL_HORIZONTAL_DOWN:
                walls.add(tile);
                break;
            case ZOMBIE:
                firstLayer.add(floor);
                secondLayer.add(tile);
                Zombie zombie = new Zombie(ZOMBIE_HP,
                        new Vector2D(tile.getPosX(),tile.getPosY())
                        ,ZOMBIE_VELOCITY,
                        ZOMBIE_DAMAGE,
                        "/Zombie");
                zombies.add(zombie);
                break;
            case ITEM_KEY:
                firstLayer.add(floor);
                secondLayer.add(tile);
                Item key = new Item("Key",
                        " ",
                        new Vector2D(tile.getPosX(), tile.getPosY()),
                        tile.getSurfaceType());
                items.add(key);
                break;
            case ITEM_POTION:
                firstLayer.add(floor);
                secondLayer.add(tile);
                Item potion = new Item("Potion",
                        "Restores 20 HP",
                        new Vector2D(tile.getPosX(), tile.getPosY()),
                        tile.getSurfaceType());
                items.add(potion);
                break;
            case ITEM_AMMO:
                firstLayer.add(floor);
                secondLayer.add(tile);
                Item ammo = new Item("Ammo",
                        "Contains 10 bullets",
                        new Vector2D(tile.getPosX(), tile.getPosY()),
                                tile.getSurfaceType());
                items.add(ammo);
                break;
            case ITEM_MEDICINE:
                firstLayer.add(floor);
                secondLayer.add(tile);
                Item mdeicine = new Item("Medicine",
                        "Resores 70HP",
                        new Vector2D(tile.getPosX(), tile.getPosY()),
                        tile.getSurfaceType());
                items.add(mdeicine);
                break;
            case NPC:
                firstLayer.add(floor);
                secondLayer.add(tile);
                NPC npc = new NPC("Survivor",
                        NPC_QUEST_ITEM,
                        new Vector2D(tile.getPosX(), tile.getPosY()),
                        NPC_QUEST);
                npcs.add(npc);
                break;
            case DOOR:
                firstLayer.add(floor);
                secondLayer.add(tile);
                doors.add(tile);
                break;
            case DOOR_END_OF_LVL:
                firstLayer.add(floor);
                secondLayer.add(tile);
                doors.add(tile);
            default:
                firstLayer.add(floor);
                break;
        }
    }
}
