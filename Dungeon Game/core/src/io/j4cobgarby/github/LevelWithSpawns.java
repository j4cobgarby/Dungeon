package io.j4cobgarby.github;

import com.badlogic.gdx.math.Vector2;

public class LevelWithSpawns extends Level {
	Vector2 spawn;
	
	public LevelWithSpawns(String tmx, Vector2 spawn) {
		super(tmx);
		this.spawn = spawn;
	}

}
