package gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Tile;

public class TitleScreen implements Drawable, KeyListener{
	
	Tile[][] tiles;
	Font f;
	Window w;
	
	public TitleScreen(Window w) {
		this.w = w;
		f = new Font("./res/font.png", "abcdefghijklmnopqrstuvwxyz?!. 1234567890", 1, 8, 10, 4);
		tiles = new Tile[30][10];
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 10; j++) {
				Tile add = new Tile(Tile.GRASS);
				if(Math.random() < 0.8) {
					add.type = Tile.DIRT;
				}
				if(Math.random() < 0.6) {
					add.type = Tile.SNOW;
				}
				tiles[i][j] = add;
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		g.translate(8*Drawable.SCALE, 8*Drawable.SCALE);
		f.draw(g, "Final");
		g.translate(-8*Drawable.SCALE, -8*Drawable.SCALE);
		g.translate(17*Drawable.SCALE, 17*Drawable.SCALE);
		f.draw(g, "Harvest");
		g.translate(-17*Drawable.SCALE, -17*Drawable.SCALE);
		g.translate(1 * Drawable.SCALE, (4*9-1) * Drawable.SCALE);
		f.draw(g, "Press any key to start");
		g.translate(-1 * Drawable.SCALE, -(4*9-1) * Drawable.SCALE);
		
		g.translate(0, GameScreen.height/2);		
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 10; j++) {
				int dx = i * Drawable.SCALE * Tile.WIDTH;
				int dy = j * Drawable.SCALE * Tile.HEIGHT;
				g.translate(dx, dy);
				tiles[i][j].draw(g);
				g.translate(-dx, -dy);
			}
		}
		g.translate(0, -GameScreen.height/2);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		w.gs.play();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}