package test;

import game.Tile;
import gui.Window;

public class WindowTest {
	public static void main(String[] args) {
		Tile.load();
		Window w = new Window("Test");
		w.display();
	}
}
