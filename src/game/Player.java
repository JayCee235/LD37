package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import audio.AudioPlayer;
import gui.Drawable;
import gui.GameScreen;

public class Player implements Drawable, KeyListener {
	public int x, y, w, h;
	int mx, my;
	Image sprite;
	GameMap gm;
	
	Image selector;
	
	int cap = 0;
	
	public int tool;
	int maxTool = 5;
	
	public int health = 100;
	
	int[] seeds;
	int[] crops;
	
	int[] walkable = new int[]{Tile.DIRT, Tile.GRASS, Tile.BABY_WHEAT, Tile.WHEAT, Tile.SNOW, Tile.FLOOR};
	
	boolean showCrops;
	
	boolean[] keysDown;
	
	int facing;
	
	public List<Drawable> hud;
	
	AudioPlayer place, plant, harvest, till;
	public int cx, cy;
	
	public Player(String spritePath) {
		this.x = 0;
		this.y = 0;
		this.mx = 0;
		this.my = 0;
		tool = 0;
		this.cx = 0;
		this.cy = 0;
		
		facing  = 3;
		
		hud = new ArrayList<Drawable>();
		
		showCrops = true;
		
		int cropType = 2;
		seeds = new int[cropType];
		crops = new int[cropType];
		
		seeds[0] = 16;
		crops[0] = 0;
		
		seeds[1] = 3;
		crops[1] = 0;
		
		this.keysDown = new boolean[255];
		try {
			sprite = ImageIO.read(this.getClass().getResource(spritePath));
			selector = ImageIO.read(this.getClass().getResourceAsStream("/res/selector.png"));
			
			place = new AudioPlayer("/res/placementSound.wav");
			plant = new AudioPlayer("/res/plantSound.wav");
			harvest = new AudioPlayer("/res/harvestSound.wav");
			till = new AudioPlayer("/res/tillSound.wav");
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
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
	
	public void setTool(int t) {
		((Button) hud.get(tool)).deselect();
		tool = t;
		while(tool < 0) {
			tool += maxTool + 1;
		}
		while(tool > maxTool) {
			tool -= maxTool + 1;
		}
		((Button) hud.get(tool)).select();
		
	}
	
	@Override
	public void draw(Graphics g) {
		int dx = cx * Drawable.SCALE;
		int dy = cy * Drawable.SCALE;
		int pdx = ((x+4)/8)*8 * Drawable.SCALE;
		int pdy = ((y+4)/8)*8 * Drawable.SCALE;		
		
		int ddx = pdx - dx;
		int ddy = pdy - dy;
		
//		dx = 0; dy = 0;		
		
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
			if(gm.timeLeft > 99) {
				gm.getFont().draw(g, "Time Left " + gm.timeLeft / 100);
			} else {
				gm.getFont().draw(g, "Score " + gm.score / 100);
			}
			
			g.translate(0, cropx);
			if(health <= 0) {
				gm.getFont().draw(g, "Game Over");
			} else {
				gm.getFont().draw(g, "Health " + health);
			}
						
			g.translate(0, -cropx*ff);
		}
		
		for(Drawable d : hud) {
			d.draw(g);
		}
		
		
		gm.getFont().draw(g, toWrite);
		g.translate(-fdx, -fdy);
		
		g.drawImage(sprite, 0, -3*Drawable.SCALE, w*Drawable.SCALE, (h-3)*Drawable.SCALE, 0, 0, w, h, null);
		
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
			facing = 1;
			if(my < -cap) {
				my = 0;
				y--;
				if(!contains(walkable, getTileOn().type)) y++;
			}
		}
		if(keysDown[KeyEvent.VK_S]) {
			this.my++;
			facing = 3;
			if(my > cap) {
				my = 0;
				y++;
				if(!contains(walkable, getTileOn().type)) y--;
			}
		}
		if(keysDown[KeyEvent.VK_A]) {
			this.mx++;
			facing = 0;
			if(mx > cap) {
				mx = 0;
				x--;
				if(!contains(walkable, getTileOn().type)) x++;
			}
		}
		if(keysDown[KeyEvent.VK_D]) {
			this.mx--;
			facing = 2;
			if(mx < -cap) {
				mx = 0;
				x++;
				if(!contains(walkable, getTileOn().type)) x--;
			}
		}
		if(keysDown[KeyEvent.VK_SPACE]) {
			performAction();
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
			setTool(tool - 1);
		}
		if(code == KeyEvent.VK_K) {
			setTool(tool + 1);
		}
		if(code == KeyEvent.VK_L) {
			showCrops = !showCrops;
		}
		if(code == KeyEvent.VK_1) {
			setTool(0);
		}
		if(code == KeyEvent.VK_2) {
			setTool(1);
		}
		if(code == KeyEvent.VK_3) {
			setTool(2);
		}
		if(code == KeyEvent.VK_4) {
			setTool(3);
		}
		if(code == KeyEvent.VK_5) {
			setTool(4);
		}
		if(code == KeyEvent.VK_6) {
			setTool(5);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code >= 0 && code < 255) {
			keysDown[code] = false;
		}
	}

	public void performAction() {
		Tile t = getSelectedTile();
		if(t == null) {
			return;
		}
		//Till
		if(tool == 0) {
			if(t.type == Tile.GRASS) {
				setSelectedTile(Tile.DIRT);
				try {
					till.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//Harvest
		if(tool == 1) {
			if(t.type == Tile.WHEAT) {
				setSelectedTile(Tile.DIRT);
				seeds[0] += 2;
				crops[0] += 3;
				try {
					harvest.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(t.type == Tile.TREE) {
				setSelectedTile(Tile.DIRT);
				seeds[1] += 5;
				crops[1] += 15;
				try {
					harvest.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(t.type == Tile.HAY) {
				setSelectedTile(Tile.DIRT);
				crops[0] += 4;
				try {
					harvest.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(t.type == Tile.FLOOR) {
				setSelectedTile(Tile.DIRT);
				crops[1] += 4;
				try {
					harvest.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(getSelectedTile().type == Tile.SNOW) {
				getSelectedTile().dmg++;
			}
		}
		//Wheat
		if(tool == 2) {
			if(t.type == Tile.DIRT && seeds[0] > 0) {
				setSelectedTile(Tile.BABY_WHEAT);
				seeds[0]--;
				try {
					plant.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//Tree
		if(tool == 3) {
			if((t.type == Tile.DIRT || t.type == Tile.GRASS) && seeds[1] > 0) {
				setSelectedTile(Tile.BABY_TREE);
				seeds[1]--;
				try {
					plant.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//Place Hay
		if(tool == 4) {
			int type = t.type;
			if((type == Tile.DIRT || type == Tile.GRASS) && crops[0] >= 4) {
				setSelectedTile(Tile.HAY);
				crops[0] -= 4;
				try {
					place.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		}
		//Place Floor
		if(tool == 5) {
			int type = t.type;
			if((type == Tile.DIRT || type == Tile.GRASS) && crops[1] >= 4) {
				setSelectedTile(Tile.FLOOR);
				crops[1] -= 4;
				try {
					place.play();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
		}
	}
	
}
