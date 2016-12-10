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

public class Player implements Drawable, KeyListener, MouseListener{
	int x, y, w, h;
	int mx, my;
	Image sprite;
	GameMap gm;
	
	int cap = 0;
	int farmTimer = 0;
	
	boolean[] keysDown;
	
	public Player(String spritePath) {
		this.x = 0;
		this.y = 0;
		this.mx = 0;
		this.my = 0;
		
		this.keysDown = new boolean[255];
		try {
			sprite = ImageIO.read(new File(spritePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.w = 8;
		this.h = 8;
	}
	
	public Tile getTileOn() {
		return gm.getTile(this.x + 4, this.y + 4);
	}
	
	public void setTileOn(int s) {
		gm.setTile(s, x + 4, y + 4);
	}
	
	@Override
	public void draw(Graphics g) {
		int dx = x * Drawable.SCALE;
		int dy = y * Drawable.SCALE;
		g.translate(dx, dy);
		g.drawImage(sprite, 0, 0, w*Drawable.SCALE, h*Drawable.SCALE, 0, 0, w, h, null);
	}
	
	public void tick() {
		if(farmTimer > 0) {
			farmTimer--;
		}
		if(keysDown[KeyEvent.VK_W]) {
			this.my--;
			if(my < -cap) {
				my = 0;
				y--;
			}
		}
		if(keysDown[KeyEvent.VK_S]) {
			this.my++;
			if(my > cap) {
				my = 0;
				y++;
			}
		}
		if(keysDown[KeyEvent.VK_A]) {
			this.mx++;
			if(mx > cap) {
				mx = 0;
				x--;
			}
		}
		if(keysDown[KeyEvent.VK_D]) {
			this.mx--;
			if(mx < -cap) {
				mx = 0;
				x++;
			}
		}
		if(keysDown[KeyEvent.VK_SPACE]) {
			if(getTileOn().type == Tile.GRASS) {
				setTileOn(Tile.DIRT);
				farmTimer = 10;
			}
		}
		if(x < 0) {
			x = 0;
		}
		if(y < 0) {
			y = 0;
		}
		if(x > -8 + gm.tiles.length*8) {
			x = 8+gm.tiles.length*8;
		}
		if(y > -8 + gm.tiles[0].length*8) {
			y = 8+gm.tiles[0].length*8;
		}
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
		int code = e.getKeyCode();
		if(code >= 0 && code < 255) {
			keysDown[code] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code >= 0 && code < 255) {
			keysDown[code] = false;
		}
	}
	
}
