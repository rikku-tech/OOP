import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OTHER_INFO extends JFrame {
    private CircularImagePanel photoPanel;
    private boolean isEditing = false;
    private JTextField tinFieldSidebar;
    private JPanel mainPanel;
    private JTextField registeringOfficeSidebar;
    private JTextField rdoCodeSidebar;
    private String userEmail;
    private final String DB_URL = "jdbc:mysql://localhost:3306/employer_name";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "02162005me";
    private Map<String, JTextField> formFields;
    Map<String, JComponent> spouseFields = new HashMap<>();
    Map<String, JComponent> authRepFields = new HashMap<>();
    Map<String, JComponent> taxpayerFields = new HashMap<>();
    private String taxpayerTIN;

    
    
    public OTHER_INFO(String email) {
        this.userEmail = email;
        this.formFields = new HashMap<>();
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

        // Navigation buttons with functionality
        JButton taxpayerInfoBtn = createSidebarButton("Taxpayer Information", false);
        JButton otherInfoBtn = createSidebarButton("Other Information", true);
        JButton businessInfoBtn = createSidebarButton("Business Information", false);

        // Add navigation functionality
        taxpayerInfoBtn.addActionListener(e -> {
            dispose(); // Close current window
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
            // Already on OTHER_INFO page, no action needed
        });

        businessInfoBtn.addActionListener(e -> {
            dispose(); // Close current window
            SwingUtilities.invokeLater(() -> {
                try {
                    BUS_INFO businessInfo = new BUS_INFO(userEmail);
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
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        
        // Add spouse fields
        addFormField(mainPanel, gbc, 1, 0, spouseFields, "SpouseName", "SpouseTIN", "EmploymentStatusOfSpouse");
        addFormField(mainPanel, gbc, 2, 0, spouseFields, "SpouseEmployerName", "SpouseEmployerTIN", "");
        // Add auth rep fields
        addFormField(mainPanel, gbc, 3, 0, authRepFields, "Representative_ID", "RepresentativeName", "RelationshipDate");
        addFormField(mainPanel, gbc, 4, 0, authRepFields, "RepresentativeAddress", "AddressType", "PreferredContactType");
        
        // Edit button
        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(138, 43, 226));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setFont(new Font("Inter", Font.BOLD, 14));
        editButton.setPreferredSize(new Dimension(100, 35));

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(editButton, gbc);

        // Edit button functionality
        editButton.addActionListener(e -> {
            if (isEditing) {
                boolean success = saveOtherInfo();
                if (success) {
                    isEditing = false;
                    toggleEditableFields(mainPanel, false);
                    editButton.setText("Edit");
                    JOptionPane.showMessageDialog(this, "Data saved successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save data. Please try again.");
                }
            } else {
                isEditing = true;
                toggleEditableFields(mainPanel, true);
                editButton.setText("Save");
            }
        });

        // Load initial data
        loadOtherInfo(userEmail);

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
    private void toggleEditableFields(JPanel panel, boolean editable) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component inner : ((JPanel) comp).getComponents()) {
                    if (inner instanceof JTextField) {
                        JTextField field = (JTextField) inner;
                        // Check if this JTextField belongs to taxpayerFields, don't allow edit
                        if (taxpayerFields.containsValue(field)) {
                            field.setEditable(false);
                            field.setFocusable(false);
                        } else {
                            field.setEditable(editable);
                            field.setFocusable(editable);
                        }
                    } else if (inner instanceof JComboBox) {
                        JComboBox<?> comboBox = (JComboBox<?>) inner;
                        // Same logic for JComboBox
                        if (taxpayerFields.containsValue(comboBox)) {
                            comboBox.setEnabled(false);
                        } else {
                            comboBox.setEnabled(editable);
                        }
                    }
                }
            }
        }
    }

    

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, int startCol, Map<String, JComponent> targetMap, String... labels) {
        Color fieldBgColor = new Color(230, 230, 230);     // Light gray background
        Color fieldTextColor = new Color(50, 50, 50);      // Darker gray text
        Color fieldBorderColor = new Color(180, 180, 180); // Medium gray border
        Font commonFont = new Font("Inter", Font.PLAIN, 12);
        boolean isEditable = this.isEditing; // Use the current editing state

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            if (!label.isEmpty()) {
                gbc.gridx = startCol + i;
                gbc.gridy = row;
                gbc.insets = new Insets(10, 10, 5, 10);

                JPanel fieldPanel = new JPanel(new BorderLayout(0, 4));
                fieldPanel.setBackground(Color.WHITE);

                JLabel jLabel = new JLabel(label);
                jLabel.setFont(commonFont);
                jLabel.setForeground(fieldTextColor);
                fieldPanel.add(jLabel, BorderLayout.NORTH);

                JComponent inputComponent;
                String normalizedLabel = label.replaceAll("\\s+", "").toLowerCase();

                if (normalizedLabel.equals("addresstype")) {
                    JComboBox<String> comboBox = new JComboBox<>(new String[]{"Residences", "Place Business", "Employer Address"});
                    comboBox.setFont(commonFont);
                    comboBox.setBackground(fieldBgColor);
                    comboBox.setForeground(fieldTextColor);
                    comboBox.setBorder(BorderFactory.createLineBorder(fieldBorderColor, 1));
                    comboBox.setPreferredSize(new Dimension(200, 25));
                    comboBox.setEnabled(isEditable);
                    inputComponent = comboBox;
                } else if (normalizedLabel.contains("employmentstatus")) {
                    JComboBox<String> comboBox = new JComboBox<>(new String[]{
                        "Unemployed", 
                        "Employed Locally", 
                        "Employed Abroad", 
                        "Engaged in Business/Practice of Profession"
                    });
                    comboBox.setFont(commonFont);
                    comboBox.setBackground(fieldBgColor);
                    comboBox.setForeground(fieldTextColor);
                    comboBox.setBorder(BorderFactory.createLineBorder(fieldBorderColor, 1));
                    comboBox.setPreferredSize(new Dimension(200, 25));
                    comboBox.setEnabled(isEditable);
                    inputComponent = comboBox;
                } else {
                    JTextField textField = new JTextField(15);
                    textField.setFont(commonFont);
                    textField.setBackground(fieldBgColor);
                    textField.setForeground(fieldTextColor);
                    textField.setBorder(BorderFactory.createLineBorder(fieldBorderColor, 1));
                    textField.setPreferredSize(new Dimension(200, 25));
                    textField.setEditable(isEditable);
                    inputComponent = textField;
                }

                fieldPanel.add(inputComponent, BorderLayout.CENTER);
                panel.add(fieldPanel, gbc);

                targetMap.put(label, inputComponent);
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
        field.setFocusable(false); // initially not focusable

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

    private void loadOtherInfo(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Load taxpayer info using email
            String taxpayerQuery = "SELECT * FROM taxpayer WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(taxpayerQuery)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    taxpayerTIN = rs.getString("TaxpayerTIN"); // Store TaxpayerTIN
                    tinFieldSidebar.setText(taxpayerTIN);
                    registeringOfficeSidebar.setText(rs.getString("RegisteringOffice"));
                    rdoCodeSidebar.setText(rs.getString("RDOCode"));

                    for (Map.Entry<String, JComponent> entry : taxpayerFields.entrySet()) {
                        String col = entry.getKey();
                        JComponent comp = entry.getValue();
                        String value = rs.getString(col) != null ? rs.getString(col) : "";

                        if (comp instanceof JTextField textField) {
                            textField.setText(value);
                        } else if (comp instanceof JComboBox<?> comboBox) {
                            comboBox.setSelectedItem(value);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No taxpayer found with email: " + email);
                    return;
                }
            }

            // Load spouse info using TaxpayerTIN
            String spouseQuery = "SELECT * FROM spouse WHERE TaxpayerTIN = ?";
            try (PreparedStatement stmt = conn.prepareStatement(spouseQuery)) {
                stmt.setString(1, taxpayerTIN);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    for (Map.Entry<String, JComponent> entry : spouseFields.entrySet()) {
                        String col = entry.getKey();
                        JComponent comp = entry.getValue();
                        String value = rs.getString(col) != null ? rs.getString(col) : "";

                        if (comp instanceof JTextField textField) {
                            textField.setText(value);
                        } else if (comp instanceof JComboBox<?> comboBox) {
                            comboBox.setSelectedItem(value);
                        }
                    }
                }
            }

            // Load authorized representative info using TaxpayerTIN
            String authRepQuery = "SELECT * FROM auth_rep WHERE TaxpayerTIN = ?";
            try (PreparedStatement stmt = conn.prepareStatement(authRepQuery)) {
                stmt.setString(1, taxpayerTIN);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    for (Map.Entry<String, JComponent> entry : authRepFields.entrySet()) {
                        String col = entry.getKey();
                        JComponent comp = entry.getValue();
                        String value = rs.getString(col) != null ? rs.getString(col) : "";

                        if (comp instanceof JTextField textField) {
                            textField.setText(value);
                        } else if (comp instanceof JComboBox<?> comboBox) {
                            comboBox.setSelectedItem(value);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    private boolean saveOtherInfo() {
        // Save spouseFields
        if (!saveFields(spouseFields, "spouse", taxpayerTIN)) return false;
        // Save authRepFields
        if (!saveFields(authRepFields, "auth_rep", taxpayerTIN)) return false;
        // Taxpayer fields are not saved here, since they are display-only
        return true;
    }

    private boolean saveFields(Map<String, JComponent> fields, String tableName, String taxpayerTIN) {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        StringBuilder updateClause = new StringBuilder();
        List<String> values = new ArrayList<>();

        for (String columnName : fields.keySet()) {
            JComponent comp = fields.get(columnName);
            String value = "";

            if (comp instanceof JTextField textField) {
                value = textField.getText();
            } else if (comp instanceof JComboBox<?> comboBox) {
                Object selected = comboBox.getSelectedItem();
                value = selected != null ? selected.toString() : "";
            }

            columns.append(columnName).append(", ");
            placeholders.append("?, ");
            updateClause.append(columnName).append(" = ?, ");
            values.add(value);
        }

        // Trim trailing comma and space
        if (columns.length() == 0) return true;

        String columnList = columns + "TaxpayerTIN";
        String placeholderList = placeholders + "?";
        String updateList = updateClause.substring(0, updateClause.length() - 2); // Remove last comma

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 1. Check if row exists
            boolean exists;
            try (PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT 1 FROM " + tableName + " WHERE TaxpayerTIN = ?")) {
                checkStmt.setString(1, taxpayerTIN);
                ResultSet rs = checkStmt.executeQuery();
                exists = rs.next();
            }

            if (exists) {
                // 2. UPDATE
                String updateQuery = "UPDATE " + tableName + " SET " + updateList + " WHERE TaxpayerTIN = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                    int i = 1;
                    for (String value : values) {
                        stmt.setString(i++, value);
                    }
                    stmt.setString(i, taxpayerTIN);
                    stmt.executeUpdate();
                }
            } else {
                // 3. INSERT
                String insertQuery = "INSERT INTO " + tableName + " (" + columnList + ") VALUES (" + placeholderList + ")";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    int i = 1;
                    for (String value : values) {
                        stmt.setString(i++, value);
                    }
                    stmt.setString(i, taxpayerTIN);
                    stmt.executeUpdate();
                }
            }

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
            return false;
        }
    }


    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OTHER_INFO frame = new OTHER_INFO("user@example.com"); // Replace with valid email
            frame.setVisible(true);
        });
    }*/
}
