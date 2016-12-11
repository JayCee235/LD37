package game;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import gui.Drawable;
import gui.Font;

public class GameMap implements Drawable {
	Tile[][] tiles;
	
	Player c;
	
	Font f;
	
	int timeLeft;
	
	int snowPerTick;
	
	public GameMap(int x, int y) {
		
		timeLeft = 10;
		
		snowPerTick = 1;
		
		tiles = new Tile[x][y];
		c = new Player("./res/testChar.png");
		c.x = 10;
		c.y = 10;
		c.gm = this;
		
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				tiles[i][j] = new Tile(Tile.GRASS);
			}
		}
		f = new Font("./res/font.png", "abcdefghijklmnopqrstuvwxyz?!. 1234567890", 
				1, 8, 10, 4);
	}
	
	public Font getFont() {
		return f;
	}

	public void tick() {
		if(timeLeft > 0) timeLeft--;
		if(timeLeft == 0) {
			for(int i = 0; i < snowPerTick; i++) {
				int rx = (int) (tiles.length * Math.random());
				int ry = (int) (tiles[0].length * Math.random());
			
				Tile use = tiles[rx][ry];
				if(use.isOutside()) {
					if(use.type == Tile.SNOW || use.dmg > 0) {
						use.dmg--;
					} else {
						use.type = Tile.SNOW;
						use.dmg = 0;
					}
				} else {
					if(rx == 0 || ry == 0 || rx == tiles.length-1 || ry == tiles[0].length-1 ||
							tiles[rx-1][ry].isOutside() || tiles[rx+1][ry].isOutside() || tiles[rx][ry-1].isOutside() || tiles[rx][ry+1].isOutside()) {
						use.dmg++;
					}
				}
			}
			timeLeft = 5;
			if(Math.random() < 0.1/snowPerTick) {
				snowPerTick++;
			}
		}
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].tick();
			}
		}
		this.c.tick();
		
	}
	
	public Player getChar() {
		return this.c;
	}
	
	@Override
	public void draw(Graphics g) {
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				Tile toDraw = tiles[i][j];
				int dx = Tile.WIDTH * Drawable.SCALE * i;
				int dy = Tile.HEIGHT * Drawable.SCALE * j;
				g.translate(dx, dy);
				toDraw.draw(g);
				g.translate(-dx, -dy);
			}
		}
		c.draw(g);
	}
	
	public Tile getTile(int x, int y) {
		int cx = x / Tile.WIDTH;
		int cy = y / Tile.HEIGHT;
		
		return tiles[cx][cy];
	}
	
	public void setTile(int type, int x, int y) {
		int cx = x / Tile.WIDTH;
		int cy = y / Tile.HEIGHT;
		
		tiles[cx][cy].type = type;
		tiles[cx][cy].dmg = 0;
	}
	
}
