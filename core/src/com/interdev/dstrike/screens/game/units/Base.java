package com.interdev.dstrike.screens.game.units;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.interdev.dstrike.screens.game.PlayerValues;
import com.interdev.dstrike.screens.game.bullets.BulletFactory;

public class Base extends Actor implements Combative {
    private int id;
    public int targetId = 0;

    private final float atkInterval = PlayerValues.BASE_ATTACK_INTERVAL;
    private final int bulletType = 1;

    private TextureRegion textureRegion;
    private BulletFactory bulletFactoryRef;
    private Combative targetUnit;


    public Base(boolean baseAtTheTop, BulletFactory bulletFactoryRef) {
        this.bulletFactoryRef = bulletFactoryRef;

        if (baseAtTheTop) {
            id = PlayerValues.ENEMY_BASE_ID;
        } else {
            id = PlayerValues.MY_BASE_ID;
        }

        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("base.png")));
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setScale(0.5f);
        setWidth(textureRegion.getRegionWidth());
        setHeight(textureRegion.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

    }

    private void rotateToTarget() {

    }

    public void setTarget(Combative target) {
        if (targetUnit != null) {
            bulletFactoryRef.disposeBondByShooter(this);
        }
        this.targetUnit = target;
        this.targetId = target.getID();

        bulletFactoryRef.addBond(this, targetUnit);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, getX() - getWidth() / 2, getY() - getHeight() / 2, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getBulletType() {
        return bulletType;
    }

    @Override
    public float getAttackInterval() {
        return atkInterval;
    }
}
