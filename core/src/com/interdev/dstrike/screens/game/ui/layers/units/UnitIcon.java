package com.interdev.dstrike.screens.game.ui.layers.units;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.interdev.dstrike.screens.Utils.GDXUtilily;
import com.interdev.dstrike.screens.game.units.UnitValues;
import com.interdev.dstrike.screens.game.ui.UI;

public class UnitIcon extends Actor {
    private UI ui;

    private int price;
    private String priceText;

    private float textWidth;
    private float textHeight;
    public float scale = 1f;

    public Image icon;

    public UnitIcon(int num, Group iconsGroup, UI ui) {
        this.ui = ui;
        price = UnitValues.getByType(num).price;
        priceText = String.valueOf(price);
        textWidth = ui.iconsFont.getBounds(priceText).width;
        textHeight = ui.iconsFont.getBounds(priceText).height;

        icon = new Image(ui.textureAtlas.findRegion(UnitValues.getByType(num).iconTexturePath));
        GDXUtilily.scale(icon, ui.layersScale);

        setPosition(icon.getWidth() * (1 - SelectionSystem.ICONS_OFFSET) * num, 0);
        iconsGroup.addActor(this);
    }

    public float getWidthScaled() {
        return icon.getWidth()*scale;
    }

    public void setPosition(float x, float y) {
        icon.setPosition(x, y);
      //  setBounds(icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight());

    }

    public void setScale(float scale) {
        this.scale = scale;
        icon.setScale(scale);
        setBounds(icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight());
        icon.setOrigin(0, icon.getHeight() / 2);
        //ui.iconsFont.setScale();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        icon.draw(batch, parentAlpha);
        ui.iconsFont.setScale(scale);
        ui.iconsFont.draw(batch, priceText, icon.getX() + getWidthScaled() / 2 - scale * textWidth / 2, icon.getY() + icon.getHeight() * 0.21f / scale + scale * textHeight / 2);
    }

}
