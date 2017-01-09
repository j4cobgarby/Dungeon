package io.j4cobgarby.github;

import com.badlogic.gdx.math.Vector2;

// This class MUST be used instead of Level, when making a Level. This is because, with a Level, the
// player wouldn't know where to appear!

public class LevelWithSpawns extends Level {
	Vector2 spawn;
	
	public LevelWithSpawns(String tmx, Vector2 spawn) {
		super(tmx);
		this.spawn = spawn;
	}

}
