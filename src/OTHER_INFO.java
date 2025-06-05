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
    private final String DB_PASSWORD = "Vongabriel31!";
    private Map<String, JTextField> formFields;

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

        // Add form fields with database column names
        addFormField(mainPanel, gbc, 0, 0, "SpouseName", "SpouseTIN", "SpouseEmployment");
        addFormField(mainPanel, gbc, 1, 0, "EmployerName", "SpouseEmployerTIN", "");
        addFormField(mainPanel, gbc, 2, 0, "RepresentativeID", "RepresentativeName", "RelationshipDate");
        addFormField(mainPanel, gbc, 3, 0, "Address", "AddressType", "PreferredContactType");

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
            if (isEditing) {
                // Currently in Edit mode, so we are about to Save
                boolean success = saveOtherInfo();
                if (success) {
                    isEditing = false;
                    toggleEditableFields(mainPanel, false);
                    editButton.setText("Edit");
                    JOptionPane.showMessageDialog(this, "Data saved successfully.");
                } else {
                    // Keep editing if save failed
                    JOptionPane.showMessageDialog(this, "Failed to save data. Please try again.");
                }
            } else {
                // Switch to Edit mode
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

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, int startCol, String... labels) {
        for (int i = 0; i < labels.length; i++) {
            if (!labels[i].isEmpty()) {
                gbc.gridx = startCol + i;
                gbc.gridy = row;
                JPanel fieldPanel = createLabeledField(labels[i]);
                panel.add(fieldPanel, gbc);
                
                // Store field reference in map with database column name as key
                JTextField field = getTextFieldFromPanel(fieldPanel);
                if (field != null) {
                    formFields.put(labels[i], field);
                }
            }
        }
    }

    private void toggleEditableFields(JPanel panel, boolean editable) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component inner : ((JPanel) comp).getComponents()) {
                    if (inner instanceof JTextField) {
                        JTextField field = (JTextField) inner;
                        if (editable) {
                            field.setEditable(true);
                            field.setFocusable(true);
                        } else {
                            field.setEditable(false);
                            field.setFocusable(false);
                        }
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
        String query = "SELECT * FROM taxpayer WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Load sidebar info
                tinFieldSidebar.setText(rs.getString("TaxpayerTIN"));
                registeringOfficeSidebar.setText(rs.getString("RegisteringOffice"));
                rdoCodeSidebar.setText(rs.getString("RDOCode"));

                // Load form fields using the map
                for (Map.Entry<String, JTextField> entry : formFields.entrySet()) {
                    String columnName = entry.getKey();
                    JTextField field = entry.getValue();
                    String value = rs.getString(columnName);
                    field.setText(value != null ? value : "");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No taxpayer found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading taxpayer data: " + e.getMessage());
        }
    }

    private boolean saveOtherInfo() {
        StringBuilder columns = new StringBuilder();
        List<String> values = new ArrayList<>();
        
        for (String columnName : formFields.keySet()) {
            if (columns.length() > 0) {
                columns.append(", ");
            }
            columns.append(columnName).append(" = ?");
            values.add(formFields.get(columnName).getText());
        }

        String updateQuery = "UPDATE taxpayer SET " + columns.toString() + " WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            int paramIndex = 1;
            for (String value : values) {
                stmt.setString(paramIndex++, value);
            }
            stmt.setString(paramIndex, userEmail);

            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
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
