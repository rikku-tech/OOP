import javax.swing.*;
import java.awt.*;

class MAIN {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        // Load original image
        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");

        // Resize to 200x200 pixels
        Image resizedImage = originalImage.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Create a panel to hold your UI components with absolute positioning
        JPanel centerPanel = new JPanel(null);
        centerPanel.setPreferredSize(new Dimension(1000, 700));

        // Add your components with absolute positions inside centerPanel
        JLabel logo = new JLabel(resizedIcon);
        logo.setBounds(400, 140, 200, 200);
        centerPanel.add(logo);

        LOGIN btn1 = new LOGIN(frame);
        REGISTER btn2 = new REGISTER(frame);

        btn1.setBounds(505, 505, 150, 50);
        btn2.setBounds(350, 505, 150, 50);

        btn1.setBackground(new Color(138, 43, 226));
        btn1.setForeground(Color.WHITE);
        btn1.setFocusPainted(false);
        btn1.setBorderPainted(false);

        btn2.setBackground(Color.WHITE);
        btn2.setForeground(Color.BLACK);
        btn2.setFocusPainted(false);
        btn2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        centerPanel.add(btn1);
        centerPanel.add(btn2);

        JLabel welcomeText = new JLabel("Welcome to ");
        welcomeText.setFont(new Font("DM Sans", Font.BOLD, 55));
        welcomeText.setForeground(Color.BLACK);
        welcomeText.setBounds(265, 320, 350, 80);

        JLabel blocText = new JLabel("BLOC");
        blocText.setFont(new Font("DM Sans", Font.BOLD, 55));
        blocText.setForeground(new Color(138, 43, 226, 200));
        blocText.setBounds(590, 320, 350, 80);

        JLabel underText = new JLabel("Your Tax Info, Organized and Accessible.");
        underText.setFont(new Font("DM Sans", Font.BOLD, 20));
        underText.setForeground(Color.gray);
        underText.setBounds(310, 380, 750, 80);

        centerPanel.add(welcomeText);
        centerPanel.add(blocText);
        centerPanel.add(underText);

        // Create wrapper panel
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapperPanel.add(centerPanel);

        // Add wrapperPanel to frame
        frame.add(wrapperPanel);

        // Frame settings
        frame.setTitle("Bloc");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setIconImage(originalImage.getImage());
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
