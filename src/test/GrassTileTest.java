package test;

import game.GameMap;
import gui.GameScreen;
import gui.Window;

public class GrassTileTest {
	public static void main(String[] args) {
		Window w = new Window("test");
		GameScreen gs = new GameScreen(800, 600);
		GameMap gm = new GameMap(20, 20);
		
		w.add(gs);
		gs.addSprite(gm);
		
		w.display();
	}
}