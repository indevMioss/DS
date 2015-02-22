package com.interdev.dstrike.screens.game.ui.layers.gas;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.interdev.dstrike.screens.game.ui.UI;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;

public class GasLayer implements UILayer {
    private UI ui;
    private Stage stage;

    public GasLayer(final UI ui, final Image bg, float layerScale) {
        this.ui = ui;
        this.stage = ui.stage;
    }

    @Override
    public void setVisible(boolean vis) {

    }
}
