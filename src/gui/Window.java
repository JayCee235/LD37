package gui;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import game.Tile;

public class Window {
	JFrame frame;
	GameScreen gs;
	TitleScreen ts;
	
	public Window(String name) {
		this.frame = new JFrame(name);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/res/toolSickle.png"));
		
		frame.setIconImage(icon.getImage());
	}
	
	public void display() {		
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}

	
	public void start() {
		gs.running = false;
		Thread gg = new Thread(gs);
		gg.start();
	}
	
	public void add(GameScreen gs) {
		if(this.ts != null) this.frame.removeKeyListener(this.ts);
		if(this.gs != null) this.frame.removeKeyListener(this.gs.getChar());
		this.gs = gs;
		this.frame.add(gs);
		
		this.frame.addKeyListener(gs.getChar());
		gs.addMouseListener(gs);
	}
	
	public void add(TitleScreen ts) {
		if(this.ts != null) this.frame.removeKeyListener(this.ts);
		if(this.gs != null) {
			this.frame.removeKeyListener(this.gs.getChar());
			this.gs.addSprite(ts);
			gs.stopMusic();
		}
		this.ts = ts;
		this.frame.addKeyListener(ts);
	}

	public void addListener(KeyListener ks) {
		frame.addKeyListener(ks);
	}
	
	
}
