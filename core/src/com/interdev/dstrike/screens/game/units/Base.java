package com.interdev.dstrike.screens.game.units;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.interdev.dstrike.screens.game.Player;
import com.interdev.dstrike.screens.game.PlayerValues;
import com.interdev.dstrike.screens.game.bullets.BulletFactory;

public class Base extends Actor implements Combative {

    private static final float rotationSpeed = 360f; //degrees per second

    private int id;
    public int targetId = 0;

    private final float atkInterval = PlayerValues.BASE_ATTACK_INTERVAL;
    private final int bulletType = 1;

    private float destRotation;

    private TextureRegion textureRegion;
    private BulletFactory bulletFactoryRef;
    private Combative targetUnit;
    private boolean baseAtTheTop;

    public Base(boolean baseAtTheTop, BulletFactory bulletFactoryRef) {
        this.bulletFactoryRef = bulletFactoryRef;
        this.baseAtTheTop = baseAtTheTop;

        if (baseAtTheTop) {
            destRotation = 0;
            id = PlayerValues.ENEMY_BASE_ID;
        } else {
            destRotation = -180;
            id = PlayerValues.MY_BASE_ID;
        }
        setRotation(destRotation);

        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("base.png")));
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setScale(0.5f);
        setWidth(textureRegion.getRegionWidth());
        setHeight(textureRegion.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

    }

    private void setDestRotation(Combative target) {
            destRotation = ((float) Math.toDegrees(Math.atan2(getY() - target.getY(), getX() - target.getX()) - Math.PI / 2));

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
        if (targetUnit != null && targetUnit.isAlive()) {
            setDestRotation(targetUnit);
        } else {
            if (baseAtTheTop) {
                destRotation = 0;
            } else {
                destRotation = -180;
            }
        }
        if (getRotation() != destRotation) {
            rotateToDestAngle(deltaTime);
        }

        super.act(deltaTime);
    }

    private void rotateToDestAngle(float deltaTime) {
        float rotationDelta = rotationSpeed * deltaTime;
        System.out.println(destRotation);
        if (baseAtTheTop) {
            if (getRotation() < destRotation) {
                setRotation(Math.min(getRotation() + rotationDelta, destRotation));
            } else if (getRotation() > destRotation) {
                setRotation(Math.max(getRotation() - rotationDelta, destRotation));
            }
        } else {
            if (getRotation() < destRotation) {
                setRotation(Math.min(getRotation() + rotationDelta, destRotation));
            } else if (getRotation() > destRotation) {
                setRotation(Math.max(getRotation() - rotationDelta, destRotation));
            }
        }
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
    public boolean isAlive() {
        return (Player.myLives > 0);
    }

    @Override
    public float getAttackInterval() {
        return atkInterval;
    }
}
