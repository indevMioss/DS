package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.interdev.dstrike.screens.Utils.GDXUtilily;
import com.interdev.dstrike.screens.game.GameScreen;
import com.interdev.dstrike.screens.game.Player;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;
import com.interdev.dstrike.screens.game.ui.layers.WaveTimer;
import com.interdev.dstrike.screens.game.ui.layers.gas.GasLayer;
import com.interdev.dstrike.screens.game.ui.layers.main.MainLayer;
import com.interdev.dstrike.screens.game.ui.layers.units.UnitPurchaseLayer;
import com.interdev.dstrike.screens.game.ui.layers.upgrades.UpgradesLayer;

public class UI {
    public static float BG_SAFE_HEIGHT_SCALED;
    private static final int BG_SAFE_HEIGHT = 316;

    public final static int MONEY_FONT_SIZE = 42;
    public final static int ICONS_FONT_SIZE = 36;

    public float virtualWidth;

    public float layersScale;

    public TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("ui/ui3.txt"));

    private Texture bgTR = new Texture(Gdx.files.internal("ui/bg1.png"));
    private Texture glassTR = new Texture(Gdx.files.internal("ui/glass2.png"));

    public Image bg;// = new Image(textureAtlas.findRegion("bg"));
    public Image glass;// = new Image(textureAtlas.findRegion("glass"));

    private SpriteBatch uiBatch = new SpriteBatch();
    public Stage stage;

    public BitmapFont moneyFont;
    public BitmapFont iconsFont;
    private float moneyTextWidth;
    private float moneyTextHeight;

    public enum UiLayers {MAIN, UPGRADES, UNITS, GAS;}
    public UiLayers currentLayerName;
    public UILayer currentLayer;

    public MainLayer mainLayer;
    public UnitPurchaseLayer unitPurchaseLayer;
    public UpgradesLayer upgradesLayer;
    public GasLayer gasLayer;

    public LivesAnimation livesAnimation;
    public WaveTimer waveTimer;

    private ImageButton infoButton;
    private ImageButton backButton;

    public GameScreen gameScreenRef;


    public UI(GameScreen gameScreen) {
        this.gameScreenRef = gameScreen;

        bgTR.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        glassTR.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        bg = new Image(bgTR);
        glass = new Image(glassTR);

        virtualWidth = gameScreenRef.virutalWidth;
        layersScale = virtualWidth / bg.getWidth();
        BG_SAFE_HEIGHT_SCALED = BG_SAFE_HEIGHT*layersScale;
        stage = new Stage();
        gameScreenRef.inputMultiplexer.addProcessor(stage);


        GDXUtilily.scale(bg, layersScale);
        stage.addActor(bg);

        createFonts();

        recalculateMoneyTextBounds();

        createButtons();

        createLivesBar();

        waveTimer = new WaveTimer(this);

        createLayers();

        setUiLayer(UiLayers.UNITS);

    }

    private void createLayers() {
        mainLayer = new MainLayer(this);
        upgradesLayer = new UpgradesLayer(this);
        gasLayer = new GasLayer(this);
        unitPurchaseLayer = new UnitPurchaseLayer(this);
    }

    private void createLivesBar() {
        livesAnimation = new LivesAnimation(this);
        livesAnimation.setScale(layersScale);
        livesAnimation.setCenterPos(bg.getWidth() * 0.5f, bg.getHeight() * 0.605f);

        GDXUtilily.scale(glass, layersScale);
        GDXUtilily.setPosCentr(glass, bg.getWidth() * 0.5f, bg.getHeight() * 0.609f);
        //raw drawing, no attaching to the stage
    }

    private void createButtons() {
        TextureAtlas.AtlasRegion infoButtonAR = textureAtlas.findRegion("info_button");
        TextureAtlas.AtlasRegion infoButton2AR = textureAtlas.findRegion("info_button2");

        TextureAtlas.AtlasRegion backButtonAR = textureAtlas.findRegion("back_button");
        TextureAtlas.AtlasRegion backButton2AR = textureAtlas.findRegion("back_button2");

        infoButton = new ImageButton(new TextureRegionDrawable(infoButtonAR), new TextureRegionDrawable(infoButton2AR));
        backButton = new ImageButton(new TextureRegionDrawable(backButtonAR), new TextureRegionDrawable(backButton2AR));

        ClickListener infoButtonListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("info button");
            }
        };

        ClickListener backButtonListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("back button");
                setUiLayer(UiLayers.MAIN);
            }
        };

        infoButton.addListener(infoButtonListener);
        backButton.addListener(backButtonListener);

        GDXUtilily.scale(infoButton, layersScale);
        GDXUtilily.scale(backButton, layersScale);

        infoButton.setPosition(0, bg.getHeight() * 0.58f);
        backButton.setPosition(bg.getWidth() - backButton.getWidth(), bg.getHeight() * 0.58f);

        stage.addActor(infoButton);
        stage.addActor(backButton);

        infoButton.setVisible(false);
        backButton.setVisible(false);

    }

    private void createFonts() {
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

        uiBatch.begin();
        livesAnimation.draw(deltaTime, uiBatch);
        glass.draw(uiBatch, 1f);
        moneyFont.draw(uiBatch, String.valueOf(Player.money), bg.getWidth() * 0.49f - moneyTextWidth / 2, bg.getHeight() * 0.79f + moneyTextHeight / 2);
        waveTimer.draw(deltaTime, uiBatch);
        uiBatch.end();

        if (currentLayer.equals(unitPurchaseLayer)) {
            unitPurchaseLayer.draw(deltaTime);
        }

    }

    public void recalculateMoneyTextBounds() {
        moneyTextWidth = moneyFont.getBounds(String.valueOf(Player.money)).width;
        moneyTextHeight = moneyFont.getBounds(String.valueOf(Player.money)).height;
    }

    public float getBgHeight() {
        return BG_SAFE_HEIGHT * layersScale;
    }

    public void setUiLayer(UiLayers layer) {
        if (layer != currentLayerName) {
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
            infoButton.setVisible(!currentLayerName.equals(UiLayers.MAIN));
            backButton.setVisible(!currentLayerName.equals(UiLayers.MAIN));

        }
    }
}
