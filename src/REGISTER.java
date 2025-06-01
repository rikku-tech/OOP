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
        frame.setSize(1200, 800); // Match login and main window size
        frame.setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 20, 6, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel title = new JLabel("Create a BLOC account", SwingConstants.CENTER);
        title.setFont(new Font("DM Sans", Font.BOLD, 25));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++; // move to next row after title

        JTextField tin = new JTextField();
        addLabeledField("TIN*", tin, contentPanel, gbc);

        JTextField fname = new JTextField();
        addLabeledField("First Name*", fname, contentPanel, gbc);

        // Custom Icon for the checkbox
        Icon customCheckIcon = new Icon() {
            private final int size = 16;

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                AbstractButton button = (AbstractButton) c;
                ButtonModel model = button.getModel();

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw the box background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(x, y, size, size, 4, 4);

                if (model.isSelected()) {
                    g2.setColor(Color.decode("#0A2353")); // Dark blue background
                    g2.fillRoundRect(x, y, size, size, 4, 4);

                    // Draw the check mark
                    g2.setStroke(new BasicStroke(2));
                    g2.setColor(Color.WHITE);
                    g2.drawLine(x + 4, y + 8, x + 7, y + 11);
                    g2.drawLine(x + 7, y + 11, x + 12, y + 5);
                }

                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };

        JCheckBox noMid = new JCheckBox("I have no middle name");
        noMid.setFocusPainted(false);
        noMid.setIcon(customCheckIcon);
        noMid.setSelectedIcon(customCheckIcon);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        contentPanel.add(noMid, gbc);
        gbc.gridwidth = 1;

        JTextField mname = new JTextField();
        addLabeledField("Middle Name*", mname, contentPanel, gbc);

        JTextField lname = new JTextField();
        addLabeledField("Last Name*", lname, contentPanel, gbc);

        JTextField suffix = new JTextField();
        addLabeledField("Suffix", suffix, contentPanel, gbc);

        JTextField dob = new JTextField();
        addLabeledField("Date of Birth*", dob, contentPanel, gbc);

        JTextField civil = new JTextField();
        addLabeledField("Civil Status*", civil, contentPanel, gbc);

        JTextField gender = new JTextField();
        addLabeledField("Gender*", gender, contentPanel, gbc);

        JTextField email = new JTextField();
        addLabeledField("Email*", email, contentPanel, gbc);

        JPasswordField password = new JPasswordField();
        addLabeledField("Password*", password, contentPanel, gbc);

        JPasswordField confirm = new JPasswordField();
        addLabeledField("Confirm Password*", confirm, contentPanel, gbc);

        // Register Button styled like login page
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(138, 43, 226));  // purple background
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

        // Back Button styled similar but white background and purple text
        gbc.gridy++;
        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(new Color(138, 43, 226));  // purple text
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("DM Sans", Font.BOLD, 16));
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setOpaque(true);
        backBtn.setContentAreaFilled(true);
        backBtn.setRolloverEnabled(false);
        backBtn.setPreferredSize(new Dimension(200, 40));
        contentPanel.add(backBtn, gbc);

        backBtn.addActionListener(ev -> {
            frame.dispose();
            mainframe.setVisible(true);
        });

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        frame.setContentPane(scrollPane);
        frame.setVisible(true);

        // Reopen main window on close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                mainframe.setVisible(true);
            }
        });
    }

    private void addLabeledField(String labelText, JComponent field, JPanel panel, GridBagConstraints gbc) {
        JLabel label;

        if (labelText.endsWith("*")) {
            String mainText = labelText.substring(0, labelText.length() - 1);
            label = new JLabel("<html>" + mainText + "<font color='red'>*</font></html>");
        } else {
            label = new JLabel(labelText);
        }

        label.setFont(new Font("DM Sans", Font.PLAIN, 14));

        // Add label at current position
        gbc.gridx = 0;
        panel.add(label, gbc);

        // Add field next to label
        gbc.gridx = 1;
        panel.add(field, gbc);

        // Reset x for next label-field pair and move to next row
        gbc.gridx = 0;
        gbc.gridy++;
    }
}
