package com.interdev.dstrike.screens.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActiveUnit extends Actor {

    public short type;
    public int id;
    public int targetId;
    public short lives;

    private float timeFromLastTargetXYUpdate = 0;
    private float lastX, lastY;
    private float targetX, targetY;

    private TextureRegion textureRegion;

    public ActiveUnit(float x, float y, short type, int id) {
        this.type = type;
        this.id = id;
        setPosition(x,y);

        targetX = x;
        targetY = y;
        lastX = targetX;
        lastY = targetY;

        UnitValues.UnitVal unitType = UnitValues.getByType(type);

        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(unitType.texturePath)));
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        setWidth(textureRegion.getRegionWidth());
        setHeight(textureRegion.getRegionHeight());
    }

    public void setTargetDestination(float x, float y) {
        timeFromLastTargetXYUpdate = 0;
        lastX = targetX;
        lastY = targetY;

        int cellX = (int) (x/64);
        int cellY = (int) (y/64);

        targetX = cellX*64 + 32;
        targetY = cellY*64 + 32;

        //   targetX = x;
     //   targetY = y;

    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        timeFromLastTargetXYUpdate += deltaTime;

        float percent = timeFromLastTargetXYUpdate/(GameScreen.tickInterval/1000f);
        if (percent > 1f) percent = 1f;

        setX(Interpolation.linear.apply(lastX, targetX, percent));
        setY(Interpolation.linear.apply(lastY, targetY, percent));

    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, getX() - getWidth()/2, getY() - getHeight()/2, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
