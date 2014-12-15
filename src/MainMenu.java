package Main;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenu extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		// set the visibility of the JFrame so we can see it
		new MainMenu().setVisible(true);
		
	}
	
	private MainMenu() {
		// runs super in JFrame which names the window, sets the size, prevents resizing and lets the jframe exit
		super("Main Menu");
		setSize(600, 600); //1024x768
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Flowlayout places buttons in a good position
		setLayout(new FlowLayout());
		// creates start and instruction buttons to open those respective pages.
		JButton startButton = new JButton("Start Game");
		add(startButton);
		startButton.addActionListener(this);
		
		JButton instructButton = new JButton("Instructions");
		add(instructButton);
		
		
		
		
		
		// action listener -checks for an event to be called and runs code when it is.
		
	}

	@Override
	// functions that are called when you click on button
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
	}
}
