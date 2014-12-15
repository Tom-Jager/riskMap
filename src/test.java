

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class test extends JFrame {

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		gs.setFullScreenWindow(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		System.out.println(frame.getWidth());

	}
}
