package io.j4cobgarby.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input    .Keys;
import com.badlogic.gdx.graphics .Texture;
import com.badlogic.gdx.graphics .g2d      .Sprite;
import com.badlogic.gdx.graphics .g2d      .SpriteBatch;
import com.badlogic.gdx.maps     .tiled.TiledMapTileLayer;
import com.badlogic.gdx.math     .MathUtils;

public class Player {
	Texture   tex;
	Sprite    sprite;
	
	boolean hasKey_r  = false;
	boolean hasKey_g  = false;
	boolean hasKey_b  = false;
	boolean hasKey_y  = false;
	
	boolean doneLevel = false;
	
	int        score;
	int        x, y;
	float      move_delay;
	float      maxHealth, maxMana;
	float      currentHealth, currentMana;
	
	public Player(Texture tex, int x, int y) {
		this.tex             = tex;
		this.x               = x;
		this.y               = y;
		
		this.score           = 0;
		
		this.sprite          = new Sprite(tex);
		this.move_delay      = 0.5f;
		
		this.maxHealth       = 200;
		this.maxMana         = 150;
		this.currentHealth   = maxHealth;
		this.currentMana     = maxMana;
		
		this.sprite.setPosition(this.x * Main.tileSize, this.y * Main.tileSize);
	}
	
	public void update() {
		// Cheking for pickups
		try {
			switch (Main.currentLevel.getCell("pickups", x, y).getTile().getId()) { // Gets ID of current tile on pickups layer.
			case Level.redKeyID:	this.hasKey_r = true; 
									break;
									
			case Level.blueKeyID: 	this.hasKey_b = true; 
									break;
									
			case Level.greenKeyID: 	this.hasKey_g = true; 
									break;
									
			case Level.yellowKeyID:	this.hasKey_y = true; 
									break;
									
			case Level.coinID:		this.score++;
									break;
									
			case Level.healthPotID: this.currentHealth = MathUtils.clamp(this.currentHealth + 25, 0, this.maxHealth);
									break;
									
			case Level.manaPotID:   this.currentMana   = MathUtils.clamp(this.currentMana   + 30, 0, this.maxMana);
									break;
			}
		}   catch (NullPointerException e) {} // The tile, if no pickup is there, will return null. this catches it.
		
		try {
			switch (Main.currentLevel.getCell("base", x, y).getTile().getId()) {
			case Level.doorID:      this.doneLevel = true;
									break;
			}
		}   catch (NullPointerException e) {}
		
		TiledMapTileLayer layer = (TiledMapTileLayer) Main.currentLevel.getLevel().getLayers().get("base");
		
		// Checking for keyholes where the player is trying to move.
		if (Gdx.input.isKeyJustPressed(Keys.UP)    && !Main.currentLevel.doesCollide(x, y + 1)) {
			switch (Main.currentLevel.getCell("base", x, y + 1).getTile().getId()) {
			case Level.redID:		if (this.hasKey_r) {layer.setCell(x, y + 1, null);} break;
			case Level.blueID:		if (this.hasKey_b) {layer.setCell(x, y + 1, null);} break;
			case Level.greenID:		if (this.hasKey_g) {layer.setCell(x, y + 1, null);} break;
			case Level.yellowID: 	if (this.hasKey_y) {layer.setCell(x, y + 1, null);} break;
			}}
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)  && !Main.currentLevel.doesCollide(x, y - 1)) { 
			switch (Main.currentLevel.getCell("base", x, y - 1).getTile().getId()) {
			case Level.redID:		if (this.hasKey_r) {layer.setCell(x, y - 1, null);} break;
			case Level.blueID:		if (this.hasKey_b) {layer.setCell(x, y - 1, null);} break;
			case Level.greenID:		if (this.hasKey_g) {layer.setCell(x, y - 1, null);} break;
			case Level.yellowID: 	if (this.hasKey_y) {layer.setCell(x, y - 1, null);} break;
			}}
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)  && !Main.currentLevel.doesCollide(x - 1, y)) { 
			switch (Main.currentLevel.getCell("base", x - 1, y).getTile().getId()) {
			case Level.redID:		if (this.hasKey_r) {layer.setCell(x - 1, y, null);} break;
			case Level.blueID:		if (this.hasKey_b) {layer.setCell(x - 1, y, null);} break;
			case Level.greenID:		if (this.hasKey_g) {layer.setCell(x - 1, y, null);} break;
			case Level.yellowID: 	if (this.hasKey_y) {layer.setCell(x - 1, y, null);} break;
			}}
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && !Main.currentLevel.doesCollide(x + 1, y)) { 
			switch (Main.currentLevel.getCell("base", x + 1, y).getTile().getId()) {
			case Level.redID:		if (this.hasKey_r) {layer.setCell(x + 1, y, null);} break;
			case Level.blueID:		if (this.hasKey_b) {layer.setCell(x + 1, y, null);} break;
			case Level.greenID:		if (this.hasKey_g) {layer.setCell(x + 1, y, null);} break;
			case Level.yellowID: 	if (this.hasKey_y) {layer.setCell(x + 1, y, null);} break;
			}}

		TiledMapTileLayer picksLayer = (TiledMapTileLayer) Main.currentLevel.getLevel().getLayers().get("pickups");
		picksLayer.setCell(x, y, null);
		
		// doesCollide returns true if a specified cell WON'T collide.
		if (Gdx.input.isKeyJustPressed(Keys.UP)    && Main.currentLevel.doesCollide(x, y + 1)) { y++; }
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)  && Main.currentLevel.doesCollide(x, y - 1)) { y--; }
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)  && Main.currentLevel.doesCollide(x - 1, y)) { x--; }
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && Main.currentLevel.doesCollide(x + 1, y)) { x++; }

		// Slowly move to position.
		this.sprite.setPosition(
				MathUtils.lerp(this.sprite.getX(), this.x * 8, (float) 0.3),
				MathUtils.lerp(this.sprite.getY(), this.y * 8, (float) 0.3) );
	}
	
	public void draw(SpriteBatch batch) {if (batch.isDrawing()) {sprite.draw(batch);}}
	
	//Getters and setters
	public boolean isHasKey_r()       {return hasKey_r;      }

	public boolean isHasKey_g()       {return hasKey_g;      }

	public boolean isHasKey_b()       {return hasKey_b;      }

	public boolean isHasKey_y()       {return hasKey_y;      }

	public int     getScore()         {return score;         }

	public float   getMaxHealth()     {return maxHealth;     }

	public float   getMaxMana()       {return maxMana;       }

	public float   getCurrentHealth() {return currentHealth; }

	public float   getCurrentMana()   {return currentMana;   }

	public void    setHasKey_r      (boolean hasKey_r     ) {this.hasKey_r = hasKey_r;           }

	public void    setHasKey_g      (boolean hasKey_g     ) {this.hasKey_g = hasKey_g;           }

	public void    setHasKey_b      (boolean hasKey_b     ) {this.hasKey_b = hasKey_b;           }

	public void    setHasKey_y      (boolean hasKey_y     ) {this.hasKey_y = hasKey_y;           }

	public void    setScore         (int     score        ) {this.score = score;                 }

	public void    setMaxHealth     (float   maxHealth    ) {this.maxHealth = maxHealth;         }

	public void    setMaxMana       (float   maxMana      ) {this.maxMana = maxMana;             }

	public void    setCurrentHealth (float   currentHealth) {this.currentHealth = currentHealth; }

	public void    setCurrentMana   (float   currentMana  ) {this.currentMana = currentMana;     }
}
