import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.sql.*;
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
    private Map<String, JComponent> registrationFormFields = new HashMap<>();
    private Map<String, JComponent> viewFormFields = new HashMap<>();
    private JTextField tinFieldSidebar;
    private JTextField registeringOfficeSidebar;
    private JTextField rdoCodeSidebar;
    private String userEmail;
    private final String DB_URL = "jdbc:mysql://localhost:3306/employer_name";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "02162005me";
    private String taxpayerTIN;  // store taxpayerTIN here
    private Map<String, JButton> trashButtonsMap = new HashMap<>();

    public BUS_INFO(String email) {
        this.userEmail = email;

        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");

        setTitle("Business Information");
        setIconImage(originalImage.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(1200, 800));
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
        loadSidebarData(userEmail);



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
        logoutButton.setFont(new Font("DM Sans", Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setPreferredSize(new Dimension(110, 30));

        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
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
        taxpayerName.setFont(new Font("DM Sans", Font.BOLD, 14));
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

        taxpayerInfoBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    TAX_INFO taxInfo = new TAX_INFO(userEmail);
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
                    OTHER_INFO otherInfo = new OTHER_INFO(userEmail);
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

        ImageIcon warningIcon = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
        JLabel warningLabel = new JLabel(warningIcon);
        JLabel messageLabel = new JLabel("Oops! It looks like you haven't registered a business yet. Would you like to create one now?");
        messageLabel.setFont(new Font("DM Sans", Font.PLAIN, 16));

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(138, 43, 226));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("DM Sans", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerButton.addActionListener(e -> {
            showPanel("REGISTRATION_FORM");
        });



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

        String[] fields = {
            "Business Registration Number*", "Single Business Number*", "Industry Type*",
            "Trade Business Name*", "Regulatory Body*", "Business Registration Date*",
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
        registerButton.setFont(new Font("DM Sans", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

     // inside createRegistrationFormPanel()

        registerButton.addActionListener(e -> {
            // Validation
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

            // Save
            boolean saved = performInsert(taxpayerTIN, registrationFormFields);
            if (saved) {
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
        editButton.setFont(new Font("DM Sans", Font.BOLD, 14));
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        editButton.addActionListener(e -> {
            if (!isEditing) {
                isEditing = true;
                editButton.setText("Save Changes");
                toggleFieldsEditable(viewFormFields, true);
            } else {
            	boolean saved = performUpdate(taxpayerTIN, viewFormFields);
            	if (saved) {
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

        loadSidebarData(userEmail);
    }

    private void toggleFieldsEditable(Map<String, JComponent> fields, boolean editable) {
        for (Map.Entry<String, JComponent> entry : fields.entrySet()) {
            JComponent comp = entry.getValue();
            if (comp instanceof JTextField) {
                JTextField tf = (JTextField) comp;
                tf.setEditable(editable);
                tf.setFocusable(editable);
            } else if (comp instanceof JComboBox) {
                ((JComboBox<?>) comp).setEnabled(editable);
            }

            JButton trashBtn = trashButtonsMap.get(entry.getKey());
            if (trashBtn != null) {
                trashBtn.setEnabled(editable);
            }
        }
    }

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
    
    private boolean recordExists(String taxpayerTIN) {
        String query = "SELECT COUNT(*) FROM bussinessinfo WHERE TRIM(TaxpayerTIN) = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, taxpayerTIN.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    private boolean performUpdate(String taxpayerTIN, Map<String, JComponent> formFields) {
        String[] keys = {
            "Business Registration Number",
            "Single Business Number",
            "Industry Type",
            "Trade Business Name",
            "Regulatory Body",
            "Business Registration Date",
            "Line of Business"
        };

        String updateQuery = "UPDATE bussinessinfo SET "
                + "BusinessRegistrationNumber = ?, SingleBusinessNumber = ?, "
                + "IndustryType = ?, TradeBusinessName = ?, RegulatoryBody = ?, "
                + "BusinessRegistrationDate = ?, LineOfBusiness = ? "
                + "WHERE TaxpayerTIN = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            for (int i = 0; i < keys.length; i++) {
                JComponent comp = formFields.get(keys[i]);
                String value = extractValue(comp);
                if (keys[i].equals("Business Registration Date") && value.trim().isEmpty()) {
                    stmt.setNull(i + 1, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(i + 1, value);
                }
            }
            System.out.println("Attempting to insert TIN: " + taxpayerTIN);

            stmt.setString(8, taxpayerTIN);
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean performInsert(String taxpayerTIN, Map<String, JComponent> formFields) {
        String[] keys = {
            "Business Registration Number",
            "Single Business Number",
            "Industry Type",
            "Trade Business Name",
            "Regulatory Body",
            "Business Registration Date",
            "Line of Business"
        };

        String insertQuery = "INSERT INTO bussinessinfo "
                + "(TaxpayerTIN, BusinessRegistrationNumber, SingleBusinessNumber, IndustryType, "
                + "TradeBusinessName, RegulatoryBody, BusinessRegistrationDate, LineOfBusiness) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
        	System.out.println("Attempting to insert TIN: " + taxpayerTIN);

            stmt.setString(1, taxpayerTIN);
            int paramIndex = 2;
            for (String key : keys) {
                JComponent comp = formFields.get(key);
                String value = extractValue(comp);
                if (key.equals("Business Registration Date") && value.trim().isEmpty()) {
                    stmt.setNull(paramIndex, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(paramIndex, value);
                }
                paramIndex++;
            }

            int insertedRows = stmt.executeUpdate();
            return insertedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private void showPanel(String name) {
        CardLayout cl = (CardLayout) mainContentPanel.getLayout();
        cl.show(mainContentPanel, name);
        currentState = name;  // Update currentState whenever you switch panels
    }


    private JPanel createIndustryTypeField(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("DM Sans", Font.PLAIN, 12));

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(Color.WHITE);

        String[] options = {"Primary", "Secondary"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("DM Sans", Font.PLAIN, 12));
        comboBox.setBackground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(150, 30));

        fieldPanel.add(comboBox, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLabeledField(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("DM Sans", Font.PLAIN, 12));

        JPanel fieldPanel = new JPanel(new BorderLayout(5, 0));
        fieldPanel.setBackground(Color.WHITE);

        JTextField field = new JTextField();
        field.setFont(new Font("DM Sans", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(150, 30));
        field.setEditable(true);
        field.setFocusable(true);

        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLabeledFieldSmall(String labelText, boolean editable) {
        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("DM Sans", Font.PLAIN, 9));

        JTextField field = new JTextField();
        field.setFont(new Font("DM Sans", Font.PLAIN, 9));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        field.setPreferredSize(new Dimension(80, 18));
        field.setMaximumSize(new Dimension(80, 18));
        field.setEditable(editable);
        field.setFocusable(editable);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(100, 40));
        return panel;
    }

    private JButton createSidebarButton(String text, boolean isSelected) {
        JButton button = new JButton(text);
        button.setFont(new Font("DM Sans", Font.BOLD, 15));
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

    private boolean sidebarDataLoaded = false; // Flag to check if sidebar data is loaded

    private void loadSidebarData(String email) {
        // This runs first to fetch the taxpayerTIN
        String query = "SELECT TaxpayerTIN, RegisteringOffice, RDOCode FROM taxpayer WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String taxpayerTIN = rs.getString("TaxpayerTIN");
                this.taxpayerTIN = taxpayerTIN;

                tinFieldSidebar.setText(taxpayerTIN);
                registeringOfficeSidebar.setText(rs.getString("RegisteringOffice"));
                rdoCodeSidebar.setText(rs.getString("RDOCode"));

                // 🔥 Now we load business info and switch panels accordingly
                loadBusinessInfo(taxpayerTIN); // This is responsible for showing VIEW or INITIAL
            } else {
                JOptionPane.showMessageDialog(this, "No taxpayer found with email: " + email);
                showPanel("INITIAL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading sidebar data: " + e.getMessage());
            showPanel("INITIAL");
        }
    }

    private boolean businessInfoLoaded = false; // Flag to check if business info is loaded

    private void loadBusinessInfo(String taxpayerTIN) {
        this.taxpayerTIN = taxpayerTIN; // ✅ Always store it for saving later

    	   if (taxpayerTIN == null || taxpayerTIN.trim().isEmpty()) {
    	        System.out.println("TIN is null/empty.");
    	        showPanel("INITIAL");  // fallback
    	        return;
    	    }
    	   
    	   System.out.println("businessInfoLoaded: " + businessInfoLoaded + ", currentState: " + currentState);


        String query = "SELECT BusinessRegistrationNumber, SingleBusinessNumber, IndustryType, TradeBusinessName, RegulatoryBody, BusinessRegistrationDate, LineOfBusiness " +
                       "FROM bussinessinfo WHERE TaxpayerTIN = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxpayerTIN);
            System.out.println("Executing query: " + stmt);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Data found for TIN: " + taxpayerTIN);
                // Set field values safely
                setField("Business Registration Number", rs.getString("BusinessRegistrationNumber"));
                setField("Single Business Number", rs.getString("SingleBusinessNumber"));
                setField("Industry Type", rs.getString("IndustryType"));
                setField("Trade Business Name", rs.getString("TradeBusinessName"));
                setField("Regulatory Body", rs.getString("RegulatoryBody"));
                setField("Business Registration Date", rs.getString("BusinessRegistrationDate"));
                setField("Line of Business", rs.getString("LineOfBusiness"));


                showPanel("VIEW");  // ✅ show VIEW here only
                businessInfoLoaded = true;
                
            } else {
                System.out.println("No business data found, showing INITIAL panel.");
                showPanel("INITIAL");
                businessInfoLoaded = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load business info.");
            showPanel("INITIAL");
        }
    }


    private void setField(String fieldName, String value) {
        JComponent comp = viewFormFields.get(fieldName);
        if (comp != null) {
            if (comp instanceof JTextField) {
                // Only set non-null and non-empty value to avoid overwriting with empty
                if (value != null && !value.trim().isEmpty()) {
                    ((JTextField) comp).setText(value);
                    System.out.println(fieldName + " set to: " + ((JTextField) comp).getText());
                } else {
                    System.out.println(fieldName + " retrieved empty or null, skipping set");
                }
            } else if (comp instanceof JComboBox) {
                if (value != null && !value.trim().isEmpty()) {
                    ((JComboBox<String>) comp).setSelectedItem(value);
                    System.out.println(fieldName + " set to: " + value);
                } else {
                    System.out.println(fieldName + " retrieved empty or null for JComboBox, skipping set");
                }
            }
        } else {
            System.out.println("Field not found in viewFormFields: " + fieldName);
        }
    }
    private String extractValue(JComponent comp) {
        if (comp instanceof JTextField) {
            return ((JTextField) comp).getText().trim();
        } else if (comp instanceof JComboBox<?>) {
            Object selected = ((JComboBox<?>) comp).getSelectedItem();
            return selected != null ? selected.toString().trim() : "";
        }
        return "";
    }

}


