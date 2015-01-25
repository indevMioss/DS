package com.interdev.dstrike.screens.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Base extends Actor {

    public int lives = PlayerValues.BASE_START_LIVES;
    public int targetID = 0;

    private final int damage = PlayerValues.BASE_DAMAGE;
    private TextureRegion textureRegion;


    public Base() {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("base.png")));
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setScale(0.5f);
        setWidth(textureRegion.getRegionWidth());
        setHeight(textureRegion.getRegionHeight());
        setOrigin(getWidth()/2, getHeight()/2);

    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, getX() - getWidth()/2, getY() - getHeight()/2, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
