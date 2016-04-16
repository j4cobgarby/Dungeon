package io.j4cobgarby.github.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.j4cobgarby.github.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Dungeon game, b0.04";
		config.width = 1080; config.height = 720;
		config.resizable = false;
		
		new LwjglApplication(new Main(), config);
	}
}
