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

    public LivesAnimation(UI ui) {
        livesTextureArray = ui.textureAtlas.findRegions("lives1");
        livesLeftSideTextureArray = ui.textureAtlas.findRegions("lives_left_side");
        livesRightSideTextureArray = ui.textureAtlas.findRegions("lives_right_side");

        livesAnimation = new Animation(0.05f, livesTextureArray, Animation.PlayMode.LOOP);
        leftSideAnimation = new Animation(0.05f, livesLeftSideTextureArray, Animation.PlayMode.LOOP);
        rightSideAnimation = new Animation(0.05f, livesRightSideTextureArray, Animation.PlayMode.LOOP);

        livesAnimWidth = livesAnimation.getKeyFrame(0).getRegionWidth();
        livesAnimHeight = livesAnimation.getKeyFrame(0).getRegionHeight();
        sideAnimWidth = leftSideAnimation.getKeyFrame(0).getRegionWidth();
        sideAnimHeight = leftSideAnimation.getKeyFrame(0).getRegionHeight();

    }

    public void setCenterPos(float x, float y) {
        this.x = x - livesAnimation.getKeyFrame(0).getRegionWidth() * scaleX / 2;
        this.y = y - livesAnimation.getKeyFrame(0).getRegionHeight() * scaleX / 2;
    }

    public void setScale(float scale) {
        scaleX = scale;
        scaleY = scale;
    }

    public void draw(float deltaTime, Batch batch) {
        elapsedTime += deltaTime;

        batch.end();
        TextureRegion region = livesAnimation.getKeyFrame(elapsedTime);
        float myLivesLossPercent = 1 - Player.myLives / PlayerValues.BASE_START_LIVES;
        float enemyLivesLossPercent = 1 - Player.enemyLives / PlayerValues.BASE_START_LIVES;
        int xStart = (int) (x + (livesAnimWidth * scaleX / 2) * myLivesLossPercent);
        int xToEndDelta = (int) ((livesAnimWidth * scaleX) - (livesAnimWidth * scaleX / 2) * myLivesLossPercent - (livesAnimWidth * scaleX / 2) * enemyLivesLossPercent);
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(xStart, (int) y, xToEndDelta, (int) (livesAnimHeight * scaleY));
        batch.begin();
        batch.draw(region, x, y, livesAnimWidth * scaleX, livesAnimHeight * scaleY);
        batch.end();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        batch.begin();

        batch.draw(leftSideAnimation.getKeyFrame(elapsedTime), xStart - sideAnimWidth * scaleX / 2, y + livesAnimHeight * scaleY / 2 - sideAnimHeight * scaleY / 2, sideAnimWidth * scaleX, sideAnimHeight * scaleY);
        batch.draw(rightSideAnimation.getKeyFrame(elapsedTime), xStart + xToEndDelta - sideAnimWidth * scaleX / 2, y + livesAnimHeight * scaleY / 2 - sideAnimHeight * scaleY / 2, sideAnimWidth * scaleX, sideAnimHeight * scaleY);

    }

}
