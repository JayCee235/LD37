package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;

import audio.AudioPlayer;
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
	
	private AudioPlayer music;
	private boolean shouldPerform;
	
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
		
		String pre = "/res/tool";
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
			b.y += i*(b.h + 1) - 1;
			this.buttons.add(b);
		}
		
		try {
			this.music = new AudioPlayer("/res/Final Harvest.wav");
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startMusic() {
		try {
			music.play();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopMusic() {
		music.stop();
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
			b.deselect();
			ds.add(b);
		}
		add.getChar().setTool(0);
		
		startMusic();
		
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
				if(shouldPerform) {
					c.performAction();
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
			int x = getChar().x;
			int y = getChar().y;
			
			getChar().cx = x;
			getChar().cy = y;
			
			int dx = x * Drawable.SCALE - width/2 + 4*Drawable.SCALE;
			int dy = y * Drawable.SCALE - height/2 + 4*Drawable.SCALE;
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
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(getChar() != null) {
			boolean hitButton = false;
			Player p = getChar();
			for(Button b : this.buttons) {
				boolean hit = b.inside(e.getX(), e.getY());
				hitButton = hitButton || hit;
				if(hit) {
					p.setTool(b.getData());
				}
			}
			if(!hitButton) {
				shouldPerform = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		shouldPerform = false;
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
