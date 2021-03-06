package test;

import game.GameMap;
import game.Tile;
import gui.GameScreen;
import gui.Window;

public class GrassTileTest {
	public static void main(String[] args) {
		Tile.load();
		Window w = new Window("test");
		GameScreen gs = new GameScreen(w, 800, 600);
		GameMap gm = new GameMap(30, 30);
		
		gs.addSprite(gm);
		gs.addGameMap(gm);
		
		w.add(gs);
		
		w.display();
		w.start();
	}
}
