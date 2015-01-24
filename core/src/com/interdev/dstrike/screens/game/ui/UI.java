package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;

public class UI {

    public Stage stage;
    public Texture bgTexture;
    public Image bg;
    private UnitPurchaseButton unitPurchaseButton;

    public UI(float virtualWidth, InputMultiplexer inputMultiplexer) {
        stage = new Stage();
        inputMultiplexer.addProcessor(stage);

        bgTexture = new Texture(Gdx.files.internal("ui_bg.png"));
        bg = new Image(bgTexture);
        bg.setPosition(0,0);
        stage.addActor(bg);

        unitPurchaseButton = new UnitPurchaseButton();
        stage.addActor(unitPurchaseButton);

        scaleTo(virtualWidth);

        bg.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Log.info("UI BG pressed");
                return true;
            }

        });
    }

    public void scaleTo(float width) {
        bg.setScale(width/bg.getWidth());
        unitPurchaseButton.setScale(bg.getScaleX());
        unitPurchaseButton.setPosition(getScaledWidth()/2 - unitPurchaseButton.getWidth()*unitPurchaseButton.getScaleX()/2, getScaledHeight()/2 - unitPurchaseButton.getHeight()*unitPurchaseButton.getScaleY()/2);
    }

    public float getScaledWidth() {
        return bg.getWidth()*bg.getScaleX();
    }
    public float getScaledHeight() {
        return bg.getHeight()*bg.getScaleY();
    }

    public void draw(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }
}
