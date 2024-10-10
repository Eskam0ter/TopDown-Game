package Model;

/**
 * Contains constant values used throughout the application.
 */
public final class Constants {
    public static final int PLAYER_HP = 100; // Initial hit points of the player
    public static final int ZOMBIE_HP = 100; // Initial hit points of zombies
    public static final int ORIGINAL_TILE_SIZE = 16; // Original size of a tile
    public static final int CHARACTER_SCALE = 2; // Scale factor for character size
    public static final int CHARACTER_SIZE = ORIGINAL_TILE_SIZE * CHARACTER_SCALE; // Size of the character
    public static final int ITEM_SIZE = CHARACTER_SIZE; // Size of an item
    public static final int MAP_SCALE = 4; // Scale factor for map size
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * MAP_SCALE; // Size of a tile after scaling
    public static final int ZOMBIE_DAMAGE = 2; // Damage dealt by zombies
    public static final int BULLET_DAMAGE = 25; // Damage dealt by bullets
    public static final Vector2D ZOMBIE_VELOCITY = new Vector2D(8, 8); // Velocity of zombies
    public static final Vector2D PLAYER_VELOCITY = new Vector2D(10, 10); // Velocity of the player
    public static final Vector2D BULLET_VELOCITY = new Vector2D(15, 15); // Velocity of bullets
    public static final int ANIMATION_FRAME_DELAY = 55; // Delay between animation frames
    public static final String PLAYER_ANIMATIONS_PATH = "/Player"; // Path to player animations
    public static final int INVENTORY_CAPACITY = 2; // Capacity of the player's inventory
    public static final int ZOMBIE_MOVEMENT_DELAY = 600; // Delay between zombie movements
    public static final Player DEFAULT_PLAYER = new Player("", PLAYER_HP,
            new Vector2D(0, 0),
            PLAYER_VELOCITY,
            PLAYER_ANIMATIONS_PATH,
            new Inventory(INVENTORY_CAPACITY),
            new Weapon("", "", "",
                    new Vector2D(0, 0),
                    10)); // Default player instance
    public static final int FPS = 60; // Frames per second
    public static final Item NPC_QUEST_ITEM = new Item("Key", "The survivor gave it to you", ObjectType.ITEM_KEY); // Quest item for NPCs
    public static final Quest NPC_QUEST = new Quest("Hi! Would you be so polite and BRING ME THIS FREAKING MEDICINE?"); // Quest for NPCs
    private Constants() {} // Private constructor to prevent instantiation
}
