package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.IntMap;
import com.interdev.dstrike.screens.game.UnitValues;

public class UnitImages {

    private Stage stage;
    private IntMap<Image> imagesMap;

    public UnitImages(Stage stage) {
        this.stage = stage;
        imagesMap = new IntMap<Image>();
    }

    public Image getUnitImage(short type) {
        if (!imagesMap.containsKey(type)) {
            Texture texture = new Texture(Gdx.files.internal(UnitValues.getByType(type).texturePath));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Image unit = new Image(texture);
            stage.addActor(unit);
            imagesMap.put(type, unit);
        }
        return imagesMap.get(type);
    }
}

