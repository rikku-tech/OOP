import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class TAX_INFO extends JFrame {
    private CircularImagePanel photoPanel;
    private List<JTextField> formFields = new ArrayList<>();  // To hold all JTextFields in form
    private boolean isEditing = false; // To track edit state

    public TAX_INFO() {

        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");

        setTitle("Taxpayer Information");
        setIconImage(originalImage.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel
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

        JButton logoutButton = new JButton("Log out");
        logoutButton.setBackground(new Color(138, 43, 226));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutButton.setFont(new Font("Inter", Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setPreferredSize(new Dimension(110, 30));

        // Add logout button action listener
        logoutButton.addActionListener(e -> {
            try {
                // Dispose the TAX_INFO window
                TAX_INFO.this.dispose();
                
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

        JPanel photoWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        photoWrapper.setBackground(Color.WHITE);
        photoPanel = new CircularImagePanel(100);
        photoWrapper.add(photoPanel);

        sidebarContentWrapper.add(photoWrapper);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel taxpayerName = new JLabel("Taxpayer Name");
        taxpayerName.setFont(new Font("Inter", Font.BOLD, 14));
        taxpayerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarContentWrapper.add(taxpayerName);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Sidebar fields: Make sure these fields are NOT editable even when toggling edit mode
        sidebarContentWrapper.add(createLabeledFieldSmall("Taxpayer TIN", false));
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarContentWrapper.add(createLabeledFieldSmall("Registering Office", false));
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarContentWrapper.add(createLabeledFieldSmall("RDO Code", false));
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton taxpayerInfoBtn = createSidebarButton("Taxpayer Information", true);
        JButton otherInfoBtn = createSidebarButton("Other Information", false);
        JButton businessInfoBtn = createSidebarButton("Business Information", false);

        // Add navigation functionality
        taxpayerInfoBtn.addActionListener(e -> {
            // Already on TAX_INFO page, no action needed
        });

        otherInfoBtn.addActionListener(e -> {
            dispose(); // Close current window
            SwingUtilities.invokeLater(() -> {
                try {
                    OTHER_INFO otherInfo = new OTHER_INFO();
                    otherInfo.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to open Other Information page.",
                        "Navigation Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
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

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[] labels = {
            "Taxpayer Name", "Taxpayer Type", "Taxpayer Classification",
            "Registering Office", "BIR Registration Date", "Philsys Card Number",
            "Trade Business Name", "Line of Business", "Availing of the 8% income tax rate option?",
            "Date of Birth", "Place of Birth", "Gender",
            "Civil Status", "Citizenship", "Other Citizenship",
            "Mother's Maiden Name", "Father's Name"
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int col = 0;
        int row = 0;

        for (String label : labels) {
            gbc.gridx = col;
            gbc.gridy = row;
            JPanel labeledField = createLabeledField(label);
            formPanel.add(labeledField, gbc);

            // Get the JTextField inside the panel and add to formFields list
            JTextField field = (JTextField) labeledField.getComponent(1);
            field.setEditable(false); // disable editing initially
            formFields.add(field);

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(138, 43, 226));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setFont(new Font("Inter", Font.BOLD, 14));
        editButton.setPreferredSize(new Dimension(100, 35));
        gbc.gridx = 2;
        gbc.gridy = row + 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(editButton, gbc);

        // Add Edit button action listener
        editButton.addActionListener(e -> {
            isEditing = !isEditing; // toggle edit mode

            for (JTextField field : formFields) {
                field.setEditable(isEditing);
            }

            // Optionally, change button text to "Save" or "Edit"
            editButton.setText(isEditing ? "Save" : "Edit");
        });

        add(formPanel, BorderLayout.CENTER);

        // Photo upload
        photoPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(TAX_INFO.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        BufferedImage img = ImageIO.read(file);
                        photoPanel.setImage(img);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TAX_INFO.this, "Failed to load image.");
                    }
                }
            }
        });
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
        field.setEditable(editable);  // sidebar fields NOT editable regardless of toggle

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
        button.setMaximumSize(new Dimension(200, 45));  // Made it bigger
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
            TAX_INFO frame = new TAX_INFO();
            frame.setVisible(true);
        });
    }
}
