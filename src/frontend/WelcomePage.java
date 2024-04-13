package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WelcomePage {
    public WelcomePage() {
        JFrame frame = new JFrame("Welcome Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(400,400);

        JPanel panel = new JPanel(new GridLayout(1, 2));

        try {
            Image backgroundImage = ImageIO.read(getClass().getResource("background2.jpg"));
            int newWidth = (int) (backgroundImage.getWidth(null) * 0.5);
            int newHeight = (int) (backgroundImage.getHeight(null) * 0.5);
            Image resizedImage = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon backgroundImageIcon = new ImageIcon(resizedImage);

            JLabel backgroundLabel = new JLabel(backgroundImageIcon);
            panel.add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel secondColumnPanel = new JPanel();
        secondColumnPanel.setLayout(new BoxLayout(secondColumnPanel, BoxLayout.Y_AXIS));

        try {
            Image logoImage = ImageIO.read(getClass().getResource("logo.png"));
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            secondColumnPanel.add(logoLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel welcomeLabel = new JLabel("<html><font color='#000000'>Welcome to<br>Everest Engineering College</font></html>");
        welcomeLabel.setFont(new Font("Poppins", Font.PLAIN, 66));

        secondColumnPanel.add(welcomeLabel);

        panel.add(secondColumnPanel);

        frame.add(panel);
        frame.setVisible(true);
    }
}
