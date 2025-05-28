import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.sun.tools.javac.Main;

public class REGISTER extends JButton implements  ActionListener {

private JFrame mainframe;

public REGISTER(JFrame frame){
	mainframe=frame;
	setText("Create an Account");
    setBounds(300, 600, 250, 100);
    addActionListener(this);
	setFocusable(false);
    setFont(new Font("DM Sans", Font.PLAIN, 14));
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