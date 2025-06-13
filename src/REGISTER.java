import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class REGISTER extends JButton implements ActionListener {

    private JFrame mainframe;
    private Connection connection;

    public REGISTER(JFrame frame) {
        mainframe = frame;
        
        try {
            String url = "jdbc:mysql://localhost:3306/employer_name";
            String user = "root";
            String password = "Vongabriel31!";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainframe, "Failed to connect to database", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        setText("Create an Account");
        setBounds(300, 600, 250, 100);
        addActionListener(this);
        setFocusable(false);
        setFont(new Font("DM Sans", Font.PLAIN, 14));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainframe.setVisible(false);

        ImageIcon originalImage = new ImageIcon("C:\\Users\\VON GABRIEL COSTUNA\\git\\OOP\\LOGO.png");

        JFrame frame = new JFrame("Bloc - Register");
        frame.setIconImage(originalImage.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1400, 900);
        frame.setMinimumSize(new Dimension(1200, 800));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title (large, bold, elegant)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        JLabel title = new JLabel("Create a BLOC account", SwingConstants.CENTER);
        title.setFont(new Font("DM Sans", Font.BOLD, 36));
        title.setForeground(new Color(0x111827)); // dark neutral text
        contentPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Inputs data

        JTextField tin = new JTextField();
        JTextField rdoCode = new JTextField();
        JTextField fname = new JTextField();
        JTextField mname = new JTextField();
        JTextField lname = new JTextField();
        JTextField suffix = new JTextField();
        JTextField dob = new JTextField();
        JTextField placeOfBirth = new JTextField();

        // Gender dropdown (modern style)
        String[] genderOptions = {"Male", "Female"};
        JComboBox<String> genderDropdown = new JComboBox<>(genderOptions);
        genderDropdown.setBackground(Color.WHITE);

        // Civil Status dropdown
        String[] civilStatusOptions = {"Single", "Married", "Widowed", "Legally Separated"};
        JComboBox<String> civilStatusDropdown = new JComboBox<>(civilStatusOptions);
        civilStatusDropdown.setBackground(Color.WHITE);

        JTextField email = new JTextField();
        JPasswordField password = new JPasswordField();
        JPasswordField confirm = new JPasswordField();

        // Right column inputs
        String[] registeringOfficeOptions = {"Head Office", "Branch Office", "Facility"};
        JComboBox<String> registeringOfficeDropdown = new JComboBox<>(registeringOfficeOptions);
        registeringOfficeDropdown.setBackground(Color.WHITE);

        JTextField philsysCardNumber = new JTextField();
        JTextField localResidence = new JTextField();
        JTextField businessResidence = new JTextField();
        JTextField fathersName = new JTextField();
        JTextField mothersMaidenName = new JTextField();
        JTextField birRegistrationDate = new JTextField();
        JTextField citizenship = new JTextField();
        JTextField otherCitizenship = new JTextField();

        // Taxpayer Type dropdown
        String[] taxpayerTypes = {
            "Single Proprietorship Only (Resident Citizen)",
            "Resident Alien - Single Proprietorship",
            "Resident Alien - Professional",
            "Professional - Licensed (PRC, IBP)",
            "Professional - In General",
            "Professional and Single Proprietor",
            "Mixed Income Earner - Compensation Income Earner & Single Proprietor",
            "Mixed Income Earner - Compensation Income Earner & Professional",
            "Mixed Income Earner - Compensation Income Earner, Single Proprietorship & Professional",
            "Non-Resident Alien Engaged in Trade/Business",
            "Estate - Filipino Citizen",
            "Estate - Foreign National",
            "Trust - Filipino Citizen",
            "Trust - Foreign National"
        };
        JComboBox<String> taxpayerTypeDropdown = new JComboBox<>(taxpayerTypes);
        taxpayerTypeDropdown.setMaximumRowCount(8);
        taxpayerTypeDropdown.setBackground(Color.WHITE);

        // Taxpayer Classification dropdown
        String[] taxpayerClassifications = {"Micro", "Small", "Medium", "Large"};
        JComboBox<String> taxpayerClassificationDropdown = new JComboBox<>(taxpayerClassifications);
        taxpayerClassificationDropdown.setBackground(Color.WHITE);

        // Income tax option dropdown
        String[] incomeTaxOptions = {"Yes", "No"};
        JComboBox<String> incomeTaxDropdown = new JComboBox<>(incomeTaxOptions);
        incomeTaxDropdown.setBackground(Color.WHITE);

        int row = 1;

        // Using addLabeledField helper to layout fields

        addLabeledField("TIN", tin, contentPanel, gbc, 0, row);
        addLabeledField("Registering Office", registeringOfficeDropdown, contentPanel, gbc, 2, row++);
        
        addLabeledField("RDO Code", rdoCode, contentPanel, gbc, 0, row);
        addLabeledField("Philsys Card Number", philsysCardNumber, contentPanel, gbc, 2, row++);
        
        addLabeledField("First Name", fname, contentPanel, gbc, 0, row);
        addLabeledField("Taxpayer Type", taxpayerTypeDropdown, contentPanel, gbc, 2, row++);
        
        addLabeledField("Middle Name", mname, contentPanel, gbc, 0, row);
        addLabeledField("Taxpayer Classification", taxpayerClassificationDropdown, contentPanel, gbc, 2, row++);
        
        addLabeledField("Last Name", lname, contentPanel, gbc, 0, row);
        addLabeledField("Local Residence", localResidence, contentPanel, gbc, 2, row++);
        
        addLabeledField("Suffix", suffix, contentPanel, gbc, 0, row);
        addLabeledField("Business Residence", businessResidence, contentPanel, gbc, 2, row++);
        
        addLabeledField("Date of Birth", dob, contentPanel, gbc, 0, row);
        addLabeledField("Father's Name", fathersName, contentPanel, gbc, 2, row++);
        
        addLabeledField("Place of Birth", placeOfBirth, contentPanel, gbc, 0, row);
        addLabeledField("Mother's Maiden Name", mothersMaidenName, contentPanel, gbc, 2, row++);
        
        addLabeledField("Gender", genderDropdown, contentPanel, gbc, 0, row);
        addLabeledField("BIR Registration Date", birRegistrationDate, contentPanel, gbc, 2, row++);
        
        addLabeledField("Civil Status", civilStatusDropdown, contentPanel, gbc, 0, row);
        addLabeledField("Availing of the 8% income tax rate option?", incomeTaxDropdown, contentPanel, gbc, 2, row++);
        
        addLabeledField("Email", email, contentPanel, gbc, 0, row);
        addLabeledField("Citizenship", citizenship, contentPanel, gbc, 2, row++);
        
        addLabeledField("Password", password, contentPanel, gbc, 0, row);
        addLabeledField("Other Citizenship", otherCitizenship, contentPanel, gbc, 2, row++);
        
        addLabeledField("Confirm Password", confirm, contentPanel, gbc, 0, row++);

        // Register button
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 4;
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(138, 43, 226));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setFont(new Font("DM Sans", Font.BOLD, 16));
        registerBtn.setBorderPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setOpaque(true);
        registerBtn.setContentAreaFilled(true);
        registerBtn.setRolloverEnabled(false);
        registerBtn.setPreferredSize(new Dimension(200, 40));
        contentPanel.add(registerBtn, gbc);
        
        registerBtn.addActionListener(ev -> {
            try {
                String tinVal = tin.getText().trim();
                String fnameVal = fname.getText().trim();
                String mnameVal = mname.getText().trim();
                String lnameVal = lname.getText().trim();
                String dobText = dob.getText().trim();
                String civilVal = civilStatusDropdown.getSelectedItem().toString().trim();
                String genderVal = genderDropdown.getSelectedItem().toString().trim();

                String emailVal = email.getText().trim();
                String passwordVal = new String(password.getPassword());
                String confirmVal = new String(confirm.getPassword());
                String rdoCodeVal = rdoCode.getText().trim();

                String taxpayerTypeVal = (String) taxpayerTypeDropdown.getSelectedItem();
                String taxpayerClassificationVal = (String) taxpayerClassificationDropdown.getSelectedItem();
                String motherMaidenVal = mothersMaidenName.getText().trim();
                String fatherNameVal = fathersName.getText().trim();
                String birRegDateText = birRegistrationDate.getText().trim();
                String incomeTaxOptionVal = (String) incomeTaxDropdown.getSelectedItem();
                String registeringOfficeVal = (String) registeringOfficeDropdown.getSelectedItem();
                String philsysCardNumberVal = philsysCardNumber.getText().trim();
                String placeOfBirthVal = placeOfBirth.getText().trim();
                String localResidenceVal = localResidence.getText().trim();
                String businessResidenceVal = businessResidence.getText().trim();

                if (!passwordVal.equals(confirmVal)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.sql.Date dobDate = java.sql.Date.valueOf(dobText);
                java.sql.Date birRegDate = java.sql.Date.valueOf(birRegDateText);

                String fullName = fnameVal + " " + (mnameVal.isEmpty() ? "" : mnameVal + " ") + lnameVal;
                fullName = fullName.trim();

                int rdoCodeInt;
                try {
                    rdoCodeInt = Integer.parseInt(rdoCodeVal);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frame, "RDO Code must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = insertTaxpayerToDatabase(
                    tinVal, fullName, dobDate, civilVal, genderVal, emailVal, passwordVal, rdoCodeInt,
                    taxpayerTypeVal, taxpayerClassificationVal, motherMaidenVal, fatherNameVal,
                    birRegDate, incomeTaxOptionVal, registeringOfficeVal, philsysCardNumberVal, placeOfBirthVal,
                    citizenship.getText().trim(), otherCitizenship.getText().trim(),
                    localResidenceVal, businessResidenceVal
                );

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    SwingUtilities.invokeLater(() -> {
                        try {
                            TAX_INFO taxInfo = new TAX_INFO(emailVal);
                            taxInfo.setVisible(true);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                "Failed to open Taxpayer Information page.",
                                "Navigation Error",
                                JOptionPane.ERROR_MESSAGE);
                            mainframe.setVisible(true);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Date Format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back button below Register
        gbc.gridy++;
        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(new Color(138, 43, 226));
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("DM Sans", Font.BOLD, 16));
        backBtn.setBorderPainted(true);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setOpaque(true);
        backBtn.setContentAreaFilled(true);
        backBtn.setPreferredSize(new Dimension(200, 40));
        contentPanel.add(backBtn, gbc);

        backBtn.addActionListener(ev -> {
            frame.dispose();
            mainframe.setVisible(true);
        });

        frame.add(new JScrollPane(contentPanel));
        frame.setVisible(true);
    }

    // Helper method to add label and field side-by-side at given row and column
    private void addLabeledField(String labelText, JComponent field, JPanel panel, GridBagConstraints gbc, int column, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("DM Sans", Font.PLAIN, 14));

        if (column == 0) {
            field.setPreferredSize(new Dimension(180, 28));
        } else if (column == 2) {
            field.setPreferredSize(new Dimension(350, 28));
        }

        gbc.gridx = column;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = column + 1;
        panel.add(field, gbc);
    }

    public boolean insertTaxpayerToDatabase(
            String tin,
            String fullName,
            java.sql.Date dob,
            String civilStatus,
            String gender,
            String email,
            String password,
            int rdoCode,
            String taxpayerType,
            String taxpayerClassification,
            String motherMaidenName,
            String fatherName,
            java.sql.Date birRegistrationDate,
            String incomeTaxOption,
            String registeringOffice,
            String philsysCardNumber,
            String placeOfBirth,
            String citizenship,
            String otherCitizenship,
            String localResidence,
            String businessResidence
        ) {
        try {
        	String sql = "INSERT INTO taxpayer (" +
                    "TaxpayerTIN, TaxPayerName, DateOfBirth, CivilStatus, Gender, Email, Password, RdoCode, " +
                    "TaxpayerType, TaxPayerClassification, MotherMaidenName, FatherName, BIRRegistrationDate, " +
                    "IncomeTaxRateOption, RegisteringOffice, PhilsysCardNumber, PlaceOfBirth, Citizenship, " +
                    "OtherCitizenship, LocalResidenceAddress, BusinessResidenceAddress) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tin);
            ps.setString(2, fullName);
            ps.setDate(3, dob);
            ps.setString(4, civilStatus);
            ps.setString(5, gender);
            ps.setString(6, email);
            ps.setString(7, password);
            ps.setInt(8, rdoCode);
            ps.setString(9, taxpayerType);
            ps.setString(10, taxpayerClassification);
            ps.setString(11, motherMaidenName);
            ps.setString(12, fatherName);
            ps.setDate(13, birRegistrationDate);
            ps.setString(14, incomeTaxOption);
            ps.setString(15, registeringOffice);
            ps.setString(16, philsysCardNumber);
            ps.setString(17, placeOfBirth);
            ps.setString(18, citizenship);
            ps.setString(19, otherCitizenship);
            ps.setString(20, localResidence);
            ps.setString(21, businessResidence);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
