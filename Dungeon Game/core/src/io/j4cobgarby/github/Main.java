package io.j4cobgarby.github;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {
	// An orthographic (2D, in this case) camera.
    OrthographicCamera          camera;
	
	// This SpriteBatch will render most things. For instance,
	// the player; the world; the monsters.
    SpriteBatch                 batch;
	
	// This SpriteBatch will render the HUD (heads up display).
	// A seperate batch is used to ease the coding OF the
	// HUD. This is only for the text and keys in the HUD.
    SpriteBatch                 hudBatch;
	
	// This ShapeRenderer is for, for example, the debugging
	// collision shapes.
    ShapeRenderer               shaper;
	
	// This renders the non-sprite parts of the HUD, i.e. the
	// actual box, outline, etc.
    ShapeRenderer               hudRend;
	
	// The instance of the Player class, used for the player of
	// the game. It's public and static so that it can be
	// accessed outside of this class, used in other classes easily.
    public static Player        player;
	
	// The HUD. 'nuff said.
    Hud                         hud;
    
	// Some levels.             // currentLevel is changed to whatever level the player is currently on.
    static LevelWithSpawns      currentLevel;
    static LevelWithSpawns      level1;
    static LevelWithSpawns      level2;
    
	// This is to render the map. I use Tiled to create the tilemaps.
    TiledMapRenderer            tiledMapRenderer;
    
	// I'm not sure, but I think this is the size, in pixels,
	// of the tiles.
    static int                  tileSize = 8;
    
	// The display version of the game.
    static String               version = "b0.06";
    
	// Whether or not to show the debug information. In a release build,
	// this will always be false.
    static boolean              show_debug = false;
    
    @Override
    public void create () {
        float w = Gdx.graphics.getWidth  ();
        float h = Gdx.graphics.getHeight ();
        
        hud              = new Hud                        ();
        batch            = new SpriteBatch                ();
        hudBatch         = new SpriteBatch                ();
        hudRend          = new ShapeRenderer              ();
        shaper           = new ShapeRenderer              ();
        
        level1           = new LevelWithSpawns            ("dungeon_1.tmx", new Vector2(14,  7));
        level2           = new LevelWithSpawns            ("dungeon_2.tmx", new Vector2(18, 10));
        
        currentLevel     = level2;
        
        player           = new Player                     (new Texture("player_mage.png"), (int)currentLevel.spawn.x, (int)currentLevel.spawn.y);
        tiledMapRenderer = new OrthogonalTiledMapRenderer (level2.getLevel());
        camera           = new OrthographicCamera         ();
        
        camera.position.x  = player.x;
        camera.position.y  = player.y;
        camera.zoom        = 0.1f;
        camera.setToOrtho  (false,w,h);
        camera.update      ();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor  (1, 0, 0, 1);
        Gdx.gl.glBlendFunc   (GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear       (GL20.GL_COLOR_BUFFER_BIT);
        
        camera.position.x = MathUtils.lerp(camera.position.x, player.x * 8,	0.03f); 
		camera.position.y = MathUtils.lerp(camera.position.y, player.y * 8, 0.03f);
        
        batch .setProjectionMatrix (camera.combined);
        shaper.setProjectionMatrix (camera.combined);
        
        camera.update              ();
        try {
        	tiledMapRenderer.setView     (camera);
        	tiledMapRenderer.render      ();
        } catch (NullPointerException e) {}
        
        player.update              ();
        handleInput                ();
        
        hudRend.begin              (ShapeType.Filled);
        hud.drawShapes             (hudRend);
        hudRend.end                ();
        
        hudBatch.begin             ();
        hud.draw(hudBatch);
        hudBatch.end               ();
        
        batch.begin                ();
        
        player.draw(batch);
        
        batch.end                  ();
        
        if (show_debug) {
	        shaper.begin(ShapeType.Line);
	        for (Rectangle r : level1.getCollisionRectangles()) {
	        	shaper.rect(r.x, r.y, r.width, r.height);
	        }
	        shaper.end();
        }
    }
    
    public void handleInput () {
    	if (Gdx.input.isKeyJustPressed(Keys.P)) {show_debug = !show_debug;}
    	if (Gdx.input.isKeyJustPressed(Keys.O)) {this.tiledMapRenderer = new OrthogonalTiledMapRenderer (currentLevel.getLevel());}
    	if (Gdx.input.isKeyJustPressed(Keys.I)) {currentLevel = level2;}
    	if (Gdx.input.isKeyPressed    (Keys.K)) {player.setCurrentHealth(player.getCurrentHealth() - 2);}
    	if (Gdx.input.isKeyPressed    (Keys.L)) {player.setCurrentHealth(player.getCurrentHealth() + 2);}
    	if (Gdx.input.isKeyPressed    (Keys.N)) {player.setCurrentMana  (player.getCurrentMana() - 2);}
    	if (Gdx.input.isKeyPressed    (Keys.M)) {player.setCurrentMana  (player.getCurrentMana() + 2);}
    }
}
