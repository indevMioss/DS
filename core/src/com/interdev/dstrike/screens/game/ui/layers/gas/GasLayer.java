package com.interdev.dstrike.screens.game.ui.layers.gas;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.interdev.dstrike.screens.game.ui.UI;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;

public class GasLayer implements UILayer {
    private UI ui;
    private Stage stage;

    public GasLayer(final UI ui) {
        this.ui = ui;
        this.stage = ui.stage;
    }

    @Override
    public void setVisible(boolean vis) {

    }
}
