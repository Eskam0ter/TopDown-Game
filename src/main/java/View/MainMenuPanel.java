package View;

import Controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.net.URL;

/**
 * Represents the main menu panel of the game.
 * This panel contains buttons for starting a new game, loading a saved game, and exiting the game.
 */
public class MainMenuPanel extends JPanel {
    private JButton startButton;
    private JButton loadButton;
    private JButton exitButton;
    private GameController gameController;

    public MainMenuPanel() {
        // Загрузка изображений для кнопок
        URL imageUrl = getClass().getResource("/MainMenu/new_game_unselected.png");
        ImageIcon newGameUnselected = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/MainMenu/new_game_selected.png");
        ImageIcon newGameSelected = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/MainMenu/load_unselected.png");
        ImageIcon loadUnselected = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/MainMenu/load_selected.png");
        ImageIcon loadSelected = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/MainMenu/exit_unselected.png");
        ImageIcon exitUnselected = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/MainMenu/exit_selected.png");
        ImageIcon exitSelected = new ImageIcon(imageUrl);

        setBackground(Color.BLACK);

        // Создание кнопок с изображениями в качестве иконок
        startButton = new JButton(newGameUnselected);
        loadButton = new JButton(loadUnselected);
        exitButton = new JButton(exitUnselected);

        // Установка непрозрачности и отступов для кнопок
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setMargin(new Insets(0, 0, 0, 0));

        loadButton.setOpaque(false);
        loadButton.setContentAreaFilled(false);
        loadButton.setBorderPainted(false);
        loadButton.setFocusPainted(false);
        loadButton.setMargin(new Insets(0, 0, 0, 0));

        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setMargin(new Insets(0, 0, 0, 0));

        // Создание слушателей событий для кнопки "Начать игру"
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.startGame();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setIcon(newGameSelected);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setIcon(newGameUnselected);
            }
        });

        loadButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.loadGame();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                loadButton.setIcon(loadSelected);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadButton.setIcon(loadUnselected);
            }
        });

        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.exitGame();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(exitSelected);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(exitUnselected);
            }
        });

        // Создание слушателей событий для кнопок "Настройки" и "Выход"
        // ...

        // Установка менеджера компоновки GridBagLayout и добавление кнопок
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Отступы между кнопками
        add(startButton, gbc);

        gbc.gridy = 1;
        add(loadButton, gbc);

        gbc.gridy = 2;
        add(exitButton, gbc);
    }

    public void setController(GameController gameController) {
        this.gameController = gameController;
    }
}
