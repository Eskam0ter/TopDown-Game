package Controller;

import Model.*;
import View.*;

import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static Model.Constants.*;
import static Model.GameState.*;

public class GameController implements Runnable {
    private static final Logger logger = Logger.getLogger(GameController.class.getName());
    private Player player;
    private InputManager inputManager;
    private Model.Level level;
    private GamePanel gamePanel;
    private Thread gameThread;
    private CollisionManager collisionManager;
    private GameState gameState;
    private boolean isRunning;
    private MainMenuPanel mainMenuPanel;
    private GameOverPanel gameOverPanel;
    private GameFrame gameFrame;
    private GameInitializer gameInitializer;

    public GameController(Player player, Model.Level level, GamePanel gamePanel, CollisionManager collisionManager, GameOverPanel gameOverPanel,
                          MainMenuPanel mmp, GameInitializer GI) {
        this.player = player;
        this.level = level;
        this.gamePanel = gamePanel;
        this.collisionManager = collisionManager;
        this.gameOverPanel = gameOverPanel;
        this.gameInitializer = GI;
        this.mainMenuPanel = mmp;
    }

    public void setCollisionManager(CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

    public void setMainMenuPanel(MainMenuPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void setGameOverPanel(GameOverPanel gameOverPanel) {
        this.gameOverPanel = gameOverPanel;
    }

    public void setGameInitializer(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLevel(Model.Level level) {
        this.level = level;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }


    /**
     * Starts the game.
     */
    public void startGame() {
        if (!(gameFrame.getActualPanel() instanceof GamePanel)) {
            gameFrame.switchPanel(gamePanel);
            logger.log(Level.INFO, "Switched panel to GamePanel.");
        }
        gameState = PLAYING;
        isRunning = true;
        logger.log(Level.INFO, "Game started. Game state set to PLAYING and isRunning set to true.");
        startGamethread();
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        gameState = PAUSED;
        isRunning = false;
    }

    /**
     * Crafts items for the player.
     */
    public void playerCraft() {
        player.craft();
    }

    /**
     * Processes quest completion when player interacts with NPCs.
     */
    public void passQuest() {
        for (NPC npc : level.getNpcs()) {
            if (player.isNear(npc)) {
                player.getInventory().getItems().forEach((key, value) -> {
                    if (value.getItemType() == ObjectType.ITEM_MEDICINE) {
                        player.getInventory().getItems().remove(key);
                        npc.completeQuest(player);
                    }
                });
            }
        }
    }

    /**
     * Uses the left item in the player's inventory.
     */
    public void useLeftItem() {
        player.useLeftItem();
    }

    /**
     * Uses the right item in the player's inventory.
     */
    public void useRightItem() {
        player.useRightItem();
    }

    /**
     * Starts the game thread.
     */
    public void startGamethread() {
        Thread oldThread = gameThread;
        gameThread = new Thread(this);
        gameThread.start();
        if (oldThread != null && oldThread.isAlive()) {
            try {
                oldThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the player's current animation.
     */
    public void stopAnimation() {
        player.stopCurrentAnimation();
    }


    /**
     * Runs the game loop.
     */
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (isRunning) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                timer += currentTime - lastTime;
                lastTime = currentTime;

                if(delta >= 1) {
                    update();
                    delta--;
                    drawCount++;
                }
                if (timer >= 1000000000) {
//                    System.out.println("FPS:" + drawCount + this.gameThread);
                    drawCount = 0;
                    timer = 0;
                }
        }
    }

    /**
     * Loads the game.
     */
    public void loadGame() {
        gameInitializer.loadGame();
    }

    /**
     * Exits the game.
     */
    public void exitGame() {
        System.exit(0);
    }


    /**
     * Saves the game state.
     */
    public void saveGame() {
        String saveFilePath = System.getProperty("user.home") + "/save/save.dat"; // Path to the save file

        // Create directory if it doesn't exist
        File directory = new File(saveFilePath).getParentFile();
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, "Directory created: " + directory.getAbsolutePath());
            } else {
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "Failed to create directory: " + directory.getAbsolutePath());
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(saveFilePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(player.getInventory());
            out.writeObject(player.getCurrentPosition());
            out.writeInt(gameInitializer.getLvl());

            Logger.getLogger(getClass().getName()).log(Level.INFO, "Game state saved to: " + saveFilePath);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error while saving game state", e);
        }
    }



    /**
     * Updates the game state.
     */
    public void update() {
        Iterator<Bullet> bulletIterator = level.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.move();
            if (collisionManager.checkBulletCollision(bullet)) {
                bulletIterator.remove();
            }
        }
        moveZombiesRandomly();
        showDialog();
        gamePanel.repaint();
        if (isLvlFinished()) {
            isRunning = false;
            gameInitializer.startNextLevel();
        }
        if(this.player.checkDeath()) {
            gameState = GAME_OVER;
            isRunning = false;
            gameFrame.switchPanel(gameOverPanel);
        }

    }

    /**
     * Fires a bullet from the player's current weapon.
     */
    public void playerFire() {
        Bullet bullet = player.useCurrentWeapon();
        if (bullet != null) {
            level.getBullets().add(bullet);
        }
    }

    /**
     * Checks if the level is finished.
     * @return True if the level is finished, otherwise false.
     */
    private boolean isLvlFinished() {
        return level.getEndOfLevel().isLevelFinished();
    }

    /**
     * Moves the player in the specified direction.
     * @param direction The direction to move the player.
     */
    public void movePlayer(Vector2D direction) {
        if (!collisionManager.checkPlayerCollision(direction)) {
            player.move(direction);
        }
    }

    /**
     * Moves zombies randomly.
     */
    private void moveZombiesRandomly() {
        zombiesRandomMovement();
    }



    public void zombiesRandomMovement() {
        Random random = new Random();
        for (Zombie zombie : level.getZombies()) {
            if (zombie.isPlayerVisible(player)) {
                Vector2D playerDirection = zombie.calculatePlayerDirection(player);
                if (!collisionManager.checkZombieCollision(zombie, playerDirection))
                    zombie.moveIfReady(playerDirection);
            } else {
                if (random.nextDouble() < 0.8) {
                    if (!collisionManager.checkZombieCollision(zombie, zombie.getDirection().getVector()))
                        zombie.moveIfReady(zombie.getDirection().getVector()); // Зомби продолжает движение в текущем направлении
                }

                Vector2D[] directions = {new Vector2D(0, -1), new Vector2D(0, 1), new Vector2D(-1, 0), new Vector2D(1, 0)};
                Collections.shuffle(Arrays.asList(directions));
                Vector2D direction = directions[0];
                if (!collisionManager.checkZombieCollision(zombie, direction)) {
                    zombie.moveIfReady(direction);
                }
            }
        }
    }


    /**
     * Shows dialog with NPCs if player is near them.
     */

    private void showDialog(){
        NPC npc = collisionManager.isPlayerNearNpc();
        gamePanel.setNpcWithDialog(npc);
    }


    /**
     * Drops the left item from the player's inventory.
     */
    public void dropLeftItem() {
        Item droppedItem = player.dropLeftItem();
        if (droppedItem.getItemType() != ObjectType.EMPTY) {
            level.getItems().add(droppedItem);
        }
    }



    /**
     * Drops the right item from the player's inventory.
     */
    public void dropRightItem() {
        Item droppedItem = player.dropRightItem();
        if (droppedItem != null) {
            level.getItems().add(droppedItem);
        }
    }


    /**
     * Picks up the item into left cell of players inventory from the ground.
     */
    public void pickUpLeft() {
        Item item = new Item(" ", " ", ObjectType.EMPTY);
        item = collisionManager.checkPlayerCollisionWithItem(player, item);
        if (item.getItemType() != ObjectType.EMPTY){
            if (player.getInventory().getItems().get(0) == null) {
                player.getInventory().addItem(item, 0);
                level.getItems().remove(item);
            }
        }
    }

    /**
     * Picks up the item into right cell of players inventory from the ground.
     */
    public void pickUpRight() {
        Item item = new Item(" ", " ", ObjectType.EMPTY);
        item = collisionManager.checkPlayerCollisionWithItem(player, item);
        if (item.getItemType() != ObjectType.EMPTY){
            if (player.getInventory().getItems().get(1) == null || player.getInventory().getItems().get(0) != null && player.getInventory().getItems().get(0) == null) {
                player.getInventory().addItem(item, 1);
                level.getItems().remove(item);
            }
        }
    }
}
