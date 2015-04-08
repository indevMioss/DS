package com.interdev.dstrike.screens.game.units;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.interdev.dstrike.screens.game.GameScreen;
import com.interdev.dstrike.screens.game.bullets.BulletFactory;

public class ActiveUnit extends Actor implements Combative {

    public short type;
    public int id;

    public int targetId = 0;
    private Combative targetUnit;

    public short lives;
    private float atkInterval;
    private int bulletType;


    private float timeFromLastTargetXYUpdate = 0;

    private float lastX, lastY;
    private float targetX, targetY;

    private TextureRegion textureRegion;

    private BulletFactory bulletFactoryRef;

    public ActiveUnit(float x, float y, short type, int id, BulletFactory bulletFactoryRef) {
        this.bulletFactoryRef = bulletFactoryRef;

        this.type = type;
        this.id = id;
        setPosition(x, y);

        targetX = x;
        targetY = y;
        lastX = targetX;
        lastY = targetY;

        UnitValues.UnitVal unitType = UnitValues.getByType(type);

        atkInterval = unitType.atk_interval;
        bulletType = unitType.bullet_type;

        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(unitType.texturePath)));
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        setWidth(textureRegion.getRegionWidth());
        setHeight(textureRegion.getRegionHeight());
    }

    public void setTargetDestination(float x, float y) {
        timeFromLastTargetXYUpdate = 0;
        lastX = targetX;
        lastY = targetY;

        targetX = x;
        targetY = y;

    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        timeFromLastTargetXYUpdate += deltaTime;

        float percent = timeFromLastTargetXYUpdate / (GameScreen.tickInterval / 1000f);
        if (percent > 1f) percent = 1f;

        setX(Interpolation.linear.apply(lastX, targetX, percent));
        setY(Interpolation.linear.apply(lastY, targetY, percent));

    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, getX() - getWidth() / 2, getY() - getHeight() / 2, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void setTarget(Combative target) {
        if (targetUnit != null) {
            bulletFactoryRef.disposeBondByShooter(this);
        }
        this.targetUnit = target;
        this.targetId = target.getID();

        bulletFactoryRef.addBond(this, targetUnit);
    }

    public void dispose() {

        bulletFactoryRef.disposeBondByShooter(this);
        bulletFactoryRef.disposeBondByTarget(this);
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
        return (lives > 0);
    }

    @Override
    public float getAttackInterval() {
        return atkInterval;
    }
}
