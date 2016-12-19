package executable;

import java.awt.Dimension;
import java.awt.Toolkit;

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
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		GameScreen gs = new GameScreen(w, (int) s.getWidth(), (int) s.getHeight());
		w.setBorderless(true);
		
		TitleScreen ts = new TitleScreen(w);
		ts.timer = 0;
		
		w.add(gs);
		gs.addTitleScreen(ts);
		
		w.display();
		w.start();
	}
}
