package View;

import Controller.InputManager;

import javax.swing.*;
import java.awt.*;

public class GameFrame {

    private JPanel actualPanel;
    private JFrame frame;
    private InputManager im;

    public GameFrame(JPanel panel, InputManager inputManager) {
        this.frame = new JFrame();
        frame.setResizable(false);
        this.im = inputManager;
        frame.addKeyListener(this.im);
        frame.setTitle("Apocalypse Miami");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLayout(new BorderLayout()); // Устанавливаем BorderLayout
        actualPanel = panel;
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocus();
    }

    /**
     * Switches the current panel being displayed in the frame.
     * @param newPanel The new panel to switch to.
     */
    public void switchPanel(JPanel newPanel) {
        frame.getContentPane().removeAll();
        actualPanel = newPanel;
        frame.getContentPane().add(newPanel, BorderLayout.CENTER);
        frame.requestFocus();// Добавляем новую панель в центр контейнера
        frame.revalidate(); // Пересчитываем компоновку компонентов
        frame.repaint();
    }

    public JPanel getActualPanel() {
        return actualPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setIm(InputManager im) {
        this.im = im;
    }
}
