package View;

import Controller.GameController;
import Controller.GameInitializer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameOverPanel extends JPanel {
    private JButton restartButton;
    private Image backgroundImage;
    private GameController gameController;
    private GameInitializer GI;

    public void setGameController(GameController gameController, GameInitializer GI) {
        this.gameController = gameController;
        this.GI = GI;
    }

    public GameOverPanel() {
        setLayout(new BorderLayout());
        URL imageUrl = getClass().getResource("/restartButton.png");
        ImageIcon restartButtonIcon = new ImageIcon(imageUrl);
        restartButton = new JButton(restartButtonIcon);

        // Устанавливаем размер кнопки
        restartButton.setPreferredSize(new Dimension(1280, 720));
        restartButton.setOpaque(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setFocusPainted(false);
        restartButton.setMargin(new Insets(0, 0, 0, 0));

        // Устанавливаем кнопку в центр панели
        add(restartButton, BorderLayout.NORTH);
        backgroundImage = new ImageIcon(getClass().getResource("/gameOver.png")).getImage();


        restartButton.addActionListener(e -> {
            GI.restart();
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }




}
