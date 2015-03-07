package com.interdev.dstrike.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.Utils.GDXUtilily;

public class OptionsScreen implements Screen {

    private Stage stage;

    private Slider soundSlider;
    private Slider musicSlider;

    private Label soundLbl;
    private Label musicLbl;
    private Label userNameLbl;

    private TextField userNameTxtField;


    @Override
    public void show() {

        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        soundLbl = new Label("SOUND", uiSkin);
        musicLbl = new Label("MUSIC", uiSkin);
        userNameLbl = new Label("NICKNAME:", uiSkin);

        soundSlider = new Slider(0, 2, 0.2f, false, uiSkin);
        musicSlider = new Slider(0, 2, 0.2f, false, uiSkin);

        userNameTxtField = new TextField("NewPlayer", uiSkin);

        soundLbl.setPosition(Main.screen_width / 2 - soundLbl.getWidth() * 2, Main.screen_height / 2 - soundLbl.getHeight() / 2);
        userNameTxtField.setPosition(soundLbl.getX() - soundLbl.getWidth() / 4, soundLbl.getY() + soundLbl.getHeight() * 2);
        userNameLbl.setPosition(soundLbl.getX() - soundLbl.getWidth() / 4, soundLbl.getY() + soundLbl.getHeight() * 4);
        soundSlider.setPosition(soundLbl.getX(), soundLbl.getY() - soundLbl.getHeight() * 2);
        musicLbl.setPosition(soundSlider.getX(), soundSlider.getY() - soundSlider.getHeight() * 2);
        musicSlider.setPosition(musicLbl.getX(), musicLbl.getY() - musicLbl.getHeight() * 2);


        soundSlider.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Main.soundVolume = soundSlider.getValue();
            }
        });

        musicSlider.addListener(new ChangeListener() {
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                Main.musicVolume = musicSlider.getValue();
            }
        });

        stage = new Stage();

        stage.addActor(soundSlider);
        stage.addActor(musicSlider);
        stage.addActor(soundLbl);
        stage.addActor(musicLbl);
        stage.addActor(userNameTxtField);
        stage.addActor(userNameLbl);

        GDXUtilily.scale(soundSlider, Main.resolution_scale_componet * 2);
        GDXUtilily.scale(musicSlider, Main.resolution_scale_componet * 2);

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