package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.interdev.dstrike.screens.game.Player;
import com.interdev.dstrike.screens.game.PlayerValues;

public class LivesAnimation extends Actor {

    public Array<TextureAtlas.AtlasRegion> livesTextureArray;
    public Array<TextureAtlas.AtlasRegion> livesLeftSideTextureArray, livesRightSideTextureArray;

    private Animation livesAnimation, leftSideAnimation, rightSideAnimation;

    private float elapsedTime = 0;
    private float x = 0;
    private float y = 0;
    private float scaleX = 1;
    private float scaleY = 1;
    private float livesAnimWidth, livesAnimHeight;
    private float sideAnimWidth, sideAnimHeight;

    private final float sideAnimsOffset = 0.6f;
    private float actualOffset;

    public LivesAnimation(UI ui) {
        livesTextureArray = ui.textureAtlas.findRegions("lives1");
        livesLeftSideTextureArray = ui.textureAtlas.findRegions("lives_left_side");
        livesRightSideTextureArray = ui.textureAtlas.findRegions("lives_right_side");

        livesAnimation = new Animation(0.05f, livesTextureArray, Animation.PlayMode.LOOP);
        leftSideAnimation = new Animation(0.05f, livesLeftSideTextureArray, Animation.PlayMode.LOOP);
        rightSideAnimation = new Animation(0.05f, livesRightSideTextureArray, Animation.PlayMode.LOOP);

        recalculateScalableValues();

    }

    public void setCenterPos(float x, float y) {
        this.x = x - livesAnimation.getKeyFrame(0).getRegionWidth() * scaleX / 2;
        this.y = y - livesAnimation.getKeyFrame(0).getRegionHeight() * scaleX / 2;
    }

    public void setScale(float scale) {
        scaleX = scale;
        scaleY = scale;
        recalculateScalableValues();
    }

    private void recalculateScalableValues() {
        livesAnimWidth = livesAnimation.getKeyFrame(0).getRegionWidth() * scaleX;
        livesAnimHeight = livesAnimation.getKeyFrame(0).getRegionHeight() * scaleY;
        sideAnimWidth = leftSideAnimation.getKeyFrame(0).getRegionWidth() * scaleX;
        sideAnimHeight = leftSideAnimation.getKeyFrame(0).getRegionHeight() * scaleY;
        actualOffset = sideAnimsOffset * sideAnimWidth * scaleX;

    }


    public void draw(float deltaTime, Batch batch) {
        elapsedTime += deltaTime;

        batch.end();
        TextureRegion region = livesAnimation.getKeyFrame(elapsedTime);
        float myLivesLossPercent = 1 - Player.myLives / PlayerValues.BASE_START_LIVES;
        float enemyLivesLossPercent = 1 - Player.enemyLives / PlayerValues.BASE_START_LIVES;
        int xStart = (int) (x + (livesAnimWidth / 2) * myLivesLossPercent);
        int xToEndDelta = (int) ((livesAnimWidth) - (livesAnimWidth / 2) * myLivesLossPercent - (livesAnimWidth / 2) * enemyLivesLossPercent);
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(xStart, (int) y, xToEndDelta, (int) (livesAnimHeight));
        batch.begin();
        batch.draw(region, x, y, livesAnimWidth, livesAnimHeight);
        batch.end();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        batch.begin();


        float leftSideAnimX = Math.max(xStart - sideAnimWidth / 2, x + actualOffset - sideAnimWidth / 2);
        float rightSideAnimX = Math.min(xStart + xToEndDelta - sideAnimWidth / 2, x + livesAnimWidth - actualOffset - sideAnimWidth / 2);


        batch.draw(leftSideAnimation.getKeyFrame(elapsedTime), leftSideAnimX, y + livesAnimHeight / 2 - sideAnimHeight / 2, sideAnimWidth, sideAnimHeight);
        batch.draw(rightSideAnimation.getKeyFrame(elapsedTime), rightSideAnimX, y + livesAnimHeight / 2 - sideAnimHeight / 2, sideAnimWidth, sideAnimHeight);

    }

}
