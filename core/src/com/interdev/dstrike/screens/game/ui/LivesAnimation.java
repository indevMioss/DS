package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class LivesAnimation extends Animation {
    private float elapsedTime = 0;
    private float x = 0;
    private float y = 0;
    private float scaleX = 1;
    private float scaleY = 1;

    public LivesAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
    }

    public void setCenterPos(float x, float y) {
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
        batch.draw(region, x - region.getRegionWidth() * scaleX / 2, y - region.getRegionHeight() * scaleX / 2, region.getRegionWidth() * scaleX, region.getRegionHeight() * scaleY);
    }

}
