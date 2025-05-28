import javax.swing.*;

import java.awt.Font;
import java.awt.event.*;

public class LOGIN extends JButton implements ActionListener {

private JFrame mainframe;//stored reference the main window
    public LOGIN(JFrame frame) {
       this.mainframe=frame;
        setText("Login");
        
        setBounds(20, 600, 250, 100);
        setFocusable(false);
        setFont(new Font("DM Sans", Font.PLAIN, 14));
        addActionListener(this);
        
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Close the main window
    	mainframe.setVisible(false);
        // Open a new window
        ImageIcon originalImage = new ImageIcon("C:\\Users\\Marick\\eclipse-workspace\\PROJECT_IM\\src\\123.png\\");

        JFrame frame = new JFrame("New Window");
        frame.setIconImage(originalImage.getImage());

        frame.setLayout(null);
        frame.setTitle("IM Project");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.addWindowListener(new WindowAdapter() {
        
        public void windowClosing(WindowEvent we) {
        	
        	
        	mainframe.setVisible(true);
        }
        		
        		
        		
        		
    });
        
        
        
        
    }
}