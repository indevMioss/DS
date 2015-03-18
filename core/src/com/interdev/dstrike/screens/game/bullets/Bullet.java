package com.interdev.dstrike.screens.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

public class Bullet extends Actor implements Pool.Poolable {

    public static TextureRegion bullet1TR = new TextureRegion(new Texture(Gdx.files.internal("bullet1.png")));
    public static TextureRegion bullet2TR = new TextureRegion(new Texture(Gdx.files.internal("bullet2.png")));

    private TextureRegion textureRegion;

    public Bullet() {
        System.out.println("bulletCreated");

    }

    public void initTexture(int type) {
        switch (type) {
            case 1:
                textureRegion = bullet1TR;
                break;
            case 2:
                textureRegion = bullet2TR;
                break;

            default:
                textureRegion = bullet1TR;
                break;
        }
        setBounds(getX(), getY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setOrigin(getWidth()/2, getHeight()/2);
    }

    @Override
    public void reset() {
        setVisible(false);
        setPosition(0, 0);
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

