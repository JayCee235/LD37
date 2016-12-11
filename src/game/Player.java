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
import gui.GameScreen;

public class Player implements Drawable, KeyListener {
	public int x, y, w, h;
	int mx, my;
	Image sprite;
	GameMap gm;
	
	Image selector;
	
	int cap = 0;
	
	int tool;
	int maxTool = 5;
	
	public int health = 100;
	
	int[] seeds;
	int[] crops;
	
	int[] walkable = new int[]{Tile.DIRT, Tile.GRASS, Tile.BABY_WHEAT, Tile.WHEAT, Tile.SNOW, Tile.FLOOR};
	
	boolean showCrops;
	
	boolean[] keysDown;
	
	int facing;
	
	public Player(String spritePath) {
		this.x = 0;
		this.y = 0;
		this.mx = 0;
		this.my = 0;
		tool = 0;
		
		facing  = 3;
		
		showCrops = false;
		
		int cropType = 2;
		seeds = new int[cropType];
		crops = new int[cropType];
		
		seeds[0] = 16;
		crops[0] = 0;
		
		seeds[1] = 3;
		crops[1] = 0;
		
		this.keysDown = new boolean[255];
		try {
			sprite = ImageIO.read(new File(spritePath));
			selector = ImageIO.read(new File("./res/selector.png"));
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
	
	public Tile getSelectedTile() {
		Tile work = null;
		switch(facing) {
		case 0:
			x -= 8;
			if(x >= 0) {
				work = getTileOn();
			}
			x += 8;
			break;
		case 1:
			y -= 8;
			if(y >= 0) {
				work = getTileOn();
			}
			y += 8;
			break;
		case 2:
			x += 8;
			if(x < -8 + gm.tiles.length*8) {
				work = getTileOn();
			}
			x -= 8;
			break;
		case 3:
			y += 8;
			if(y < -8 + gm.tiles[0].length*8) {
				work = getTileOn();
			}
			y -= 8;
			break;
		}
		return work;
	}
	
	public void setSelectedTile(int type) {
		Tile t = getSelectedTile();
		if(t == null) {
			return;
		}
		t.type = type;
	}
	
	@Override
	public void draw(Graphics g) {
		
		int dx = x * Drawable.SCALE;
		int dy = y * Drawable.SCALE;
		int pdx = ((x+4)/8)*8 * Drawable.SCALE;
		int pdy = ((y+4)/8)*8 * Drawable.SCALE;
		
		int ddx = pdx - dx;
		int ddy = pdy - dy;
		
		g.translate(dx, dy);
		
		int fdx = -GameScreen.width/2 + 5*Drawable.SCALE;
		int fdy = -GameScreen.height/2 + 5*Drawable.SCALE;
		
		g.translate(fdx, fdy);
		String toWrite = "";
		switch(tool) {
		case 0:
			toWrite = "Till";
			break;
		case 1:
			toWrite = "Harvest";
			break;
		case 2:
			toWrite = "Wheat Seeds" + seeds[0];
			break;
		case 3:
			toWrite = "Saplings " + seeds[1];
			break;
		case 4:
			toWrite = "Place Hay";
			break;
		case 5:
			toWrite = "Place Floor";
			break;
		}
		
		if(showCrops) {
			int ff = crops.length + 2;
			int cropx = 9*Drawable.SCALE;
			g.translate(0, cropx);
			gm.getFont().draw(g, "Wheat " + crops[0]);
			
			g.translate(0, cropx);
			gm.getFont().draw(g, "Logs " + crops[1]);
			
			g.translate(0, cropx);
			gm.getFont().draw(g, "Time Left " + gm.timeLeft / 100);
			
			g.translate(0, cropx);
			if(health <= 0) {
				gm.getFont().draw(g, "Game Over");
			} else {
				gm.getFont().draw(g, "Health " + health);
			}
						
			g.translate(0, -cropx*ff);
		}
		
		
		gm.getFont().draw(g, toWrite);
		g.translate(-fdx, -fdy);
		
		g.drawImage(sprite, 0, 0, w*Drawable.SCALE, h*Drawable.SCALE, 0, 0, w, h, null);
		
		g.translate(ddx, ddy);
		switch(facing) {
		case 0:
			x -= 8;
			if(x >= 0) {
				g.drawImage(selector, -8*Drawable.SCALE, 0, (w-8)*Drawable.SCALE, h*Drawable.SCALE, 0, 0, w, h, null);
			}
			x += 8;
			break;
		case 1:
			y -= 8;
			if(y >= 0) {
				g.drawImage(selector, 0, -8*Drawable.SCALE, w*Drawable.SCALE, (h-8)*Drawable.SCALE, 0, 0, w, h, null);
			}
			y += 8;
			break;
		case 2:
			x += 8;
			if(x < -8 + gm.tiles.length*8) {
				g.drawImage(selector, 8*Drawable.SCALE, 0, (w+8)*Drawable.SCALE, h*Drawable.SCALE, 0, 0, w, h, null);
			}
			x -= 8;
			break;
		case 3:
			y += 8;
			if(y < -8 + gm.tiles[0].length*8) {
				g.drawImage(selector, 0, 8*Drawable.SCALE, w*Drawable.SCALE, (h+8)*Drawable.SCALE, 0, 0, w, h, null);
			}
			y -= 8;
			break;
		}
		g.translate(-ddx, -ddy);
		
		g.translate(-dx, -dy);
	}
	
	boolean contains(int[] arr, int p) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == p)
				return true;
		}
		return false;
	}
	
	public void tick() {
		if(keysDown[KeyEvent.VK_W]) {
			this.my--;
			if(my < -cap) {
				my = 0;
				y--;
				if(!contains(walkable, getTileOn().type)) y++;
				facing = 1;
			}
		}
		if(keysDown[KeyEvent.VK_S]) {
			this.my++;
			if(my > cap) {
				my = 0;
				y++;
				if(!contains(walkable, getTileOn().type)) y--;
				facing = 3;
			}
		}
		if(keysDown[KeyEvent.VK_A]) {
			this.mx++;
			if(mx > cap) {
				mx = 0;
				x--;
				if(!contains(walkable, getTileOn().type)) x++;
				facing = 0;
			}
		}
		if(keysDown[KeyEvent.VK_D]) {
			this.mx--;
			if(mx < -cap) {
				mx = 0;
				x++;
				if(!contains(walkable, getTileOn().type)) x--;
				facing = 2;
			}
		}
		if(keysDown[KeyEvent.VK_SPACE] && getSelectedTile() != null) {
			//Till
			if(tool == 0) {
				if(getSelectedTile().type == Tile.GRASS) {
					setSelectedTile(Tile.DIRT);
				}
			}
			//Harvest
			if(tool == 1) {
				if(getSelectedTile().type == Tile.WHEAT) {
					setSelectedTile(Tile.DIRT);
					seeds[0] += 2;
					crops[0] += 3;
				} else if(getSelectedTile().type == Tile.TREE) {
					setSelectedTile(Tile.DIRT);
					seeds[1] += 5;
					crops[1] += 15;
				} else if(getSelectedTile().type == Tile.HAY) {
					setSelectedTile(Tile.DIRT);
					crops[0] += 4;
				} else if(getSelectedTile().type == Tile.FLOOR) {
					setSelectedTile(Tile.DIRT);
					crops[1] += 4;
				} else if(getSelectedTile().type == Tile.SNOW) {
					getSelectedTile().dmg++;
				}
			}
			//Wheat
			if(tool == 2) {
				if(getSelectedTile().type == Tile.DIRT && seeds[0] > 0) {
					setSelectedTile(Tile.BABY_WHEAT);
					seeds[0]--;
				}
			}
			//Tree
			if(tool == 3) {
				if(getSelectedTile().type == Tile.DIRT && seeds[1] > 0) {
					setSelectedTile(Tile.BABY_TREE);
					seeds[1]--;
				}
			}
			//Place Hay
			if(tool == 4) {
				int t = getSelectedTile().type;
				if((t == Tile.DIRT || t == Tile.GRASS) && crops[0] >= 4) {
					setSelectedTile(Tile.HAY);
					crops[0] -= 4;
				}				
			}
			//Place Floor
			if(tool == 5) {
				int t = getSelectedTile().type;
				if((t == Tile.DIRT || t == Tile.GRASS) && crops[1] >= 4) {
					setSelectedTile(Tile.FLOOR);
					crops[1] -= 4;
				}				
			}
		}
		
		if(x <= 0) {
			x = 0;
		}
		if(y <= 0) {
			y = 0;
		}
		if(x >= -8 + gm.tiles.length*8) {
			x = -8+gm.tiles.length*8;
		}
		if(y >= -8 + gm.tiles[0].length*8) {
			y = -8+gm.tiles[0].length*8;
		}
		if(getTileOn().type == Tile.SNOW) {
			health--;
		}
		
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
		if(code == KeyEvent.VK_J) {
			tool--;
			if(tool < 0) {
				tool = maxTool;
			}
		}
		if(code == KeyEvent.VK_K) {
			tool++;
			if(tool > maxTool) {
				tool = 0;
			}
		}
		if(code == KeyEvent.VK_L) {
			showCrops = !showCrops;
		}
		if(code == KeyEvent.VK_Y) {
			tool = 0;
		}
		if(code == KeyEvent.VK_U) {
			tool = 1;
		}
		if(code == KeyEvent.VK_I) {
			tool = 2;
		}
		if(code == KeyEvent.VK_O) {
			tool = 3;
		}
		if(code == KeyEvent.VK_P) {
			tool = 4;
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
