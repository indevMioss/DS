package com.interdev.dstrike.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.Utils.TextureButton;
import com.interdev.dstrike.screens.game.camera.MultipleVirtualViewportBuilder;
import com.interdev.dstrike.screens.game.camera.OrthographicCameraWithVirtualViewport;
import com.interdev.dstrike.screens.game.camera.VirtualViewport;
import com.interdev.dstrike.screens.game.ui.UI;

public class StartMenuScreen implements Screen {
    private Stage stage;
    private TextureButton button1;

    @Override
    public void show() {
        stage = new Stage();


        button1 = new TextureButton(new Texture(Gdx.files.internal("play_online_button.jpg")));
        button1.setPosition(Main.screen_width / 2 - button1.getWidth() / 2, Main.screen_height / 2 - button1.getHeight() / 2);
        button1.setScale(Main.resolution_scale_componet);
        System.out.println(Main.screen_width + " " + Gdx.graphics.getWidth());
        button1.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.GAME);
            }
        });
        stage.addActor(button1);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        // use true here to center the camera
        // that's what you probably want in case of a UI
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}