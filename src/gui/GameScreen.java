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
	public static int width, height;
	List<Drawable> sprites;
	GameMap gm;
	Window w;
	
	boolean running;
	
	public GameScreen(Window w, int width, int height) {
		this.w = w;
		sprites = new LinkedList<Drawable>();
		this.width = width;
		this.height = height;
		
		Dimension s = new Dimension(width, height);
		
		this.setMinimumSize(s);
		this.setMaximumSize(s);
		this.setPreferredSize(s);
		
	}
	
	public Player getChar() {
		if(gm != null) return this.gm.getChar();
		return null;
	}
	
	public void play() {
		sprites.clear();
		GameMap add = new GameMap(30, 30);
		
		addSprite(add);
		addGameMap(add);
		
		w.add(this);
//		w.start();
	}
	
	public void addGameMap(GameMap gm) {
		this.gm = gm;
	}
	
	public void addTitleScreen(TitleScreen ts) {
		w.add(ts);
	}
	
	public void addSprite(Drawable d) {
		this.sprites.add(d);
	}
	
	public void run() {
		running = true;
		while(running) {
			long time = System.currentTimeMillis();
			if(this.gm != null) {
				this.gm.tick();
				if(getChar().health < 0) {
					w.frame.removeKeyListener(getChar());
					this.sprites.remove(gm);
					this.gm = null;
					TitleScreen ts = new TitleScreen(w);
//					this.sprites.add(ts);
//					w.frame.addKeyListener(ts);
					w.add(ts);
				}
			}
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
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, width, height);
		if(gm != null) {
			int dx = getChar().x * Drawable.SCALE - width/2 + 4*Drawable.SCALE;
			int dy = getChar().y * Drawable.SCALE - height/2 + 4*Drawable.SCALE;
			g.translate(-dx, -dy);
			for(Drawable d : sprites) {
				d.draw(g);
			}
			g.translate(dx, dy);
		} else {
			for(Drawable d : sprites) {
				d.draw(g);
			}
		}
		
	}

}
