package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

public class GameScreen extends JComponent{
	int width, height;
	List<Drawable> sprites;
	
	public GameScreen(int width, int height) {
		sprites = new LinkedList<Drawable>();
		this.width = width;
		this.height = height;
		
		Dimension s = new Dimension(width, height);
		
		this.setMinimumSize(s);
		this.setMaximumSize(s);
		this.setPreferredSize(s);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		for(Drawable d : sprites) {
			d.draw(g);
		}
	}

}
