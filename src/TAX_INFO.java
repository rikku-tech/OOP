import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TAX_INFO extends JButton implements ActionListener {

    private JFrame mainframe;

    public TAX_INFO(JFrame frame) {
        mainframe = frame;
        setText("Tax Info");
        setBounds(300, 700, 250, 100);
        addActionListener(this);
        setFocusable(false);
        setFont(new Font("DM Sans", Font.PLAIN, 14));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainframe.setVisible(false);

        JFrame frame = new JFrame("Bloc - Taxpayer Information");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel title = new JLabel("Taxpayer Information", SwingConstants.CENTER);
        title.setFont(new Font("DM Sans", Font.BOLD, 25));
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JTextField tin = new JTextField();
        addLabeledField("TIN*", tin, panel, gbc);

        JTextField rdo = new JTextField();
        addLabeledField("RDO Code*", rdo, panel, gbc);

        JTextField taxpayerName = new JTextField();
        addLabeledField("Taxpayer Name*", taxpayerName, panel, gbc);

        JTextField address = new JTextField();
        addLabeledField("Address*", address, panel, gbc);

        JTextField taxType = new JTextField();
        addLabeledField("Tax Type*", taxType, panel, gbc);

        JTextField lineOfBusiness = new JTextField();
        addLabeledField("Line of Business*", lineOfBusiness, panel, gbc);

        // Submit Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton submitBtn = new JButton("Submit");
        stylePrimaryButton(submitBtn);
        panel.add(submitBtn, gbc);

        // Back Button
        gbc.gridy++;
        JButton backBtn = new JButton("Back");
        styleSecondaryButton(backBtn);
        panel.add(backBtn, gbc);

        backBtn.addActionListener(ev -> {
            frame.dispose();
            mainframe.setVisible(true);
        });

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        frame.setContentPane(scrollPane);
        frame.setVisible(true);

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

        gbc.gridx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(101, 99, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("DM Sans", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 40));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(101, 99, 255));
        button.setFocusPainted(false);
        button.setFont(new Font("DM Sans", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 40));
    }
}
