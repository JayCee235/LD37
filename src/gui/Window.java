package gui;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import game.Tile;

public class Window {
	private JFrame frame;
	GameScreen gs;
	TitleScreen ts;
	
	public Window(String name) {
		this.frame = new JFrame(name);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void display() {		
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}
	
	public void start() {
		Thread gg = new Thread(gs);
		gg.start();
	}
	
	public void add(GameScreen gs) {
		if(this.ts != null) this.frame.removeKeyListener(this.ts);
		if(this.gs != null) this.frame.removeKeyListener(this.gs.getChar());
		this.gs = gs;
		this.frame.add(gs);
		
		this.frame.addKeyListener(gs.getChar());
	}
	
	public void add(TitleScreen ts) {
		if(this.ts != null) this.frame.removeKeyListener(this.ts);
		if(this.gs != null) {
			this.frame.removeKeyListener(this.gs.getChar());
			this.gs.addSprite(ts);
		}
		this.ts = ts;
		this.frame.addKeyListener(ts);
	}

	public void addListener(KeyListener ks) {
		frame.addKeyListener(ks);
	}
	
	
}
