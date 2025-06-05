import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LOGIN extends JButton implements ActionListener {

    private JFrame mainframe;

    public LOGIN(JFrame frame) {
        this.mainframe = frame;

        setText("Login");
        setBounds(20, 600, 250, 100);
        setFocusable(false);
        setFont(new Font("DM Sans", Font.PLAIN, 14));
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainframe.setVisible(false);

        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");
        Image resizedImage = originalImage.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JFrame loginFrame = new JFrame("Login");
        loginFrame.setIconImage(originalImage.getImage());
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel centerPanel = new JPanel(null);
        centerPanel.setPreferredSize(new Dimension(1000, 700));
        int panelWidth = 1000;

        JLabel logo = new JLabel(resizedIcon);
        logo.setBounds((panelWidth - 80) / 2, 100, 80, 80);
        centerPanel.add(logo);

        JLabel welcomeText = new JLabel("Welcome back!");
        welcomeText.setFont(new Font("DM Sans", Font.BOLD, 32));
        welcomeText.setForeground(Color.BLACK);
        FontMetrics fmWelcome = centerPanel.getFontMetrics(welcomeText.getFont());
        int welcomeWidth = fmWelcome.stringWidth(welcomeText.getText());
        welcomeText.setBounds((panelWidth - welcomeWidth) / 2, 190, welcomeWidth + 10, 40);
        centerPanel.add(welcomeText);

        JLabel underText = new JLabel("Your Tax Info, Organized and Accessible.");
        underText.setFont(new Font("DM Sans", Font.PLAIN, 18));
        underText.setForeground(Color.GRAY);
        FontMetrics fmUnder = centerPanel.getFontMetrics(underText.getFont());
        int underWidth = fmUnder.stringWidth(underText.getText());
        underText.setBounds((panelWidth - underWidth) / 2, 230, underWidth + 10, 25);
        centerPanel.add(underText);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("DM Sans", Font.PLAIN, 14));
        emailLabel.setBounds(350, 280, 300, 20);
        centerPanel.add(emailLabel);

        JTextField emailField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        emailField.setBounds(350, 305, 300, 40);
        emailField.setFont(new Font("DM Sans", Font.PLAIN, 14));
        emailField.setOpaque(false);
        emailField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        centerPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("DM Sans", Font.PLAIN, 14));
        passwordLabel.setBounds(350, 350, 300, 20);
        centerPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        passwordField.setBounds(350, 375, 300, 40);
        passwordField.setFont(new Font("DM Sans", Font.PLAIN, 14));
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        centerPanel.add(passwordField);
        
        // Custom Icon for the checkbox
        Icon customCheckIcon = new Icon() {
            private final int size = 16;

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                AbstractButton button = (AbstractButton) c;
                ButtonModel model = button.getModel();

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw the box
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(x, y, size, size, 4, 4);

                if (model.isSelected()) {
                    g2.setColor(Color.decode("#0A2353")); // Dark blue background
                    g2.fillRoundRect(x, y, size, size, 4, 4);

                    // Draw the check mark
                    g2.setStroke(new BasicStroke(2));
                    g2.setColor(Color.WHITE);
                    g2.drawLine(x + 4, y + 8, x + 7, y + 11);
                    g2.drawLine(x + 7, y + 11, x + 12, y + 5);
                }

                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };



     // Create the checkbox with custom icon
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setBounds(350, 420, 150, 30);
        rememberMe.setFont(new Font("DM Sans", Font.PLAIN, 14));
        rememberMe.setBackground(null);
        rememberMe.setFocusPainted(false);
        rememberMe.setOpaque(false);
        rememberMe.setIcon(customCheckIcon);
        rememberMe.setSelectedIcon(customCheckIcon);
        centerPanel.add(rememberMe);

        // Login Button 
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(350, 460, 300, 45);
        loginButton.setBackground(new Color(138, 43, 226));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("DM Sans", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        centerPanel.add(loginButton);

        // Add working redirect to TAX_INFO
        loginButton.addActionListener(ev -> {
            // Here you could add actual credential checking if needed
            loginFrame.dispose();  // Close login window
            new TAX_INFO().setVisible(true);  // Redirect to TAX_INFO
        });

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(350, 515, 300, 45);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(138, 43, 226));
        backButton.setFont(new Font("DM Sans", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        centerPanel.add(backButton);

        backButton.addActionListener(ev -> {
            loginFrame.dispose();
            mainframe.setVisible(true);
        });

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapperPanel.add(centerPanel);

        loginFrame.add(wrapperPanel);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                mainframe.setVisible(true);
            }
        });
    }
}
