package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import gui.Drawable;

public class Tile implements Drawable{
	public static final int WIDTH = 8, HEIGHT = 8;
	public static final int GRASS = 0;
	public static final int DIRT = 1;
	public static final int BABY_WHEAT = 2;
	public static final int WHEAT = 3;
	public static final Image[] SPRITES = new Image[4];
	
	int type;
	int dmg;
	
	public Tile(int type) {
		this.type = type;
	}
	
	public static void load() {
		String[] paths = new String[SPRITES.length];
		
		String b = "./res/";
		String a = ".png";
		
		paths[0] = b+"testGrass"+a;
		paths[1] = b+"testFarm"+a;
		paths[2] = b+"babyWheat"+a;
		paths[3] = b+"wheat"+a;
		
		try {
			for(int i = 0; i < SPRITES.length; i++) {
				SPRITES[i] = ImageIO.read(new File(paths[i]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		switch(type) {
		case GRASS:
			break;
		case DIRT:
			if(Math.random() < 0.001) {
				dmg++;
				if(dmg > 5) {
					dmg = 0;
					type = GRASS;
				}
			}
			break;
		case BABY_WHEAT:
			if(Math.random() < 0.01) {
				dmg++;
				if(dmg > 7) {
					dmg = 0;
					type = WHEAT;
				}
			}
		default:
				
		}
	}
	
	public void draw(Graphics g) {
		Image sprite = SPRITES[this.type];
		g.drawImage(sprite, 0, 0, WIDTH*Drawable.SCALE, HEIGHT*Drawable.SCALE, 
				0, 0, WIDTH, HEIGHT, null);
	}
	
}
