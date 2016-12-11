package test;

import game.Tile;
import gui.GameScreen;
import gui.TitleScreen;
import gui.Window;

public class TitleScreenTest {
	
	public static void main(String[] args) {
		Tile.load();
		Window w = new Window("Test");
		GameScreen gs = new GameScreen(w, 1000, 800);
		
		TitleScreen ts = new TitleScreen(w);
		
//		gs.addSprite(ts);
		
		w.add(gs);
		gs.addTitleScreen(ts);
//		w.add(ts);
		
		w.display();
		w.start();
	}
	
}
