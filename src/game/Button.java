package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.Drawable;

public class Button implements Drawable{
	Image sprite;
	Image selected;
	
	boolean isSelected;
	
	public int w, h;
	
	public int x, y;
	
	int data;
	
	public Button(String path, String selectPath, int x, int y, int tool) {
		try {
			sprite = ImageIO.read(new File(path));
			selected = ImageIO.read(new File(selectPath));
			w = sprite.getWidth(null);
			h = sprite.getHeight(null);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.data = tool;
	}
	
	public void toggleSelect() {
		this.isSelected = !this.isSelected;
	}
	
	public void select() {
		this.isSelected = true;
	}
	
	public void deselect() {
		this.isSelected = false;
	}
	
	public boolean inside(int cx, int cy) {
		int dx = cx / Drawable.SCALE;
		int dy = cy / Drawable.SCALE;
		
//		System.out.println("dx: " + dx + " dy " + dy);
//		System.out.println("My x: "+ x + " My y: " + y);
//		System.out.println("My w: "+ w + " My h: " + h);
		
		boolean xBound = dx >= x && dx <= x + w;
		boolean yBound = dy >= y && dy <= y + h;
		
		return xBound && yBound;
	}
	
	public int getData() {
		return this.data;
	}

	@Override
	public void draw(Graphics g) {
		Image draw = isSelected ? selected : sprite;
		
		int sx = x*Drawable.SCALE;
		int sy = y*Drawable.SCALE;
		
		int sx2 = w*Drawable.SCALE + sx;
		int sy2 = h*Drawable.SCALE + sy;
		
		g.drawImage(draw, sx, sy, sx2, sy2, 
				0, 0, w, h, null);
	}
	
}
