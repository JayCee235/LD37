package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Font {
	Image base;
	String chars;
	int offset;
	int size;
	
	int width, height;
	
	public Font(String path, String chars, int offset, int size, int w, int h) {
		try {
			this.base = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.chars = chars;
		this.offset = offset;
		this.size = size;
		this.width = w;
		this.height = h;
		
		
		
	}
	
	public void drawChar(Graphics g, char c) {
		char ch = Character.toLowerCase(c);
		int index = chars.indexOf(ch);
		int x = index % width;
		int y = index / width;
		
		int sx = (size+offset)*x;
		int sy = (size+offset)*y;
		
		g.drawImage(base, 0, 0, size*Drawable.SCALE, size*Drawable.SCALE, sx, sy, sx+size, sy+size, null);
	}
	
	public void draw(Graphics g, String s) {
		int dx = 0;
		for(int i = 0; i < s.length(); i++) {
			drawChar(g, s.charAt(i));
			g.translate((size + 1)*Drawable.SCALE, 0);
			dx += size + 1;
		}
		g.translate(-dx*Drawable.SCALE, 0);
	}
	
}