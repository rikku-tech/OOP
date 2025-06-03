import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class OTHER_INFO extends JFrame {
    private CircularImagePanel photoPanel;
    private boolean isEditing = false;

    public OTHER_INFO() {
        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");

        setTitle("Other Information");
        setIconImage(originalImage.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel with logo and logout button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#F9F6F2"));
        topPanel.setPreferredSize(new Dimension(getWidth(), 50));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        logoPanel.setBackground(Color.decode("#F9F6F2"));

        ImageIcon rawIcon = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");
        Image scaledIcon = rawIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon headerIcon = new ImageIcon(scaledIcon);

        JLabel headerIconLabel = new JLabel(headerIcon);
        logoPanel.add(headerIconLabel);

        JLabel blocLabel = new JLabel("BLOC");
        blocLabel.setFont(new Font("DM Sans", Font.BOLD, 20));
        blocLabel.setForeground(Color.decode("#122D70"));
        logoPanel.add(blocLabel);

        // Logout button
        JButton logoutButton = new JButton("Log out");
        logoutButton.setBackground(new Color(138, 43, 226));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutButton.setFont(new Font("Inter", Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setPreferredSize(new Dimension(110, 30));

        // Add logout functionality
        logoutButton.addActionListener(e -> {
            try {
                // Dispose the OTHER_INFO window
                OTHER_INFO.this.dispose();
                
                // Ensure the new MAIN frame is created on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    try {
                        // Call the MAIN page creation
                        MAIN.main(new String[]{});
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, 
                            "Failed to redirect to the MAIN page.",
                            "Logout Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "An unexpected error occurred during logout.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        topPanel.add(logoPanel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(230, getHeight()));
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JPanel sidebarContentWrapper = new JPanel();
        sidebarContentWrapper.setLayout(new BoxLayout(sidebarContentWrapper, BoxLayout.Y_AXIS));
        sidebarContentWrapper.setBackground(Color.WHITE);
        sidebarContentWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Photo panel
        JPanel photoWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        photoWrapper.setBackground(Color.WHITE);
        photoPanel = new CircularImagePanel(100);
        photoWrapper.add(photoPanel);

        sidebarContentWrapper.add(photoWrapper);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 10)));

        // Taxpayer Name
        JLabel taxpayerName = new JLabel("Taxpayer Name");
        taxpayerName.setFont(new Font("Inter", Font.BOLD, 14));
        taxpayerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarContentWrapper.add(taxpayerName);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        // Sidebar fields
        sidebarContentWrapper.add(createLabeledFieldSmall("Taxpayer TIN", false));
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarContentWrapper.add(createLabeledFieldSmall("Registering Office", false));
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarContentWrapper.add(createLabeledFieldSmall("RDO Code", false));
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        // Navigation buttons with functionality
        JButton taxpayerInfoBtn = createSidebarButton("Taxpayer Information", false);
        JButton otherInfoBtn = createSidebarButton("Other Information", true);
        JButton businessInfoBtn = createSidebarButton("Business Information", false);

        // Add navigation functionality
        taxpayerInfoBtn.addActionListener(e -> {
            dispose(); // Close current window
            SwingUtilities.invokeLater(() -> {
                try {
                    TAX_INFO taxInfo = new TAX_INFO();
                    taxInfo.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to open Taxpayer Information page.",
                        "Navigation Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        otherInfoBtn.addActionListener(e -> {
            // Already on OTHER_INFO page, no action needed
        });

        businessInfoBtn.addActionListener(e -> {
            dispose(); // Close current window
            SwingUtilities.invokeLater(() -> {
                try {
                    BUS_INFO businessInfo = new BUS_INFO();
                    businessInfo.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to open Business Information page.",
                        "Navigation Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        sidebarContentWrapper.add(taxpayerInfoBtn);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 12)));
        sidebarContentWrapper.add(otherInfoBtn);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 12)));
        sidebarContentWrapper.add(businessInfoBtn);

        sidebar.add(sidebarContentWrapper, BorderLayout.NORTH);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        add(sidebar, BorderLayout.WEST);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Add form fields based on the image
        addFormField(mainPanel, gbc, 0, 0, "Spouse Name", "Spouse TIN", "Spouse Employment");
        addFormField(mainPanel, gbc, 1, 0, "Employer Name", "Spouse Employer TIN", "");
        addFormField(mainPanel, gbc, 2, 0, "Representative ID", "Representative Name", "Relationship Date");
        addFormField(mainPanel, gbc, 3, 0, "Address", "Address Type", "Preferred Contact Type");

        // Edit button
        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(138, 43, 226));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setFont(new Font("Inter", Font.BOLD, 14));
        editButton.setPreferredSize(new Dimension(100, 35));

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(editButton, gbc);

        // Edit button functionality
        editButton.addActionListener(e -> {
            isEditing = !isEditing;
            editButton.setText(isEditing ? "Save" : "Edit");
            toggleEditableFields(mainPanel, isEditing);
        });

        add(mainPanel, BorderLayout.CENTER);

        // Photo upload functionality
        photoPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(OTHER_INFO.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        BufferedImage img = ImageIO.read(file);
                        photoPanel.setImage(img);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(OTHER_INFO.this, "Failed to load image.");
                    }
                }
            }
        });
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, int startCol, String... labels) {
        for (int i = 0; i < labels.length; i++) {
            if (!labels[i].isEmpty()) {
                gbc.gridx = startCol + i;
                gbc.gridy = row;
                panel.add(createLabeledField(labels[i]), gbc);
            }
        }
    }

    private void toggleEditableFields(JPanel panel, boolean editable) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component inner : ((JPanel) comp).getComponents()) {
                    if (inner instanceof JTextField) {
                        ((JTextField) inner).setEditable(editable);
                    }
                }
            }
        }
    }

    private JPanel createLabeledField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Inter", Font.PLAIN, 12));

        JTextField field = new JTextField();
        field.setFont(new Font("Inter", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(150, 30));
        field.setEditable(false);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLabeledFieldSmall(String labelText, boolean editable) {
        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Inter", Font.PLAIN, 9));

        JTextField field = new JTextField();
        field.setFont(new Font("Inter", Font.PLAIN, 9));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        field.setPreferredSize(new Dimension(80, 18));
        field.setMaximumSize(new Dimension(80, 18));
        field.setEditable(editable);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(100, 40));
        return panel;
    }

    private JButton createSidebarButton(String text, boolean isSelected) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 15));
        button.setBackground(isSelected ? new Color(138, 43, 226) : Color.WHITE);
        button.setForeground(isSelected ? Color.WHITE : Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 1, true));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(200, 45));
        return button;
    }

    private static class CircularImagePanel extends JPanel {
        private BufferedImage image;
        private int diameter;

        public CircularImagePanel(int diameter) {
            this.diameter = diameter;
            setPreferredSize(new Dimension(diameter, diameter));
            setOpaque(false);
        }

        public void setImage(BufferedImage image) {
            this.image = image;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (image != null) {
                Shape clip = new Ellipse2D.Float(0, 0, diameter, diameter);
                g2.setClip(clip);
                g2.drawImage(image, 0, 0, diameter, diameter, null);
            } else {
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, diameter, diameter);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(0, 0, diameter, diameter);
            }
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OTHER_INFO frame = new OTHER_INFO();
            frame.setVisible(true);
        });
    }
}
