package com.interdev.dstrike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.interdev.dstrike.networking.DSClient;
import com.interdev.dstrike.screens.ScreenManager;
import com.interdev.dstrike.screens.Screens;
import com.interdev.dstrike.screens.game.GameScreen;

public class Main extends Game {

	public static DSClient dsClient;
	public static GameScreen gameScreenReference;
	public static float resolution_scale_componet;
	public static float screen_width, screen_height;


	@Override
	public void create () {
		screen_height = Gdx.graphics.getHeight();
		screen_width = Gdx.graphics.getWidth();

		resolution_scale_componet = Gdx.graphics.getWidth() / 720f;
		System.out.println(resolution_scale_componet);
		ScreenManager.getInstance().initialize(this);
		dsClient = new DSClient();
	//	Log.set(Log.LEVEL_DEBUG);

		ScreenManager.getInstance().show(Screens.AUTHORIZATION);
	}

	@Override
	public void dispose () {
		super.dispose();
		ScreenManager.getInstance().dispose();
	}

}
