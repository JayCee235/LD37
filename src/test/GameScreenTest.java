package test;

import gui.GameScreen;
import gui.Window;

public class GameScreenTest {
	public static void main(String[] args) {
		Window w = new Window("Test");
		GameScreen gs = new GameScreen(800, 600);
		w.add(gs);
		w.display();
	}
}
