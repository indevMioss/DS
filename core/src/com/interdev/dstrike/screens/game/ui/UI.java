package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.interdev.dstrike.screens.GDXUtilily;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;
import com.interdev.dstrike.screens.game.ui.layers.gas.GasLayer;
import com.interdev.dstrike.screens.game.ui.layers.main.MainLayer;
import com.interdev.dstrike.screens.game.ui.layers.units.UnitPurchaseLayer;
import com.interdev.dstrike.screens.game.ui.layers.upgrades.UpgradesLayer;

public class UI {
    public final static int BG_SAFE_HEIGHT = 316;

    public final static int MONEY_FONT_SIZE = 42;
    public final static int ICONS_FONT_SIZE = 36;

    public BitmapFont moneyFont;
    public BitmapFont iconsFont;

    public enum UiLayers {MAIN, UPGRADES, UNITS, GAS;}

    public UiLayers currentLayerName;
    public UILayer currentLayer;

    public final float virtualWidth;


    private SpriteBatch livesBatch = new SpriteBatch();

    public MainLayer mainLayer;
    public UnitPurchaseLayer unitPurchaseLayer;
    public UpgradesLayer upgradesLayer;
    public GasLayer gasLayer;

    public float layersScale;

    public Stage stage;

    public TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("ui/ui2.txt"));
    public Array<TextureAtlas.AtlasRegion> livesTextureArray = textureAtlas.findRegions("lives1");
    public LivesAnimation livesAnimation;

    public Image bg = new Image(textureAtlas.findRegion("bg"));
    public Image glass = new Image(textureAtlas.findRegion("glass"));

    public UI(float virtualWidth, InputMultiplexer inputMultiplexer) {


        this.virtualWidth = virtualWidth;
        layersScale = virtualWidth / bg.getWidth();
        stage = new Stage();
        inputMultiplexer.addProcessor(stage);

        initFonts();

        GDXUtilily.scale(bg, layersScale);
        stage.addActor(bg);

        System.out.println(livesTextureArray.size + " -----------");
        livesAnimation = new LivesAnimation(0.07f, livesTextureArray, Animation.PlayMode.LOOP);
        livesAnimation.setScale(layersScale);
        livesAnimation.setCenterPos(bg.getWidth() * 0.5f, bg.getHeight() * 0.605f);

        GDXUtilily.scale(glass, layersScale);
        GDXUtilily.setPosCentr(glass, bg.getWidth() * 0.5f, bg.getHeight() * 0.605f);


        mainLayer = new MainLayer(this, bg, layersScale);
        upgradesLayer = new UpgradesLayer(this, bg, layersScale);
        gasLayer = new GasLayer(this, bg, layersScale);
        unitPurchaseLayer = new UnitPurchaseLayer(this, bg, layersScale, BG_SAFE_HEIGHT * layersScale);

        setUiLayer(UiLayers.UNITS);

    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font_ui.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) ((float) MONEY_FONT_SIZE * layersScale);
        moneyFont = generator.generateFont(parameter);

        parameter.size = (int) ((float) ICONS_FONT_SIZE * layersScale);
        iconsFont = generator.generateFont(parameter);
        iconsFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

    }


    public void draw(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();

        livesBatch.begin();
        livesAnimation.draw(deltaTime, livesBatch);
        glass.draw(livesBatch, 1f);
        livesBatch.end();

        if (currentLayer.equals(unitPurchaseLayer)) {
            unitPurchaseLayer.draw(deltaTime);
        }

    }

    public float getBgHeight() {
        return BG_SAFE_HEIGHT * layersScale;
    }

    public void setUiLayer(UiLayers layer) {
        if (currentLayer != null) {
            currentLayer.setVisible(false);
        }
        currentLayerName = layer;
        switch (currentLayerName) {
            case MAIN:
                currentLayer = mainLayer;
                break;
            case UPGRADES:
                currentLayer = upgradesLayer;
                break;
            case UNITS:
                currentLayer = unitPurchaseLayer;
                break;
            case GAS:
                currentLayer = gasLayer;
                break;
        }
        currentLayer.setVisible(true);

    }
}
