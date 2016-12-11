package executable;

import game.Tile;
import gui.GameScreen;
import gui.TitleScreen;
import gui.Window;

public class FinalHarvest {
	public static void main(String[] args) {
		int width = 800;
		int height = 600;
		
		try {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			width = 800;
			height = 600;
		}
		
		Tile.load();
		Window w = new Window("Final Harvest");
		GameScreen gs = new GameScreen(w, width, height);
		
		TitleScreen ts = new TitleScreen(w);
		ts.timer = 0;
		
		w.add(gs);
		gs.addTitleScreen(ts);
		
		w.display();
		w.start();
	}
}
