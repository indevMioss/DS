package com.interdev.dstrike.screens.Utils;

/**
 * Created by Evg256 on 05.03.2015.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


public class TextureButton extends ImageButton {

    public TextureButton(Texture texture_up, Texture texture_down) {
        super(new SpriteDrawable(new Sprite(texture_up)), new SpriteDrawable(new Sprite(texture_down)));
    }

    public TextureButton(Texture texture) {
        super(new SpriteDrawable(new Sprite(texture)));
    }
}

