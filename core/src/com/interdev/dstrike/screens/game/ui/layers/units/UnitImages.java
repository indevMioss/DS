package com.interdev.dstrike.screens.game.ui.layers.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.IntMap;
import com.interdev.dstrike.screens.game.UnitValues;

public class UnitImages {

    private IntMap<Image> imagesMap;

    public UnitImages() {
        imagesMap = new IntMap<Image>();
    }

    public Image getUnitImage(short type) {
        if (!imagesMap.containsKey(type)) {
            Texture texture = new Texture(Gdx.files.internal(UnitValues.getByType(type).texturePath));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Image unit = new Image(texture);
            imagesMap.put(type, unit);
        }
        return imagesMap.get(type);
    }
}

