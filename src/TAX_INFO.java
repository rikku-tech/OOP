import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.sql.*;
public class TAX_INFO extends JFrame {
    private CircularImagePanel photoPanel;
    private List<JTextField> formFields = new ArrayList<>();  // To hold all JTextFields in form
    private boolean isEditing = false; // To track edit state
    private JTextField tinFieldSidebar;
    private JTextField registeringOfficeSidebar;
    private JTextField rdoCodeSidebar;
    private String userEmail;

    public TAX_INFO(String email) {
    	this.userEmail = email;
     
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
            "Local Residence", "Business Residence", "Availing of the 8% income tax rate option?",
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
            field.setFocusable(false); // initially not focusable
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
<<<<<<< HEAD
            isEditing = !isEditing; // toggle edit mode

            for (JTextField field : formFields) {
                if (isEditing) {
                    field.setEditable(true);
                    field.setFocusable(true);
                } else {
                    field.setEditable(false);
                    field.setFocusable(false);
                }
=======
            if (isEditing) {
                // Currently in Edit mode, so we are about to Save
                boolean success = saveTaxpayerData();
                if (success) {
                    isEditing = false;
                    for (JTextField field : formFields) {
                        field.setEditable(false);
                    }
                    editButton.setText("Edit");
                    JOptionPane.showMessageDialog(this, "Data saved successfully.");
                } else {
                    // Keep editing if save failed
                    JOptionPane.showMessageDialog(this, "Failed to save data. Please try again.");
                }
            } else {
                // Switch to Edit mode
                isEditing = true;
                for (JTextField field : formFields) {
                    field.setEditable(true);
                }
                editButton.setText("Save");
>>>>>>> ff131e9833e1a752b7c92f2b752a3612d8385520
            }
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
        loadTaxpayerData(userEmail);  // Replace this with a hardcoded valid TIN from your database

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
        field.setFocusable(false);  // sidebar fields should never be focusable

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

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TAX_INFO frame = new TAX_INFO("user@example.com"); // Replace with a valid email or pass dynamically
            frame.setVisible(true);
        });
    }*/

    
    private final String DB_URL = "jdbc:mysql://localhost:3306/employer_name"; // replace your_database with your DB name
    private final String DB_USER = "root"; // your DB username
    private final String DB_PASSWORD = "02162005me"; // your DB password

    // Call this method after initializing the form fields in your constructor (at the end of TAX_INFO constructor)
    private void loadTaxpayerData(String email) {
        String query = "SELECT * FROM taxpayer WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                formFields.get(0).setText(rs.getString("TaxPayerName"));
                formFields.get(1).setText(rs.getString("TaxpayerType"));
                formFields.get(2).setText(rs.getString("TaxPayerClassification"));
                formFields.get(3).setText(rs.getString("RegisteringOffice"));
                formFields.get(4).setText(rs.getDate("BirRegistrationDate") != null ? rs.getDate("BirRegistrationDate").toString() : "");
                formFields.get(5).setText(rs.getString("PhilsysCardNumber"));
                formFields.get(6).setText("");
                formFields.get(7).setText("");
                formFields.get(8).setText(rs.getString("IncomeTaxRateOption"));
                formFields.get(9).setText(rs.getDate("Dateofbirth") != null ? rs.getDate("Dateofbirth").toString() : "");
                formFields.get(10).setText(rs.getString("PlaceOfBirth"));
                formFields.get(11).setText(rs.getString("Gender"));
                formFields.get(12).setText(rs.getString("CivilStatus"));
                formFields.get(13).setText(rs.getString("Citizenship"));
                formFields.get(14).setText(rs.getString("OtherCitizenship"));
                formFields.get(15).setText(rs.getString("MotherMaidenName"));
                formFields.get(16).setText(rs.getString("FatherName"));

                tinFieldSidebar.setText(rs.getString("TaxpayerTIN"));
                registeringOfficeSidebar.setText(rs.getString("RegisteringOffice"));
                rdoCodeSidebar.setText(rs.getString("RDOCode"));

            } else {
                JOptionPane.showMessageDialog(this, "No taxpayer found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading taxpayer data: " + e.getMessage());
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
    
    private boolean saveTaxpayerData() {
        String updateQuery = "UPDATE taxpayer SET "
                + "TaxPayerName = ?, "
                + "TaxpayerType = ?, "
                + "TaxPayerClassification = ?, "
                + "RegisteringOffice = ?, "
                + "BirRegistrationDate = ?, "
                + "PhilsysCardNumber = ?, "
                + "IncomeTaxRateOption = ?, "
                + "Dateofbirth = ?, "
                + "PlaceOfBirth = ?, "
                + "Gender = ?, "
                + "CivilStatus = ?, "
                + "Citizenship = ?, "
                + "OtherCitizenship = ?, "
                + "MotherMaidenName = ?, "
                + "FatherName = ? "
                + "WHERE email = ?";  // or WHERE TaxpayerTIN = ?

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            // Fill parameters based on formFields indexes
            stmt.setString(1, formFields.get(0).getText());  // TaxPayerName
            stmt.setString(2, formFields.get(1).getText());  // TaxpayerType
            stmt.setString(3, formFields.get(2).getText());  // TaxPayerClassification
            stmt.setString(4, formFields.get(3).getText());  // RegisteringOffice

            // Parse and set BirRegistrationDate (Date type)
            String birDateStr = formFields.get(4).getText();
            if (!birDateStr.isEmpty()) {
                java.sql.Date birDate = java.sql.Date.valueOf(birDateStr); // Format: "yyyy-[m]m-[d]d"
                stmt.setDate(5, birDate);
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setString(6, formFields.get(5).getText());  // PhilsysCardNumber
            // Skipping formFields 6 & 7 because you set empty strings (adjust if needed)

            stmt.setString(7, formFields.get(8).getText());  // IncomeTaxRateOption

            // Dateofbirth
            String dobStr = formFields.get(9).getText();
            if (!dobStr.isEmpty()) {
                java.sql.Date dob = java.sql.Date.valueOf(dobStr);
                stmt.setDate(8, dob);
            } else {
                stmt.setNull(8, java.sql.Types.DATE);
            }

            stmt.setString(9, formFields.get(10).getText());  // PlaceOfBirth
            stmt.setString(10, formFields.get(11).getText()); // Gender
            stmt.setString(11, formFields.get(12).getText()); // CivilStatus
            stmt.setString(12, formFields.get(13).getText()); // Citizenship
            stmt.setString(13, formFields.get(14).getText()); // OtherCitizenship
            stmt.setString(14, formFields.get(15).getText()); // MotherMaidenName
            stmt.setString(15, formFields.get(16).getText()); // FatherName

            stmt.setString(16, userEmail);  // use email or appropriate key to identify row

            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


}