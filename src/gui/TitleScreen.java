package gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Tile;

public class TitleScreen implements Drawable, KeyListener{
	
	Tile[][] tiles;
	Font f;
	Window w;
	int xx, yy;
	int score;
	
	public int timer;
	
	public TitleScreen(Window w) {
		this.w = w;
		timer = 100;
		f = new Font("./res/font.png", "abcdefghijklmnopqrstuvwxyz?!. 1234567890", 1, 8, 10, 4);
		xx = 50;
		yy = 20;
		tiles = new Tile[xx][yy];
		for(int i = 0; i < xx; i++) {
			for(int j = 0; j < yy; j++) {
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
		if(timer <= 0) {
			g.translate(1 * Drawable.SCALE, (4*9-1) * Drawable.SCALE);
			f.draw(g, "Press any key to start");
			g.translate(-1 * Drawable.SCALE, -(4*9-1) * Drawable.SCALE);
		} else {
			g.translate(1 * Drawable.SCALE, (4*9-1) * Drawable.SCALE);
			f.draw(g, "Game Over... score " + score);
			g.translate(-1 * Drawable.SCALE, -(4*9-1) * Drawable.SCALE);
		}
		g.translate(0, GameScreen.height/2);		
		for(int i = 0; i < xx; i++) {
			for(int j = 0; j < yy; j++) {
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
		if(timer <= 0) w.gs.play();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
