package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.Drawable;

public class Character implements Drawable, KeyListener, MouseListener{
	int x, y, w, h;
	Image sprite;
	
	public Character(String spritePath) {
		this.x = 0;
		this.y = 0;
		try {
			sprite = ImageIO.read(new File(spritePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.w = 8;
		this.h = 8;
	}
	
	@Override
	public void draw(Graphics g) {
		int dx = x * Drawable.SCALE * w;
		int dy = y * Drawable.SCALE * h;
		g.translate(dx, dy);
		g.drawImage(sprite, 0, 0, w*Drawable.SCALE, h*Drawable.SCALE, 0, 0, w, h, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
