import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class BUS_INFO extends JFrame {
    private CircularImagePanel photoPanel;
    private boolean isEditing = false;
    private String currentState = "INITIAL"; // INITIAL, REGISTRATION_FORM, VIEW, EDIT
    private JPanel mainContentPanel;
    private JPanel initialPanel;
    private JPanel registrationFormPanel;
    private JPanel viewPanel;
    private Map<String, JTextField> formFields;
    private JTextField tinFieldSidebar;
    private JTextField registeringOfficeSidebar;
    private JTextField rdoCodeSidebar;
    private String userEmail;
    private final String DB_URL = "jdbc:mysql://localhost:3306/employer_name";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "02162005me";
    private Map<String, JComponent> registrationFormFields = new HashMap<>();
    private Map<String, JComponent> viewFormFields = new HashMap<>();
    private String taxpayerTIN;  // store taxpayerTIN here
    private Map<String, JButton> trashButtonsMap = new HashMap<>();

    public BUS_INFO(String email) {
        this.userEmail = email;
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
        showPanel("VIEW");
        mainContentPanel.revalidate();
        mainContentPanel.repaint();

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
            dispose(); // Close current window
            SwingUtilities.invokeLater(() -> {
                try {
                    // Create and show new MAIN frame
                    JFrame frame = new JFrame();
                    MAIN.setupMainFrame(frame);
                    frame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to return to main page.",
                        "Logout Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
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
        JPanel tinPanel = createLabeledFieldSmall("Taxpayer TIN", false);
        sidebarContentWrapper.add(tinPanel);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        tinFieldSidebar = getTextFieldFromPanel(tinPanel);

        JPanel regOfficePanel = createLabeledFieldSmall("Registering Office", false);
        sidebarContentWrapper.add(regOfficePanel);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        registeringOfficeSidebar = getTextFieldFromPanel(regOfficePanel);

        JPanel rdoPanel = createLabeledFieldSmall("RDO Code", false);
        sidebarContentWrapper.add(rdoPanel);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        rdoCodeSidebar = getTextFieldFromPanel(rdoPanel);

        // Navigation buttons
        JButton taxpayerInfoBtn = createSidebarButton("Taxpayer Information", false);
        JButton otherInfoBtn = createSidebarButton("Other Information", false);
        JButton businessInfoBtn = createSidebarButton("Business Information", true);

        // Add navigation functionality
        taxpayerInfoBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    TAX_INFO taxInfo = new TAX_INFO(userEmail);
                    taxInfo.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace(); 

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
                    OTHER_INFO otherInfo = new OTHER_INFO( this.userEmail);
                    otherInfo.setVisible(true);
                } catch (Exception ex) {
                    System.out.println("Exception caught in businessInfoBtn");

                    ex.printStackTrace(); 

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

    
 // Store fields for registration form (both JTextField and JComboBox)


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

        String[] fields = {
        	    "Business Registration Number*", "Single Business Number*", "Industry Type*",
        	    "Trade Business Name*", "Regulatory Body", "Business Registration Date*",
        	    "Line of Business*"
        	};


        int row = 0, col = 0;
        for (String field : fields) {
            if (col >= 3) {
                col = 0;
                row++;
            }
            gbc.gridx = col;
            gbc.gridy = row;

            if (field.contains("Industry Type")) {
                JPanel industryPanel = createIndustryTypeField(field);
                formPanel.add(industryPanel, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) ((JPanel) industryPanel.getComponent(1)).getComponent(0);
                registrationFormFields.put(field.replace("*", ""), comboBox);
            } else {
                JPanel fieldPanel = createLabeledField(field);
                formPanel.add(fieldPanel, gbc);
                JTextField textField = (JTextField) ((JPanel) fieldPanel.getComponent(1)).getComponent(0);
                textField.setEditable(true);
                textField.setFocusable(true);
                registrationFormFields.put(field.replace("*", ""), textField);
            }
            col++;
        }

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(138, 43, 226));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Inter", Font.BOLD, 14));
        registerButton.setFocusPainted(false);

        registerButton.addActionListener(e -> {
            // Validate required fields
            for (String key : registrationFormFields.keySet()) {
                JComponent comp = registrationFormFields.get(key);
                String value = "";
                if (comp instanceof JTextField) {
                    value = ((JTextField) comp).getText().trim();
                } else if (comp instanceof JComboBox) {
                    Object selected = ((JComboBox<?>) comp).getSelectedItem();
                    value = selected != null ? selected.toString().trim() : "";
                }
                if (value.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please fill in all required fields marked with *",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Save to database (stub function)
            boolean success = saveBusinessData();
            if (success) {
                // Sync data to view panel
                syncFormData(registrationFormFields, viewFormFields);
                JOptionPane.showMessageDialog(this, "Business registered successfully!");
                showPanel("VIEW");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to register business. Please try again.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 2;
        gbc.gridy = row + 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(registerButton, gbc);

        registrationFormPanel.add(formPanel, BorderLayout.CENTER);
    }

    // Similar createViewPanel with editable toggle and sync support
    private void createViewPanel() {
        viewFormFields = new HashMap<>();
        viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBackground(Color.WHITE);
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        String[] fields = {
            "Business Registration Number", "Single Business Number", "Industry Type",
            "Trade Business Name", "Regulatory Body", "Business Registration Date",
            "Line of Business"
        };

        int row = 0, col = 0;
        for (String field : fields) {
            if (col >= 3) {
                col = 0;
                row++;
            }
            gbc.gridx = col;
            gbc.gridy = row;

            if (field.contains("Industry Type")) {
                JPanel industryPanel = createIndustryTypeField(field);
                formPanel.add(industryPanel, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) ((JPanel) industryPanel.getComponent(1)).getComponent(0);
                comboBox.setEnabled(false);
                viewFormFields.put(field, comboBox);
            } else {
                JPanel fieldPanel = createLabeledField(field);
                formPanel.add(fieldPanel, gbc);
                JTextField textField = (JTextField) ((JPanel) fieldPanel.getComponent(1)).getComponent(0);
                textField.setEditable(false);
                textField.setFocusable(false);
                viewFormFields.put(field, textField);
            }
            col++;
        }

        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(138, 43, 226));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Inter", Font.BOLD, 14));
        editButton.setFocusPainted(false);

        editButton.addActionListener(e -> {
            if (!isEditing) {
                isEditing = true;
                editButton.setText("Save Changes");
                toggleFieldsEditable(viewFormFields, true);
            } else {
                boolean success = saveBusinessData(this.taxpayerTIN);
                if (success) {
                    isEditing = false;
                    editButton.setText("Edit");
                    toggleFieldsEditable(viewFormFields, false);
                    JOptionPane.showMessageDialog(this, "Business data saved successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save business data. Please try again.");
                }
            }
        });

        gbc.gridx = 2;
        gbc.gridy = row + 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(editButton, gbc);

        viewPanel.add(formPanel, BorderLayout.CENTER);

        // Load data stub
        loadSidebarData(userEmail);
    }


    // Helper: Toggle editable/focusable for all fields (including JComboBox)
    private void toggleFieldsEditable(Map<String, JComponent> fields, boolean editable) {
        for (Map.Entry<String, JComponent> entry : fields.entrySet()) {
            String key = entry.getKey();
            JComponent comp = entry.getValue();
            if (comp instanceof JTextField) {
                JTextField tf = (JTextField) comp;
                tf.setEditable(editable);
                tf.setFocusable(editable);
            } else if (comp instanceof JComboBox) {
                ((JComboBox<?>) comp).setEnabled(editable);
            }

            // Toggle trash button for this field if present (only main panels have trash buttons)
            JButton trashBtn = trashButtonsMap.get(key);
            if (trashBtn != null) {
                trashBtn.setEnabled(editable);
            }
        }
    }
    // Helper: Sync data from source form to target form
    private void syncFormData(Map<String, JComponent> source, Map<String, JComponent> target) {
        for (String key : source.keySet()) {
            JComponent sourceComp = source.get(key);
            JComponent targetComp = target.get(key);
            if (sourceComp != null && targetComp != null) {
                String value = "";
                if (sourceComp instanceof JTextField) {
                    value = ((JTextField) sourceComp).getText();
                } else if (sourceComp instanceof JComboBox) {
                    Object selected = ((JComboBox<?>) sourceComp).getSelectedItem();
                    value = selected != null ? selected.toString() : "";
                }

                if (targetComp instanceof JTextField) {
                    ((JTextField) targetComp).setText(value);
                } else if (targetComp instanceof JComboBox) {
                    ((JComboBox<String>) targetComp).setSelectedItem(value);
                }
            }
        }
    }

    // Stub: Saving business data - replace with real DB logic
    private boolean saveBusinessData() {
        System.out.println("Entering saveBusinessData method.");
        
        System.out.println("Saving data, current viewFormFields keys:");
        for (String key : viewFormFields.keySet()) {
            System.out.println(" - " + key);
        }

        String updateQuery = "UPDATE bussinessinfo SET "
            + "BusinessRegistrationNumber = ?, SingleBusinessNumber = ?, "
            + "IndustryType = ?, TradeBusinessName = ?, RegulatoryBody = ?, "
            + "BusinessRegistrationDate = ?, LineOfBusiness = ? "
            + "WHERE TaxpayerTIN = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            String[] keys = {
                "Business Registration Number",
                "Single Business Number",
                "Industry Type",
                "Trade Business Name", 
                "Regulatory Body",
                "Business Registration Date",
                "Line of Business"
            };

            // Log taxpayerTIN value
            System.out.println("Using taxpayerTIN for update: " + taxpayerTIN);

            for (int i = 0; i < keys.length; i++) {
                JComponent comp = viewFormFields.get(keys[i]);
                String value = "";
                if (comp == null) {
                    System.out.println("Missing field in viewFormFields: " + keys[i]);
                } else if (comp instanceof JTextField) {
                    value = ((JTextField) comp).getText();
                } else if (comp instanceof JComboBox) {
                    Object selected = ((JComboBox<?>) comp).getSelectedItem();
                    value = selected != null ? selected.toString() : "";
                }
                stmt.setString(i + 1, value);
                System.out.println("Setting parameter " + (i + 1) + ": " + value); // Log each parameter value
            }

            stmt.setString(8, taxpayerTIN); // Correctly use taxpayerTIN here

            // Log the final SQL statement (for debugging purposes)
            System.out.println("Executing update query: " + stmt.toString());

            int updatedRows = stmt.executeUpdate();
            System.out.println("Update executed. Rows updated: " + updatedRows); // Log the result of the update
            return updatedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
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

        JPanel fieldPanel = new JPanel(new BorderLayout(5, 0)); // Small gap between field and button
        fieldPanel.setBackground(Color.WHITE);

        JTextField field = new JTextField();
        field.setFont(new Font("Inter", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(150, 30));
        field.setEditable(true);
        field.setFocusable(true);

        // Create trash bag button with icon
        ImageIcon trashIcon = null;
        try {
            trashIcon = new ImageIcon(new ImageIcon("")
                    .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)); // Increased size to 40x40
        } catch (Exception e) {
            System.err.println("Trash icon image load failed.");
        }

        JButton trashButton = new JButton(trashIcon);
        trashButton.setPreferredSize(new Dimension(40, 40)); // Set preferred size
        trashButton.setFocusPainted(false);
        trashButton.setBorder(BorderFactory.createEmptyBorder());	
        trashButton.setContentAreaFilled(false);
        trashButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Fallback if the icon is not loaded
        if (trashIcon == null || trashIcon.getIconWidth() <= 0 || trashIcon.getIconHeight() <= 0) {
            trashButton.setText("🗑"); // Unicode trash can emoji as fallback
            trashButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24)); // Adjust font size for visibility
            trashButton.setForeground(new Color(128, 0, 0));
        }

        trashButton.addActionListener(e -> {
            if (isEditing) { // Only respond if in edit mode
                field.setText(" "); // Set single space to “delete” content per user request
            }
        });

        fieldPanel.add(field, BorderLayout.CENTER);
        fieldPanel.add(trashButton, BorderLayout.EAST); // Trash button on right side

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);

        // Store trashButton mapped to the labelText key without "*"
        trashButtonsMap.put(labelText.replace("*", "").trim(), trashButton);

        return panel;
    }


    // Sidebar small fields remain unchanged - no trash button there as requested
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

    private JTextField getTextFieldFromPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                return (JTextField) comp;
            }
        }
        return null;
    }
    
    private void loadSidebarData(String email) {
        String query = "SELECT TaxpayerTIN, RegisteringOffice, RDOCode FROM taxpayer WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            System.out.println("Executing query: " + stmt.toString()); // Debugging line
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String taxpayerTIN = rs.getString("TaxpayerTIN");
                System.out.println("Taxpayer TIN: " + taxpayerTIN); // Debugging line
                tinFieldSidebar.setText(taxpayerTIN);
                registeringOfficeSidebar.setText(rs.getString("RegisteringOffice"));
                rdoCodeSidebar.setText(rs.getString("RDOCode"));

                // Call business info loader with the TIN
                loadBusinessInfo(taxpayerTIN);
            } else {
                JOptionPane.showMessageDialog(this, "No taxpayer found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading sidebar data: " + e.getMessage());
        }
    }
    
    private void setField(String fieldName, String value) {
        JComponent comp = viewFormFields.get(fieldName);
        if (comp != null) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText(value != null ? value : "");
                System.out.println(fieldName + " set to: " + ((JTextField) comp).getText());
            } else if (comp instanceof JComboBox) {
                ((JComboBox<String>) comp).setSelectedItem(value);
                System.out.println(fieldName + " set to: " + value);
            }
        } else {
            System.out.println("Field not found in viewFormFields: " + fieldName);
        }
    }

    private void loadBusinessInfo(String taxpayerTIN) {
        System.out.println("loadBusinessInfo called with TIN: " + taxpayerTIN);
        this.taxpayerTIN = taxpayerTIN; // save TIN for later use

        String query = "SELECT BusinessRegistrationNumber, SingleBusinessNumber, IndustryType, TradeBusinessName, RegulatoryBody, BusinessRegistrationDate, LineOfBusiness " +
                       "FROM bussinessinfo WHERE TaxpayerTIN = ?"; // Corrected table name

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxpayerTIN);
            System.out.println("Executing query: " + stmt.toString()); // Debugging line
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Data found for TIN: " + taxpayerTIN);
                setField("Business Registration Number", rs.getString("BusinessRegistrationNumber"));
                setField("Single Business Number", rs.getString("SingleBusinessNumber"));
                setField("Industry Type", rs.getString("IndustryType"));
                setField("Trade Business Name", rs.getString("TradeBusinessName"));
                setField("Regulatory Body", rs.getString("RegulatoryBody"));
                setField("Business Registration Date", rs.getString("BusinessRegistrationDate"));
                setField("Line of Business", rs.getString("LineOfBusiness"));

                showPanel("VIEW");
            } else {
                System.out.println("No data found for TIN: " + taxpayerTIN);
                showPanel("INITIAL");
            }

        } catch (SQLException e) {
            System.err.println("SQL Error during loadBusinessInfo: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading business info: " + e.getMessage());
        }
    }

    

    
    private boolean saveBusinessData(String taxpayerTIN) {
        System.out.println("Saving data, current viewFormFields keys:");
        for (String key : viewFormFields.keySet()) {
            System.out.println(" - " + key);
        }

        if (taxpayerTIN == null) {
            System.err.println("Error: taxpayerTIN is null");
            JOptionPane.showMessageDialog(null, "Error: Taxpayer TIN is missing.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int maxTINLength = 11;
        System.out.println("TaxpayerTIN length: " + taxpayerTIN.length());
        System.out.println("TaxpayerTIN value: '" + taxpayerTIN + "'");
        if (taxpayerTIN.length() > maxTINLength) {
            System.err.println("Error: TaxpayerTIN length exceeds max column length of " + maxTINLength);
            return false;
        }

        // Declare keys ONCE for reuse
        String[] keys = {
            "Business Registration Number",
            "Single Business Number",
            "Industry Type",
            "Trade Business Name",
            "Regulatory Body",
            "Business Registration Date",
            "Line of Business"
        };

        // --- UPDATE ---
        String updateQuery = "UPDATE bussinessinfo SET "
                + "BusinessRegistrationNumber = ?, SingleBusinessNumber = ?, "
                + "IndustryType = ?, TradeBusinessName = ?, RegulatoryBody = ?, "
                + "BusinessRegistrationDate = ?, LineOfBusiness = ? "
                + "WHERE TaxpayerTIN = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            for (int i = 0; i < keys.length; i++) {
                JComponent comp = viewFormFields.get(keys[i]);
                String value = "";
                if (comp == null) {
                    System.out.println("Missing field: " + keys[i]);
                } else if (comp instanceof JTextField) {
                    value = ((JTextField) comp).getText();
                } else if (comp instanceof JComboBox) {
                    Object selected = ((JComboBox<?>) comp).getSelectedItem();
                    value = selected != null ? selected.toString() : "";
                }

                if (keys[i].equals("Business Registration Date") && value.trim().isEmpty()) {
                    stmt.setNull(i + 1, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(i + 1, value);
                }

                System.out.println("Setting parameter " + (i + 1) + ": " + value);
            }

            stmt.setString(8, taxpayerTIN);
            System.out.println("Using TaxpayerTIN (WHERE clause): " + taxpayerTIN);

            int updatedRows = stmt.executeUpdate();
            System.out.println("Update executed. Rows updated: " + updatedRows);
            if (updatedRows > 0) return true;

        } catch (SQLException ex) {
            System.err.println("SQL Error during update: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        // --- INSERT ---
        String insertQuery = "INSERT INTO bussinessinfo "
                + "(TaxpayerTIN, BusinessRegistrationNumber, SingleBusinessNumber, IndustryType, "
                + "TradeBusinessName, RegulatoryBody, BusinessRegistrationDate, LineOfBusiness) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, taxpayerTIN);

            for (int i = 0; i < keys.length; i++) {
                JComponent comp = viewFormFields.get(keys[i]);
                String value = "";
                if (comp == null) {
                    System.out.println("Missing field: " + keys[i]);
                } else if (comp instanceof JTextField) {
                    value = ((JTextField) comp).getText();
                } else if (comp instanceof JComboBox) {
                    Object selected = ((JComboBox<?>) comp).getSelectedItem();
                    value = selected != null ? selected.toString() : "";
                }

                if (keys[i].equals("Business Registration Date") && value.trim().isEmpty()) {
                    stmt.setNull(i + 2, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(i + 2, value);
                }

                System.out.println("Setting parameter " + (i + 2) + ": " + value);
            }

            int insertedRows = stmt.executeUpdate();
            System.out.println("Insert executed. Rows inserted: " + insertedRows);
            return insertedRows > 0;

        } catch (SQLException ex) {
            System.err.println("SQL Error during insert: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}
// In createRegistrationFormPanel() and createViewPanel(), when you call createLabeledField(),
// the trash button is created and stored to trashButtonsMap automatically.

// No changes required for sidebar because createLabeledFieldSmall() has no trash button,
// so sidebar fields remain intact and without trash icon.

// This way:
// - Trash icon shows only in main forms
// - Clears field text to " " when clicked during edit mode only
// - Sidebar and email/taxpayerTIN fields unaffected


    
