package com.interdev.dstrike;

import com.badlogic.gdx.Game;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.networking.DSClient;
import com.interdev.dstrike.screens.ScreenManager;
import com.interdev.dstrike.screens.Screens;

public class Main extends Game {
	
	@Override
	public void create () {
		ScreenManager.getInstance().initialize(this);
		new DSClient();
		Log.set(Log.LEVEL_DEBUG);

		ScreenManager.getInstance().show(Screens.START_MENU);
	}

	@Override
	public void dispose () {
		super.dispose();
		ScreenManager.getInstance().dispose();
	}

}
