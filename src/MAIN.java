import javax.swing.*;
import java.awt.*;

class MAIN {

    public static void main(String[] args) {
    	JFrame frame = new JFrame();

    	// Load original image
    	ImageIcon originalImage = new ImageIcon("C:\\Users\\Marick\\eclipse-workspace\\PROJECT_IM\\src\\LOGO .png");

    	// Resize to 200x200 pixels
    	Image resizedImage = originalImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    	ImageIcon resizedIcon = new ImageIcon(resizedImage);

    	// Set logo JLabel with resized icon
    	JLabel logo = new JLabel(resizedIcon);
    	logo.setBounds(655, 180, 200, 200);  
        // Create Buttons
        LOGIN btn1 = new LOGIN(frame);
        REGISTER btn2 = new REGISTER(frame);

        // Position buttons side-by-side
        btn1.setBounds(760, 545, 150, 50);
        btn2.setBounds(   600, 545, 150, 50);

        // Style LOGIN (violet background)
        btn1.setBackground(new Color(138, 43, 226));
        btn1.setForeground((Color.WHITE));


        // Set text color (foreground)
        btn1.setForeground(Color.WHITE);

        btn1.setFocusPainted(false);
        btn1.setBorderPainted(false);

        // Style REGISTER (white with thin black border)
        btn2.setBackground(Color.WHITE);
        btn2.setForeground(Color.BLACK);
        btn2.setFocusPainted(false);
        btn2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Welcome Text ("Welcome to Bloc") - split for styling
        JLabel welcomeText = new JLabel("Welcome to ");
        welcomeText.setFont(new Font("DM Sans", Font.BOLD, 55));
        welcomeText.setForeground(Color.BLACK);
        
        
        //underText
        JLabel underText = new JLabel("Your Tax Info, Organized and Accessible. ");
        underText.setFont(new Font("DM Sans", Font.BOLD, 20));
        underText.setForeground(Color.gray);
        
        JLabel blocText = new JLabel("BLOC");
        blocText.setFont(new Font("DM Sans", Font.BOLD, 55));
        blocText.setForeground(new Color(138, 43, 226, 200)); // Slightly transparent violet

        //label position
        welcomeText.setBounds(535, 400, 350, 80);
        blocText.setBounds(855, 400, 350, 80);
        underText.setBounds(575, 455, 750, 80);

        // Add components to frame
        frame.add(logo);
        frame.add(welcomeText);
        frame.add(blocText);
        frame.add(btn1);
        frame.add(btn2);
        frame.add(underText);

        // Frame config
        frame.setLayout(null);
        frame.setTitle("IM Project");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setIconImage(originalImage.getImage());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
