package com.interdev.dstrike.screens.game.ui.layers.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.interdev.dstrike.screens.GDXUtilily;
import com.interdev.dstrike.screens.game.ui.UI;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;

public class MainLayer implements UILayer {

    private Stage stage;

    private final ClickListener upgradesButtonListener;
    private final ClickListener unitsButtonListener;
    private final ClickListener gasButtonListener;

    public ImageButton upgradesButton;
    public ImageButton unitsButton;
    public ImageButton gasButton;


    public MainLayer(final UI ui) {
        this.stage = ui.stage;

        TextureRegion upgradesButtonTR = new TextureRegion(new Texture(Gdx.files.internal("ui/upgrades_button.png")));
        TextureRegion upgradesButtonTR2 = new TextureRegion(new Texture(Gdx.files.internal("ui/upgrades_button2.png")));
        TextureRegion unitsButtonTR = new TextureRegion(new Texture(Gdx.files.internal("ui/units_button.png")));
        TextureRegion unitsButtonTR2 = new TextureRegion(new Texture(Gdx.files.internal("ui/units_button2.png")));
        TextureRegion gasButtonTR = new TextureRegion(new Texture(Gdx.files.internal("ui/gas_button.png")));
        TextureRegion gasButtonTR2 = new TextureRegion(new Texture(Gdx.files.internal("ui/gas_button2.png")));

        upgradesButtonTR.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        upgradesButtonTR2.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        unitsButtonTR.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        unitsButtonTR2.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gasButtonTR.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gasButtonTR2.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        upgradesButton = new ImageButton(new TextureRegionDrawable(upgradesButtonTR), new TextureRegionDrawable(upgradesButtonTR2));
        unitsButton = new ImageButton(new TextureRegionDrawable(unitsButtonTR), new TextureRegionDrawable(unitsButtonTR2));
        gasButton = new ImageButton(new TextureRegionDrawable(gasButtonTR), new TextureRegionDrawable(gasButtonTR2));

        upgradesButtonListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ui.setUiLayer(UI.UiLayers.UPGRADES);
            }
        };

        unitsButtonListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ui.setUiLayer(UI.UiLayers.UNITS);
            }
        };

        gasButtonListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ui.setUiLayer(UI.UiLayers.GAS);
            }
        };

        GDXUtilily.scale(upgradesButton, ui.layersScale/2);
        GDXUtilily.scale(unitsButton, ui.layersScale/2);
        GDXUtilily.scale(gasButton, ui.layersScale/2);

        GDXUtilily.setPosCentr(upgradesButton, ui.bg.getWidth() * 0.18f, ui.bg.getHeight() * 0.27f);
        GDXUtilily.setPosCentr(unitsButton, ui.bg.getWidth() * 0.5f, ui.bg.getHeight() * 0.27f);
        GDXUtilily.setPosCentr(gasButton, ui.bg.getWidth() * 0.82f, ui.bg.getHeight() * 0.27f);

        upgradesButton.setVisible(false);
        unitsButton.setVisible(false);
        gasButton.setVisible(false);

        stage.addActor(upgradesButton);
        stage.addActor(unitsButton);
        stage.addActor(gasButton);

    }

    @Override
    public void setVisible(boolean visible) {
        upgradesButton.setVisible(visible);
        unitsButton.setVisible(visible);
        gasButton.setVisible(visible);

        if (visible) {
            upgradesButton.addListener(upgradesButtonListener);
            unitsButton.addListener(unitsButtonListener);
            gasButton.addListener(gasButtonListener);
        } else {
            upgradesButton.clearListeners();
            unitsButton.clearListeners();
            gasButton.clearListeners();
        }
    }
}
