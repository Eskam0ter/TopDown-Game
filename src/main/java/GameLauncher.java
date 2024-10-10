import Controller.CollisionManager;
import Controller.GameController;
import Controller.GameInitializer;
import Controller.InputManager;
import Model.*;
import View.*;

public class GameLauncher {
    public static void main(String[] args) {
        GameInitializer GI = new GameInitializer(1);
        GI.init();

    }
}
