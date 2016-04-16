package io.j4cobgarby.github;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

public class Level {
	
	TiledMap level;
	
	
	// Various tile IDs
	static final int redID       = 11; // - Keyholes
	static final int greenID     = 14;
	static final int blueID      =  9;
	static final int yellowID    = 12;
	
	static final int coinID      =  3; // - Coin
	static final int redKeyID    =  7; // - Keys
	static final int greenKeyID  =  6;
	static final int blueKeyID   =  5;
	static final int yellowKeyID =  8;
	static final int healthPotID = 20; // - Potions
	static final int manaPotID   = 21;
	
	static final int doorID      = 4;
	
	
	// Functions
	
	public Level(String tmx)                        {level = new TmxMapLoader().load(tmx);}
	
	public TiledMapTileLayer getLayer(String name)  {return (TiledMapTileLayer) this.level.getLayers().get(name);}
	
	public Cell getCell(String layer, int x, int y) {return getLayer(layer).getCell(x, y);}
	
	public int getHeight(String layer)              {return getLayer(layer).getHeight();}
	
	public int getWidth(String layer)               {return getLayer(layer).getWidth();}
	
	public Rectangle[] getCollisionRectangles()     {
		float tileWidth = Main.currentLevel.getCell("base", 1, 1).getTile().getTextureRegion().getRegionWidth();
		float tileHeight = Main.currentLevel.getCell("base", 1, 1).getTile().getTextureRegion().getRegionHeight();
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
		for (int y = 0; y < Main.currentLevel.getHeight("base"); y++) {
			for (int x = 0; x < Main.currentLevel.getWidth("base"); x++) {
				try {
					boolean collides = Boolean.valueOf(Main.currentLevel.getCell("base", x, y).getTile().getProperties().get("can_pass").toString());
					if (!collides) rects.add(new Rectangle(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
				} catch (NullPointerException e) {}
			}
		}
		Rectangle[] r = new Rectangle[rects.size()]; rects.toArray(r);
		return r;
	}
	
	public Boolean doesCollide(int x, int y)        {
		try {return Boolean.valueOf(Main.currentLevel.getCell("base", x, y).getTile().getProperties().get("can_pass").toString());}
		catch (NullPointerException e) {return true;}
	}
	
	public TiledMap getLevel()                      {return level;}
}
