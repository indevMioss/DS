package com.interdev.dstrike.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.interdev.dstrike.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "..~='DESERT STRIKE'=~..";
		config.height = 1280;
		config.width = 720;
		new LwjglApplication(new Main(), config);
	}
}
