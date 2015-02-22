package com.interdev.dstrike.screens.game.ui.layers.upgrades;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.interdev.dstrike.screens.game.ui.UI;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;

public class UpgradesLayer implements UILayer {

    private Stage stage;
    private UI ui;

    public UpgradesLayer(final UI ui, final Image bg, float layerScale) {
        this.ui = ui;
        this.stage = ui.stage;

    }

    @Override
    public void setVisible(boolean vis) {

    }
}
