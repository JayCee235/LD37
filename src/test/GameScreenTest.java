package test;

import game.Tile;
import gui.GameScreen;
import gui.Window;

public class GameScreenTest {
	public static void main(String[] args) {
		Tile.load();
		Window w = new Window("Test");
		GameScreen gs = new GameScreen(w, 800, 600);
		w.add(gs);
		w.display();
	}
}
