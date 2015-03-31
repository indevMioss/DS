package com.interdev.dstrike.screens.game.units;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PassiveUnit extends Actor {

    public short type;
    public int id;

    public short price;
    public short lives;
    public short damage;
    public short atk_range;
    public float atk_speed;
    public short walk_speed;

    private TextureRegion textureRegion;

    public PassiveUnit(int x, int y, short type) {
        this.type = type;

        UnitValues.UnitVal unitType = UnitValues.getByType(type);
        price = unitType.price;
        lives = unitType.lives;
        damage = unitType.damage;
        atk_range = unitType.atk_range;
        atk_speed = unitType.atk_interval;
        walk_speed = unitType.walk_speed;

        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(unitType.texturePath)));
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        setWidth(textureRegion.getRegionWidth());
        setHeight(textureRegion.getRegionHeight());

        setPosition(x,y);
    }


    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, getX() - getWidth() / 2, getY() - getHeight() / 2, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
