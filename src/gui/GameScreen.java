package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import game.GameMap;
import game.Player;

public class GameScreen extends JComponent implements Runnable{
	int width, height;
	List<Drawable> sprites;
	GameMap gm;
	
	
	public GameScreen(int width, int height) {
		sprites = new LinkedList<Drawable>();
		this.width = width;
		this.height = height;
		
		Dimension s = new Dimension(width, height);
		
		this.setMinimumSize(s);
		this.setMaximumSize(s);
		this.setPreferredSize(s);
		
	}
	
	public Player getChar() {
		return this.gm.getChar();
	}
	
	public void addGameMap(GameMap gm) {
		this.gm = gm;
	}
	
	public void addSprite(Drawable d) {
		this.sprites.add(d);
	}
	
	public void run() {
		while(true) {
			long time = System.currentTimeMillis();
			this.gm.tick();
			this.repaint();
			
			long last = time;
			time = System.currentTimeMillis();
			try {
				long runTime = time - last;
				long timeSleep = 1000/60 - runTime;
				if(timeSleep < 0) {
					timeSleep = 0;
				}
				Thread.sleep(timeSleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int dx = getChar().x * Drawable.SCALE - width/2 + 4*Drawable.SCALE;
		int dy = getChar().y * Drawable.SCALE - height/2 + 4*Drawable.SCALE;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.translate(-dx, -dy);
		for(Drawable d : sprites) {
			d.draw(g);
		}
		g.translate(dx, dy);
	}

}
