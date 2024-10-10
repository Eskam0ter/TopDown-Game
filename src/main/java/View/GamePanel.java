package View;

import Controller.GameController;
import Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Model.Constants.*;

public class GamePanel extends JPanel{
    private int offsetX;
    private int offsetY;

    private int screenWidth;
    private int screenHeight;

    private Image npcImage;
    private Image keyImage;
    private Image ammoImage;
    private Image medicineImage;
    private Image potionImage;
    private Image floorImage;
    private Image wallHorizontalUpImage;
    private Image wallHorizontalDownImage;
    private Image wallVerticalLeftImage;
    private Image wallVerticalRightImage;
    private Image wallCornerLeftImage;
    private Image wallCornerRightImage;
    private Image doorImage;
    private Image doorOpenedImage;
    private Image inventoryImage;

    private NPC npcWithDialog;



    private final Player player;
    private Level level;
    private GameController gameController;

    public GamePanel(Player player, Level level) {
        this.player = player;
        this.level = level;
        this.screenWidth = level.getLevelWidth();
        this.screenHeight = level.getLevelHeight();
        this.npcWithDialog = null;

//        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(new Color(37,19,26));
        this.setDoubleBuffered(true);
        this.loadAssets();
    }


    public void setController(GameController controller) {
        this.gameController = controller;
    }

    /**
     * Loads the assets (images) used in the game.
     */
    private void loadAssets() {
        try {
            InputStream is = getClass().getResourceAsStream("/carpet.png");

            floorImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Walls/wallHorizontalUp.png");
            wallHorizontalUpImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Walls/wallHorizontalDown.png");
            wallHorizontalDownImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Walls/wallVerticalLeft.png");
            wallVerticalLeftImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Walls/wallVerticalRight.png");
            wallVerticalRightImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Walls/wallCornerLeft.png");
            wallCornerLeftImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Walls/wallCornerRight.png");
            wallCornerRightImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/key.png");
            keyImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/potion.png");
            potionImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/medicine.png");
            medicineImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/ammo.png");
            ammoImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/door.png");
            doorImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/inventory.png");
            inventoryImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/npc.png");
            npcImage = ImageIO.read(is);
            is = getClass().getResourceAsStream("/doorOpened.png");
            doorOpenedImage = ImageIO.read(is);

        } catch (IOException e) {
            System.out.println("Failed to load player image.");
            e.printStackTrace();
        }
    }

//    private void drawItems(Graphics g) {
//        for (Item item : level.getItems()) {
//            SurfaceType itemType = item.getItemType();
//            switch (itemType){
//                case ITEM_KEY:
//                    g.drawImage(keyImage, item.getPosition().getX(), item.getPosition().getY(),ITEM_SIZE, ITEM_SIZE, null);
//                    break;
//                case ITEM_AMMO:
//                    g.drawImage(ammoImage, item.getPosition().getX(), item.getPosition().getY(),ITEM_SIZE, ITEM_SIZE, null);
//                    break;
//                case ITEM_POTION:
//                    g.drawImage(potionImage, item.getPosition().getX(), item.getPosition().getY(),ITEM_SIZE, ITEM_SIZE, null);
//                    break;
//                case ITEM_MEDICINE:
//                    g.drawImage(medicineImage, item.getPosition().getX(), item.getPosition().getY(),ITEM_SIZE, ITEM_SIZE, null);
//            }
//        }
//    }


