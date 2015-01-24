package com.interdev.dstrike.screens.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Unit extends Actor {

    public short type;
    public int id;
    public int targetId;
    public short lives;


    private TextureRegion textureRegion;

    public Unit(short type) {
        this.type = type;

        switch (type) {
            case 1:
                textureRegion = new TextureRegion(new Texture(Gdx.files.internal("unit1.png")));
                initUnitValues(UnitValues.unit1);
                break;
            case 2:
                textureRegion = new TextureRegion(new Texture(Gdx.files.internal("unit2.png")));
                initUnitValues(UnitValues.unit2);
                break;
            case 3:
                textureRegion = new TextureRegion(new Texture(Gdx.files.internal("unit3.png")));
                initUnitValues(UnitValues.unit3);
                break;
            case 4:
                textureRegion = new TextureRegion(new Texture(Gdx.files.internal("unit4.png")));
                initUnitValues(UnitValues.unit4);
                break;
        }
        setWidth(textureRegion.getRegionWidth());
        setHeight(textureRegion.getRegionHeight());
    }

    private void initUnitValues(UnitValues.UnitVal unitTypeValues) {

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
