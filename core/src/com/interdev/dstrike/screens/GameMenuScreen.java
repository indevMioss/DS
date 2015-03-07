package com.interdev.dstrike.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.Utils.TextureButton;

public class GameMenuScreen implements Screen {
    private Stage stage;
    private TextureButton random_player_btn = new TextureButton("random_player_button.jpg");
    private TextureButton play_with_friend_btn = new TextureButton("play_with_friend_button.jpg");
    private TextureButton leaderboard_btn = new TextureButton("leaderboard_button.jpg");
    private TextureButton help_btn = new TextureButton("howtoplay_button.jpg");

    @Override
    public void show() {
        stage = new Stage();
        random_player_btn.setPosition(Main.screen_width / 2 - random_player_btn.getWidth() / 2, Main.screen_height - random_player_btn.getHeight() * 2);
        play_with_friend_btn.setPosition(random_player_btn.getX(), random_player_btn.getY() - random_player_btn.getHeight() * 2);
        leaderboard_btn.setPosition(play_with_friend_btn.getX(), play_with_friend_btn.getY() - play_with_friend_btn.getHeight() * 2);
        help_btn.setPosition(leaderboard_btn.getX(), leaderboard_btn.getY() - leaderboard_btn.getHeight() * 2);

        random_player_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.LOADING);
            }
        });
        play_with_friend_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.INVITE_FRIEND);
            }
        });
        leaderboard_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.LEADERBOARD);
            }
        });
        help_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.INSTRUCTIONS);
            }
        });
        stage.addActor(random_player_btn);
        stage.addActor(play_with_friend_btn);
        stage.addActor(leaderboard_btn);
        stage.addActor(help_btn);
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
