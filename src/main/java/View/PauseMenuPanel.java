//package View;
//
//import Controller.GameController;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
//public class PauseMenuPanel extends JPanel implements MouseListener {
//    private JButton resumeButton;
//    private JButton loadButton;
//    private JButton exitButton;
//    private GameController gameController;
//    private Image backgroundImage;
//
//    public PauseMenuPanel() {
//        // Загрузка изображений для кнопок
//        ImageIcon startIcon = new ImageIcon("ApocalypseMiami/src/main/java/Asset/MainMenu/new_game_unselected1.png");
//        ImageIcon loadIcon = new ImageIcon("ApocalypseMiami/src/main/java/Asset/MainMenu/load_unselect1ed.png");
//        ImageIcon exitIcon = new ImageIcon("ApocalypseMiami/src/main/java/Asset/MainMenu/exit_unselec1ted.png");
//        backgroundImage = new ImageIcon("ApocalypseMiami/src/main/java/Asset/MainMenu/pause_background.png").getImage();
//
//        setPreferredSize(new Dimension(300, 400));
//    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(backgroundImage, 0, 0, null);
//    }
//
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//}
