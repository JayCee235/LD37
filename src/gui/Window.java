package gui;
import java.awt.Dimension;

import javax.swing.JFrame;

import game.Tile;

public class Window {
	private JFrame frame;
	GameScreen gs;
	
	public Window(String name) {
		this.frame = new JFrame(name);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void display() {		
		frame.pack();
		frame.setLocationRelativeTo(null);
		Tile.load();
		
		Thread gg = new Thread(gs);
		gg.start();
		
		frame.setVisible(true);
	}
	
	public void add(GameScreen gs) {
		this.gs = gs;
		this.frame.add(gs);
		
		this.frame.addKeyListener(gs.getChar());
		this.frame.addMouseListener(gs.getChar());
	}
	
	
}
