package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import game.Button;
import game.GameMap;
import game.Player;

public class GameScreen extends JComponent implements Runnable, MouseListener{
	public static int width, height;
	List<Drawable> sprites;
	GameMap gm;
	Window w;
	
	List<Button> buttons;
	
	boolean running;
	
	public GameScreen(Window w, int width, int height) {
		this.w = w;
		sprites = new LinkedList<Drawable>();
		this.buttons = new ArrayList<Button>();
		GameScreen.width = width;
		GameScreen.height = height;
		
		Dimension s = new Dimension(width, height);
		
		this.setMinimumSize(s);
		this.setMaximumSize(s);
		this.setPreferredSize(s);
		
		String pre = "./res/tool";
		String post = ".png";
		String sel = "Selected";
		
		String[] tools = new String[]{"Hoe", "Sickle", "Bag", "Tree", "Hay", "Floor"};
		String[] nPath = new String[6];
		String[] sPath = new String[6];
		
		for(int i = 0; i < 6; i++) {
			nPath[i] = pre+tools[i]+post;
			sPath[i] = pre+tools[i]+sel+post;
			Button b = new Button(nPath[i], sPath[i], 
					(width/Drawable.SCALE), 
					1, i);
			b.x -= b.w + 2;
			b.y += i*(b.h + 1);
			this.buttons.add(b);
		}
		buttons.get(0).toggleSelect();
		
	}
	
	public Player getChar() {
		if(gm != null) return this.gm.getChar();
		return null;
	}
	
	public void play() {
		sprites.clear();
		GameMap add = new GameMap(30, 30);
		List<Drawable> ds = add.getChar().hud;
		for(int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			ds.add(b);
		}
		
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
				Player c = getChar();
				if(c.health < 0) {
					int score = gm.score / 100;
					w.frame.removeKeyListener(getChar());
					this.sprites.remove(gm);
					this.gm = null;
					TitleScreen ts = new TitleScreen(w);
					ts.score = score;
//					this.sprites.add(ts);
//					w.frame.addKeyListener(ts);
					w.add(ts);
				}
			}
			if(w.ts != null) {
				w.ts.timer--;
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
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.CYAN);
		if(gm != null && gm.timeLeft < 100) {
			g.setColor(Color.BLUE);
		}
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if(getChar() != null) {
			Player p = getChar();
			for(Button b : this.buttons) {
				boolean hit = b.inside(e.getX(), e.getY());
				if(hit) {
					p.setTool(b.getData());
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
