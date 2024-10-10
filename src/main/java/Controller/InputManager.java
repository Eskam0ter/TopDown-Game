//    package Controller;
//
//    import Model.GameState;
//    import Model.Vector2D;
//
//    import javax.net.ssl.KeyManager;
//    import java.awt.event.KeyEvent;
//    import java.awt.event.KeyListener;
//
//    import static Model.GameState.*;
//
//    public class InputManager implements KeyListener {
//        private final GameController gameController;
//
//        public InputManager(GameController gameController) {
//            this.gameController = gameController;
//        }
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            int keyCode = e.getKeyCode();
//            switch (keyCode) {
//                case KeyEvent.VK_UP:
//                case KeyEvent.VK_W:
//                    gameController.movePlayer(new Vector2D(0, -1));
//                    break;
//                case KeyEvent.VK_DOWN:
//                case KeyEvent.VK_S:
//                    gameController.movePlayer(new Vector2D(0, 1));
//                    break;
//                case KeyEvent.VK_LEFT:
//                case KeyEvent.VK_A:
//                    gameController.movePlayer(new Vector2D(-1, 0));
//                    break;
//                case KeyEvent.VK_RIGHT:
//                case KeyEvent.VK_D:
//                    gameController.movePlayer(new Vector2D(1, 0));
//                    break;
//                case KeyEvent.VK_SPACE:
//                    gameController.playerFire();
//                    break;
//                case KeyEvent.VK_I:
//                    gameController.pauseGame();
//                    break;
//                case KeyEvent.VK_F:
//                    gameController.playerCraft();
//                    break;
//                case KeyEvent.VK_1:
//                    gameController.useLeftItem();
//                    break;
//                case KeyEvent.VK_2:
//                    gameController.useRightItem();
//                    break;
//                case KeyEvent.VK_ENTER:
//                    gameController.passQuest();
//                    break;
//                case KeyEvent.VK_E:
//                    gameController.dropRightItem();
//                    break;
//                case KeyEvent.VK_Q:
//                    gameController.dropLeftItem();
//                    break;
//                case KeyEvent.VK_Z:
//                    gameController.pickUpLeft();
//                    break;
//                case KeyEvent.VK_X:
//                    gameController.pickUpRight();
//                    break;
//                case KeyEvent.VK_F5:
//                    gameController.saveGame();
//                    break;
//                case KeyEvent.VK_ESCAPE:
//                    gameController.exitGame();
//                    break;
//
//    //                ЕСЛИ БУДУ РЕАЛИЗОВЫВАТЬ МЕХАНИКУ ВЫПАДЕНИЯ ПРЕДМЕТОВ ИЗ ЗОМБИ
//    //            case KeyEvent.VK_E:
//    //                gameController.pickUpItem();
//    //                break;
//            }
//        }
//
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//            int keyCode = e.getKeyCode();
//            switch (keyCode) {
//                case KeyEvent.VK_UP:
//                case KeyEvent.VK_W:
//                    gameController.stopAnimation();
//                    break;
//                case KeyEvent.VK_DOWN:
//                case KeyEvent.VK_S:
//                    gameController.stopAnimation();
//                    break;
//                case KeyEvent.VK_LEFT:
//                case KeyEvent.VK_A:
//                    gameController.stopAnimation();
//                    break;
//                case KeyEvent.VK_RIGHT:
//                case KeyEvent.VK_D:
//                    gameController.stopAnimation();
//                    break;
//            }
//        }
//
//        @Override
//        public void keyTyped(KeyEvent e) {
//            // Не реализовано
//        }
//    }
//
//
package Controller;

import Model.Vector2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;

public class InputManager implements KeyListener {
    private final GameController gameController;
    private final Map<Integer, Boolean> keys = new HashMap<>();;
    private final Timer timer;

    public InputManager(GameController gameController) {
        this.gameController = gameController;

        // Устанавливаем таймер для обновления состояния каждые 50 мс (20 раз в секунду)
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        timer.start();
    }

    private void update() {
        boolean isUpPressed = keys.getOrDefault(KeyEvent.VK_UP, false) || keys.getOrDefault(KeyEvent.VK_W, false);
        boolean isDownPressed = keys.getOrDefault(KeyEvent.VK_DOWN, false) || keys.getOrDefault(KeyEvent.VK_S, false);
        boolean isLeftPressed = keys.getOrDefault(KeyEvent.VK_LEFT, false) || keys.getOrDefault(KeyEvent.VK_A, false);
        boolean isRightPressed = keys.getOrDefault(KeyEvent.VK_RIGHT, false) || keys.getOrDefault(KeyEvent.VK_D, false);

        if (isUpPressed) {
            gameController.movePlayer(new Vector2D(0, -1));
        }
        if (isDownPressed) {
            gameController.movePlayer(new Vector2D(0, 1));
        }
        if (isLeftPressed) {
            gameController.movePlayer(new Vector2D(-1, 0));
        }
        if (isRightPressed) {
            gameController.movePlayer(new Vector2D(1, 0));
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys.put(keyCode, true);

        switch (keyCode) {
            case KeyEvent.VK_I:
                gameController.pauseGame();
                break;
            case KeyEvent.VK_F:
                gameController.playerCraft();
                break;
            case KeyEvent.VK_1:
                gameController.useLeftItem();
                break;
            case KeyEvent.VK_2:
                gameController.useRightItem();
                break;
            case KeyEvent.VK_ENTER:
                gameController.passQuest();
                break;
            case KeyEvent.VK_E:
                gameController.dropRightItem();
                break;
            case KeyEvent.VK_Q:
                gameController.dropLeftItem();
                break;
            case KeyEvent.VK_Z:
                gameController.pickUpLeft();
                break;
            case KeyEvent.VK_X:
                gameController.pickUpRight();
                break;
            case KeyEvent.VK_F5:
                gameController.saveGame();
                break;
            case KeyEvent.VK_ESCAPE:
                gameController.exitGame();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys.put(keyCode, false);

        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                gameController.playerFire();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                gameController.stopAnimation();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Не реализовано
    }
}
