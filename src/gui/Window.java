package gui;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	private JFrame frame;
	
	public Window(String name) {
		this.frame = new JFrame(name);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void display() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void add(GameScreen gs) {
		this.frame.add(gs);
	}
	
	
}
