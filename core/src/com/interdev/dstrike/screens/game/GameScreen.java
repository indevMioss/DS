package com.interdev.dstrike.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.screens.game.camera.MultipleVirtualViewportBuilder;
import com.interdev.dstrike.screens.game.camera.OrthographicCameraWithVirtualViewport;
import com.interdev.dstrike.screens.game.camera.VirtualViewport;

public class GameScreen implements Screen, GestureDetector.GestureListener {

    private int virutalWidth;
    private int virutalHeight;
    private int totalFieldWidth;
    private int totalFieldHeight;

    private float zoom;
    private float initialZoom = 1f;

    private MultipleVirtualViewportBuilder multipleVirtualViewportBuilder;
    private VirtualViewport virtualViewport;
    private OrthographicCameraWithVirtualViewport camera;

    private Texture battlefieldBgTexture;
    private Texture personalFieldBgTexture;
    private Image battlefieldBg;
    private Image personalFieldBg;

    private Stage mainStage;


    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GestureDetector(this));
        initTextures();

        multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(640, 1136, 720, 1280);
        virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCameraWithVirtualViewport(virtualViewport);
        virutalWidth = (int) virtualViewport.getVirtualWidth();
        virutalHeight = (int) virtualViewport.getVirtualHeight();

        totalFieldWidth = battlefieldBgTexture.getWidth();
        totalFieldHeight = battlefieldBgTexture.getHeight() + personalFieldBgTexture.getWidth();

        zoom = (float)totalFieldWidth / virutalWidth;

        Log.info("virutalWidth " + virutalWidth + "  virutalHeight " + virutalHeight);
        Log.info("totalFieldWidth " + totalFieldWidth + "  totalFieldHeight " + totalFieldHeight);

        camera.position.set(totalFieldWidth / 2, totalFieldHeight / 2, 10f);
        camera.update(true);


        personalFieldBg = new Image(personalFieldBgTexture);
        personalFieldBg.setPosition(0, 0);
        battlefieldBg = new Image(battlefieldBgTexture);
        battlefieldBg.setPosition(personalFieldBg.getX() + 0, personalFieldBg.getY() + personalFieldBg.getHeight());


        mainStage = new Stage();
        mainStage.getViewport().setCamera(camera);

        mainStage.addActor(personalFieldBg);
        mainStage.addActor(battlefieldBg);

    }

    private void initTextures() {
        battlefieldBgTexture = new Texture(Gdx.files.internal("battlefield_bg.jpg"));
        personalFieldBgTexture = new Texture(Gdx.files.internal("personal_field_bg.png"));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.zoom = zoom;
        camera.update();

        mainStage.getBatch().setProjectionMatrix(camera.projection);
        mainStage.act(Gdx.graphics.getDeltaTime());
        mainStage.draw();


    }

    private void checkCameraBounds() {
        float minCameraX = camera.zoom * (camera.viewportWidth / 2);
        float maxCameraX = totalFieldWidth - minCameraX;
        float minCameraY = camera.zoom * (camera.viewportHeight / 2);
        float maxCameraY = totalFieldHeight - minCameraY;

        if (camera.position.x > maxCameraX) camera.position.x = maxCameraX;
        if (camera.position.x < minCameraX) camera.position.x = minCameraX;
        if (camera.position.y > maxCameraY) camera.position.y = maxCameraY;
        if (camera.position.y < minCameraY) camera.position.y = minCameraY;
    }

    @Override
    public void resize(int width, int height) {
        virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        virutalWidth = (int) virtualViewport.getVirtualWidth();
        virutalHeight = (int) virtualViewport.getVirtualHeight();

        camera.setVirtualViewport(virtualViewport);
        camera.updateViewport();
        camera.position.set(totalFieldWidth / 2, totalFieldHeight / 2, 0f);
        checkCameraBounds();


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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        initialZoom = zoom;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX, deltaY);
        checkCameraBounds();
        camera.update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        //Calculate pinch to zoom
        float ratio = initialDistance / distance;
        //Clamp range and set zoom
        zoom = MathUtils.clamp(initialZoom * ratio, 0.5f, 1.25f);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}