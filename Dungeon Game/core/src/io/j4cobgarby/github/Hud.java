package io.j4cobgarby.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Hud {
	FreeTypeFontGenerator  gen;
	FreeTypeFontParameter  par;
	BitmapFont             pm_b;
	
	final float            maxWidth    = 250;
	final float            barsStartX  = 300;
	final float            barsHeight  =  20;
	final Color            healthColor = new Color(0xA0E08Bff);
	final Color            healthAlt   = new Color(0x92CC7Eff);
	final Color            manaColor   = new Color(0x8BCBE0ff);
	final Color            manaAlt     = new Color(0x7EB9CCff);
	
	float                  currentHealthWidth;
	
	Texture                coin;
	
	public Hud() {
		gen             = new FreeTypeFontGenerator (Gdx.files.internal("pixelmix_bold.ttf"));
		par             = new FreeTypeFontParameter ();
		
		par.size        = 31;
		par.color       = Color.BLACK;
		par.borderColor = Color.WHITE;
		par.borderWidth = 1.2f;
		
		pm_b            = gen.generateFont  (par);
		
		coin            = new Texture       ("coin_small.png");
	}
	
	public void drawShapes (ShapeRenderer rend) {
		if (rend.isDrawing()) {
			// HUD Background
			rend.setColor(Color.WHITE);
			rend.rect(0, 0, 553, 48);
			
			// Information bars (health, mana)
				// Health
			rend.setColor(healthAlt);
			rend.rect(barsStartX, 3 + barsHeight + 2, maxWidth, barsHeight);
			rend.setColor(healthColor);
			rend.rect(barsStartX, 3 + barsHeight + 2, Main.player.getCurrentHealth() / Main.player.getMaxHealth() * maxWidth, barsHeight);
			
				//Mana
			rend.setColor(manaAlt);
			rend.rect(barsStartX, 3, maxWidth, barsHeight);
			rend.setColor(manaColor);
			rend.rect(barsStartX, 3, Main.player.getCurrentMana() / Main.player.getMaxMana() * maxWidth, barsHeight);
		}
	}
	
	public void draw(SpriteBatch batch) {
		
		// Displaying score
		if (batch.isDrawing()) {
			pm_b.draw  (batch, Integer.toString(Main.player.getScore()), 50, 40);
			batch.draw (coin, 1, 1, coin.getWidth()*6, coin.getHeight()*6);
		}
		
	}
}