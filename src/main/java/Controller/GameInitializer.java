package Controller;

import Model.*;
import View.GameFrame;
import View.GameOverPanel;
import View.GamePanel;
import View.MainMenuPanel;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameInitializer {
    private GameController gameController;
    private GameFrame gf;
    private JFrame currentFrame;
    private int lvl;

    public int getLvl() {
        return lvl;
    }

    public GameInitializer(int level) {
        this.lvl = level;
    }

    /**
     * Initializes the game with the specified level.
     */
    public void init() {

        Level level = Level.loadLevelFromJson("/Levels/Test_level" + lvl +".json");

        CollisionManager collisionManager = new CollisionManager(level, level.getPlayer());
        GamePanel gamePanel = new GamePanel(level.getPlayer(), level);
        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        GameOverPanel gameOverPanel = new GameOverPanel();
        if (gameController != null) {
            gameController.setGamePanel(gamePanel);
            gameController.setGameOverPanel(gameOverPanel);
            gameController.setMainMenuPanel(mainMenuPanel);
            gameController.setCollisionManager(collisionManager);
            gameController.setLevel(level);
            gameController.setPlayer(level.getPlayer());
        }
        else {
            gameController = new GameController(level.getPlayer(), level, gamePanel, collisionManager, gameOverPanel,mainMenuPanel, this);
        }
        InputManager inputManager = new InputManager(gameController);
        if (gf != null) {
            gf.switchPanel(mainMenuPanel);
            gf.setIm(inputManager);
        }
        else {
            gf = new GameFrame(mainMenuPanel, inputManager);
        }
        gameController.setGameFrame(gf);
        gameController.setInputManager(inputManager);
        gameOverPanel.setGameController(gameController, this);
        gamePanel.setController(gameController);
        mainMenuPanel.setController(gameController);
    }

    /**
     * Restarts the game with the current level.
     */
    public void restart() {
        Level level = Level.loadLevelFromJson("/Levels/Test_level" + lvl +".json");
        GamePanel gamePanel = new GamePanel(level.getPlayer(), level);
        GameOverPanel gameOverPanel = new GameOverPanel();
        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        CollisionManager collisionManager = new CollisionManager(level, level.getPlayer());
//        gameController = new GameController(level.getPlayer(), level, gamePanel, collisionManager, gameOverPanel,mainMenuPanel, this);
        gameController.setGamePanel(gamePanel);
        gameController.setGameOverPanel(gameOverPanel);
        gameController.setMainMenuPanel(mainMenuPanel);
        gameController.setCollisionManager(collisionManager);
        gameController.setLevel(level);
        gameController.setPlayer(level.getPlayer());
        InputManager inputManager = new InputManager(gameController);

        gf.switchPanel(gamePanel);
        gf.setIm(inputManager);

//        gf = new GameFrame(gamePanel, inputManager);
        gameController.setGameFrame(gf);
        gameController.setInputManager(inputManager);
        gameOverPanel.setGameController(gameController, this);
        gamePanel.setController(gameController);
        mainMenuPanel.setController(gameController);
        gameController.startGame();
    }

    /**
     * Starts the next level.
     */
    public void startNextLevel() {
        lvl++;
        if (lvl >= 3) {
            lvl = 1;
            init();
        }
        else {
            Level level = Level.loadLevelFromJson("/Levels/Test_level" + lvl +".json");
            GamePanel gamePanel = new GamePanel(level.getPlayer(), level);
            GameOverPanel gameOverPanel = new GameOverPanel();
            MainMenuPanel mainMenuPanel = new MainMenuPanel();
            CollisionManager collisionManager = new CollisionManager(level, level.getPlayer());
//        gameController = new GameController(level.getPlayer(), level, gamePanel, collisionManager, gameOverPanel,mainMenuPanel, this);
            gameController.setGamePanel(gamePanel);
            gameController.setGameOverPanel(gameOverPanel);
            gameController.setMainMenuPanel(mainMenuPanel);
            gameController.setCollisionManager(collisionManager);
            gameController.setLevel(level);
            gameController.setPlayer(level.getPlayer());
            InputManager inputManager = new InputManager(gameController);

            gf.switchPanel(gamePanel);
            gf.setIm(inputManager);

//        gf = new GameFrame(gamePanel, inputManager);
            gameController.setGameFrame(gf);
            gameController.setInputManager(inputManager);
            gameOverPanel.setGameController(gameController, this);
            gamePanel.setController(gameController);
            mainMenuPanel.setController(gameController);
            gameController.startGame();
        }
    }

    /**
     * Loads the game state from the save file.
     */
    public void loadGame() {
        String saveFilePath = System.getProperty("user.home") + "/save/save.dat";

        try (FileInputStream fileIn = new FileInputStream(saveFilePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            Inventory inventory = (Inventory) in.readObject();
            Vector2D position = (Vector2D) in.readObject();
            int levelId = in.readInt();

            Level level = Level.loadLevelFromJson("/Levels/Test_level" + levelId +".json");
            level.getPlayer().setInventory(inventory);
            level.getPlayer().setCurrentPosition(position);
            GamePanel gamePanel = new GamePanel(level.getPlayer(), level);
            GameOverPanel gameOverPanel = new GameOverPanel();
            MainMenuPanel mainMenuPanel = new MainMenuPanel();
            CollisionManager collisionManager = new CollisionManager(level, level.getPlayer());
            if (gameController != null) {
                gameController.setGamePanel(gamePanel);
                gameController.setGameOverPanel(gameOverPanel);
                gameController.setMainMenuPanel(mainMenuPanel);
                gameController.setCollisionManager(collisionManager);
                gameController.setLevel(level);
                gameController.setPlayer(level.getPlayer());
            }
            else {
                gameController = new GameController(level.getPlayer(), level, gamePanel, collisionManager, gameOverPanel,mainMenuPanel, this);
            }
            InputManager inputManager = new InputManager(gameController);
            if (gf != null) {
                gf.switchPanel(mainMenuPanel);
                gf.setIm(inputManager);
            }
            else {
                gf = new GameFrame(mainMenuPanel, inputManager);
            }
            gameController.setGameFrame(gf);
            gameController.setInputManager(inputManager);
            gameOverPanel.setGameController(gameController, this);
            gamePanel.setController(gameController);
            mainMenuPanel.setController(gameController);
            gameController.startGame();
            currentFrame = gf.getFrame();
            System.out.println("Состояние игры загружено из ApocalypseMiami/Asset/save/save.dat");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
