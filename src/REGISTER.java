import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class REGISTER extends JButton implements ActionListener {

    private JFrame mainframe;

    public REGISTER(JFrame frame) {
        mainframe = frame;
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
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 20, 6, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // title spans all columns
        JLabel title = new JLabel("Create a BLOC account", SwingConstants.CENTER);
        title.setFont(new Font("DM Sans", Font.BOLD, 25));
        contentPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // First column fields
        JTextField tin = new JTextField();
        JTextField rdoCode = new JTextField();
        JTextField fname = new JTextField();
        JTextField mname = new JTextField();
        JTextField lname = new JTextField();
        JTextField suffix = new JTextField();
        JTextField dob = new JTextField();
        JTextField placeOfBirth = new JTextField();
        JTextField gender = new JTextField();
        JTextField civil = new JTextField();
        JTextField email = new JTextField();
        JPasswordField password = new JPasswordField();
        JPasswordField confirm = new JPasswordField();

        // Second column fields
        JTextField registeringOffice = new JTextField();
        JTextField tradeBusinessName = new JTextField();
        JTextField philsysCardNumber = new JTextField();

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

        String[] taxpayerClassifications = {"Micro", "Small", "Medium", "Large"};
        JComboBox<String> taxpayerClassificationDropdown = new JComboBox<>(taxpayerClassifications);
        taxpayerClassificationDropdown.setBackground(Color.WHITE);

        JTextField lineOfBusiness = new JTextField();
        JTextField fathersName = new JTextField();
        JTextField mothersMaidenName = new JTextField();
        JTextField birRegistrationDate = new JTextField();

        String[] incomeTaxOptions = {"Yes", "No"};
        JComboBox<String> incomeTaxDropdown = new JComboBox<>(incomeTaxOptions);
        incomeTaxDropdown.setBackground(Color.WHITE);

        JTextField citizenship = new JTextField();
        JTextField otherCitizenship = new JTextField();

        // Row counter starts at 1 after title
        int row = 1;

        // Add rows according to your order
        addLabeledField("TIN", tin, contentPanel, gbc, 0, row);
        addLabeledField("Registering Office", registeringOffice, contentPanel, gbc, 2, row++);
        
        addLabeledField("RDO Code", rdoCode, contentPanel, gbc, 0, row);
        addLabeledField("Trading Business Name", tradeBusinessName, contentPanel, gbc, 2, row++);
        
        addLabeledField("First Name", fname, contentPanel, gbc, 0, row);
        addLabeledField("Philsys Card Number", philsysCardNumber, contentPanel, gbc, 2, row++);
        
        addLabeledField("Middle Name", mname, contentPanel, gbc, 0, row);
        addLabeledField("Taxpayer Type", taxpayerTypeDropdown, contentPanel, gbc, 2, row++);
        
        addLabeledField("Last Name", lname, contentPanel, gbc, 0, row);
        addLabeledField("Taxpayer Classification", taxpayerClassificationDropdown, contentPanel, gbc, 2, row++);
        
        addLabeledField("Suffix", suffix, contentPanel, gbc, 0, row);
        addLabeledField("Line of Business", lineOfBusiness, contentPanel, gbc, 2, row++);
        
        addLabeledField("Date of Birth", dob, contentPanel, gbc, 0, row);
        addLabeledField("Father's Name", fathersName, contentPanel, gbc, 2, row++);
        
        addLabeledField("Place of Birth", placeOfBirth, contentPanel, gbc, 0, row);
        addLabeledField("Mother's Maiden Name", mothersMaidenName, contentPanel, gbc, 2, row++);
        
        addLabeledField("Gender", gender, contentPanel, gbc, 0, row);
        addLabeledField("BIR Registration Date", birRegistrationDate, contentPanel, gbc, 2, row++);
        
        addLabeledField("Civil Status", civil, contentPanel, gbc, 0, row);
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
            field.setPreferredSize(new Dimension(150, 28));
        } else if (column == 2) {
            field.setPreferredSize(new Dimension(400, 28));
        }

        gbc.gridx = column;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = column + 1;
        panel.add(field, gbc);
    }
}
