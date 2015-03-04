package com.interdev.dstrike.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.networking.PackedCell;
import com.interdev.dstrike.screens.game.camera.MultipleVirtualViewportBuilder;
import com.interdev.dstrike.screens.game.camera.OrthographicCameraWithVirtualViewport;
import com.interdev.dstrike.screens.game.camera.VirtualViewport;
import com.interdev.dstrike.screens.game.ui.UI;

public class GameScreen implements Screen, GestureDetector.GestureListener {
    public static int tickInterval = 0; //инициализируется повторно с сервера

    public static final int BATTLE_FIELD_TILES = 2;

    public static float personalFieldWidth, personalFieldHeight;
    public static float totalFieldWidth, totalFieldHeight;

    public boolean screenLoaded = false;

    public Player player;
    public UI ui;
    public float virutalWidth;
    public float virutalHeight;

    private float zoom;
    private float initialZoom = 1f;

    private MultipleVirtualViewportBuilder multipleVirtualViewportBuilder;
    private VirtualViewport virtualViewport;

    public OrthographicCameraWithVirtualViewport camera;
    public InputMultiplexer inputMultiplexer;

    private Texture battlefieldBgTexture = new Texture(Gdx.files.internal("bg_tile.jpg"));
    private Texture platformTexture = new Texture(Gdx.files.internal("platform.jpg"));
    private Texture platformTopTexture = new Texture(Gdx.files.internal("platform_top.png"));


    public Stage mainStage;
    private Base myBase, enemyBase;

    private Texture cellTexture = new Texture(Gdx.files.internal("cell.png"));
    private Image cellImage = new Image(cellTexture);

    @Override
    public void show() {
        Main.gameScreenReference = this;

        multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(480, 800, 720, 1280);
        virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCameraWithVirtualViewport(virtualViewport);
        virutalWidth = (int) virtualViewport.getVirtualWidth();
        virutalHeight = (int) virtualViewport.getVirtualHeight();

        totalFieldWidth = battlefieldBgTexture.getWidth();
        totalFieldHeight = battlefieldBgTexture.getHeight() * BATTLE_FIELD_TILES + platformTexture.getHeight();

        zoom = totalFieldWidth / virutalWidth;

        Log.info("virutalWidth " + virutalWidth + "  virutalHeight " + virutalHeight);
        Log.info("totalFieldWidth " + totalFieldWidth + "  totalFieldHeight " + totalFieldHeight);
        camera.position.set(totalFieldWidth / 2, totalFieldHeight / 2, 10f);
        camera.update(true);

        mainStage = new Stage();
        mainStage.getViewport().setCamera(camera);

        Image platform = new Image(platformTexture);
        platform.setPosition(0, 0);
        mainStage.addActor(platform);


        personalFieldWidth = platform.getWidth();
        personalFieldHeight = platform.getHeight();


        for (int i = 0; i < BATTLE_FIELD_TILES; i++) {
            Image battlefieldBgTile = new Image(battlefieldBgTexture);
            battlefieldBgTile.setPosition(platform.getX() + 0, platform.getHeight() + battlefieldBgTile.getHeight() * i);
            mainStage.addActor(battlefieldBgTile);
        }

        Image platformTop = new Image(platformTopTexture);
        platformTop.setPosition(0, platform.getHeight());
        mainStage.addActor(platformTop);

        float basesOffset = 0.2f;
        myBase = new Base();
        myBase.setPosition(totalFieldWidth / 2, personalFieldHeight + personalFieldHeight * basesOffset);
        myBase.setRotation(180);
        mainStage.addActor(myBase);

        enemyBase = new Base();
        enemyBase.setPosition(totalFieldWidth / 2, totalFieldHeight - personalFieldHeight * basesOffset);
        mainStage.addActor(enemyBase);


        player = new Player(this);

        inputMultiplexer = new InputMultiplexer();
        ui = new UI(this);
        inputMultiplexer.addProcessor(new GestureDetector(this));
        Gdx.input.setInputProcessor(inputMultiplexer);

        screenLoaded = true;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.zoom = zoom;
        camera.update();

        mainStage.getBatch().setProjectionMatrix(camera.projection);
        mainStage.act(delta);
        mainStage.draw();
        drawCells(mainStage.getBatch());

        ui.draw(delta);

    }

    private void drawCells(Batch batch) {
        batch.begin();
        if (player.packedCells != null) {
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 16; j++) {
                    PackedCell cell = player.packedCells[i][j];
                    if (cell != null) {
                        if (cell.free) {
                            cellImage.setColor(1f, 1f, 1f, 1f);
                        } else {
                            cellImage.setColor(1f, 0.5f, 0.5f, 1f);
                        }
                        cellImage.setPosition(cell.x, cell.y);
                        cellImage.draw(batch, 1f);
                    }
                }
            }
        }

        batch.end();
    }

    private void checkCameraBounds() {
        float minCameraX = camera.zoom * (camera.viewportWidth / 2);
        float maxCameraX = totalFieldWidth - minCameraX;
        float minCameraY = camera.zoom * (camera.viewportHeight / 2 - ui.getBgHeight());
        float maxCameraY = totalFieldHeight - minCameraY - ui.getBgHeight() * camera.zoom;

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


    // ------------------------------------------------- GestureDetector.GestureListener implementation methods -------------------------------------------------


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