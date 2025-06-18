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
    private List<JComponent> formFields = new ArrayList<>();  // Hold JTextField and JComboBox together for form fields
    private boolean isEditing = false; // To track edit state
    private JTextField tinFieldSidebar;
    private JTextField rdoCodeSidebar;
    private String userEmail;
    private final String DB_URL = "jdbc:mysql://localhost:3306/employer_name";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "02162005me";

    public TAX_INFO(String email) {
        this.userEmail = email;
        

        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");

        setTitle("Taxpayer Information");
        setIconImage(originalImage.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
     // Style fix for JComboBox
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", Color.BLACK);
        UIManager.put("ComboBox.selectionBackground", new Color(200, 200, 255));
        UIManager.put("ComboBox.selectionForeground", Color.BLACK);


        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(getWidth(), 50));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        logoPanel.setBackground(Color.WHITE);

        ImageIcon rawIcon = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");
        Image scaledIcon = rawIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon headerIcon = new ImageIcon(scaledIcon);

        JLabel headerIconLabel = new JLabel(headerIcon);
        logoPanel.add(headerIconLabel);

        JLabel blocLabel = new JLabel("BLOC");
        blocLabel.setFont(new Font("DM Sans", Font.BOLD, 20));
        blocLabel.setForeground(new Color(0x122D70));
        logoPanel.add(blocLabel);

        JButton logoutButton = new JButton("Log out");
        logoutButton.setBackground(new Color(138, 43, 226));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutButton.setFont(new Font("Inter", Font.BOLD, 14));
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

        JPanel tinPanel = createLabeledFieldSmall("Taxpayer TIN", false);
        sidebarContentWrapper.add(tinPanel);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        tinFieldSidebar = getTextFieldFromPanel(tinPanel);

        JPanel rdoPanel = createLabeledFieldSmall("RDO Code", false);
        sidebarContentWrapper.add(rdoPanel);
        sidebarContentWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        rdoCodeSidebar = getTextFieldFromPanel(rdoPanel);

        JButton taxpayerInfoBtn = createSidebarButton("Taxpayer Information", true);
        JButton otherInfoBtn = createSidebarButton("Other Information", false);
        JButton businessInfoBtn = createSidebarButton("Business Information", false);

        taxpayerInfoBtn.addActionListener(e -> { /* no action */ });
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
        businessInfoBtn.addActionListener(e -> {
            dispose();
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

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // generous padding per design guidelines

        String[] taxpayerTypes = {"Single Proprietorship Only (Resident Citizen)",
        		"Resident Alien - Single Proprietorship",
        		"Resident Alien - Professional",
        		"Professional - Licensed (PRC, IBP)",
        		"Professional - In General"
        		,"Professional and Single Propprietor"
        		,"Mixed Income Earner - Compensation Income Earner & Single Proprietor",
        		"Mixed Income Earner - Compensation Income Earner & Professional",
        		"Mixed IncomeEarner - Compensation Income Earner, Single Proprietorship & Professional",
        		"Non-Resident Alien Engaged in Trade/Business","Estate - Filipino Citizen",
        		"Estate - Foreign National",
        		"Trust - Filipino Citizen",
        		" Trust - Foreign National"};
        
        String[] taxpayerClassifications = {"Micro", "Small", "Medium","Large"};
        String[] incomeTaxRateOptions = {"Yes", "No"};
        String[] civilStatuses = {"Single", "Married", "Widowed", "Divorced"};
        String[] genders = {"Male", "Female", "Other"};
        String[] registeringOffices = {"Head Office", "Branch Office", "Facility"};  // example options
   
        String[] labels = {
            "Taxpayer Name", "Taxpayer Type", "Taxpayer Classification",
            "Registering Office", "BIR Registration Date", "Philsys Card Number",
            "Local Residence", "Business Residence",
            "Availing of the 8% income tax rate option?",
            "Date of Birth", "Place of Birth", "Gender",
            "Civil Status", "Citizenship", "Other Citizenship",
            "Mother's Maiden Name", "Father's Name"
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // generous margin for pristine spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int col = 0;
        int row = 0;

        for (String label : labels) {
            gbc.gridx = col;
            gbc.gridy = row;

            if (label.equals("Taxpayer Type")) {
                JPanel labeledField = createLabeledComboBoxField(label, taxpayerTypes);
                formPanel.add(labeledField, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) labeledField.getComponent(1);
                comboBox.setEnabled(false);
                formFields.add(comboBox);
            } else if (label.equals("Taxpayer Classification")) {
                JPanel labeledField = createLabeledComboBoxField(label, taxpayerClassifications);
                formPanel.add(labeledField, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) labeledField.getComponent(1);
                comboBox.setEnabled(false);
                formFields.add(comboBox);
            } else if (label.equals("Registering Office")) {
                JPanel labeledField = createLabeledComboBoxField(label, registeringOffices);
                formPanel.add(labeledField, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) labeledField.getComponent(1);
                comboBox.setEnabled(false);
                formFields.add(comboBox);
            } else if (label.equals("Availing of the 8% income tax rate option?")) {
                JPanel labeledField = createLabeledComboBoxField(label, incomeTaxRateOptions);
                formPanel.add(labeledField, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) labeledField.getComponent(1);
                comboBox.setEnabled(false);
                formFields.add(comboBox);
            } else if (label.equals("Civil Status")) {
                JPanel labeledField = createLabeledComboBoxField(label, civilStatuses);
                formPanel.add(labeledField, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) labeledField.getComponent(1);
                comboBox.setEnabled(false);
                formFields.add(comboBox);
            } else if (label.equals("Gender")) {
                JPanel labeledField = createLabeledComboBoxField(label, genders);
                formPanel.add(labeledField, gbc);
                JComboBox<String> comboBox = (JComboBox<String>) labeledField.getComponent(1);
                comboBox.setEnabled(false);
                formFields.add(comboBox);
            } else {
                JPanel labeledField = createLabeledField(label);
                formPanel.add(labeledField, gbc);
                JTextField field = (JTextField) labeledField.getComponent(1);
                field.setEditable(false);
                field.setFocusable(false);
                formFields.add(field);
            }

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

        editButton.addActionListener(e -> {
            if (isEditing) {
                boolean success = saveTaxpayerData();
                if (success) {
                    isEditing = false;
                    for (JComponent comp : formFields) {
                        if (comp instanceof JTextField) {
                            ((JTextField) comp).setEditable(false);
                            ((JTextField) comp).setFocusable(false);
                        } else if (comp instanceof JComboBox) {
                            ((JComboBox<?>) comp).setEnabled(false);
                        }
                    }
                    editButton.setText("Edit");
                    JOptionPane.showMessageDialog(this, "Data saved successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save data. Please try again.");
                }
            } else {
                isEditing = true;
                for (JComponent comp : formFields) {
                    if (comp instanceof JTextField) {
                        ((JTextField) comp).setEditable(true);
                        ((JTextField) comp).setFocusable(true);
                    } else if (comp instanceof JComboBox) {
                        ((JComboBox<?>) comp).setEnabled(true);
                    }
                }
                editButton.setText("Save");
            }
        });

        add(formPanel, BorderLayout.CENTER);

        loadTaxpayerData(userEmail);

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

    private JPanel createLabeledComboBoxField(String labelText, String[] options) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Inter", Font.PLAIN, 12));

        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Inter", Font.PLAIN, 12));
        comboBox.setForeground(Color.BLACK);
        comboBox.setBackground(Color.WHITE);
        comboBox.setOpaque(true);
        comboBox.setFocusable(false);
        comboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        comboBox.setPreferredSize(new Dimension(150, 30));

        // Force black font color on disabled state using renderer
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setForeground(Color.BLACK);
                return c;
            }
        });

        comboBox.setEnabled(false);
        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);
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
        field.setFocusable(false);

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

    private void loadTaxpayerData(String email) {
        String query = "SELECT * FROM taxpayer WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                setFieldValue(0, rs.getString("TaxPayerName"));
                setComboBoxValue(1, rs.getString("TaxpayerType"));
                setComboBoxValue(2, rs.getString("TaxPayerClassification"));
                setComboBoxValue(3, rs.getString("RegisteringOffice"));
                setFieldValue(4, rs.getDate("BirRegistrationDate") != null ? rs.getDate("BirRegistrationDate").toString() : "");
                setFieldValue(5, rs.getString("PhilsysCardNumber"));
                setFieldValue(6, rs.getString("LocalResidenceAddress"));
                setFieldValue(7, rs.getString("BusinessResidenceAddress"));
                setComboBoxValue(8, rs.getString("IncomeTaxRateOption"));
                setFieldValue(9, rs.getDate("Dateofbirth") != null ? rs.getDate("Dateofbirth").toString() : "");
                setFieldValue(10, rs.getString("PlaceOfBirth"));
                setComboBoxValue(11, rs.getString("Gender"));
                setComboBoxValue(12, rs.getString("CivilStatus"));
                setFieldValue(13, rs.getString("Citizenship"));
                setFieldValue(14, rs.getString("OtherCitizenship"));
                setFieldValue(15, rs.getString("MotherMaidenName"));
                setFieldValue(16, rs.getString("FatherName"));

                tinFieldSidebar.setText(rs.getString("TaxpayerTIN"));
                rdoCodeSidebar.setText(rs.getString("RDOCode"));
            } else {
                JOptionPane.showMessageDialog(this, "No taxpayer found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading taxpayer data: " + e.getMessage());
        }
    }

    private void setFieldValue(int index, String value) {
        if (index >= 0 && index < formFields.size()) {
            JComponent comp = formFields.get(index);
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText(value);
            }
        }
    }

    private void setComboBoxValue(int index, String value) {
        if (index >= 0 && index < formFields.size()) {
            JComponent comp = formFields.get(index);
            if (comp instanceof JComboBox) {
                ((JComboBox<String>) comp).setSelectedItem(value);
            }
        }
    }

    private boolean saveTaxpayerData() {
        String updateQuery = "UPDATE taxpayer SET "
                + "TaxPayerName = ?, "
                + "TaxpayerType = ?, "
                + "TaxPayerClassification = ?, "
                + "RegisteringOffice = ?, "
                + "BirRegistrationDate = ?, "
                + "PhilsysCardNumber = ?, "
                + "LocalResidenceAddress = ?, "
                + "BusinessResidenceAddress = ?, "
                + "IncomeTaxRateOption = ?, "
                + "Dateofbirth = ?, "
                + "PlaceOfBirth = ?, "
                + "Gender = ?, "
                + "CivilStatus = ?, "
                + "Citizenship = ?, "
                + "OtherCitizenship = ?, "
                + "MotherMaidenName = ?, "
                + "FatherName = ? "
                + "WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, ((JTextField) formFields.get(0)).getText());
            stmt.setString(2, ((JComboBox<String>) formFields.get(1)).getSelectedItem().toString());
            stmt.setString(3, ((JComboBox<String>) formFields.get(2)).getSelectedItem().toString());
            stmt.setString(4, ((JComboBox<String>) formFields.get(3)).getSelectedItem().toString());

            String birDateStr = ((JTextField) formFields.get(4)).getText();
            if (!birDateStr.isEmpty()) {
                java.sql.Date birDate = java.sql.Date.valueOf(birDateStr);
                stmt.setDate(5, birDate);
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setString(6, ((JTextField) formFields.get(5)).getText());
            stmt.setString(7, ((JTextField) formFields.get(6)).getText());
            stmt.setString(8, ((JTextField) formFields.get(7)).getText());
            stmt.setString(9, ((JComboBox<String>) formFields.get(8)).getSelectedItem().toString());

            String dobStr = ((JTextField) formFields.get(9)).getText();
            if (!dobStr.isEmpty()) {
                java.sql.Date dob = java.sql.Date.valueOf(dobStr);
                stmt.setDate(10, dob);
            } else {
                stmt.setNull(10, java.sql.Types.DATE);
            }

            stmt.setString(11, ((JTextField) formFields.get(10)).getText());
            stmt.setString(12, ((JComboBox<String>) formFields.get(11)).getSelectedItem().toString());
            stmt.setString(13, ((JComboBox<String>) formFields.get(12)).getSelectedItem().toString());
            stmt.setString(14, ((JTextField) formFields.get(13)).getText());
            stmt.setString(15, ((JTextField) formFields.get(14)).getText());
            stmt.setString(16, ((JTextField) formFields.get(15)).getText());
            stmt.setString(17, ((JTextField) formFields.get(16)).getText());

            stmt.setString(18, userEmail);

            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}



