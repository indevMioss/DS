package com.interdev.dstrike.screens.game.ui.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.interdev.dstrike.screens.game.PlayerValues;
import com.interdev.dstrike.screens.game.ui.UI;

public class WaveTimer extends Animation {
    private static float REGION_ARRAY_SIZE = 16;

    private float elapsedTime;
    private float x;
    private float y;
    private float scaleX;
    private float scaleY;

    public WaveTimer(UI ui) {
        super((PlayerValues.WAVE_SPAWN_INTERVAL/REGION_ARRAY_SIZE)/1000f, ui.textureAtlas.findRegions("timer"), PlayMode.LOOP);
        this.setScale(ui.layersScale*0.75f);
        this.setPosition(0, ui.stage.getHeight() - this.getKeyFrame(0).getRegionHeight()*scaleY);

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setScale(float scale) {
        scaleX = scale;
        scaleY = scale;
    }

    public void draw(float deltaTime, Batch batch) {
        elapsedTime += deltaTime;
        TextureRegion region = getKeyFrame(elapsedTime);
        batch.draw(region, x, y, region.getRegionWidth() * scaleX, region.getRegionHeight() * scaleY);
    }


    public void onWaveSpawned() {
        elapsedTime = 0;
    }
}

