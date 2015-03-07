package com.interdev.dstrike.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.Utils.GDXUtilily;
import com.interdev.dstrike.screens.Utils.TextureButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import javax.management.StringValueExp;
import java.awt.*;

public class StartMenuScreen implements Screen {
    private Stage stage;
    Group background;
    Image bgd_image;
    private TextureButton play_online_btn = new TextureButton("play_online_button.jpg");
    private TextureButton options_btn = new TextureButton("options_button.jpg");



    @Override
    public void show() {





        stage = new Stage();
        bgd_image = new Image(new Sprite(new Texture("startmenubg.jpg")));

        background = new Group();
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        play_online_btn.setPosition(Main.screen_width / 2 - play_online_btn.getWidth() / 2, Main.screen_height / 2 - play_online_btn.getHeight() / 2);
        options_btn.setPosition(play_online_btn.getX(), play_online_btn.getY() - play_online_btn.getHeight() * 2);

        play_online_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.GAME_MENU);
            }
        });
        options_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.OPTIONS);
            }
        });

        stage.addActor(background);
        GDXUtilily.scale(bgd_image, Main.resolution_scale_componet);
        background.addActor(bgd_image);
        stage.addActor(play_online_btn);
        stage.addActor(options_btn);
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