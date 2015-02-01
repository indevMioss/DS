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
    public UnitPurchaseSystem unitPurchaseSystem;

    public UI(float virtualWidth, InputMultiplexer inputMultiplexer) {
        stage = new Stage();
        inputMultiplexer.addProcessor(stage);

        bgTexture = new Texture(Gdx.files.internal("ui_bg.png"));
        bg = new Image(bgTexture);
        bg.setPosition(0, 0);
        bg.setScale(virtualWidth / bg.getWidth());
        stage.addActor(bg);

        unitPurchaseSystem = new UnitPurchaseSystem(bg.getHeight()*bg.getScaleY(), stage);
        unitPurchaseSystem.setScale(bg.getScaleX());
        unitPurchaseSystem.setPosition(getScaledWidth() / 2 - unitPurchaseSystem.getWidth() * unitPurchaseSystem.getScaleX() / 2, getScaledHeight() / 2 - unitPurchaseSystem.getHeight() * unitPurchaseSystem.getScaleY() / 2);
        stage.addActor(unitPurchaseSystem);


        bg.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Log.info("UI BG touchDown");
                return true;
            }

        });
    }

    public float getScaledWidth() {
        return bg.getWidth() * bg.getScaleX();
    }

    public float getScaledHeight() {
        return bg.getHeight() * bg.getScaleY();
    }

    public void draw(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }
}
