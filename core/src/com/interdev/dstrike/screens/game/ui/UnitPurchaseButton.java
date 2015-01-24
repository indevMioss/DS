package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.esotericsoftware.minlog.Log;

public class UnitPurchaseButton extends Actor {

    public Texture texture;

    public UnitPurchaseButton() {
        texture = new Texture(Gdx.files.internal("unit_purchase_button.png"));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());

        setBounds(getX(), getY(), getWidth(), getHeight());


        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Log.info("button pressed");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Log.info("button unpressed");
            }
        });
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
    }

}
