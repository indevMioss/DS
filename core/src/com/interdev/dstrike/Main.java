package com.interdev.dstrike;

import com.badlogic.gdx.Game;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.networking.DSClient;
import com.interdev.dstrike.screens.ScreenManager;
import com.interdev.dstrike.screens.Screens;
import com.interdev.dstrike.screens.game.GameScreen;

public class Main extends Game {
	public static DSClient dsClient;
	public static GameScreen gameScreenReference;


	@Override
	public void create () {
		ScreenManager.getInstance().initialize(this);
		dsClient = new DSClient();
		Log.set(Log.LEVEL_DEBUG);

		ScreenManager.getInstance().show(Screens.GAME);
	}

	@Override
	public void dispose () {
		super.dispose();
		ScreenManager.getInstance().dispose();
	}

}