    /**
     * Draws the first layer of the level.
     * @param g The graphics object.
     */
    private void drawFirstLayer(Graphics g) {
        for (Tile tile : level.getFirstLayer()) {
            ObjectType sf = tile.getSurfaceType();
            switch (sf){
                case FLOOR:
                    g.drawImage(floorImage, tile.getPosX(), tile.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
            }
        }
    }

    /**
     * Draws the walls of the level.
     * @param g The graphics object.
     */
    private void drawWalls(Graphics g) {
        for (Tile wall : level.getWalls()) {
            ObjectType wallType = wall.getSurfaceType();
            switch (wallType){
                case WALL_HORIZONTAL_UP:
                    g.drawImage(wallHorizontalUpImage, wall.getPosX(), wall.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
                case WALL_HORIZONTAL_DOWN:
                    g.drawImage(wallHorizontalDownImage, wall.getPosX(), wall.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
                case WALL_VERTICAL_LEFT:
                    g.drawImage(wallVerticalLeftImage, wall.getPosX(), wall.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
                case WALL_VERTICAL_RIGHT:
                    g.drawImage(wallVerticalRightImage, wall.getPosX(), wall.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
                case WALL_CORNER_LEFT:
                    g.drawImage(wallCornerLeftImage, wall.getPosX(), wall.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
                case WALL_CORNER_RIGHT:
                    g.drawImage(wallCornerRightImage, wall.getPosX(), wall.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
            }
        }
    }

    /**
     * Draws the items in the level.
     * @param g The graphics object.
     */
    private void drawitems(Graphics g) {
        for (Item item : level.getItems()) {
            ObjectType itemType = item.getItemType();
            switch (itemType) {
                case ITEM_KEY:
                    g.drawImage(keyImage, item.getPosition().getX() + ORIGINAL_TILE_SIZE, item.getPosition().getY() + ORIGINAL_TILE_SIZE, ITEM_SIZE, ITEM_SIZE, null);
                    break;
                case ITEM_POTION:
                    g.drawImage(potionImage, item.getPosition().getX() + ORIGINAL_TILE_SIZE, item.getPosition().getY() + ORIGINAL_TILE_SIZE, ITEM_SIZE, ITEM_SIZE, null);
                    break;
                case ITEM_AMMO:
                    g.drawImage(ammoImage, item.getPosition().getX() + ORIGINAL_TILE_SIZE, item.getPosition().getY() + ORIGINAL_TILE_SIZE, ITEM_SIZE, ITEM_SIZE, null);
                    break;
                case ITEM_MEDICINE:
                    g.drawImage(medicineImage, item.getPosition().getX() + ORIGINAL_TILE_SIZE, item.getPosition().getY() + ORIGINAL_TILE_SIZE,ITEM_SIZE,ITEM_SIZE, null);
                    break;
            }
        }
    }

    /**
     * Draws the bullets in the level.
     * @param g The graphics object.
     */
    private void drawBullets(Graphics g) {
        if (level.getBullets().size() > 0) {
            for (Bullet bullet : level.getBullets()) {
                g.setColor(Color.BLACK);
                g.fillRect(bullet.getPosition().getX(), bullet.getPosition().getY(), 10, 10);
            }
        }

    }

    /**
     * Draws the second layer of the level, including NPCs and doors.
     * @param g The graphics object.
     */
    private void drawSecondLayer(Graphics g) {
        for (Tile tile : level.getSecondLayer()) {
            ObjectType sf = tile.getSurfaceType();
            switch (sf){
                case NPC:
                    g.drawImage(npcImage, tile.getPosX(), tile.getPosY(), CHARACTER_SIZE, CHARACTER_SIZE, null);
                    break;
                case DOOR:
                case DOOR_END_OF_LVL:
                    g.drawImage(doorImage, tile.getPosX(), tile.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
                case DOOR_OPENED:
                    g.drawImage(doorOpenedImage, tile.getPosX(), tile.getPosY(), TILE_SIZE, TILE_SIZE, null);
                    break;
            }
        }
    }

    /**
     * Sets the NPC with dialog to be displayed.
     * @param npc The NPC with dialog.
     */
    public void setNpcWithDialog(NPC npc) {
        this.npcWithDialog = npc;
    }

    /**
     * Draws the dialog of the specified NPC.
     * @param npc The NPC whose dialog to draw.
     * @param g The graphics object.
     */
    private void showDialog(NPC npc, Graphics g) {
        g.drawString(npc.getCurrentDialog().getText(), npc.getCurrentPosition().getX(), npc.getCurrentPosition().getY() - 10);
    }

    /**
     * Draws the game over screen.
     * @param g The graphics object.
     */
    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);
    }

    /**
     * Draws the heads-up display (HUD) including health bar, ammo count, and inventory.
     * @param g The graphics object.
     */
    private void drawHUD(Graphics g) {
        // Определение координат для отрисовки полоски здоровья с учетом смещения камеры
        int hudX = -offsetX;
        int hudY = -offsetY;

        int itemX = 1020 - offsetX;
        int itemY = 640 - offsetY;
        int distance = 90;


        int hpBarX = 130 - offsetX;
        int hpBarY = 650 - offsetY;

        int ammoBarX = 130 - offsetX;
        int ammoBarY = 683 - offsetY;

        g.setColor(Color.RED);
        int barWidth = player.getHp();
        // Преобразуем количество пуль в строку
        String bulletsLeft = String.valueOf(player.getCurrentWeapon().getBulletsLeft());

// Указываем координаты, например (10, 10)


        g.drawImage(inventoryImage, hudX, hudY, getWidth(), getHeight(), null);
        g.fillRect(hpBarX, hpBarY, barWidth, 15);
        g.setColor(Color.WHITE);
        g.drawString(bulletsLeft, ammoBarX, ammoBarY);
        for (Integer pos : player.getInventory().getItems().keySet()) {
            ObjectType sf = player.getInventory().getItems().get(pos).getItemType();
            switch (sf) {
                case ITEM_KEY:
                    g.drawImage(keyImage,  itemX + pos * distance, itemY, ITEM_SIZE, ITEM_SIZE, null);
                    break;
                case ITEM_POTION:
                    g.drawImage(potionImage,  itemX + pos * distance, itemY, ITEM_SIZE, ITEM_SIZE, null);
                    break;
                case ITEM_AMMO:
                    g.drawImage(ammoImage,  itemX + pos * distance, itemY, ITEM_SIZE, ITEM_SIZE, null);
                    break;
                case ITEM_MEDICINE:
                    g.drawImage(medicineImage,  itemX + pos * distance, itemY, ITEM_SIZE, ITEM_SIZE, null);
                    break;
            }
        }
    }

    /**
     * Sets the camera offset based on the player's position.
     * @param player The player object.
     */
    private void setCameraOffset(Player player) {
        if (player != null) {
            int playerX = player.getCurrentPosition().getX();
            int playerY = player.getCurrentPosition().getY();

            // Определение смещения камеры так, чтобы персонаж оставался в центре панели
            this.offsetX = getWidth() / 2 - playerX;
            this.offsetY = getHeight() / 2 - playerY;
        }
    }


    private void drawZombies(Graphics g) {
        for (Zombie zombie : level.getZombies()) {
            BufferedImage currentFrame = zombie.getCurrentAnimation().getCurrentFrame();
            int x = zombie.getCurrentPosition().getX();
            int y = zombie.getCurrentPosition().getY();

            // Рисуем зомби
            g.drawImage(currentFrame, x, y, CHARACTER_SIZE, CHARACTER_SIZE, null);

            if (zombie.isDamaged())
                drawHealthBar(g, x, y - 10, zombie.getHp(), ZOMBIE_HP);
        }
    }

    private void drawHealthBar(Graphics g, int x, int y, int currentHealth, int maxHealth) {
        // Вычисляем ширину полоски здоровья на основе текущего здоровья
        int healthBarWidth = (int) ((double) currentHealth / maxHealth * 40);

        // Устанавливаем цвет для фона полоски (серый)
        g.setColor(Color.GRAY);
        g.fillRect(x, y, 50, 5);

        // Устанавливаем цвет для полоски здоровья (зеленый)
        g.setColor(Color.GREEN);
        g.fillRect(x, y, healthBarWidth, 5);

        // Устанавливаем цвет для рамки полоски (черный)
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 50, 5);
    }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setCameraOffset(player);
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(offsetX, offsetY);

            drawFirstLayer(g);
            drawWalls(g);
            drawitems(g);
            drawSecondLayer(g);
            drawZombies(g);
            drawHUD(g);
            drawBullets(g);
            if (this.npcWithDialog != null) {
                showDialog(this.npcWithDialog, g);
            }
            int playerX = player.getCurrentPosition().getX(); // Получаем координату X игрока
            int playerY = player.getCurrentPosition().getY(); // Получаем координату Y игрока

            BufferedImage currentFrame = player.getCurrentAnimation().getCurrentFrame();
            g.drawImage(currentFrame, playerX, playerY, CHARACTER_SIZE, CHARACTER_SIZE, null);
            g2d.translate(-offsetX, -offsetY);
        }
}






