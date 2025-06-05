import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class BUS_INFO extends JFrame {
    private CircularImagePanel photoPanel;
    private boolean isEditing = false;
    private String currentState = "INITIAL"; // INITIAL, REGISTRATION_FORM, VIEW, EDIT
    private JPanel mainContentPanel;
    private JPanel initialPanel;
    private JPanel registrationFormPanel;
    private JPanel viewPanel;
    private Map<String, JTextField> formFields;

    public BUS_INFO() {
        formFields = new HashMap<>();
        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");

        setTitle("Business Information");
        setIconImage(originalImage.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel with logo and logout button
        createTopPanel();

        // Sidebar
        createSidebar();

        // Main content panel that will switch between registration and view
        mainContentPanel = new JPanel(new CardLayout());
        mainContentPanel.setBackground(Color.WHITE);

        // Create all panels
        createInitialPanel();
        createRegistrationFormPanel();
        createViewPanel();

        // Add panels to main content
        mainContentPanel.add(initialPanel, "INITIAL");
        mainContentPanel.add(registrationFormPanel, "REGISTRATION_FORM");
        mainContentPanel.add(viewPanel, "VIEW");

        // Show initial panel
        showPanel("INITIAL");

        add(mainContentPanel, BorderLayout.CENTER);
    }

    private void createTopPanel() {
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
                dispose();
                SwingUtilities.invokeLater(() -> {
                    try {
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
    }

    private void createSidebar() {
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

        // Navigation buttons
        JButton taxpayerInfoBtn = createSidebarButton("Taxpayer Information", false);
        JButton otherInfoBtn = createSidebarButton("Other Information", false);
        JButton businessInfoBtn = createSidebarButton("Business Information", true);

        // Add navigation functionality
        taxpayerInfoBtn.addActionListener(e -> {
            dispose();
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
            dispose();
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

        sidebarContentWrapper.add(taxpayerInfoBtn);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 12)));
        sidebarContentWrapper.add(otherInfoBtn);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 12)));
        sidebarContentWrapper.add(businessInfoBtn);

        sidebar.add(sidebarContentWrapper, BorderLayout.NORTH);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        add(sidebar, BorderLayout.WEST);
    }

    private void createInitialPanel() {
        initialPanel = new JPanel(new BorderLayout());
        initialPanel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        // Warning icon and message
        ImageIcon warningIcon = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
        JLabel warningLabel = new JLabel(warningIcon);
        JLabel messageLabel = new JLabel("Oops! It looks like you haven't registered a business yet. Would you like to create one now?");
        messageLabel.setFont(new Font("Inter", Font.PLAIN, 16));

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(138, 43, 226));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Inter", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        registerButton.addActionListener(e -> {
            showPanel("REGISTRATION_FORM");
        });

        // Layout components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(warningLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 20, 10);
        centerPanel.add(messageLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(registerButton, gbc);

        initialPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void createRegistrationFormPanel() {
        registrationFormPanel = new JPanel(new BorderLayout());
        registrationFormPanel.setBackground(Color.WHITE);
        registrationFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Add form fields
        String[] fields = {
            "Business Registration Number*", "Single Business Number*", "Industry Type*",
            "Trade Business Name*", "Regulatory Body*", "Business Registration Date*",
            "Line of Business*"
        };

        int row = 0;
        int col = 0;
        for (String field : fields) {
            if (col >= 3) {
                col = 0;
                row++;
            }
            gbc.gridx = col;
            gbc.gridy = row;
            
            if (field.contains("Industry Type")) {
                JPanel fieldPanel = createIndustryTypeField(field);
                formPanel.add(fieldPanel, gbc);
            } else {
                JPanel fieldPanel = createLabeledField(field);
                formPanel.add(fieldPanel, gbc);
                
                // Store the text field reference
                JTextField textField = (JTextField) ((JPanel) fieldPanel.getComponent(1)).getComponent(0);
                formFields.put(field, textField);
                textField.setEditable(true); // Fields are editable in registration form
                textField.setFocusable(true); // Fields are focusable in registration form
            }
            
            col++;
        }

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(138, 43, 226));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Inter", Font.BOLD, 14));
        registerButton.setFocusPainted(false);

        registerButton.addActionListener(e -> {
            // Transfer data from registration form to view panel
            Map<String, String> fieldMapping = new HashMap<>();
            fieldMapping.put("Business Registration Number", "Business Registration Number*");
            fieldMapping.put("Single Business Number", "Single Business Number*");
            fieldMapping.put("Industry Type", "Industry Type*");
            fieldMapping.put("Trade Business Name", "Trade Business Name*");
            fieldMapping.put("Regulatory Body", "Regulatory Body*");
            fieldMapping.put("Business Registration Date", "Business Registration Date*");
            fieldMapping.put("Line of Business", "Line of Business*");

            // Copy values from registration form to view panel
            for (Map.Entry<String, String> entry : fieldMapping.entrySet()) {
                String viewField = entry.getKey();
                String regField = entry.getValue();
                if (formFields.containsKey(viewField) && formFields.containsKey(regField)) {
                    String value = formFields.get(regField).getText();
                    formFields.get(viewField).setText(value);
                }
            }
            
            showPanel("VIEW");
        });

        gbc.gridx = 2;
        gbc.gridy = row + 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(registerButton, gbc);

        registrationFormPanel.add(formPanel, BorderLayout.CENTER);
    }

    private void createViewPanel() {
        viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBackground(Color.WHITE);
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Add form fields
        String[] fields = {
            "Business Registration Number", "Single Business Number", "Industry Type",
            "Trade Business Name", "Regulatory Body", "Business Registration Date",
            "Line of Business"
        };

        int row = 0;
        int col = 0;
        for (String field : fields) {
            if (col >= 3) {
                col = 0;
                row++;
            }
            gbc.gridx = col;
            gbc.gridy = row;
            
            if (field.contains("Industry Type")) {
                JPanel fieldPanel = createIndustryTypeField(field);
                formPanel.add(fieldPanel, gbc);
            } else {
                JPanel fieldPanel = createLabeledField(field);
                formPanel.add(fieldPanel, gbc);
                
                // Store the text field reference
                JTextField textField = (JTextField) ((JPanel) fieldPanel.getComponent(1)).getComponent(0);
                formFields.put(field, textField);
            }
            
            col++;
        }

        // Edit/Save button
        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(138, 43, 226));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Inter", Font.BOLD, 14));
        editButton.setFocusPainted(false);

        editButton.addActionListener(e -> {
            if (!isEditing) {
                // Switching to edit mode
                isEditing = true;
                editButton.setText("Save Changes");
                toggleDeleteIcons(true);
                toggleFieldsEditable(true);
            } else {
                // Update UI
                isEditing = false;
                editButton.setText("Edit");
                toggleDeleteIcons(false);
                toggleFieldsEditable(false);
            }
        });

        gbc.gridx = 2;
        gbc.gridy = row + 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(editButton, gbc);

        viewPanel.add(formPanel, BorderLayout.CENTER);
    }

    private void toggleDeleteIcons(boolean show) {
        for (JTextField field : formFields.values()) {
            JPanel parent = (JPanel) field.getParent();
            
            // Remove existing delete button if any
            for (Component comp : parent.getComponents()) {
                if (comp instanceof JButton && "delete".equals(comp.getName())) {
                    parent.remove(comp);
                }
            }
            
            if (show) {
                JButton deleteBtn = new JButton("🗑");
                deleteBtn.setName("delete");
                deleteBtn.setForeground(Color.RED);
                deleteBtn.setBorder(BorderFactory.createEmptyBorder());
                deleteBtn.setContentAreaFilled(false);
                deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                deleteBtn.addActionListener(e -> field.setText(""));
                
                parent.add(deleteBtn, BorderLayout.EAST);
            }
            
            parent.revalidate();
            parent.repaint();
        }
    }

    private void toggleFieldsEditable(boolean editable) {
        for (JTextField field : formFields.values()) {
            if (editable) {
                field.setEditable(true);
                field.setFocusable(true);
            } else {
                field.setEditable(false);
                field.setFocusable(false);
            }
        }
    }

    private void showPanel(String panelName) {
        currentState = panelName;
        CardLayout cl = (CardLayout) mainContentPanel.getLayout();
        cl.show(mainContentPanel, panelName);
    }

    private JPanel createIndustryTypeField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Inter", Font.PLAIN, 12));

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(Color.WHITE);

        String[] options = {"Primary", "Secondary"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Inter", Font.PLAIN, 12));
        comboBox.setBackground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(150, 30));
        
        fieldPanel.add(comboBox, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLabeledField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Inter", Font.PLAIN, 12));

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(Color.WHITE);

        JTextField field = new JTextField();
        field.setFont(new Font("Inter", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(150, 30));
        field.setEditable(false);
        field.setFocusable(false); // initially not focusable

        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);
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
            if (editable) {
                field.setEditable(true);
                field.setFocusable(true);
            } else {
                field.setEditable(false);
                field.setFocusable(false);
            }

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
            BUS_INFO frame = new BUS_INFO();
            frame.setVisible(true);
        });
    }
}
