package game;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import gui.Drawable;

public class GameMap implements Drawable {
	Tile[][] tiles;
	Map<String, Tile> tileTypes;
	
	Character c;
	
	public GameMap(int x, int y) {
		tiles = new Tile[x][y];
		tileTypes = new HashMap<String, Tile>();
		tileTypes.put("testGrass", new TestGrassTile("./res/testGrass.png"));
		c = new Character("./res/testChar.png");
		c.x = 10;
		c.y = 10;
		
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				tiles[i][j] = tileTypes.get("testGrass");
			}
		}
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
		int cx = x / Tile.WIDTH / Drawable.SCALE;
		int cy = y / Tile.HEIGHT / Drawable.SCALE;
		
		return tiles[cx][cy];
	}
	
}
