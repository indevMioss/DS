package com.interdev.dstrike.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.Utils.TextureButton;


/**
 * Created by Evg256 on 07.03.2015.
 */


public class AuthorizationScreen implements Screen {
    private Stage stage;

    private Label userIdLbl;
    private Label passwordLbl;
    private Label loginLbl;

    private TextField userIdTxtField;
    private TextField passwordTxtField;

    private CheckBox rememberPassCheckBox;

    private TextureButton loginBtn = new TextureButton("login_button.png");

    private boolean rememberPassEnabled = false;

    @Override
    public void show() {

        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        userIdLbl = new Label("User ID / E-mail:", uiSkin);
        passwordLbl = new Label("Password:", uiSkin);
        loginLbl = new Label("Log in to your account", uiSkin);

        userIdTxtField = new TextField("E-mail", uiSkin);
        passwordTxtField = new TextField("", uiSkin);

        rememberPassCheckBox = new CheckBox("Remember me on this device", uiSkin);

        userIdLbl.setPosition(Main.screen_width / 2 - userIdLbl.getWidth() * 2, Main.screen_height / 2 - userIdLbl.getHeight() / 2);
        loginLbl.setPosition(userIdLbl.getX() - userIdLbl.getWidth() / 4, userIdLbl.getY() + userIdLbl.getHeight() * 2);
        passwordLbl.setPosition(userIdLbl.getX(), userIdLbl.getY() - userIdLbl.getHeight() * 2.3f);
        userIdTxtField.setPosition(userIdLbl.getX() + userIdLbl.getWidth() * 1.5f, userIdLbl.getY() - userIdLbl.getHeight() / 2);
        userIdTxtField.setSize(300, 40);
        passwordTxtField.setPosition(userIdTxtField.getX(), userIdTxtField.getY() - userIdTxtField.getHeight() * 1.3f);
        passwordTxtField.setSize(300, 40);
        passwordTxtField.setPasswordCharacter('*');
        passwordTxtField.setPasswordMode(true);
        rememberPassCheckBox.setPosition(passwordTxtField.getX(), passwordTxtField.getY() - passwordTxtField.getHeight() * 1.3f);
        rememberPassCheckBox.setChecked(!rememberPassEnabled);
        loginBtn.setPosition(rememberPassCheckBox.getX(), rememberPassCheckBox.getY() - rememberPassCheckBox.getHeight() * 6);

        rememberPassCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                rememberPassEnabled = !rememberPassCheckBox.isChecked();
            }
        });

        loginBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // ADD SOME COD HERE TO DO
            }
        });

            stage = new Stage();

        stage.addActor(userIdLbl);
        stage.addActor(loginLbl);
        stage.addActor(passwordLbl);
        stage.addActor(userIdTxtField);
        stage.addActor(passwordTxtField);
        stage.addActor(rememberPassCheckBox);
        stage.addActor(loginBtn);

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